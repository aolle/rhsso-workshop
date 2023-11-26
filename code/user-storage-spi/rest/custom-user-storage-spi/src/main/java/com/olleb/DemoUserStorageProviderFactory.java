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

import org.keycloak.Config.Scope;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;

/**
 * @author Àngel Ollé Blázquez
 */
public class DemoUserStorageProviderFactory implements UserStorageProviderFactory<DemoUserStorageProvider> {

    private ExternalUsersService service;

    @Override
    public DemoUserStorageProvider create(KeycloakSession keycloakSession, ComponentModel componentModel) {
        return new DemoUserStorageProvider(keycloakSession, componentModel, service);
    }

    @Override
    public String getId() {
        return "demo-user-provider";
    }

    @Override
    public void init(Scope config) {
        String host = Optional
                .ofNullable(System.getenv("USER_STORAGE_CUSTOM_SPI_TARGET_HOST"))
                .orElse(config.get("target-host"));

        service = new ExternalUsersService(host);
    }

}
