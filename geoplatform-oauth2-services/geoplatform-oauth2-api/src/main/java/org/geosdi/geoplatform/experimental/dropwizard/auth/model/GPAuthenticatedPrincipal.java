/*
 *  geo-platform
 *  Rich webgis framework
 *  http://geo-platform.org
 * ====================================================================
 *
 * Copyright (C) 2008-2014 geoSDI Group (CNR IMAA - Potenza - ITALY).
 *
 * This program is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by 
 * the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version. This program is distributed in the 
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details. You should have received a copy of the GNU General 
 * Public License along with this program. If not, see http://www.gnu.org/licenses/ 
 *
 * ====================================================================
 *
 * Linking this library statically or dynamically with other modules is 
 * making a combined work based on this library. Thus, the terms and 
 * conditions of the GNU General Public License cover the whole combination. 
 * 
 * As a special exception, the copyright holders of this library give you permission 
 * to link this library with independent modules to produce an executable, regardless 
 * of the license terms of these independent modules, and to copy and distribute 
 * the resulting executable under terms of your choice, provided that you also meet, 
 * for each linked independent module, the terms and conditions of the license of 
 * that module. An independent module is a module which is not derived from or 
 * based on this library. If you modify this library, you may extend this exception 
 * to your version of the library, but you are not obligated to do so. If you do not 
 * wish to do so, delete this exception statement from your version. 
 *
 */
package org.geosdi.geoplatform.experimental.dropwizard.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.geosdi.geoplatform.support.jackson.GPJacksonSupport;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class GPAuthenticatedPrincipal implements Serializable, Principal {

    private static final long serialVersionUID = 116137119912701030L;

    @JsonIgnore
    private final static ObjectMapper mapper = new GPJacksonSupport().getDefaultMapper();

    private String name;
    private Collection<String> roles;
    private Collection<String> groups;
    private boolean adminPrincipal;
    /*
     * Extra attributes, depending on the authentication implementation. Note that we only support String - String attributes as we
     * need to be able to persist the Principal generically
     */
    private Map<String, String> attributes;

    public GPAuthenticatedPrincipal() {
        super();
    }

    public GPAuthenticatedPrincipal(String username) {
        this(username, new ArrayList<String>());
    }

    public GPAuthenticatedPrincipal(String username, Collection<String> roles) {
        this(username, roles, new HashMap<String, String>());
    }

    public GPAuthenticatedPrincipal(String username, Collection<String> roles,
            Map<String, String> attributes) {
        this(username, roles, attributes, new ArrayList<String>());
    }

    public GPAuthenticatedPrincipal(String username, Collection<String> roles,
            Map<String, String> attributes, Collection<String> groups) {
        this(username, roles, attributes, groups, false);
    }

    public GPAuthenticatedPrincipal(String username, Collection<String> roles,
            Map<String, String> attributes, Collection<String> groups,
            boolean adminPrincipal) {
        this.name = username;
        this.roles = roles;
        this.attributes = attributes;
        this.groups = groups;
        this.adminPrincipal = adminPrincipal;
    }

    /**
     * @return the roles
     */
    public Collection<String> getRoles() {
        return roles;
    }

    /**
     * @return the attributes
     */
    public Map<String, String> getAttributes() {
        return attributes;
    }

    /**
     * Get the given attribute.
     *
     * @param key the attribute key to get.
     * @return String value if attribute found. Null if attribute not found or
     * no attributes at all.
     */
    public String getAttribute(String key) {
        return (attributes != null) ? attributes.get(key) : null;
    }

    public void addAttribute(String key, String value) {
        if (attributes == null) {
            attributes = new HashMap<>();
        }
        attributes.put(key, value);
    }

    public void addGroup(String name) {
        if (groups == null) {
            groups = new ArrayList<>();
        }
        groups.add(name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.security.Principal#getName()
     */
    @Override
    public String getName() {
        return name;
    }

    @JsonIgnore
    public String getDisplayName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(Collection<String> roles) {
        this.roles = roles;
    }

    /**
     * @param attributes the attributes to set
     */
    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public Collection<String> getGroups() {
        return groups;
    }

    public void setGroups(Collection<String> groups) {
        this.groups = groups;
    }

    @JsonIgnore
    public boolean isGroupAware() {
        return !CollectionUtils.isEmpty(groups);
    }

    public boolean isAdminPrincipal() {
        return adminPrincipal;
    }

    public void setAdminPrincipal(boolean adminPrincipal) {
        this.adminPrincipal = adminPrincipal;
    }

    @Override
    public String toString() {
        return getClass().getName() + " {" + "name = " + name
                + ", roles = " + roles + ", groups = " + groups
                + ", attributes = " + attributes + '}';
    }

    @JsonIgnore
    public String serialize() {
        try {
            return mapper.writeValueAsString(this);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Unable to serialize Principal:" + toString(), e);
        }
    }

    @JsonIgnore
    public static GPAuthenticatedPrincipal deserialize(String json) {
        try {
            return mapper.readValue(json, GPAuthenticatedPrincipal.class);
        } catch (IOException e) {
            throw new RuntimeException("Unable to serialize Principal:" + json,
                    e);
        }
    }

}
