/*
 * This file is part of Red Hat Single Sign-On workshop
 * Copyright (C) 2022 Àngel Ollé Blázquez
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.olleb.action;

import java.lang.invoke.MethodHandles;
import java.util.Locale;
import java.util.function.Predicate;

import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;
import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.common.util.SecretGenerator;
import org.keycloak.theme.Theme;

import com.olleb.service.TelegramMessage;
import com.olleb.service.TelegramService;

/**
 * @author Àngel Ollé Blázquez
 */
public class TelegramRequiredAction implements RequiredActionProvider {

    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());

    private static final TelegramService TELEGRAM_SERVICE = TelegramService.getInstance();

    private static final Predicate<String> BLANK = s -> s == null || s.isBlank();

    @Override
    public void evaluateTriggers(RequiredActionContext context) {
        String telegramId = context.getUser().getFirstAttribute(TelegramRequiredActionConstants.TELEGRAM_ID_ATTRIBUTE);
        if (BLANK.test(telegramId)) {
            context.getUser().addRequiredAction(TelegramRequiredActionConstants.PROVIDER_ID);
        }
    }

    @Override
    public void requiredActionChallenge(RequiredActionContext context) {
        try {
            Theme theme = context.getSession().theme().getTheme(Theme.Type.LOGIN);
            String msg = theme.getMessages(Locale.ENGLISH)
                    .getProperty(TelegramRequiredActionConstants.TELEGRAM_ID_PROPERTY);
            String code = SecretGenerator.getInstance().randomString(
                    TelegramRequiredActionConstants.TELEGRAM_ROLLING_CODE_LENGTH,
                    SecretGenerator.DIGITS);

            context.getAuthenticationSession()
                    .setAuthNote(TelegramRequiredActionConstants.TELEGRAM_SECURITY_CODE_ATTRIBUTE, code);

            msg = String.format(msg, code, TelegramService.getTelegramBotUsername());

            Response response = context.form()
                    .setAttribute(TelegramRequiredActionConstants.TELEGRAM_MESSAGE_SECURITY_CODE_ATTRIBUTE, msg)
                    .createForm(TelegramRequiredActionConstants.TEMPLATE);

            context.challenge(response);
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void processAction(RequiredActionContext context) {
        String subscriptionCode = context.getHttpRequest().getDecodedFormParameters()
                .getFirst(TelegramRequiredActionConstants.TELEGRAM_ROLLING_CODE_ATTRIBUTE);

        if (BLANK.test(subscriptionCode)) {
            requiredActionChallenge(context);
            return;
        }

        String code = context.getAuthenticationSession()
                .getAuthNote(TelegramRequiredActionConstants.TELEGRAM_SECURITY_CODE_ATTRIBUTE);
        TelegramMessage message = TELEGRAM_SERVICE.getMessageFromSecurityCode(code);

        if (message != null && message.getSubscriptionCode().equals(subscriptionCode)) {
            String userId = message.getId();
            String username = message.getUsername();

            context.getUser().setSingleAttribute(TelegramRequiredActionConstants.TELEGRAM_ID_ATTRIBUTE, userId);
            context.getUser().setSingleAttribute(TelegramRequiredActionConstants.TELEGRAM_USERNAME_ATTRIBUTE, username);
            context.getUser().removeRequiredAction(TelegramRequiredActionConstants.PROVIDER_ID);

            context.success();
            return;
        }

        requiredActionChallenge(context);
    }

    @Override
    public void close() {
        // no-op
    }

}
