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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author Àngel Ollé Blázquez
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TelegramMessage {

    private String id;
    private String username;
    private String text;
    private String subscriptionCode;

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubscriptionCode() {
        return subscriptionCode;
    }

    public void setSubscriptionCode(String subscriptionCode) {
        this.subscriptionCode = subscriptionCode;
    }

    @JsonProperty("message")
    private void unpackNameFromNestedObject(JsonNode json) {
        id = unpackChatValueFromMessageChat(json, "id");
        username = unpackChatValueFromMessageChat(json, "username");
        text = json.get("text").asText();
    }

    private String unpackChatValueFromMessageChat(JsonNode json, String property) {
        return json.get("chat").get(property).asText();
    }

    @Override
    public String toString() {
        return "TelegramMessage [id=" + id + ", username=" + username + ", text=" + text + ", subscriptionCode="
                + subscriptionCode + "]";
    }
}
