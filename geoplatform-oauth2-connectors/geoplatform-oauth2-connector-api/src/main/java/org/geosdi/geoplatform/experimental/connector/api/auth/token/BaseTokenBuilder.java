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
package org.geosdi.geoplatform.experimental.connector.api.auth.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import java.io.IOException;
import java.util.Objects;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.commons.codec.binary.Base64;
import org.geosdi.geoplatform.experimental.connector.api.auth.responce.AccessTokenResponse;
import org.geosdi.geoplatform.experimental.connector.api.settings.OAuth2ClientSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public abstract class BaseTokenBuilder implements OAuth2TokenBuilder {

    private static final Logger logger = LoggerFactory.getLogger(
            BaseTokenBuilder.class);
    //
    private final OAuth2ClientSettings clientSettings;
    private final String auth;
    private Client client;
    private ObjectMapper mapper;

    protected BaseTokenBuilder(OAuth2ClientSettings theClientSettings) {
        this(theClientSettings, defaultClient(), defaultMapper());
    }

    protected BaseTokenBuilder(OAuth2ClientSettings theClientSettings,
            Client theClient) {
        this(theClientSettings, theClient, defaultMapper());
    }

    protected BaseTokenBuilder(OAuth2ClientSettings theClientSettings,
            ObjectMapper theMapper) {
        this(theClientSettings, defaultClient(), theMapper);
    }

    protected BaseTokenBuilder(OAuth2ClientSettings theClientSettings,
            Client theClient, ObjectMapper theMapper) {
        Preconditions.checkNotNull(theClient, "The client must not be null.");
        Preconditions.checkNotNull(theMapper, "The ObjectMapper must "
                + "not be null.");
        Preconditions.checkNotNull(theClientSettings, "The OAuthClientSettings "
                + "must not be null.");

        this.client = theClient;
        this.mapper = theMapper;
        this.clientSettings = theClientSettings;
        this.auth = "Basic ".concat(new String(Base64.encodeBase64(
                clientSettings.getClientId().concat(":")
                .concat(clientSettings.getClientSecret()).getBytes())));
    }

    @Override
    public final AccessTokenResponse createToken() throws CreateTokenException {
        ClientResponse clientResponse = this.client
                .resource(this.clientSettings.getAccessTokenURL())
                .header(HttpHeaders.AUTHORIZATION, this.auth)
                .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                .post(ClientResponse.class, createFormData());

        try {
            AccessTokenResponse accessTokenResponse = this.mapper
                    .readValue(clientResponse.getEntityInputStream(),
                            AccessTokenResponse.class);
            if (accessTokenResponse == null) {
                throw new CreateTokenException(
                        "The AccessTokenResponse is null.");
            }
            return accessTokenResponse;
        } catch (IOException e) {
            logger.error("Could not parse ClientResponse - " + e.getMessage());
            throw new CreateTokenException("Could not parse ClientResponse : "
                    + clientResponse, e);
        }
    }

    @Override
    public final void destroy() throws Exception {
        logger.debug("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@DISPOSING : {}\n", this);
        this.client.destroy();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.auth);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BaseTokenBuilder other = (BaseTokenBuilder) obj;
        return Objects.equals(this.auth, other.auth);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {" + "clientSettings = "
                + clientSettings + '}';
    }

    protected abstract MultivaluedMap<String, String> createFormData();

    private static Client defaultClient() {
        return Client.create();
    }

    private static ObjectMapper defaultMapper() {
        return new ObjectMapper();
    }
}
