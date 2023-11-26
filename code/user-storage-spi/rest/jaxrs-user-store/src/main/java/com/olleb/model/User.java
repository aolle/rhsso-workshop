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
package com.olleb.model;

/**
 * @author Àngel Ollé Blázquez
 */
public class User {

    private final String userId;

    private final String username;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String password;

    private User(UserBuilder userBuilder) {
        this.userId = userBuilder.userId;
        this.username = userBuilder.username;
        this.email = userBuilder.email;
        this.firstName = userBuilder.firstName;
        this.lastName = userBuilder.lastName;
        this.password = userBuilder.password;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static class UserBuilder {
        private final String userId;
        private final String username;

        private String email;
        private String firstName;
        private String lastName;
        private String password;

        public User build() {
            return new User(this);
        }

        public UserBuilder(String userId, String username) {
            this.userId = userId;
            this.username = username;
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

        public UserBuilder setPassword(String password){
            this.password = password;
            return this;
        }
    }

}
