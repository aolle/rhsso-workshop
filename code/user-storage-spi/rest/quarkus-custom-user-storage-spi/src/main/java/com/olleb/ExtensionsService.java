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

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

/**
 * @author Àngel Ollé Blázquez
 */
@ApplicationScoped
@RegisterRestClient(configKey = "com.olleb.extensions.api")
@ClientHeaderParam(name = "Authorization", value = "{token}")
public interface ExtensionsService {

    @GET
    Set<ExternalUser> getUsers();

    @GET
    @Path("{id}")
    ExternalUser getUserById(@PathParam("id") String userId);

    default String token() {
        return ConfigProvider.getConfig().getOptionalValue("extensions.token", String.class).orElseThrow();
    }

}
