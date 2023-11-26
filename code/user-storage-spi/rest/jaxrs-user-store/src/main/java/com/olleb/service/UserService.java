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
package com.olleb.service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import com.olleb.model.User;
import com.olleb.model.User.UserBuilder;

/**
 * @author Àngel Ollé Blázquez
 */
@ApplicationScoped
public class UserService {

    private static final Map<String, User> userMap = Map.of(
            "1", new UserBuilder("1", "nata")
                    .setFirstName("Nata")
                    .setLastName("Natilla")
                    .setEmail("nata@example.com")
                    .setPassword("natapassword")
                    .build(),
            "2", new UserBuilder("2", "freud")
                    .setFirstName("Freud")
                    .setLastName("Redhead")
                    .setEmail("freud@example.com")
                    .setPassword("freudpassword")
                    .build(),
            "3", new UserBuilder("3", "mao")
                    .setFirstName("Mao")
                    .setLastName("Meow")
                    .setEmail("mao@example.com")
                    .setPassword("maopassword")
                    .build(),
            "4", new UserBuilder("4", "dark")
                    .setFirstName("Dark")
                    .setLastName("Butcher")
                    .setEmail("dark@example.com")
                    .setPassword("darkpassword")
                    .build(),
            "5", new UserBuilder("5", "abel")
                    .setFirstName("Abel")
                    .setLastName("Miiii")
                    .setEmail("abel@example.com")
                    .setPassword("abelpassword")
                    .build(),
            "6", new UserBuilder("6", "flash")
                    .setFirstName("Flash")
                    .setLastName("Jr")
                    .setEmail("flash@example.com")
                    .setPassword("flashpassword")
                    .build()

    );

    public Set<User> getUsers() {
        return userMap.values().stream().collect(Collectors.toSet());
    }

    public Optional<User> getUserById(final String id) {
        return Optional.of(userMap.get(id));
    }

}
