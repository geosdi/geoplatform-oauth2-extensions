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
package org.geosdi.geoplatform.experimental.dropwizard.resources.secure.project;

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
import org.geosdi.geoplatform.core.model.GPAccountProject;
import org.geosdi.geoplatform.core.model.GPProject;
import org.geosdi.geoplatform.request.LikePatternType;
import org.geosdi.geoplatform.request.PaginatedSearchRequest;
import org.geosdi.geoplatform.request.PutAccountsProjectRequest;
import org.geosdi.geoplatform.request.RequestByAccountProjectIDs;
import org.geosdi.geoplatform.request.SearchRequest;
import org.geosdi.geoplatform.request.project.ImportProjectRequest;
import org.geosdi.geoplatform.request.project.SaveProjectRequest;
import org.geosdi.geoplatform.response.AccountProjectPropertiesDTO;
import org.geosdi.geoplatform.response.ProjectDTO;
import org.geosdi.geoplatform.response.ShortAccountDTOContainer;
import org.geosdi.geoplatform.response.WSGetAccountProjectsResponse;
import org.geosdi.geoplatform.services.rs.path.GPServiceRSPathConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Path(value = GPServiceRSPathConfig.GP_SECURE_PROJECT_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Component(value = "secureProjectResource")
public class GPSecureProjectResource extends BaseProjectResource {

    private static final Logger logger = LoggerFactory.getLogger(
            GPSecureProjectResource.class);

    //<editor-fold defaultstate="collapsed" desc="Secure Project">
    // =========================================================================
    // === Secure Project
    // ========================================================================= 
    @POST
    @Path(value = GPServiceRSPathConfig.SAVE_PROJECT_PATH)
    @Override
    public Long saveProject(@Auth Principal principal,
            SaveProjectRequest saveProjectRequest) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@Executing secure saveProjectRequest - "
                + "Principal : {}\n\n", principal.getName());
        return super.saveProject(saveProjectRequest);
    }

    @Path(value = GPServiceRSPathConfig.INSERT_PROJECT_PATH)
    @POST
    @Override
    public Long insertProject(@Auth Principal principal, GPProject project)
            throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@Executing secure insertProject - "
                + "Principal : {}\n\n", principal.getName());
        return super.insertProject(project);
    }

    @PUT
    @Path(value = GPServiceRSPathConfig.UPDATE_PROJECT_PATH)
    @Override
    public Long updateProject(@Auth Principal principal, GPProject project)
            throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@Executing secure updateProject - "
                + "Principal : {}\n\n", principal.getName());
        return super.updateProject(project);
    }

    @DELETE
    @Path(value = GPServiceRSPathConfig.DELETE_PROJECT_PATH)
    @Override
    public Boolean deleteProject(@Auth Principal principal,
            @PathParam(value = "projectID") Long projectID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@Executing secure deleteProject - "
                + "Principal : {}\n\n", principal.getName());
        return super.deleteProject(projectID);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_PROJECT_DETAIL_PATH)
    @Override
    public GPProject getProjectDetail(@Auth Principal principal,
            @PathParam(value = "projectID") Long projectID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@Executing secure getProjectDetail - "
                + "Principal : {}\n\n", principal.getName());
        return super.getProjectDetail(projectID);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_NUMBER_OF_ELEMENTS_PROJECT_PATH)
    @Override
    public Integer getNumberOfElementsProject(@Auth Principal principal,
            @PathParam(value = "projectID") Long projectID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@Executing secure "
                + "getNumberOfElementsProject - Principal : {}\n\n",
                principal.getName());
        return super.getNumberOfElementsProject(projectID);
    }

    @PUT
    @Path(value = GPServiceRSPathConfig.SET_PROJECT_SHARED_PATH)
    @Override
    public void setProjectShared(@Auth Principal principal,
            @QueryParam(value = "projectID") Long projectID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@Executing secure setProjectShared - "
                + "Principal : {}\n\n", principal.getName());
        super.setProjectShared(projectID);
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Secure AccountProject">
    // ==========================================================================
    // === Secure AccountProject
    // ==========================================================================
    @POST
    @Path(value = GPServiceRSPathConfig.INSERT_ACCOUNT_PROJECT_PATH)
    @Override
    public Long insertAccountProject(@Auth Principal principal,
            GPAccountProject accountProject) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@Executing secure insertAccountProject - "
                + "Principal : {}\n\n", principal.getName());
        return super.insertAccountProject(accountProject);
    }

    @PUT
    @Path(value = GPServiceRSPathConfig.UPDATE_ACCOUNT_PROJECT_PATH)
    @Override
    public Long updateAccountProject(@Auth Principal principal,
            GPAccountProject accountProject) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@Executing secure updateAccountProject - "
                + "Principal : {}\n\n", principal.getName());
        return super.updateAccountProject(accountProject);
    }

    @DELETE
    @Path(value = GPServiceRSPathConfig.DELETE_ACCOUNT_PROJECT_PATH)
    @Override
    public Boolean deleteAccountProject(@Auth Principal principal,
            @PathParam(value = "accountProjectID") Long accountProjectID)
            throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@Executing secure deleteAccountProject - "
                + "Principal : {}\n\n", principal.getName());
        return super.deleteAccountProject(accountProjectID);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_ACCOUNT_PROJECT_PATH)
    @Override
    public GPAccountProject getAccountProject(@Auth Principal principal,
            @PathParam(value = "accountProjectID") Long accountProjectID)
            throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@Executing secure getAccountProject - "
                + "Principal : {}\n\n", principal.getName());
        return super.getAccountProject(accountProjectID);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_ACCOUNT_PROJECTS_BY_ACCOUNT_ID)
    @Override
    public WSGetAccountProjectsResponse getAccountProjectsByAccountID(
            @Auth Principal principal,
            @PathParam(value = "accountID") Long accountID) {
        logger.debug("\n\n@@@@@@@@@@@@@Executing secure "
                + "getAccountProjectsByAccountID - Principal : {}\n\n",
                principal.getName());
        return super.getAccountProjectsByAccountID(accountID);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_ACCOUNT_PROJECTS_BY_PROJECT_ID_PATH)
    @Override
    public WSGetAccountProjectsResponse getAccountProjectsByProjectID(
            @Auth Principal principal,
            @PathParam(value = "projectID") Long projectID) {
        logger.debug("\n\n@@@@@@@@@@@@@Executing secure "
                + "getAccountProjectsByProjectID - Principal : {}\n\n",
                principal.getName());
        return super.getAccountProjectsByProjectID(projectID);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_ACCOUNT_PROJECT_BY_ACCOUNT_AND_PROJECT_IDS_PATH)
    @Override
    public GPAccountProject getAccountProjectByAccountAndProjectIDs(
            @Auth Principal principal,
            @PathParam(value = "accountID") Long accountID,
            @PathParam(value = "projectID") Long projectID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@Executing secure "
                + "getAccountProjectByAccountAndProjectIDs - Principal : {}\n\n",
                principal.getName());
        return super.getAccountProjectByAccountAndProjectIDs(accountID,
                projectID);
    }

    @Override
    public Long getAccountProjectsCount(Principal principal,
            @QueryParam(value = "accountID") Long accountID,
            @QueryParam("request") SearchRequest request) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@Executing secure "
                + "getAccountProjectsCount - Principal : {}\n\n",
                principal.getName());
        return super.getAccountProjectsCount(accountID, request);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_ACCOUNT_PROJECTS_COUNT_PATH)
    @Override
    public Long getAccountProjectsCount(@Auth Principal principal,
            @QueryParam(value = "accountID") Long accountID,
            @QueryParam(value = "nameLike") String nameLike,
            @QueryParam(value = "likeType") LikePatternType likeType)
            throws Exception {
        return getAccountProjectsCount(accountID, (nameLike != null)
                ? (likeType != null)
                        ? new SearchRequest(nameLike, likeType)
                        : new SearchRequest(nameLike) : new SearchRequest()
        );
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_DEFAULT_ACCOUNT_PROJECT_PATH)
    @Override
    public GPAccountProject getDefaultAccountProject(@Auth Principal principal,
            @PathParam(value = "accountID") Long accountID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@Executing secure "
                + "getDefaultAccountProject - Principal : {}\n\n",
                principal.getName());
        return super.getDefaultAccountProject(accountID);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.SEARCH_ACCOUNT_PROJECTS_PATH)
    @Override
    public List<ProjectDTO> searchAccountProjects(@Auth Principal principal,
            @QueryParam(value = "accountID") Long accountID,
            @QueryParam("request") PaginatedSearchRequest request)
            throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@Executing secure "
                + "searchAccountProjects - Principal : {}\n\n",
                principal.getName());
        return super.searchAccountProjects(accountID, request);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_PROJECT_OWNER_PATH)
    @Override
    public GPAccountProject getProjectOwner(@Auth Principal principal,
            @PathParam(value = "projectID") Long projectID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@Executing secure getProjectOwner - "
                + "Principal : {}\n\n", principal.getName());
        return super.getProjectOwner(projectID);
    }

    @PUT
    @Path(value = GPServiceRSPathConfig.SET_PROJECT_OWNER_PATH)
    @Override
    public Boolean setProjectOwner(@Auth Principal principal,
            RequestByAccountProjectIDs request) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@Executing secure setProjectOwner - "
                + "Principal : {}\n\n", principal.getName());
        return super.setProjectOwner(request);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_DEFAULT_PROJECT_PATH)
    @Override
    public GPProject getDefaultProject(@Auth Principal principal,
            @PathParam(value = "accountID") Long accountID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@Executing secure getDefaultProject - "
                + "Principal : {}\n\n", principal.getName());
        return super.getDefaultProject(accountID);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_DEFAULT_PROJECT_DTO_PATH)
    @Override
    public ProjectDTO getDefaultProjectDTO(@Auth Principal principal,
            @PathParam(value = "accountID") Long accountID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@Executing secure getDefaultProjectDTO - "
                + "Principal : {}\n\n", principal.getName());
        return super.getDefaultProjectDTO(accountID);
    }

    @PUT
    @Path(value = GPServiceRSPathConfig.UPDATE_DEFAULT_PROJECT_PATH)
    @Override
    public GPProject updateDefaultProject(@Auth Principal principal,
            @QueryParam(value = "accountID") Long accountID,
            @QueryParam(value = "projectID") Long projectID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@Executing secure updateDefaultProject - "
                + "Principal : {}\n\n", principal.getName());
        return super.updateDefaultProject(accountID, projectID);
    }

    @PUT
    @Path(value = GPServiceRSPathConfig.SAVE_ACCOUNT_PROJECT_PROPERTIES_PATH)
    @Override
    public Boolean saveAccountProjectProperties(@Auth Principal principal,
            AccountProjectPropertiesDTO accountProjectProperties)
            throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@Executing secure "
                + "saveAccountProjectProperties - Principal : {}\n\n",
                principal.getName());
        return super.saveAccountProjectProperties(accountProjectProperties);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_ACCOUNTS_BY_PROJECT_ID_PATH)
    @Override
    public ShortAccountDTOContainer getAccountsByProjectID(
            @Auth Principal principal,
            @PathParam(value = "projectID") Long projectID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@Executing secure "
                + "getAccountsByProjectID - Principal : {}\n\n",
                principal.getName());
        return super.getAccountsByProjectID(projectID);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_ACCOUNTS_TO_SHARE_BY_PROJECT_ID_PATH)
    @Override
    public ShortAccountDTOContainer getAccountsToShareByProjectID(
            @Auth Principal principal,
            @PathParam(value = "projectID") Long projectID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@Executing secure "
                + "getAccountsToShareByProjectID - Principal : {}\n\n",
                principal.getName());
        return super.getAccountsToShareByProjectID(projectID);
    }

    @PUT
    @Path(value = GPServiceRSPathConfig.UPDATE_ACCOUNTS_PROJECT_SHARING_PATH)
    @Override
    public Boolean updateAccountsProjectSharing(@Auth Principal principal,
            PutAccountsProjectRequest apRequest) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@Executing secure "
                + "updateAccountsProjectSharing - Principal : {}\n\n",
                principal.getName());
        return super.updateAccountsProjectSharing(apRequest);
    }
    // </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Secure Folder / Project">
    // =========================================================================
    // === Secure Folder / Project
    // =========================================================================
    @GET
    @Path(value = GPServiceRSPathConfig.GET_PROJECT_WITH_ROOT_FOLDERS_PATH)
    @Override
    public ProjectDTO getProjectWithRootFolders(@Auth Principal principal,
            @PathParam(value = "projectID") Long projectID,
            @PathParam(value = "accountID") Long accountID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@Executing secure"
                + " getProjectWithRootFolders - Principal : {}\n\n",
                principal.getName());
        return super.getProjectWithRootFolders(projectID, accountID);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_PROJECT_WITH_EXPANDED_FOLDERS_PATH)
    @Override
    public ProjectDTO getProjectWithExpandedFolders(@Auth Principal principal,
            @PathParam(value = "projectID") Long projectID,
            @PathParam(value = "accountID") Long accountID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@Executing secure"
                + " getProjectWithExpandedFolders - Principal : {}\n\n",
                principal.getName());
        return super.getProjectWithExpandedFolders(projectID, accountID);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.EXPORT_PROJECT_PATH)
    @Override
    public ProjectDTO exportProject(@Auth Principal principal,
            @PathParam(value = "projectID") Long projectID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@Executing secure exportProject "
                + "- Principal : {}\n\n", principal.getName());
        return super.exportProject(projectID);
    }

    @POST
    @Path(value = GPServiceRSPathConfig.IMPORT_PROJECT_PATH)
    @Override
    public Long importProject(@Auth Principal principal,
            ImportProjectRequest impRequest) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@Executing secure importProject "
                + "- Principal : {}\n\n", principal.getName());
        return super.importProject(impRequest);
    }
    //</editor-fold>
}
