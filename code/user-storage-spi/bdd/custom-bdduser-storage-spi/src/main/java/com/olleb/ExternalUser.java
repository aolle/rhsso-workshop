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

import javax.persistence.Id;
import javax.persistence.NamedQuery;

@NamedQuery(name = "findAll", query = "select u from User u")
@NamedQuery(name = "findUserByEmail", query = "select u from User u where u.email = :email")
@NamedQuery(name = "findUserByUsername", query = "select u from User u where u.username = :username")
@NamedQuery(name = "findUserById", query = "select u from User u where u.id = :id")
public class ExternalUser {

    @Id
    private String id;

    private String username;
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

}
