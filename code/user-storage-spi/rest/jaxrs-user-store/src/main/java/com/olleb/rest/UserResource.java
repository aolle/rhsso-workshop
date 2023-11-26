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
package com.olleb.rest;

import java.lang.invoke.MethodHandles;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

import com.olleb.model.User;
import com.olleb.service.UserService;

import io.smallrye.common.constraint.NotNull;

/**
 * @author Àngel Ollé Blázquez
 */
@Path("api/v1/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
    
    @Inject
    UserService userService;

    @GET
    public Set<User> getUsers() {
        LOGGER.info("getUsers");
        return userService.getUsers();
    }

    @GET
    @Path("{id}")
    public User getUserById(@NotNull @PathParam("id") final String id) {
        LOGGER.info("getUserById: " + id);
        return userService.getUserById(id).orElseThrow(NotFoundException::new);
    }

}
