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
package com.olleb;

import java.util.Optional;
import java.util.Set;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;

/**
 * @author Àngel Ollé Blázquez
 */
public class ExternalUsersService {

    private String endpoint;

    public ExternalUsersService(String host) {
        this.endpoint = "http://" + Optional.ofNullable(host).orElse("localhost:8081") + "/api/v1/users";
    }

    public Set<ExternalUser> getUsers() {
        GenericType<Set<ExternalUser>> externalUsersListType = new GenericType<>() {
        };
        return ClientBuilder.newClient().target(endpoint).request().get(externalUsersListType);
    }

    public ExternalUser getUserById(String id) {
        return ClientBuilder.newClient().target(endpoint).path("{id}").resolveTemplate("id", id).request()
                .get(ExternalUser.class);
    }

}
