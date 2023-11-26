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
package com.olleb.vault;

import org.keycloak.Config.Scope;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.vault.VaultProvider;
import org.keycloak.vault.VaultProviderFactory;

import com.cyberark.conjur.api.Conjur;
import com.cyberark.conjur.api.Credentials;

/**
 * @author Àngel Ollé Blázquez
 */
public class ConjurVaultProviderFactory implements VaultProviderFactory {

    // private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());

    private static final String PROVIDER_ID = "conjur-vault";

    private Credentials credentials;

    @Override
    public VaultProvider create(KeycloakSession session) {
        Conjur conjur = new Conjur(credentials);
        return new ConjurVaultProvider(conjur);
    }

    @Override
    public void init(Scope config) {
        credentials = Credentials.fromSystemProperties();
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        // no-op
    }

    @Override
    public void close() {
        // no-op
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

}
