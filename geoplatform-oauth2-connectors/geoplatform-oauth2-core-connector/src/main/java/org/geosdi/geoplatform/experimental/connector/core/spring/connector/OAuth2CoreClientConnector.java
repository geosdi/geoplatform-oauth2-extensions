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
package org.geosdi.geoplatform.experimental.connector.core.spring.connector;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.util.List;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import org.geosdi.geoplatform.core.model.GPAccountProject;
import org.geosdi.geoplatform.core.model.GPBBox;
import org.geosdi.geoplatform.core.model.GPFolder;
import org.geosdi.geoplatform.core.model.GPLayerInfo;
import org.geosdi.geoplatform.core.model.GPMessage;
import org.geosdi.geoplatform.core.model.GPOrganization;
import org.geosdi.geoplatform.core.model.GPProject;
import org.geosdi.geoplatform.core.model.GPRasterLayer;
import org.geosdi.geoplatform.core.model.GPUser;
import org.geosdi.geoplatform.core.model.GPVectorLayer;
import org.geosdi.geoplatform.core.model.GPViewport;
import org.geosdi.geoplatform.core.model.GeoPlatformServer;
import org.geosdi.geoplatform.experimental.connector.api.auth.token.OAuth2TokenBuilder;
import org.geosdi.geoplatform.experimental.connector.api.connector.AbstractClientConnector;
import org.geosdi.geoplatform.experimental.connector.api.settings.ConnectorClientSettings;
import org.geosdi.geoplatform.gui.shared.GPLayerType;
import org.geosdi.geoplatform.request.InsertAccountRequest;
import org.geosdi.geoplatform.request.PaginatedSearchRequest;
import org.geosdi.geoplatform.request.PutAccountsProjectRequest;
import org.geosdi.geoplatform.request.RequestByAccountProjectIDs;
import org.geosdi.geoplatform.request.SearchRequest;
import org.geosdi.geoplatform.request.folder.InsertFolderRequest;
import org.geosdi.geoplatform.request.folder.WSAddFolderAndTreeModificationsRequest;
import org.geosdi.geoplatform.request.folder.WSDDFolderAndTreeModifications;
import org.geosdi.geoplatform.request.folder.WSDeleteFolderAndTreeModifications;
import org.geosdi.geoplatform.request.layer.InsertLayerRequest;
import org.geosdi.geoplatform.request.layer.WSAddLayerAndTreeModificationsRequest;
import org.geosdi.geoplatform.request.layer.WSAddLayersAndTreeModificationsRequest;
import org.geosdi.geoplatform.request.layer.WSDDLayerAndTreeModificationsRequest;
import org.geosdi.geoplatform.request.layer.WSDeleteLayerAndTreeModificationsRequest;
import org.geosdi.geoplatform.request.message.MarkMessageReadByDateRequest;
import org.geosdi.geoplatform.request.organization.WSPutRolePermissionRequest;
import org.geosdi.geoplatform.request.organization.WSSaveRoleRequest;
import org.geosdi.geoplatform.request.project.ImportProjectRequest;
import org.geosdi.geoplatform.request.project.SaveProjectRequest;
import org.geosdi.geoplatform.request.server.WSSaveServerRequest;
import org.geosdi.geoplatform.request.viewport.InsertViewportRequest;
import org.geosdi.geoplatform.request.viewport.ManageViewportRequest;
import org.geosdi.geoplatform.response.AccountProjectPropertiesDTO;
import org.geosdi.geoplatform.response.FolderDTO;
import org.geosdi.geoplatform.response.GetDataSourceResponse;
import org.geosdi.geoplatform.response.MessageDTO;
import org.geosdi.geoplatform.response.ProjectDTO;
import org.geosdi.geoplatform.response.RasterPropertiesDTO;
import org.geosdi.geoplatform.response.SearchUsersResponseWS;
import org.geosdi.geoplatform.response.ServerDTO;
import org.geosdi.geoplatform.response.ServerDTOContainer;
import org.geosdi.geoplatform.response.ShortAccountDTOContainer;
import org.geosdi.geoplatform.response.ShortLayerDTO;
import org.geosdi.geoplatform.response.ShortLayerDTOContainer;
import org.geosdi.geoplatform.response.UserDTO;
import org.geosdi.geoplatform.response.WSGetAccountProjectsResponse;
import org.geosdi.geoplatform.response.authority.GetAuthoritiesResponseWS;
import org.geosdi.geoplatform.response.authority.GetAuthorityResponse;
import org.geosdi.geoplatform.response.collection.ChildrenFolderStore;
import org.geosdi.geoplatform.response.collection.GuiComponentsPermissionMapData;
import org.geosdi.geoplatform.response.collection.LongListStore;
import org.geosdi.geoplatform.response.collection.TreeFolderElementsStore;
import org.geosdi.geoplatform.response.message.GetMessageResponse;
import org.geosdi.geoplatform.response.role.WSGetRoleResponse;
import org.geosdi.geoplatform.response.viewport.WSGetViewportResponse;
import org.geosdi.geoplatform.services.core.api.GPCoreServiceApi;

/**
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class OAuth2CoreClientConnector extends AbstractClientConnector
        implements GPCoreServiceApi {

    static final String LOGGER_MESSAGE = "\n\t@@@@@@@@@@@@@@@@@@@@@@@@@ACQUIRE ACCESS_TOKEN VALUE "
            + ": {} for Method : {}\n";

    public OAuth2CoreClientConnector(ConnectorClientSettings theClientSettings,
            Client theClient, OAuth2TokenBuilder theTokenBuilder) {
        super(theClientSettings, theClient, theTokenBuilder);
    }

    @Override
    public Long insertOrganization(GPOrganization organization) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "insertOrganization");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureOrganization/organizations/insertOrganization"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .post(Long.class, organization);
    }

    @Override
    public Boolean deleteOrganization(Long organizationID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "deleteOrganization");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureOrganization/organizations/deleteOrganization/"))
                .path(String.valueOf(organizationID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .delete(Boolean.class);
    }

    @Override
    public Long insertAccount(InsertAccountRequest insertAccountRequest)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "insertAccount");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureAccount/accounts/insertAccount"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .post(Long.class, insertAccountRequest);
    }

    @Override
    public Long updateUser(GPUser user) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "updateUser");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureAccount/accounts/updateUser"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .post(Long.class, user);
    }

    @Override
    public Boolean deleteAccount(Long accountID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "deleteAccount");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureAccount/accounts/deleteAccount/"))
                .queryParam("accountID", String.valueOf(accountID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .delete(Boolean.class);
    }

    @Override
    public GPUser getUserDetail(Long userID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getUserDetail");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureAccount/accounts/getUserDetail/"))
                .path(String.valueOf(userID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GPUser.class);
    }

    @Override
    public GPUser getUserDetailByUsername(SearchRequest request)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getUserDetailByUsername");

        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.add("nameLike", request.getNameLike());
        if (request.getLikeType() != null) {
            params.add("likeType", request.getLikeType().toString());
        }

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureAccount/accounts/getUserDetail/"
                        + "getUserDetailByUsername"))
                .queryParams(params)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GPUser.class);
    }

    @Override
    public GPUser getUserDetailByUsernameAndPassword(String username,
            String plainPassword) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken,
                "getUserDetailByUsernameAndPassword");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureAccount/accounts/getUserDetail/"
                        + "getUserDetailByUsernameAndPassword"))
                .queryParam("username", username)
                .queryParam("plainPassword", plainPassword)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GPUser.class);
    }

    @Override
    public UserDTO getShortUser(Long userID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getShortUser");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureAccount/accounts/getShortUser/"))
                .path(String.valueOf(userID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(UserDTO.class);
    }

    @Override
    public UserDTO getShortUserByUsername(SearchRequest request)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getShortUserByUsername");

        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.add("nameLike", request.getNameLike());
        if (request.getLikeType() != null) {
            params.add("likeType", request.getLikeType().name());
        }

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureAccount/accounts/getShortUserByUsername"))
                .queryParams(params)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(UserDTO.class);
    }

    @Override
    public SearchUsersResponseWS searchUsers(Long userID,
            PaginatedSearchRequest request)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "searchUsers");

        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.add("userID", String.valueOf(userID));
        params.add("num", String.valueOf(request.getNum()));
        params.add("page", String.valueOf(request.getPage()));
        if (request.getNameLike() != null) {
            params.add("nameLike", request.getNameLike());
        }

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureAccount/accounts/searchUsers"))
                .queryParams(params)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(SearchUsersResponseWS.class);
    }

    @Override
    public ShortAccountDTOContainer getAllAccounts() {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getAllAccounts");

        return client.resource(super.getRestServiceURL().concat(
                "jsonSecureAccount/accounts/getAllAccounts"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).get(
                        ShortAccountDTOContainer.class);
    }

    @Override
    public ShortAccountDTOContainer getAccounts(String organization)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getAccounts");

        return client.resource(super.getRestServiceURL().concat(
                "jsonSecureAccount/accounts/getAllOrganizationAccount/"))
                .path(organization)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).get(
                        ShortAccountDTOContainer.class);
    }

    @Override
    public Long getAccountsCount(SearchRequest request) {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getAccountsCount");

        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.add("nameLike", request.getNameLike());
        if (request.getLikeType() != null) {
            params.add("likeType", request.getLikeType().name());
        }

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureAccount/accounts/getAccountsCount"))
                .queryParams(params)
                .type(MediaType.APPLICATION_FORM_URLENCODED)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(Long.class);
    }

    @Override
    public Long getUsersCount(String organization, SearchRequest request) {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getUsersCount");

        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.add("organization", organization);
        if (request.getNameLike() != null) {
            params.add("nameLike", request.getNameLike());
        }

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureAccount/accounts/getUsersCount"))
                .queryParams(params)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(Long.class);
    }

    @Override
    public GetAuthorityResponse getAuthorities(Long accountID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getAuthorities");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureAccount/authorities/getAuthorities/"))
                .path(String.valueOf(accountID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GetAuthorityResponse.class);
    }

    @Override
    public GetAuthoritiesResponseWS getAuthoritiesDetail(String accountNaturalID)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getAuthoritiesDetail");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureAccount/authorities/getAuthoritiesByAccountNaturalID/"))
                .path(String.valueOf(accountNaturalID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GetAuthoritiesResponseWS.class);
    }

    @Override
    public void forceTemporaryAccount(Long accountID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "forceTemporaryAccount");

        client.resource(super.getRestServiceURL()
                .concat("jsonSecureAccount/accounts/forceTemporaryAccount/"))
                .path(String.valueOf(accountID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put();
    }

    @Override
    public void forceExpiredTemporaryAccount(Long accountID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "forceExpiredTemporaryAccount");

        client.resource(super.getRestServiceURL()
                .concat("jsonSecureAccount/accounts/forceExpiredTemporaryAccount/"))
                .path(String.valueOf(accountID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put();
    }

    @Override
    public Long insertAccountProject(GPAccountProject accountProject)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "insertAccountProject");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureProject/accountprojects/insertAccountProject"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .post(Long.class, accountProject);
    }

    @Override
    public Long updateAccountProject(GPAccountProject accountProject)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "updateAccountProject");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureProject/accountprojects/updateAccountProject"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Long.class, accountProject);
    }

    @Override
    public Boolean deleteAccountProject(Long accountProjectID) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GPAccountProject getAccountProject(Long accountProjectID)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getAccountProject");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureProject/accountprojects/getAccountProject/"))
                .path(String.valueOf(accountProjectID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GPAccountProject.class);
    }

    @Override
    public WSGetAccountProjectsResponse getAccountProjectsByAccountID(
            Long accountID) {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken,
                "getAccountProjectsByAccountID");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureProject/accountprojects/getAccountProjectsByAccountID/"))
                .path(String.valueOf(accountID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(WSGetAccountProjectsResponse.class);
    }

    @Override
    public WSGetAccountProjectsResponse getAccountProjectsByProjectID(
            Long projectID) {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken,
                "getAccountProjectsByProjectID");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureProject/accountprojects/getAccountProjectsByProjectID/"))
                .path(String.valueOf(String.valueOf(projectID)))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(WSGetAccountProjectsResponse.class);
    }

    @Override
    public GPAccountProject getAccountProjectByAccountAndProjectIDs(
            Long accountID, Long projectID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken,
                "getAccountProjectByAccountAndProjectIDs");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureProject/accountprojects/"
                        + "getAccountProjectByAccountAndProjectIDs/"))
                .path(String.valueOf(String.valueOf(accountID)) + "/")
                .path(String.valueOf(String.valueOf(projectID)))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GPAccountProject.class);
    }

    @Override
    public Long getAccountProjectsCount(Long accountID, SearchRequest request)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken,
                "getAccountProjectsCount");

        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.add("accountID", String.valueOf(accountID));
        if (request.getNameLike() != null) {
            params.add("nameLike", request.getNameLike());
        }
        if (request.getLikeType() != null) {
            params.add("likeType", request.getLikeType().toString());
        }

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureProject/accountprojects/getAccountProjectsCount"))
                .queryParams(params)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(Long.class);
    }

    @Override
    public GPAccountProject getDefaultAccountProject(Long accountID) throws
            Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ProjectDTO> searchAccountProjects(Long accountID,
            PaginatedSearchRequest request) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GPAccountProject getProjectOwner(Long projectID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getProjectOwner");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureProject/accountprojects/getProjectOwner/"))
                .path(String.valueOf(String.valueOf(projectID)))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GPAccountProject.class);
    }

    @Override
    public Boolean setProjectOwner(RequestByAccountProjectIDs request)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "setProjectOwner");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureProject/accountprojects/setProjectOwner"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Boolean.class, request);
    }

    @Override
    public GPProject getDefaultProject(Long accountID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getDefaultProject");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureProject/projects/getDefaultProject/"))
                .path(String.valueOf(accountID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GPProject.class);
    }

    @Override
    public ProjectDTO getDefaultProjectDTO(Long accountID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getDefaultProject");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureProject/projects/getDefaultProjectDTO/"))
                .path(String.valueOf(accountID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(ProjectDTO.class);
    }

    @Override
    public GPProject updateDefaultProject(Long accountID, Long projectID) throws
            Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Boolean saveAccountProjectProperties(
            AccountProjectPropertiesDTO accountProjectProperties)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "saveAccountProjectProperties");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureProject/projects/saveAccountProjectProperties"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Boolean.class, accountProjectProperties);
    }

    @Override
    public ShortAccountDTOContainer getAccountsByProjectID(Long projectID)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getAccountsByProjectID");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureProject/accountprojects/getAccountsByProject/"))
                .path(String.valueOf(projectID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(ShortAccountDTOContainer.class);
    }

    @Override
    public ShortAccountDTOContainer getAccountsToShareByProjectID(Long projectID)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken,
                "getAccountsToShareByProjectID");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureProject/accountprojects/getAccountsToShare/"))
                .path(String.valueOf(projectID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(ShortAccountDTOContainer.class);
    }

    @Override
    public Boolean updateAccountsProjectSharing(
            PutAccountsProjectRequest apRequest) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "updateAccountsProjectSharing");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureProject/accountprojects/"
                        + "updateAccountsProjectSharing"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Boolean.class, apRequest);
    }

    @Override
    public Long saveProject(SaveProjectRequest saveProjectRequest) throws
            Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "saveProject");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureProject/projects/saveProject"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .post(Long.class, saveProjectRequest);
    }

    @Override
    public Long insertProject(GPProject project) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "insertProject");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureProject/projects/insertProject"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .post(Long.class, project);
    }

    @Override
    public Long updateProject(GPProject project) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "updateProject");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureProject/projects/updateProject"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Long.class, project);
    }

    @Override
    public Boolean deleteProject(Long projectID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "deleteProject");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureProject/projects/deleteProject/"))
                .path(String.valueOf(projectID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .delete(Boolean.class);
    }

    @Override
    public GPProject getProjectDetail(Long projectID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getProjectDetail");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureProject/projects/getProjectDetail/"))
                .path(String.valueOf(projectID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GPProject.class);
    }

    @Override
    public Integer getNumberOfElementsProject(Long projectID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken,
                "getNumberOfElementsProject");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureProject/folders/getNumberOfElementsProject/"))
                .path(String.valueOf(projectID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(Integer.class);
    }

    @Override
    public void setProjectShared(Long projectID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "setProjectShared");

        client.resource(super.getRestServiceURL()
                .concat("jsonSecureProject/projects/setProjectShared"))
                .queryParam("projectID", String.valueOf(projectID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put();
    }

    @Override
    public GPViewport getDefaultViewport(Long accountProjectID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getDefaultViewport");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureViewport/viewports/getDefaultViewport/"))
                .path(String.valueOf(accountProjectID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GPViewport.class);
    }

    @Override
    public WSGetViewportResponse getAccountProjectViewports(
            Long accountProjectID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken,
                "getAccountProjectViewports");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureViewport/viewports/getAccountProjectViewports/"))
                .path(String.valueOf(accountProjectID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(WSGetViewportResponse.class);
    }

    @Override
    public Long insertViewport(InsertViewportRequest insertViewportReq)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "insertViewport");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureViewport/viewports/insertViewport"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .post(Long.class, insertViewportReq);
    }

    @Override
    public Long updateViewport(GPViewport viewport) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "updateViewport");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureViewport/viewports/updateViewport"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Long.class, viewport);
    }

    @Override
    public GPViewport getViewportById(Long idViewport) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getViewportById");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureViewport/viewports/getViewportById/"))
                .queryParam("idViewport", String.valueOf(idViewport))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GPViewport.class);
    }

    @Override
    public Boolean deleteViewport(Long viewportID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "deleteViewport");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureViewport/viewports/deleteViewport"))
                .queryParam("viewportID", String.valueOf(viewportID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .delete(Boolean.class);
    }

    @Override
    public void saveOrUpdateViewportList(ManageViewportRequest request)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "saveOrUpdateViewportList");

        client.resource(super.getRestServiceURL()
                .concat("jsonSecureViewport/viewports/saveOrUpdateViewportList"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(request);
    }

    @Override
    public void replaceViewportList(ManageViewportRequest request) throws
            Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "replaceViewportList");

        client.resource(super.getRestServiceURL()
                .concat("jsonSecureViewport/viewports/replaceViewportList"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(request);
    }

    @Override
    public Long insertFolder(InsertFolderRequest insertFolderRequest)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "insertFolder");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureFolder/folders/insertFolder"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .post(Long.class, insertFolderRequest);
    }

    @Override
    public Long updateFolder(GPFolder folder) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "updateFolder");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureFolder/folders/updateFolder"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Long.class, folder);
    }

    @Override
    public Boolean deleteFolder(Long folderID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "deleteFolder");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureFolder/folders/deleteFolder"))
                .queryParam("folderID", String.valueOf(folderID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .delete(Boolean.class);
    }

    @Override
    public Long saveFolderProperties(Long folderID, String folderName,
            boolean checked, boolean expanded) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Long saveAddedFolderAndTreeModifications(
            WSAddFolderAndTreeModificationsRequest sftModificationRequest)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken,
                "saveAddedFolderAndTreeModifications");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureFolder/folders/saveAddedFolderAndTreeModifications"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Long.class, sftModificationRequest);
    }

    @Override
    public Boolean saveDeletedFolderAndTreeModifications(
            WSDeleteFolderAndTreeModifications sdfModificationRequest)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken,
                "saveDeletedFolderAndTreeModifications");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureFolder/folders/saveDeletedFolderAndTreeModifications"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Boolean.class, sdfModificationRequest);
    }

    @Override
    public Boolean saveDragAndDropFolderAndTreeModifications(
            WSDDFolderAndTreeModifications sddfTreeModificationRequest)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken,
                "saveDragAndDropFolderAndTreeModifications");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureFolder/folders/saveDragAndDropFolderAndTreeModifications"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Boolean.class, sddfTreeModificationRequest);
    }

    @Override
    public FolderDTO getShortFolder(Long folderID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getShortFolder");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureFolder/folders/getShortFolder/"))
                .path(String.valueOf(folderID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(FolderDTO.class);
    }

    @Override
    public GPFolder getFolderDetail(Long folderID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getFolderDetail");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureFolder/folders/getFolderDetail/"))
                .path(String.valueOf(folderID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GPFolder.class);
    }

    @Override
    public ChildrenFolderStore getChildrenFolders(Long folderID) {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getChildrenFolders");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureFolder/folders/getChildrenFolders/"))
                .path(String.valueOf(folderID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(ChildrenFolderStore.class);
    }

    @Override
    public TreeFolderElementsStore getChildrenElements(Long folderID) {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getChildrenElements");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureFolder/folders/getChildrenElements/"))
                .path(String.valueOf(folderID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(TreeFolderElementsStore.class);
    }

    @Override
    public ProjectDTO getProjectWithRootFolders(Long projectID, Long accountID)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getProjectWithRootFolders");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureProject/folders/getProjectWithRootFolders/"))
                .path(String.valueOf(projectID) + "/")
                .path(String.valueOf(accountID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(ProjectDTO.class);
    }

    @Override
    public ProjectDTO getProjectWithExpandedFolders(Long projectID,
            Long accountID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken,
                "getProjectWithExpandedFolders");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureProject/folders/getProjectWithExpandedFolders/"))
                .path(String.valueOf(projectID) + "/")
                .path(String.valueOf(accountID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(ProjectDTO.class);
    }

    @Override
    public ProjectDTO exportProject(Long projectID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "exportProject");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureProject/projects/exportProject/"))
                .path(String.valueOf(projectID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(ProjectDTO.class);
    }

    @Override
    public Long importProject(ImportProjectRequest impRequest) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "importProject");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureProject/projects/importProject"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .post(Long.class, impRequest);
    }

    @Override
    public Long insertLayer(InsertLayerRequest layerRequest) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "insertLayer");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureLayer/layers/insertLayer"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .post(Long.class, layerRequest);
    }

    @Override
    public Long updateRasterLayer(GPRasterLayer layer) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "updateRasterLayer");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureLayer/layers/updateRasterLayer"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Long.class, layer);
    }

    @Override
    public Long updateVectorLayer(GPVectorLayer layer) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Boolean deleteLayer(Long layerID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "deleteLayer");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureLayer/layers/deleteLayer"))
                .queryParam("layerID", String.valueOf(layerID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .delete(Boolean.class);
    }

    @Override
    public Long saveAddedLayerAndTreeModifications(
            WSAddLayerAndTreeModificationsRequest addLayerRequest)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken,
                "saveAddedLayerAndTreeModifications");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureLayer/layers/saveAddedLayerAndTreeModifications"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .post(Long.class, addLayerRequest);
    }

    @Override
    public LongListStore saveAddedLayersAndTreeModifications(
            WSAddLayersAndTreeModificationsRequest addLayersRequest)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken,
                "saveAddedLayersAndTreeModifications");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureLayer/layers/addLayersAndTreeModifications"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .post(LongListStore.class, addLayersRequest);
    }

    @Override
    public Boolean saveDeletedLayerAndTreeModifications(
            WSDeleteLayerAndTreeModificationsRequest deleteLayerRequest)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken,
                "saveDeletedLayerAndTreeModifications");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureLayer/layers/saveDeletedLayerAndTreeModifications"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Boolean.class, deleteLayerRequest);
    }

    @Override
    public Boolean saveCheckStatusLayerAndTreeModifications(Long layerID,
            boolean checked) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken,
                "saveCheckStatusLayerAndTreeModifications");

        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.add("layerID", String.valueOf(layerID));
        params.add("checked", String.valueOf(checked));

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureLayer/layers/saveCheckStatusLayerAndTreeModifications"))
                .queryParams(params)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Boolean.class);
    }

    @Override
    public Boolean saveDragAndDropLayerAndTreeModifications(
            WSDDLayerAndTreeModificationsRequest ddLayerReq) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken,
                "saveDragAndDropLayerAndTreeModifications");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureLayer/layers/saveDragAndDropLayerAndTreeModifications"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Boolean.class, ddLayerReq);
    }

    @Override
    public Boolean saveLayerProperties(RasterPropertiesDTO layerProperties)
            throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GPRasterLayer getRasterLayer(Long layerID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getRasterLayer");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureLayer/layers/getRasterLayer/"))
                .path(String.valueOf(layerID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GPRasterLayer.class);
    }

    @Override
    public GPVectorLayer getVectorLayer(Long layerID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getVectorLayer");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureLayer/layers/getVectorLayer/"))
                .path(String.valueOf(layerID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GPVectorLayer.class);
    }

    @Override
    public ShortLayerDTO getShortLayer(Long layerID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getShortLayer");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureLayer/layers/getShortLayer/"))
                .path(String.valueOf(layerID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(ShortLayerDTO.class);
    }

    @Override
    public ShortLayerDTOContainer getLayers(Long projectID) {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getLayers");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureLayer/layers/getLayers/"))
                .path(String.valueOf(projectID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(ShortLayerDTOContainer.class);
    }

    @Override
    public GPBBox getBBox(Long layerID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getBBox");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureLayer/layers/getBBox/"))
                .path(String.valueOf(layerID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GPBBox.class);
    }

    @Override
    public GPLayerInfo getLayerInfo(Long layerID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getLayerInfo");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureLayer/layers/getLayerInfo/"))
                .path(String.valueOf(layerID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GPLayerInfo.class);
    }

    @Override
    public GPLayerType getLayerType(Long layerID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getLayerType");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureLayer/layers/getLayerType/"))
                .path(String.valueOf(layerID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GPLayerType.class);
    }

    @Override
    public GetDataSourceResponse getLayersDataSourceByProjectID(Long projectID)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken,
                "getLayersDataSourceByProjectID");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureLayer/layers/getLayersDataSourceByProjectID/"))
                .path(String.valueOf(projectID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GetDataSourceResponse.class);
    }

    @Override
    public WSGetRoleResponse getAllRoles(String organization) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getAllRoles");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureAcl/organizations/getAllRoles/"))
                .path(organization)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(WSGetRoleResponse.class);
    }

    @Override
    public GuiComponentsPermissionMapData getAccountPermission(Long accountID)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getAccountPermission");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureAcl/organizations/getAccountPermission/"))
                .path(String.valueOf(accountID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GuiComponentsPermissionMapData.class);
    }

    @Override
    public GuiComponentsPermissionMapData getRolePermission(String role,
            String organization) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getRolePermission");

        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.add("role", role);
        params.add("organization", organization);

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureAcl/organizations/getRolePermission"))
                .queryParams(params)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GuiComponentsPermissionMapData.class);
    }

    @Override
    public Boolean updateRolePermission(
            WSPutRolePermissionRequest putRolePermissionReq) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "updateRolePermission");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureAcl/organizations/updateRolePermission"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Boolean.class, putRolePermissionReq);
    }

    @Override
    public Boolean saveRole(WSSaveRoleRequest saveRoleReq) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "saveRole");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureAcl/organizations/saveRole"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .post(Boolean.class, saveRoleReq);
    }

    @Override
    public Long insertServer(GeoPlatformServer server) {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "insertServer");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureServer/servers/insertServer"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .post(Long.class, server);
    }

    @Override
    public Long updateServer(GeoPlatformServer server) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "updateServer");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureServer/servers/updateServer"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Long.class, server);
    }

    @Override
    public Boolean deleteServer(Long serverID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "deleteServer");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureServer/servers/deleteServer"))
                .queryParam("serverID", String.valueOf(serverID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .delete(Boolean.class);
    }

    @Override
    public ServerDTOContainer getAllServers(String organizazionName)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getAllServers");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureServer/servers/getAllServers/"))
                .path(organizazionName)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(ServerDTOContainer.class);
    }

    @Override
    public GeoPlatformServer getServerDetail(Long serverID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getServerDetail");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureServer/servers/getServerDetail/"))
                .path(String.valueOf(serverID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GeoPlatformServer.class);
    }

    @Override
    public ServerDTO getShortServer(String serverUrl) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getShortServer");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureServer/servers/getShortServer"))
                .queryParam("serverUrl", serverUrl)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(ServerDTO.class);
    }

    @Override
    public GeoPlatformServer getServerDetailByUrl(String serverUrl)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getServerDetailByUrl");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureServer/servers/getServerDetailByUrl"))
                .queryParam("serverUrl", serverUrl)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GeoPlatformServer.class);
    }

    @Override
    public ServerDTO saveServer(WSSaveServerRequest saveServerReq)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "saveServer");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureServer/servers/saveServer"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(ServerDTO.class, saveServerReq);
    }

    @Override
    public Long insertMessage(GPMessage message) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "insertMessage");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureMessage/messages/insertMessage"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .post(Long.class, message);
    }

    @Override
    public Boolean insertMultiMessage(MessageDTO messageDTO) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "insertMultiMessage");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureMessage/messages/insertMultiMessage"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .post(Boolean.class, messageDTO);
    }

    @Override
    public Boolean deleteMessage(Long messageID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "deleteMessage");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureMessage/messages/deleteMessage/"))
                .path(String.valueOf(messageID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .delete(Boolean.class);
    }

    @Override
    public GPMessage getMessageDetail(Long messageID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getMessageDetail");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureMessage/messages/getMessageDetail/"))
                .path(String.valueOf(messageID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GPMessage.class);
    }

    @Override
    public GetMessageResponse getAllMessagesByRecipient(Long recipientID)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken,
                "getAllMessagesByRecipient");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureMessage/messages/getAllMessagesByRecipient/"))
                .path(String.valueOf(recipientID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GetMessageResponse.class);
    }

    @Override
    public GetMessageResponse getUnreadMessagesByRecipient(Long recipientID)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken,
                "getUnreadMessagesByRecipient");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureMessage/messages/getUnreadMessagesByRecipient/"))
                .path(String.valueOf(recipientID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GetMessageResponse.class);
    }

    @Override
    public Boolean markMessageAsRead(Long messageID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "markMessageAsRead");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureMessage/messages/markMessageAsRead/"))
                .path(String.valueOf(messageID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Boolean.class);
    }

    @Override
    public Boolean markAllMessagesAsReadByRecipient(Long recipientID) throws
            Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken,
                "markAllMessagesAsReadByRecipient");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureMessage/messages/markAllMessagesAsReadByRecipient/"))
                .path(String.valueOf(recipientID))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Boolean.class);
    }

    @Override
    public Boolean markMessagesAsReadByDate(
            MarkMessageReadByDateRequest markMessageAsReadByDateReq) throws
            Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken,
                "markMessagesAsReadByDate");

        return client.resource(super.getRestServiceURL()
                .concat("jsonSecureMessage/messages/markMessagesAsReadByDate"))
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Boolean.class, markMessageAsReadByDateReq);
    }

    @Override
    public String getConnectorName() {
        return "GeoPlatform OAuth2 Core Client Connector";
    }
}
