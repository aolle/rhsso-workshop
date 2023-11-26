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

public final class TelegramRequiredActionConstants {

    static final String PROVIDER_ID = "telegram-otp-required-action";
    static final String DISPLAY_TEXT = "Telegram ID";

    static final String TEMPLATE = "telegram-id-config.ftl";
    static final String TELEGRAM_ID_ATTRIBUTE = "telegram_id";
    static final String TELEGRAM_ID_PROPERTY = "telegram-id-label";
    static final String TELEGRAM_USERNAME_ATTRIBUTE = "telegram_username";
    static final String TELEGRAM_ROLLING_CODE_ATTRIBUTE = "telegram_code";
    static final String TELEGRAM_SECURITY_CODE_ATTRIBUTE = "scode";
    static final String TELEGRAM_MESSAGE_SECURITY_CODE_ATTRIBUTE = "msgcode";
    static final int TELEGRAM_ROLLING_CODE_LENGTH = 6;

    private TelegramRequiredActionConstants() {
        // no-op constructor
    }
}
