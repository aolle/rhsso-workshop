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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.jboss.logging.Logger;
import org.keycloak.component.ComponentModel;
import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;

/**
 * see the internal org.keycloak.models.jpa.UserAdapter
 * see the internal org.keycloak.models.jpa.entities.UserEntity
 */
public class DemoUserStorageProvider
        implements UserStorageProvider, UserLookupProvider, UserQueryProvider, CredentialInputValidator {

    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());

    private final KeycloakSession keycloakSession;
    private final ComponentModel componentModel;

    EntityManager entityManager;

    public DemoUserStorageProvider(KeycloakSession keycloakSession, ComponentModel componentModel) {
        this.keycloakSession = keycloakSession;
        this.componentModel = componentModel;
        entityManager = keycloakSession.getProvider(JpaConnectionProvider.class, "jpa-user-store").getEntityManager();
    }

    @Override
    public void close() {
        // ok
    }

    // for demo simplification, the search will return all the users

    @Override
    public List<UserModel> getUsers(RealmModel realm) {
       List<ExternalUser> users= entityManager.createNamedQuery("findAll", ExternalUser.class)
        .getResultList();
        return users.stream().map(u -> new ExternalUserAdapter(keycloakSession, realm, componentModel, u)).collect(Collectors.toList());
    }

    @Override
    public List<UserModel> getUsers(RealmModel realm, int firstResult, int maxResults) {
        return getUsers(realm);
    }

    @Override
    public List<UserModel> searchForUser(String search, RealmModel realm) {
        return getUsers(realm);
    }

    @Override
    public List<UserModel> searchForUser(String search, RealmModel realm, int firstResult, int maxResults) {
        return searchForUser(search, realm);
    }

    @Override
    public List<UserModel> searchForUser(Map<String, String> params, RealmModel realm) {
        // ignore params
        return searchForUser(params, realm, 0, Integer.MAX_VALUE);
    }

    @Override
    public List<UserModel> searchForUser(Map<String, String> params, RealmModel realm, int firstResult,
            int maxResults) {
        return getUsers(realm, firstResult, maxResults);
    }

    @Override
    public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group) {
        return Collections.emptyList();
    }

    @Override
    public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group, int firstResult, int maxResults) {
        return Collections.emptyList();
    }

    @Override
    public List<UserModel> searchForUserByUserAttribute(String attrName, String attrValue, RealmModel realm) {
        return Collections.emptyList();
    }

    @Override
    public UserModel getUserById(String id, RealmModel realmModel) {
        // StorageId is used for referencing users that are stored outside of Keycloak
        String externalId = StorageId.externalId(id);
        ExternalUser externalUser = entityManager.find(ExternalUser.class, externalId);
        return new ExternalUserAdapter(keycloakSession, realmModel, componentModel, externalUser);
    }

    @Override
    public UserModel getUserByUsername(String username, RealmModel realmModel) {
        ExternalUser externalUser = entityManager.createNamedQuery("findUserByUsername", ExternalUser.class)
                .setParameter("username", username).getResultList().get(0);
        return new ExternalUserAdapter(keycloakSession, realmModel, componentModel, externalUser);
    }

    @Override
    public UserModel getUserByEmail(String email, RealmModel realmModel) {
        ExternalUser externalUser = entityManager.createNamedQuery("findUserByEmail", ExternalUser.class)
                .setParameter("email", email).getResultList().get(0);
        return new ExternalUserAdapter(keycloakSession, realmModel, componentModel, externalUser);
    }

    @Override
    public boolean supportsCredentialType(String credentialType) {
        return credentialType.equals(PasswordCredentialModel.TYPE);
    }

    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
        return supportsCredentialType(credentialType);
    }

    @Override
    public boolean isValid(RealmModel realm, UserModel user, CredentialInput credentialInput) {
        if (!supportsCredentialType(credentialInput.getType())) {
            return false;
        }
        StorageId storageId = new StorageId(user.getId());
        final ExternalUser externalUser = entityManager.createNamedQuery("findUserById", ExternalUser.class)
                .setParameter("id", storageId.getExternalId()).getResultList().get(0);
        if (externalUser != null) {
            String password = externalUser.getPassword();
            return password != null && password.equals(credentialInput.getChallengeResponse());
        }
        return false;
    }

    private Optional<ExternalUser> findByUsername(Set<ExternalUser> users, String username) {
        return users.stream().filter(u -> u.getUsername().equals(username)).findFirst();
    }

    private Optional<ExternalUser> findByEmail(Set<ExternalUser> users, String email) {
        return users.stream().filter(u -> u.getEmail().equals(email)).findFirst();
    }

}
