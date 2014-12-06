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
package org.geosdi.geoplatform.experimental.dropwizard.resources.secure.viewport;

import io.dropwizard.auth.Auth;
import java.security.Principal;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.geosdi.geoplatform.core.model.GPViewport;
import org.geosdi.geoplatform.request.viewport.InsertViewportRequest;
import org.geosdi.geoplatform.request.viewport.ManageViewportRequest;
import org.geosdi.geoplatform.response.viewport.WSGetViewportResponse;
import org.geosdi.geoplatform.services.rs.path.GPServiceRSPathConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Path(value = GPServiceRSPathConfig.GP_SECURE_VIEWPORT_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Component(value = "secureViewportResource")
public class GPSecureViewportResource extends BaseViewportResource {

    private static final Logger logger = LoggerFactory.getLogger(
            GPSecureViewportResource.class);

    @GET
    @Path(value = GPServiceRSPathConfig.GET_DEFAULT_VIEWPORT_PATH)
    @Override
    public GPViewport getDefaultViewport(@Auth Principal principal,
            @PathParam(value = "accountProjectID") Long accountProjectID)
            throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@Executing secure getDefaultViewport - "
                + "Principal : {}\n\n", principal.getName());
        return super.getDefaultViewport(accountProjectID);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_ACCOUNT_PROJECT_VIEWPORTS_PATH)
    @Override
    public WSGetViewportResponse getAccountProjectViewports(
            @Auth Principal principal,
            @PathParam(value = "accountProjectID") Long accountProjectID)
            throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@Executing secure"
                + " getAccountProjectViewports - Principal : {}\n\n",
                principal.getName());
        return super.getAccountProjectViewports(accountProjectID);
    }

    @POST
    @Path(value = GPServiceRSPathConfig.INSERT_VIEWPORT_PATH)
    @Override
    public Long insertViewport(@Auth Principal principal,
            InsertViewportRequest insertViewportReq) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@Executing secure insertViewport - "
                + "Principal : {}\n\n", principal.getName());
        return super.insertViewport(insertViewportReq);
    }

    @PUT
    @Path(value = GPServiceRSPathConfig.UPDATE_VIEWPORT_PATH)
    @Override
    public Long updateViewport(@Auth Principal principal,
            GPViewport viewport) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@Executing secure updateViewport - "
                + "Principal : {}\n\n", principal.getName());
        return super.updateViewport(viewport);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_VIEWPORT_BY_ID_PATH)
    @Override
    public GPViewport getViewportById(@Auth Principal principal,
            @QueryParam(value = "idViewport") Long idViewport) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@Executing secure getViewportById - "
                + "Principal : {}\n\n", principal.getName());
        return super.getViewportById(idViewport);
    }

    @DELETE
    @Path(value = GPServiceRSPathConfig.DELETE_VIEWPORT_PATH)
    @Override
    public Boolean deleteViewport(@Auth Principal principal,
            @QueryParam(value = "viewportID") Long viewportID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@Executing secure deleteViewport - "
                + "Principal : {}\n\n", principal.getName());
        return super.deleteViewport(viewportID);
    }

    @PUT
    @Path(value = GPServiceRSPathConfig.SAVE_OR_UPDATE_VIEWPORT_LIST_PATH)
    @Override
    public void saveOrUpdateViewportList(@Auth Principal principal,
            ManageViewportRequest request) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@Executing secure "
                + "saveOrUpdateViewportList - Principal : {}\n\n",
                principal.getName());
        super.saveOrUpdateViewportList(request);
    }

    @PUT
    @Path(value = GPServiceRSPathConfig.REPLACE_VIEWPORT_LIST_PATH)
    @Override
    public void replaceViewportList(@Auth Principal principal,
            ManageViewportRequest request) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@Executing secure "
                + "replaceViewportList - Principal : {}\n\n",
                principal.getName());
        super.replaceViewportList(request);
    }
}
