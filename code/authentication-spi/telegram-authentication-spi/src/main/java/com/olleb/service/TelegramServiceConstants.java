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
package com.olleb.service;

import java.util.Optional;

public final class TelegramServiceConstants {

    static final String TELEGRAM_BOT_USERNAME = Optional.ofNullable(System.getenv("TELEGRAM_BOT_USERNAME"))
            .orElseThrow(IllegalArgumentException::new);

    static final String TELEGRAM_TOKEN = Optional.ofNullable(System.getenv("TELEGRAM_TOKEN"))
            .orElseThrow(IllegalArgumentException::new);

    static final String TELEGRAM_BASE_URL = "https://api.telegram.org/bot{apitoken}";
    static final int SUBSCRIPTIONCODE_LENGTH = 10;

    private TelegramServiceConstants() {
    }
}
