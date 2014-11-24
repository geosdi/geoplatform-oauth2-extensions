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
package org.geosdi.geoplatform.experimental.dropwizard.auth;

import com.google.common.base.Optional;
import com.sun.jersey.api.client.Client;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import java.io.IOException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.map.ObjectMapper;
import org.geosdi.geoplatform.experimental.dropwizard.auth.model.GPAuthenticatedPrincipal;
import org.geosdi.geoplatform.experimental.dropwizard.auth.responce.VerifyTokenResponse;
import org.geosdi.geoplatform.experimental.dropwizard.config.AuthConfig;
import org.geosdi.geoplatform.experimental.dropwizard.config.GPServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class GPOAuthAuthenticator implements
        Authenticator<String, GPAuthenticatedPrincipal> {

    static final Logger logger = LoggerFactory.getLogger(
            GPOAuthAuthenticator.class);
    //
    private static final String ACCESS_TOKEN_KEY = "?access_token=%s";
    //
    private final Client client;
    private final String authorizationServerUrl;
    private final String authorizationValue;
    private final ObjectMapper mapper;

    public GPOAuthAuthenticator(GPServiceConfig conf, Client theClient,
            ObjectMapper theMapper) {
        if (conf == null) {
            throw new IllegalArgumentException("The GPServiceConfig must not "
                    + "be null");
        }

        if (theClient == null) {
            throw new IllegalArgumentException("The Client must not be null");
        }

        if (theMapper == null) {
            throw new IllegalArgumentException("The ObjectMapper must not"
                    + " be null");
        }

        AuthConfig auth = conf.getAuthConfig();
        authorizationServerUrl = auth.getAuthorizationServerUrl();
        authorizationValue = "Basic ".concat(new String(Base64.encodeBase64(
                auth.getKey().concat(":").concat(auth.getSecret()).getBytes())));
        this.client = theClient;
        this.mapper = theMapper;
    }

    @Override
    public Optional<GPAuthenticatedPrincipal> authenticate(String token)
            throws AuthenticationException {
        String json = client.resource(String.format(
                authorizationServerUrl.concat(ACCESS_TOKEN_KEY), token))
                .header(HttpHeaders.AUTHORIZATION, authorizationValue).accept(
                        MediaType.APPLICATION_JSON).get(String.class);
        final VerifyTokenResponse response;
        try {
            response = mapper.readValue(json, VerifyTokenResponse.class);
        } catch (IOException e) {
            logger.error("Could not parse JSON - " + e.getMessage());
            throw new AuthenticationException("Could not parse JSON: " + json, e);
        }
        return Optional.fromNullable(response.getPrincipal());
    }
}