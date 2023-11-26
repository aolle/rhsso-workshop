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

import java.lang.invoke.MethodHandles;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import io.quarkus.runtime.StartupEvent;

/**
 * @author Àngel Ollé Blázquez
 */
@ApplicationScoped
public class StartupListener {

    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());

    @Inject
    @RestClient
    ExtensionsService extensionsService;

    void onStart(@Observes StartupEvent ev) {
        Set<ExternalUser> users = extensionsService.getUsers();
        LOGGER.info(users);
        ExternalUser user = extensionsService.getUserById("1");
        LOGGER.info(user);
    }

}
