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

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.keycloak.vault.DefaultVaultRawSecret;
import org.keycloak.vault.VaultProvider;
import org.keycloak.vault.VaultRawSecret;

import com.cyberark.conjur.api.Conjur;

/**
 * @author Àngel Ollé Blázquez
 */
public class ConjurVaultProvider implements VaultProvider {

    Conjur conjur;

    public ConjurVaultProvider(Conjur conjur) {
        this.conjur = conjur;
    }

    @Override
    public VaultRawSecret obtainSecret(String vaultSecretId) {
        String secret = conjur.variables().retrieveSecret(vaultSecretId);
        ByteBuffer byteBuffer = ByteBuffer.wrap(secret.getBytes(StandardCharsets.UTF_8));
        return DefaultVaultRawSecret.forBuffer(Optional.of(byteBuffer));
    }

    @Override
    public void close() {
        // no-op
    }

}
