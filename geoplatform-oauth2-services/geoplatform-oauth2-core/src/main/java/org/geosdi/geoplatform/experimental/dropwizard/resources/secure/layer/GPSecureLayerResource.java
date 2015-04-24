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
package org.geosdi.geoplatform.experimental.dropwizard.resources.secure.layer;

import io.dropwizard.auth.Auth;
import java.security.Principal;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.geosdi.geoplatform.core.model.GPBBox;
import org.geosdi.geoplatform.core.model.GPLayerInfo;
import org.geosdi.geoplatform.core.model.GPRasterLayer;
import org.geosdi.geoplatform.core.model.GPVectorLayer;
import org.geosdi.geoplatform.gui.shared.GPLayerType;
import org.geosdi.geoplatform.request.layer.InsertLayerRequest;
import org.geosdi.geoplatform.request.layer.WSAddLayerAndTreeModificationsRequest;
import org.geosdi.geoplatform.request.layer.WSAddLayersAndTreeModificationsRequest;
import org.geosdi.geoplatform.request.layer.WSDDLayerAndTreeModificationsRequest;
import org.geosdi.geoplatform.request.layer.WSDeleteLayerAndTreeModificationsRequest;
import org.geosdi.geoplatform.response.GetDataSourceResponse;
import org.geosdi.geoplatform.response.RasterPropertiesDTO;
import org.geosdi.geoplatform.response.ShortLayerDTO;
import org.geosdi.geoplatform.response.ShortLayerDTOContainer;
import org.geosdi.geoplatform.response.collection.LongListStore;
import org.geosdi.geoplatform.services.rs.path.GPServiceRSPathConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Path(value = GPServiceRSPathConfig.GP_SECURE_LAYER_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Component(value = "secureLayerResource")
public class GPSecureLayerResource extends BaseLayerResource {

    private static final Logger logger = LoggerFactory.getLogger(
            GPSecureLayerResource.class);

    @POST
    @Path(value = GPServiceRSPathConfig.INSERT_LAYER_PATH)
    @Override
    public Long insertLayer(@Auth Principal principal,
            InsertLayerRequest layerRequest) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@@Executing secure insertLayer - "
                + "Principal : {}\n\n", principal.getName());
        return super.insertLayer(layerRequest);
    }

    @PUT
    @Path(value = GPServiceRSPathConfig.UPDATE_RASTER_LAYER_PARH)
    @Override
    public Long updateRasterLayer(@Auth Principal principal, GPRasterLayer layer)
            throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@Executing secure updateRasterLayer - "
                + "Principal : {}\n\n", principal.getName());
        return super.updateRasterLayer(layer);
    }

    @PUT
    @Path(value = GPServiceRSPathConfig.UPDATE_VECTOR_LAYER_PATH)
    @Override
    public Long updateVectorLayer(@Auth Principal principal, GPVectorLayer layer)
            throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@Executing secure updateVectorLayer - "
                + "Principal : {}\n\n", principal.getName());
        return super.updateVectorLayer(layer);
    }

    @DELETE
    @Path(value = GPServiceRSPathConfig.DELETE_LAYER_PATH)
    @Override
    public Boolean deleteLayer(@Auth Principal principal,
            @QueryParam(value = "layerID") Long layerID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@Executing secure deleteLayer - "
                + "Principal : {}\n\n", principal.getName());
        return super.deleteLayer(layerID);
    }

    @POST
    @Path(value = GPServiceRSPathConfig.ADD_LAYER_AND_TREE_MODIFICATIONS_PATH)
    @Override
    public Long saveAddedLayerAndTreeModifications(@Auth Principal principal,
            WSAddLayerAndTreeModificationsRequest addLayerRequest) throws
            Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@Executing secure "
                + "saveAddedLayerAndTreeModifications - Principal : {}\n\n",
                principal.getName());
        return super.saveAddedLayerAndTreeModifications(addLayerRequest);
    }

    @POST
    @Path(value = GPServiceRSPathConfig.ADD_LAYERS_AND_TREE_MODIFICATIONS_PATH)
    @Override
    public LongListStore saveAddedLayersAndTreeModifications(
            @Auth Principal principal,
            WSAddLayersAndTreeModificationsRequest addLayersRequest) throws
            Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@Executing secure "
                + "saveAddedLayersAndTreeModifications - Principal : {}\n\n",
                principal.getName());
        return super.saveAddedLayersAndTreeModifications(addLayersRequest);
    }

    @PUT
    @Path(value = GPServiceRSPathConfig.DELETE_LAYER_AND_TREE_MODIFICATIONS_PATH)
    @Override
    public Boolean saveDeletedLayerAndTreeModifications(
            @Auth Principal principal,
            WSDeleteLayerAndTreeModificationsRequest deleteLayerRequest) throws
            Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@Executing secure "
                + "saveDeletedLayerAndTreeModifications - Principal : {}\n\n",
                principal.getName());
        return super.saveDeletedLayerAndTreeModifications(deleteLayerRequest);
    }

    @PUT
    @Path(value = GPServiceRSPathConfig.SAVE_CHECK_STATUS_LAYER_AND_TREE_MODIFICATION_PATH)
    @Override
    public Boolean saveCheckStatusLayerAndTreeModifications(
            @Auth Principal principal, @FormParam(value = "layerID") Long layerID,
            @FormParam(value = "checked") boolean checked) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@Executing secure "
                + "saveCheckStatusLayerAndTreeModifications - Principal "
                + ": {}\n\n", principal.getName());
        return super.saveCheckStatusLayerAndTreeModifications(layerID, checked);
    }

    @PUT
    @Path(value = GPServiceRSPathConfig.SAVE_DD_LAYER_AND_TREE_MODIFICATIONS_PATH)
    @Override
    public Boolean saveDragAndDropLayerAndTreeModifications(
            @Auth Principal principal,
            WSDDLayerAndTreeModificationsRequest ddLayerReq) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@Executing secure "
                + "saveDragAndDropLayerAndTreeModifications - Principal "
                + ": {}\n\n", principal.getName());
        return super.saveDragAndDropLayerAndTreeModifications(ddLayerReq);
    }

    @PUT
    @Path(value = GPServiceRSPathConfig.SAVE_LAYERS_PROPERTIES_PATH)
    @Override
    public Boolean saveLayerProperties(@Auth Principal principal,
            RasterPropertiesDTO layerProperties) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@Executing secure saveLayerProperties "
                + "- Principal : {}\n\n", principal.getName());
        return super.saveLayerProperties(layerProperties);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_RASTER_LAYER_PATH)
    @Override
    public GPRasterLayer getRasterLayer(@Auth Principal principal,
            @PathParam(value = "layerID") Long layerID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@Executing secure getRasterLayer "
                + "- Principal : {}\n\n", principal.getName());
        return super.getRasterLayer(layerID);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_VECTOR_LAYER_PATH)
    @Override
    public GPVectorLayer getVectorLayer(@Auth Principal principal,
            @PathParam(value = "layerID") Long layerID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@Executing secure getVectorLayer "
                + "- Principal : {}\n\n", principal.getName());
        return super.getVectorLayer(layerID);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_SHORT_LAYER_PATH)
    @Override
    public ShortLayerDTO getShortLayer(@Auth Principal principal,
            @PathParam(value = "layerID") Long layerID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@Executing secure getShortLayer "
                + "- Principal : {}\n\n", principal.getName());
        return super.getShortLayer(layerID);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_LAYERS_PATH)
    @Override
    public ShortLayerDTOContainer getLayers(@Auth Principal principal,
            @PathParam(value = "projectID") Long projectID) {
        logger.debug("\n\n@@@@@@@@@@@@@@@@Executing secure getLayers "
                + "- Principal : {}\n\n", principal.getName());
        return super.getLayers(projectID);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_LAYER_BBOX_PATH)
    @Override
    public GPBBox getBBox(@Auth Principal principal,
            @PathParam(value = "layerID") Long layerID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@Executing secure getBBox "
                + "- Principal : {}\n\n", principal.getName());
        return super.getBBox(layerID);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_LAYER_INFO_PATH)
    @Override
    public GPLayerInfo getLayerInfo(@Auth Principal principal,
            @PathParam(value = "layerID") Long layerID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@Executing secure getLayerInfo "
                + "- Principal : {}\n\n", principal.getName());
        return super.getLayerInfo(layerID);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_LAYER_TYPE_PATH)
    @Override
    public GPLayerType getLayerType(@Auth Principal principal,
            @PathParam(value = "layerID") Long layerID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@Executing secure getLayerType "
                + "- Principal : {}\n\n", principal.getName());
        return super.getLayerType(layerID);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_LAYERS_DATA_SOURCE_BY_PROJECT_ID_PATH)
    @Override
    public GetDataSourceResponse getLayersDataSourceByProjectID(
            @Auth Principal principal,
            @PathParam(value = "projectID") Long projectID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@Executing secure "
                + "getLayersDataSourceByProjectID - Principal : {}\n\n",
                principal.getName());
        return super.getLayersDataSourceByProjectID(projectID);
    }

}
