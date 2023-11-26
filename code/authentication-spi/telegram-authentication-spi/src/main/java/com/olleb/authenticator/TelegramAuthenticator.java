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
package com.olleb.authenticator;

import java.lang.invoke.MethodHandles;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.common.util.SecretGenerator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.theme.Theme;

import com.olleb.service.TelegramService;

/**
 * @author Àngel Ollé Blázquez
 */
public class TelegramAuthenticator implements Authenticator {

    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        Map<String, String> config = context.getAuthenticatorConfig().getConfig();
        KeycloakSession keycloakSession = context.getSession();

        String id = context.getUser().getFirstAttribute(TelegramAuthenticatorConstants.TELEGRAM_ID_ATTRIBUTE);

        try {
            int length = Integer.parseInt(config.get(TelegramAuthenticatorConstants.LC_NAME));
            String code = SecretGenerator.getInstance().randomString(length, SecretGenerator.DIGITS);

            // EN only for this demo
            Theme theme = keycloakSession.theme().getTheme(Theme.Type.LOGIN);
            String msg = theme.getMessages(Locale.ENGLISH)
                    .getProperty(TelegramAuthenticatorConstants.TELEGRAM_MSG_PROPERTY_DESC);

            msg = String.format(msg, code);

            TelegramService.getInstance().send(id, msg);
            context.getAuthenticationSession().setAuthNote("code", code);

            Response response = context.form().createForm(TelegramAuthenticatorConstants.TEMPLATE);
            context.challenge(response);

        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        String formCode = context.getHttpRequest().getDecodedFormParameters().getFirst("telegram_code");
        String code = context.getAuthenticationSession().getAuthNote("code");

        if (code == null) {
            return;
        }

        if (formCode.equals(code)) {
            context.success();
        }
    }

    @Override
    public boolean requiresUser() {
        return true;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        String id = user.getFirstAttribute(TelegramAuthenticatorConstants.TELEGRAM_ID_ATTRIBUTE);
        return !(id == null || id.isBlank());
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
        user.addRequiredAction(TelegramAuthenticatorConstants.REQUIRED_ACTION);
    }

    @Override
    public void close() {
        // no-op
    }
}
