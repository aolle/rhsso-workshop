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

public final class TelegramAuthenticatorConstants {

    static final String PROVIDER_ID = "telegram-authenticator";

    static final String REQUIRED_ACTION = "telegram-otp-required-action";
    static final String TEMPLATE = "telegram-otp.ftl";
    static final String TELEGRAM_ID_ATTRIBUTE = "telegram_id";
    static final String TELEGRAM_MSG_PROPERTY_DESC = "telegram-otp-desc";

    static final String DISPLAY_TYPE = "Telegram Authentication";
    static final String REFERENCE_CATEGORY = "otp";
    static final String HELP_TEXT = "OTP Telegram Authentication";

    // length config properties
    static final String LC_NAME = "length";
    static final String LC_LABEL = "Code length";
    static final String LC_HELP_TEXT = "Number of digits of the security code";
    static final int LC_DEFAULT_VALUE = 6;

    private TelegramAuthenticatorConstants() {
    }

}
