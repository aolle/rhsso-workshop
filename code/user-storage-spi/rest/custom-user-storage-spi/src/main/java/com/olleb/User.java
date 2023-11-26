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

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.adapter.AbstractUserAdapter;

/**
 * @author Àngel Ollé Blázquez
 */
public class User extends AbstractUserAdapter {

    private final String username;
    private final String email;
    private final String firstName;
    private final String lastName;

    private User(UserBuilder userBuilder) {
        super(userBuilder.keycloakSession, userBuilder.realmModel, userBuilder.componentModel);
        this.username = userBuilder.username;
        this.email = userBuilder.email;
        this.firstName = userBuilder.firstName;
        this.lastName = userBuilder.lastName;

        if (storageId == null) {
            storageId = new StorageId(userBuilder.componentModel.getId(), userBuilder.externalId);
        }
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    public static class UserBuilder {
        private final KeycloakSession keycloakSession;
        private final RealmModel realmModel;
        private final ComponentModel componentModel;
        private final String username;
        private final String externalId;

        private String email;
        private String firstName;
        private String lastName;

        public User build() {
            return new User(this);
        }

        public UserBuilder(KeycloakSession keycloakSession, RealmModel realmModel, ComponentModel componentModel,
                String username, String externalId) {
            this.keycloakSession = keycloakSession;
            this.realmModel = realmModel;
            this.componentModel = componentModel;
            this.username = username;
            this.externalId = externalId;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
    }
}
