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
package org.geosdi.geoplatform.experimental.dropwizard.resources.secure.folder;

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
import org.geosdi.geoplatform.core.model.GPFolder;
import org.geosdi.geoplatform.request.folder.InsertFolderRequest;
import org.geosdi.geoplatform.request.folder.WSAddFolderAndTreeModificationsRequest;
import org.geosdi.geoplatform.request.folder.WSDDFolderAndTreeModifications;
import org.geosdi.geoplatform.request.folder.WSDeleteFolderAndTreeModifications;
import org.geosdi.geoplatform.response.FolderDTO;
import org.geosdi.geoplatform.response.collection.TreeFolderElementsStore;
import org.geosdi.geoplatform.services.rs.path.GPServiceRSPathConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Path(value = GPServiceRSPathConfig.GP_SECURE_FOLDER_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Component(value = "secureFolderResource")
public class GPSecureFolderResource extends BaseFolderResource {

    private static final Logger logger = LoggerFactory.getLogger(
            GPSecureFolderResource.class);

    @POST
    @Path(value = GPServiceRSPathConfig.INSERT_FOLDER_PATH)
    @Override
    public Long insertFolder(@Auth Principal principal,
            InsertFolderRequest insertFolderRequest) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@@Executing secure insertFolder - "
                + "Principal : {}\n\n", principal.getName());
        return super.insertFolder(insertFolderRequest);
    }

    @PUT
    @Path(value = GPServiceRSPathConfig.UPDATE_FOLDER_PATH)
    @Override
    public Long updateFolder(@Auth Principal principal, GPFolder folder)
            throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@@Executing secure updateFolder - "
                + "Principal : {}\n\n", principal.getName());
        return super.updateFolder(folder);
    }

    @DELETE
    @Path(value = GPServiceRSPathConfig.DELETE_FOLDER_PATH)
    @Override
    public Boolean deleteFolder(@Auth Principal principal, @QueryParam(
            value = "folderID") Long folderID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@@Executing secure deleteFolder - "
                + "Principal : {}\n\n", principal.getName());
        return super.deleteFolder(folderID);
    }

    @POST
    @Path(value = GPServiceRSPathConfig.SAVE_FOLDER_PROPERTIES_PATH)
    @Override
    public Long saveFolderProperties(@Auth Principal principal,
            @QueryParam(value = "folderID") Long folderID,
            @QueryParam(value = "folderName") String folderName,
            @QueryParam(value = "checked") boolean checked,
            @QueryParam(value = "expanded") boolean expanded) throws Exception {
        logger.debug(
                "\n\n@@@@@@@@@@@@@@@@@Executing secure saveFolderProperties"
                + " - Principal : {}\n\n", principal.getName());
        return super.saveFolderProperties(folderID, folderName, checked,
                expanded);
    }

    @PUT
    @Path(value = GPServiceRSPathConfig.SAVE_ADDED_FOLDER_AND_TREE_MODICATIONS_PATH)
    @Override
    public Long saveAddedFolderAndTreeModifications(@Auth Principal principal,
            WSAddFolderAndTreeModificationsRequest sftModificationRequest)
            throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@@Executing secure "
                + "saveAddedFolderAndTreeModifications - Principal : {}\n\n",
                principal.getName());
        return super.saveAddedFolderAndTreeModifications(sftModificationRequest);
    }

    @PUT
    @Path(value = GPServiceRSPathConfig.SAVE_DELETED_FOLDER_AND_TREE_MODIFICATIONS_PATH)
    @Override
    public Boolean saveDeletedFolderAndTreeModifications(
            @Auth Principal principal,
            WSDeleteFolderAndTreeModifications sdfModificationRequest)
            throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@@Executing secure "
                + "saveDeletedFolderAndTreeModifications - Principal : {}\n\n",
                principal.getName());
        return super.saveDeletedFolderAndTreeModifications(
                sdfModificationRequest);
    }

    @PUT
    @Path(value = GPServiceRSPathConfig.SAVE_DD_FOLDER_AND_TREE_MODIFICATIONS_PATH)
    @Override
    public Boolean saveDragAndDropFolderAndTreeModifications(
            @Auth Principal principal,
            WSDDFolderAndTreeModifications sddfTreeModificationRequest) throws
            Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@@Executing secure "
                + "saveDragAndDropFolderAndTreeModifications - Principal : "
                + "{}\n\n", principal.getName());
        return super.saveDragAndDropFolderAndTreeModifications(
                sddfTreeModificationRequest);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_SHORT_FOLDER_PATH)
    @Override
    public FolderDTO getShortFolder(@Auth Principal principal,
            @PathParam(value = "folderID") Long folderID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@@Executing secure getShortFolder "
                + "- Principal : {}\n\n", principal.getName());
        return super.getShortFolder(folderID);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_FOLDER_DETAIL_PATH)
    @Override
    public GPFolder getFolderDetail(@Auth Principal principal,
            @PathParam(value = "folderID") Long folderID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@@Executing secure getFolderDetail "
                + "- Principal : {}\n\n", principal.getName());
        return super.getFolderDetail(folderID);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_CHILDREN_FOLDERS_PATH)
    @Override
    public List<FolderDTO> getChildrenFolders(@Auth Principal principal,
            @PathParam(value = "folderID") Long folderID) {
        logger.debug("\n\n@@@@@@@@@@@@@@@@@Executing secure getChildrenFolders "
                + "- Principal : {}\n\n", principal.getName());
        return super.getChildrenFolders(folderID);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_CHILDREN_ELEMENTS_PATH)
    @Override
    public TreeFolderElementsStore getChildrenElements(@Auth Principal principal,
            @PathParam(value = "folderID") Long folderID) {
        logger.debug(
                "\n\n@@@@@@@@@@@@@@@@@Executing secure getChildrenElements "
                + "- Principal : {}\n\n", principal.getName());
        return super.getChildrenElements(folderID);
    }

}
