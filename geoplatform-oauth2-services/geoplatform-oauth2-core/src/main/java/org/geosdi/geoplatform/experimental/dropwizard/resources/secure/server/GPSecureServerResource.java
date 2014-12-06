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
package org.geosdi.geoplatform.experimental.dropwizard.resources.secure.server;

import io.dropwizard.auth.Auth;
import java.security.Principal;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.geosdi.geoplatform.core.model.GeoPlatformServer;
import org.geosdi.geoplatform.request.server.WSSaveServerRequest;
import org.geosdi.geoplatform.response.ServerDTO;
import org.geosdi.geoplatform.services.rs.path.GPServiceRSPathConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Path(value = GPServiceRSPathConfig.GP_SECURE_SERVER_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Component(value = "secureServerResource")
public class GPSecureServerResource extends BaseServerResource {

    private static final Logger logger = LoggerFactory.getLogger(
            GPSecureServerResource.class);

    @POST
    @Path(value = GPServiceRSPathConfig.INSERT_SERVER_PATH)
    @Override
    public Long insertServer(@Auth Principal principal, GeoPlatformServer server) {
        logger.debug("\n\n@@@@@@@@@@@@@@Executing secure insertServer - "
                + "Principal : {}\n\n", principal.getName());
        return super.insertServer(server);
    }

    @PUT
    @Path(value = GPServiceRSPathConfig.UPDATE_SERVER_PATH)
    @Override
    public Long updateServer(@Auth Principal principal, GeoPlatformServer server)
            throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@Executing secure updateServer - "
                + "Principal : {}\n\n", principal.getName());
        return super.updateServer(server);
    }

    @DELETE
    @Path(value = GPServiceRSPathConfig.DELETE_SERVER_PATH)
    @Override
    public Boolean deleteServer(@Auth Principal principal,
            @QueryParam(value = "serverID") Long serverID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@Executing secure deleteServer - "
                + "Principal : {}\n\n", principal.getName());
        return super.deleteServer(serverID);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_ALL_SERVERS_PATH)
    @Override
    public List<ServerDTO> getAllServers(@Auth Principal principal,
            @PathParam(value = "organizazionName") String organizazionName)
            throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@Executing secure getAllServers - "
                + "Principal : {}\n\n", principal.getName());
        return super.getAllServers(organizazionName);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_SERVER_DETAIL_PATH)
    @Override
    public GeoPlatformServer getServerDetail(@Auth Principal principal,
            @PathParam(value = "serverID") Long serverID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@Executing secure getServerDetail - "
                + "Principal : {}\n\n", principal.getName());
        return super.getServerDetail(serverID);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_SHORT_SERVER_PATH)
    @Override
    public ServerDTO getShortServer(@Auth Principal principal,
            @QueryParam(value = "serverUrl") String serverUrl) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@Executing secure getShortServer - "
                + "Principal : {}\n\n", principal.getName());
        return super.getShortServer(serverUrl);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_SERVER_DETAIL_BY_URL_PATH)
    @Override
    public GeoPlatformServer getServerDetailByUrl(@Auth Principal principal,
            @QueryParam(value = "serverUrl") String serverUrl) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@Executing secure getShortServer - "
                + "Principal : {}\n\n", principal.getName());
        return super.getServerDetailByUrl(serverUrl);
    }

    @PUT
    @Path(value = GPServiceRSPathConfig.SAVE_SERVER_PATH)
    @Override
    public ServerDTO saveServer(@Auth Principal principal,
            WSSaveServerRequest saveServerReq) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@Executing secure saveServer - "
                + "Principal : {}\n\n", principal.getName());
        return super.saveServer(saveServerReq);
    }
}
