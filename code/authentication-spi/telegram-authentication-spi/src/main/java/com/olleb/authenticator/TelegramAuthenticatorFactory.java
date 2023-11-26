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
package com.olleb.authenticator;

import java.util.List;

import org.keycloak.Config.Scope;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel.Requirement;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

/**
 * @author Àngel Ollé Blázquez
 */
public class TelegramAuthenticatorFactory implements AuthenticatorFactory {

    private static final TelegramAuthenticator TELEGRAM_AUTHENTICATOR = new TelegramAuthenticator();

    private static final List<ProviderConfigProperty> configProperties;

    static {
        configProperties = List.of(
                new ProviderConfigProperty(
                        TelegramAuthenticatorConstants.LC_NAME,
                        TelegramAuthenticatorConstants.LC_LABEL,
                        TelegramAuthenticatorConstants.LC_HELP_TEXT,
                        ProviderConfigProperty.STRING_TYPE,
                        TelegramAuthenticatorConstants.LC_DEFAULT_VALUE));
    }

    @Override
    public Authenticator create(KeycloakSession session) {
        return TELEGRAM_AUTHENTICATOR;
    }

    @Override
    public String getId() {
        return TelegramAuthenticatorConstants.PROVIDER_ID;
    }

    @Override
    public String getDisplayType() {
        return TelegramAuthenticatorConstants.DISPLAY_TYPE;
    }

    @Override
    public String getReferenceCategory() {
        return TelegramAuthenticatorConstants.REFERENCE_CATEGORY;
    }

    @Override
    public boolean isConfigurable() {
        return true;
    }

    @Override
    public Requirement[] getRequirementChoices() {
        return REQUIREMENT_CHOICES;
    }

    @Override
    public boolean isUserSetupAllowed() {
        return true;
    }

    @Override
    public String getHelpText() {
        return TelegramAuthenticatorConstants.HELP_TEXT;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configProperties;
    }

    @Override
    public void init(Scope config) {
        // no-op
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        // no-op
    }

    @Override
    public void close() {
        // no-op
    }

}
