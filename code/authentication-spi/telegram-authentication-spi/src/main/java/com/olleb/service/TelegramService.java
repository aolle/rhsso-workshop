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

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;
import org.keycloak.common.util.SecretGenerator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Àngel Ollé Blázquez
 */
public class TelegramService {

    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());

    private static TelegramService telegramService;

    private static final ObjectMapper mapper = new ObjectMapper();

    // only for demo purposes, can cause memory leak
    private static List<TelegramMessage> messages = new ArrayList<>();

    private static int lastUpdateId = 0;

    // SimpleHttp or JsonSimpleHttp from keycloak can be used instead
    private static final WebTarget target = ClientBuilder
            .newClient()
            .target(TelegramServiceConstants.TELEGRAM_BASE_URL)
            .resolveTemplate("apitoken", TelegramServiceConstants.TELEGRAM_TOKEN);

    private static final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    static {
        Runnable updateTask = () -> {
            String offset = lastUpdateId > 0 ? Integer.toString(lastUpdateId) : "";
            Response response = target.path("getUpdates")
                    .queryParam("offset", offset)
                    .request()
                    .get();
            if (response != null) {
                JsonNode json;
                try {
                    json = mapper.readTree(response.readEntity(String.class)).get("result");
                    if (json.size() > 0) {
                        if (lastUpdateId == 0) {
                            lastUpdateId = json.get(json.size() - 1).get("update_id").intValue();
                        }
                        lastUpdateId++;
                    }

                    messages.addAll(mapper.convertValue(json,
                            new TypeReference<List<TelegramMessage>>() {
                            }));
                } catch (IOException e) {
                    LOGGER.error(e);
                }
            }
            messages.stream().forEach(m -> {
                if (m.getSubscriptionCode() == null) {
                    String subscriptionCode = "S" + SecretGenerator.getInstance().randomString(
                            TelegramServiceConstants.SUBSCRIPTIONCODE_LENGTH,
                            SecretGenerator.ALPHANUM);
                    m.setSubscriptionCode(subscriptionCode);
                    target.path("sendMessage")
                            .queryParam("chat_id", m.getId())
                            .queryParam("text", "SSO: Your enrollment code is: " + subscriptionCode)
                            .request()
                            .get();
                }
            });

        };

        executorService.scheduleAtFixedRate(updateTask, 5, 15, TimeUnit.SECONDS);

        // demo purposes, no graceful needed
        Runtime.getRuntime().addShutdownHook(new Thread(executorService::shutdownNow));
    }

    private TelegramService() {
    }

    public void send(String id, String message) {
        target.path("sendMessage")
                .queryParam("chat_id", id)
                .queryParam("text", message)
                .request()
                .get();
    }

    public TelegramMessage getMessageFromSecurityCode(String code) {
        return messages.stream().filter(m -> m.getText().equals(code)).findAny().orElse(null);
    }

    public static TelegramService getInstance() {
        if (telegramService == null) {
            telegramService = new TelegramService();
        }
        return telegramService;
    }

    public static String getTelegramBotUsername() {
        return TelegramServiceConstants.TELEGRAM_BOT_USERNAME;
    }

}
