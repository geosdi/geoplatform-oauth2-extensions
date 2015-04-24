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

import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
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
import org.geosdi.geoplatform.request.LikePatternType;
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

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureOrganization/organizations/insertOrganization"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .post(Entity.entity(organization, MediaType.APPLICATION_JSON),
                        Long.class);
    }

    @Override
    public Boolean deleteOrganization(Long organizationID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "deleteOrganization");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureOrganization/organizations/deleteOrganization/"))
                .path(String.valueOf(organizationID))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .delete(Boolean.class);
    }

    @Override
    public Long insertAccount(InsertAccountRequest insertAccountRequest)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "insertAccount");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureAccount/accounts/insertAccount"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .post(Entity.entity(insertAccountRequest,
                                MediaType.APPLICATION_JSON), Long.class);
    }

    @Override
    public Long updateUser(GPUser user) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "updateUser");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureAccount/accounts/updateUser"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .post(Entity.entity(user, MediaType.APPLICATION_JSON),
                        Long.class);
    }

    @Override
    public Boolean deleteAccount(Long accountID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "deleteAccount");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureAccount/accounts/deleteAccount/"))
                .queryParam("accountID", String.valueOf(accountID))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .delete(Boolean.class);
    }

    @Override
    public GPUser getUserDetail(Long userID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getUserDetail");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureAccount/accounts/getUserDetail/"))
                .path(String.valueOf(userID))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GPUser.class);
    }

    @Override
    public GPUser getUserDetailByUsername(SearchRequest request)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getUserDetailByUsername");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureAccount/accounts/getUserDetail/"
                        + "getUserDetailByUsername"))
                .queryParam("nameLike", request.getNameLike())
                .queryParam("likeType", (request.getLikeType() != null)
                                ? request.getLikeType()
                                : LikePatternType.CONTAINS)
                .request(MediaType.APPLICATION_JSON)
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

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureAccount/accounts/getUserDetail/"
                        + "getUserDetailByUsernameAndPassword"))
                .queryParam("username", username)
                .queryParam("plainPassword", plainPassword)
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GPUser.class);
    }

    @Override
    public UserDTO getShortUser(Long userID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getShortUser");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureAccount/accounts/getShortUser/"))
                .path(String.valueOf(userID))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(UserDTO.class);
    }

    @Override
    public UserDTO getShortUserByUsername(SearchRequest request)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getShortUserByUsername");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureAccount/accounts/getShortUserByUsername"))
                .queryParam("nameLike", request.getNameLike())
                .queryParam("likeType", (request.getLikeType() != null)
                                ? request.getLikeType()
                                : LikePatternType.CONTAINS)
                .request(MediaType.APPLICATION_JSON)
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

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureAccount/accounts/searchUsers"))
                .queryParam("userID", String.valueOf(userID))
                .queryParam("num", String.valueOf(request.getNum()))
                .queryParam("page", String.valueOf(request.getPage()))
                .queryParam("nameLike", request.getNameLike())
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(SearchUsersResponseWS.class);
    }

    @Override
    public ShortAccountDTOContainer getAllAccounts() {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getAllAccounts");

        return client.target(super.getRestServiceURL().concat(
                "jsonSecureAccount/accounts/getAllAccounts"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(ShortAccountDTOContainer.class);
    }

    @Override
    public ShortAccountDTOContainer getAccounts(String organization)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getAccounts");

        return client.target(super.getRestServiceURL().concat(
                "jsonSecureAccount/accounts/getAllOrganizationAccount/"))
                .path(organization)
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(ShortAccountDTOContainer.class);
    }

    @Override
    public Long getAccountsCount(SearchRequest request) {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getAccountsCount");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureAccount/accounts/getAccountsCount"))
                .queryParam("nameLike", request.getNameLike())
                .queryParam("likeType", (request.getLikeType() != null)
                                ? request.getLikeType()
                                : LikePatternType.CONTAINS)
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(Long.class);
    }

    @Override
    public Long getUsersCount(String organization, SearchRequest request) {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getUsersCount");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureAccount/accounts/getUsersCount"))
                .queryParam("organization", organization)
                .queryParam("nameLike", request.getNameLike())
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(Long.class);
    }

    @Override
    public GetAuthorityResponse getAuthorities(Long accountID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getAuthorities");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureAccount/authorities/getAuthorities/"))
                .path(String.valueOf(accountID))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GetAuthorityResponse.class);
    }

    @Override
    public GetAuthoritiesResponseWS getAuthoritiesDetail(String accountNaturalID)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getAuthoritiesDetail");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureAccount/authorities/getAuthoritiesByAccountNaturalID/"))
                .path(String.valueOf(accountNaturalID))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GetAuthoritiesResponseWS.class);
    }

    @Override
    public void forceTemporaryAccount(Long accountID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "forceTemporaryAccount");

        client.target(super.getRestServiceURL()
                .concat("jsonSecureAccount/accounts/forceTemporaryAccount"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Entity.entity(accountID, MediaType.APPLICATION_JSON));
    }

    @Override
    public void forceExpiredTemporaryAccount(Long accountID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "forceExpiredTemporaryAccount");

        client.target(super.getRestServiceURL()
                .concat("jsonSecureAccount/accounts/forceExpiredTemporaryAccount"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Entity.entity(accountID, MediaType.APPLICATION_JSON));
    }

    @Override
    public Long insertAccountProject(GPAccountProject accountProject)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "insertAccountProject");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureProject/accountprojects/insertAccountProject"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .post(Entity.entity(accountProject, MediaType.APPLICATION_JSON),
                        Long.class);
    }

    @Override
    public Long updateAccountProject(GPAccountProject accountProject)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "updateAccountProject");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureProject/accountprojects/updateAccountProject"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Entity.entity(accountProject, MediaType.APPLICATION_JSON),
                        Long.class);
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

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureProject/accountprojects/getAccountProject/"))
                .path(String.valueOf(accountProjectID))
                .request(MediaType.APPLICATION_JSON)
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

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureProject/accountprojects/getAccountProjectsByAccountID/"))
                .path(String.valueOf(accountID))
                .request(MediaType.APPLICATION_JSON)
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

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureProject/accountprojects/getAccountProjectsByProjectID/"))
                .path(String.valueOf(String.valueOf(projectID)))
                .request(MediaType.APPLICATION_JSON)
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

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureProject/accountprojects/"
                        + "getAccountProjectByAccountAndProjectIDs/"))
                .path(String.valueOf(String.valueOf(accountID)) + "/")
                .path(String.valueOf(String.valueOf(projectID)))
                .request(MediaType.APPLICATION_JSON)
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

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureProject/accountprojects/getAccountProjectsCount"))
                .queryParam("accountID", String.valueOf(accountID))
                .queryParam("nameLike", request.getNameLike())
                .queryParam("likeType", (request.getLikeType() != null)
                                ? request.getNameLike()
                                : LikePatternType.CONTAINS)
                .request(MediaType.APPLICATION_JSON)
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

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureProject/accountprojects/getProjectOwner/"))
                .path(String.valueOf(String.valueOf(projectID)))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GPAccountProject.class);
    }

    @Override
    public Boolean setProjectOwner(RequestByAccountProjectIDs request)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "setProjectOwner");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureProject/accountprojects/setProjectOwner"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Entity.entity(request, MediaType.APPLICATION_JSON),
                        Boolean.class);
    }

    @Override
    public GPProject getDefaultProject(Long accountID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getDefaultProject");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureProject/projects/getDefaultProject/"))
                .path(String.valueOf(accountID))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GPProject.class);
    }

    @Override
    public ProjectDTO getDefaultProjectDTO(Long accountID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getDefaultProject");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureProject/projects/getDefaultProjectDTO/"))
                .path(String.valueOf(accountID))
                .request(MediaType.APPLICATION_JSON)
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

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureProject/projects/saveAccountProjectProperties"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Entity.entity(accountProjectProperties,
                                MediaType.APPLICATION_JSON), Boolean.class);
    }

    @Override
    public ShortAccountDTOContainer getAccountsByProjectID(Long projectID)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getAccountsByProjectID");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureProject/accountprojects/getAccountsByProject/"))
                .path(String.valueOf(projectID))
                .request(MediaType.APPLICATION_JSON)
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

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureProject/accountprojects/getAccountsToShare/"))
                .path(String.valueOf(projectID))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(ShortAccountDTOContainer.class);
    }

    @Override
    public Boolean updateAccountsProjectSharing(
            PutAccountsProjectRequest apRequest) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "updateAccountsProjectSharing");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureProject/accountprojects/"
                        + "updateAccountsProjectSharing"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Entity.entity(apRequest, MediaType.APPLICATION_JSON),
                        Boolean.class);
    }

    @Override
    public Long saveProject(SaveProjectRequest saveProjectRequest) throws
            Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "saveProject");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureProject/projects/saveProject"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .post(Entity.entity(saveProjectRequest,
                                MediaType.APPLICATION_JSON), Long.class);
    }

    @Override
    public Long insertProject(GPProject project) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "insertProject");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureProject/projects/insertProject"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .post(Entity.entity(project, MediaType.APPLICATION_JSON),
                        Long.class);
    }

    @Override
    public Long updateProject(GPProject project) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "updateProject");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureProject/projects/updateProject"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Entity.entity(project, MediaType.APPLICATION_JSON),
                        Long.class);
    }

    @Override
    public Boolean deleteProject(Long projectID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "deleteProject");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureProject/projects/deleteProject/"))
                .path(String.valueOf(projectID))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .delete(Boolean.class);
    }

    @Override
    public GPProject getProjectDetail(Long projectID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getProjectDetail");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureProject/projects/getProjectDetail/"))
                .path(String.valueOf(projectID))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GPProject.class);
    }

    @Override
    public Integer getNumberOfElementsProject(Long projectID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken,
                "getNumberOfElementsProject");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureProject/folders/getNumberOfElementsProject/"))
                .path(String.valueOf(projectID))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(Integer.class);
    }

    @Override
    public void setProjectShared(Long projectID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "setProjectShared");

        client.target(super.getRestServiceURL()
                .concat("jsonSecureProject/projects/setProjectShared"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Entity.entity(projectID, MediaType.APPLICATION_JSON));
    }

    @Override
    public GPViewport getDefaultViewport(Long accountProjectID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getDefaultViewport");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureViewport/viewports/getDefaultViewport/"))
                .path(String.valueOf(accountProjectID))
                .request(MediaType.APPLICATION_JSON)
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

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureViewport/viewports/getAccountProjectViewports/"))
                .path(String.valueOf(accountProjectID))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(WSGetViewportResponse.class);
    }

    @Override
    public Long insertViewport(InsertViewportRequest insertViewportReq)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "insertViewport");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureViewport/viewports/insertViewport"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .post(Entity.entity(insertViewportReq,
                                MediaType.APPLICATION_JSON), Long.class);
    }

    @Override
    public Long updateViewport(GPViewport viewport) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "updateViewport");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureViewport/viewports/updateViewport"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Entity.entity(viewport, MediaType.APPLICATION_JSON),
                        Long.class);
    }

    @Override
    public GPViewport getViewportById(Long idViewport) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getViewportById");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureViewport/viewports/getViewportById/"))
                .queryParam("idViewport", String.valueOf(idViewport))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GPViewport.class);
    }

    @Override
    public Boolean deleteViewport(Long viewportID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "deleteViewport");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureViewport/viewports/deleteViewport"))
                .queryParam("viewportID", String.valueOf(viewportID))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .delete(Boolean.class);
    }

    @Override
    public void saveOrUpdateViewportList(ManageViewportRequest request)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "saveOrUpdateViewportList");

        client.target(super.getRestServiceURL()
                .concat("jsonSecureViewport/viewports/saveOrUpdateViewportList"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Entity.entity(request, MediaType.APPLICATION_JSON));
    }

    @Override
    public void replaceViewportList(ManageViewportRequest request) throws
            Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "replaceViewportList");

        client.target(super.getRestServiceURL()
                .concat("jsonSecureViewport/viewports/replaceViewportList"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Entity.entity(request, MediaType.APPLICATION_JSON));
    }

    @Override
    public Long insertFolder(InsertFolderRequest insertFolderRequest)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "insertFolder");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureFolder/folders/insertFolder"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .post(Entity.entity(insertFolderRequest,
                                MediaType.APPLICATION_JSON), Long.class);
    }

    @Override
    public Long updateFolder(GPFolder folder) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "updateFolder");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureFolder/folders/updateFolder"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Entity.entity(folder, MediaType.APPLICATION_JSON),
                        Long.class);
    }

    @Override
    public Boolean deleteFolder(Long folderID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "deleteFolder");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureFolder/folders/deleteFolder"))
                .queryParam("folderID", String.valueOf(folderID))
                .request(MediaType.APPLICATION_JSON)
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

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureFolder/folders/saveAddedFolderAndTreeModifications"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Entity.entity(sftModificationRequest,
                                MediaType.APPLICATION_JSON), Long.class);
    }

    @Override
    public Boolean saveDeletedFolderAndTreeModifications(
            WSDeleteFolderAndTreeModifications sdfModificationRequest)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken,
                "saveDeletedFolderAndTreeModifications");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureFolder/folders/saveDeletedFolderAndTreeModifications"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Entity.entity(sdfModificationRequest,
                                MediaType.APPLICATION_JSON), Boolean.class);
    }

    @Override
    public Boolean saveDragAndDropFolderAndTreeModifications(
            WSDDFolderAndTreeModifications sddfTreeModificationRequest)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken,
                "saveDragAndDropFolderAndTreeModifications");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureFolder/folders/saveDragAndDropFolderAndTreeModifications"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Entity.entity(sddfTreeModificationRequest,
                                MediaType.APPLICATION_JSON), Boolean.class);
    }

    @Override
    public FolderDTO getShortFolder(Long folderID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getShortFolder");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureFolder/folders/getShortFolder/"))
                .path(String.valueOf(folderID))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(FolderDTO.class);
    }

    @Override
    public GPFolder getFolderDetail(Long folderID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getFolderDetail");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureFolder/folders/getFolderDetail/"))
                .path(String.valueOf(folderID))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GPFolder.class);
    }

    @Override
    public ChildrenFolderStore getChildrenFolders(Long folderID) {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getChildrenFolders");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureFolder/folders/getChildrenFolders/"))
                .path(String.valueOf(folderID))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(ChildrenFolderStore.class);
    }

    @Override
    public TreeFolderElementsStore getChildrenElements(Long folderID) {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getChildrenElements");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureFolder/folders/getChildrenElements/"))
                .path(String.valueOf(folderID))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(TreeFolderElementsStore.class);
    }

    @Override
    public ProjectDTO getProjectWithRootFolders(Long projectID, Long accountID)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getProjectWithRootFolders");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureProject/folders/getProjectWithRootFolders/"))
                .path(String.valueOf(projectID) + "/")
                .path(String.valueOf(accountID))
                .request(MediaType.APPLICATION_JSON)
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

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureProject/folders/getProjectWithExpandedFolders/"))
                .path(String.valueOf(projectID) + "/")
                .path(String.valueOf(accountID))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(ProjectDTO.class);
    }

    @Override
    public ProjectDTO exportProject(Long projectID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "exportProject");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureProject/projects/exportProject/"))
                .path(String.valueOf(projectID))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(ProjectDTO.class);
    }

    @Override
    public Long importProject(ImportProjectRequest impRequest) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "importProject");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureProject/projects/importProject"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .post(Entity.entity(impRequest, MediaType.APPLICATION_JSON),
                        Long.class);
    }

    @Override
    public Long insertLayer(InsertLayerRequest layerRequest) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "insertLayer");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureLayer/layers/insertLayer"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .post(Entity.entity(layerRequest, MediaType.APPLICATION_JSON),
                        Long.class);
    }

    @Override
    public Long updateRasterLayer(GPRasterLayer layer) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "updateRasterLayer");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureLayer/layers/updateRasterLayer"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Entity.entity(layer, MediaType.APPLICATION_JSON),
                        Long.class);
    }

    @Override
    public Long updateVectorLayer(GPVectorLayer layer) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Boolean deleteLayer(Long layerID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "deleteLayer");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureLayer/layers/deleteLayer"))
                .queryParam("layerID", String.valueOf(layerID))
                .request(MediaType.APPLICATION_JSON)
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

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureLayer/layers/saveAddedLayerAndTreeModifications"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .post(Entity.entity(addLayerRequest, MediaType.APPLICATION_JSON),
                        Long.class);
    }

    @Override
    public LongListStore saveAddedLayersAndTreeModifications(
            WSAddLayersAndTreeModificationsRequest addLayersRequest)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken,
                "saveAddedLayersAndTreeModifications");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureLayer/layers/addLayersAndTreeModifications"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .post(Entity.entity(addLayersRequest, MediaType.APPLICATION_JSON),
                        LongListStore.class);
    }

    @Override
    public Boolean saveDeletedLayerAndTreeModifications(
            WSDeleteLayerAndTreeModificationsRequest deleteLayerRequest)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken,
                "saveDeletedLayerAndTreeModifications");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureLayer/layers/saveDeletedLayerAndTreeModifications"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Entity.entity(deleteLayerRequest,
                                MediaType.APPLICATION_JSON), Boolean.class);
    }

    @Override
    public Boolean saveCheckStatusLayerAndTreeModifications(Long layerID,
            boolean checked) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken,
                "saveCheckStatusLayerAndTreeModifications");

        MultivaluedMap<String, String> formData = new MultivaluedHashMap();
        formData.add("layerID", String.valueOf(layerID));
        formData.add("checked", String.valueOf(checked));

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureLayer/layers/saveCheckStatusLayerAndTreeModifications"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Entity.form(formData),
                        Boolean.class);
    }

    @Override
    public Boolean saveDragAndDropLayerAndTreeModifications(
            WSDDLayerAndTreeModificationsRequest ddLayerReq) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken,
                "saveDragAndDropLayerAndTreeModifications");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureLayer/layers/saveDragAndDropLayerAndTreeModifications"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Entity.entity(ddLayerReq, MediaType.APPLICATION_JSON),
                        Boolean.class);
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

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureLayer/layers/getRasterLayer/"))
                .path(String.valueOf(layerID))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GPRasterLayer.class);
    }

    @Override
    public GPVectorLayer getVectorLayer(Long layerID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getVectorLayer");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureLayer/layers/getVectorLayer/"))
                .path(String.valueOf(layerID))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GPVectorLayer.class);
    }

    @Override
    public ShortLayerDTO getShortLayer(Long layerID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getShortLayer");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureLayer/layers/getShortLayer/"))
                .path(String.valueOf(layerID))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(ShortLayerDTO.class);
    }

    @Override
    public ShortLayerDTOContainer getLayers(Long projectID) {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getLayers");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureLayer/layers/getLayers/"))
                .path(String.valueOf(projectID))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(ShortLayerDTOContainer.class);
    }

    @Override
    public GPBBox getBBox(Long layerID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getBBox");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureLayer/layers/getBBox/"))
                .path(String.valueOf(layerID))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GPBBox.class);
    }

    @Override
    public GPLayerInfo getLayerInfo(Long layerID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getLayerInfo");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureLayer/layers/getLayerInfo/"))
                .path(String.valueOf(layerID))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GPLayerInfo.class);
    }

    @Override
    public GPLayerType getLayerType(Long layerID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getLayerType");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureLayer/layers/getLayerType/"))
                .path(String.valueOf(layerID))
                .request(MediaType.APPLICATION_JSON)
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

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureLayer/layers/getLayersDataSourceByProjectID/"))
                .path(String.valueOf(projectID))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GetDataSourceResponse.class);
    }

    @Override
    public WSGetRoleResponse getAllRoles(String organization) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getAllRoles");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureAcl/organizations/getAllRoles/"))
                .path(organization)
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(WSGetRoleResponse.class);
    }

    @Override
    public GuiComponentsPermissionMapData getAccountPermission(Long accountID)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getAccountPermission");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureAcl/organizations/getAccountPermission/"))
                .path(String.valueOf(accountID))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GuiComponentsPermissionMapData.class);
    }

    @Override
    public GuiComponentsPermissionMapData getRolePermission(String role,
            String organization) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getRolePermission");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureAcl/organizations/getRolePermission"))
                .queryParam("role", role)
                .queryParam("organization", organization)
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GuiComponentsPermissionMapData.class);
    }

    @Override
    public Boolean updateRolePermission(
            WSPutRolePermissionRequest putRolePermissionReq) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "updateRolePermission");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureAcl/organizations/updateRolePermission"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Entity.entity(putRolePermissionReq,
                                MediaType.APPLICATION_JSON), Boolean.class);
    }

    @Override
    public Boolean saveRole(WSSaveRoleRequest saveRoleReq) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "saveRole");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureAcl/organizations/saveRole"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .post(Entity.entity(saveRoleReq, MediaType.APPLICATION_JSON),
                        Boolean.class);
    }

    @Override
    public Long insertServer(GeoPlatformServer server) {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "insertServer");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureServer/servers/insertServer"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .post(Entity.entity(server, MediaType.APPLICATION_JSON),
                        Long.class);
    }

    @Override
    public Long updateServer(GeoPlatformServer server) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "updateServer");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureServer/servers/updateServer"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Entity.entity(server, MediaType.APPLICATION_JSON),
                        Long.class);
    }

    @Override
    public Boolean deleteServer(Long serverID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "deleteServer");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureServer/servers/deleteServer"))
                .queryParam("serverID", String.valueOf(serverID))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .delete(Boolean.class);
    }

    @Override
    public ServerDTOContainer getAllServers(String organizazionName)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getAllServers");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureServer/servers/getAllServers/"))
                .path(organizazionName)
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(ServerDTOContainer.class);
    }

    @Override
    public GeoPlatformServer getServerDetail(Long serverID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getServerDetail");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureServer/servers/getServerDetail/"))
                .path(String.valueOf(serverID))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GeoPlatformServer.class);
    }

    @Override
    public ServerDTO getShortServer(String serverUrl) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getShortServer");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureServer/servers/getShortServer"))
                .queryParam("serverUrl", serverUrl)
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(ServerDTO.class);
    }

    @Override
    public GeoPlatformServer getServerDetailByUrl(String serverUrl)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getServerDetailByUrl");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureServer/servers/getServerDetailByUrl"))
                .queryParam("serverUrl", serverUrl)
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GeoPlatformServer.class);
    }

    @Override
    public ServerDTO saveServer(WSSaveServerRequest saveServerReq)
            throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "saveServer");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureServer/servers/saveServer"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Entity.entity(saveServerReq, MediaType.APPLICATION_JSON),
                        ServerDTO.class);
    }

    @Override
    public Long insertMessage(GPMessage message) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "insertMessage");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureMessage/messages/insertMessage"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .post(Entity.entity(message, MediaType.APPLICATION_JSON),
                        Long.class);
    }

    @Override
    public Boolean insertMultiMessage(MessageDTO messageDTO) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "insertMultiMessage");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureMessage/messages/insertMultiMessage"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .post(Entity.entity(messageDTO, MediaType.APPLICATION_JSON),
                        Boolean.class);
    }

    @Override
    public Boolean deleteMessage(Long messageID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "deleteMessage");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureMessage/messages/deleteMessage/"))
                .path(String.valueOf(messageID))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .delete(Boolean.class);
    }

    @Override
    public GPMessage getMessageDetail(Long messageID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "getMessageDetail");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureMessage/messages/getMessageDetail/"))
                .path(String.valueOf(messageID))
                .request(MediaType.APPLICATION_JSON)
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

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureMessage/messages/getAllMessagesByRecipient/"))
                .path(String.valueOf(recipientID))
                .request(MediaType.APPLICATION_JSON)
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

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureMessage/messages/getUnreadMessagesByRecipient/"))
                .path(String.valueOf(recipientID))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .get(GetMessageResponse.class);
    }

    @Override
    public Boolean markMessageAsRead(Long messageID) throws Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken, "markMessageAsRead");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureMessage/messages/markMessageAsRead/"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Entity.entity(messageID, MediaType.APPLICATION_JSON),
                        Boolean.class);
    }

    @Override
    public Boolean markAllMessagesAsReadByRecipient(Long recipientID) throws
            Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken,
                "markAllMessagesAsReadByRecipient");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureMessage/messages/markAllMessagesAsReadByRecipient/"))
                //                .path(String.valueOf(recipientID))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Entity.entity(recipientID, MediaType.APPLICATION_JSON),
                        Boolean.class);
    }

    @Override
    public Boolean markMessagesAsReadByDate(
            MarkMessageReadByDateRequest markMessageAsReadByDateReq) throws
            Exception {
        String accessToken = super.createToken();

        logger.trace(LOGGER_MESSAGE, accessToken,
                "markMessagesAsReadByDate");

        return client.target(super.getRestServiceURL()
                .concat("jsonSecureMessage/messages/markMessagesAsReadByDate"))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,
                        "bearer ".concat(accessToken))
                .put(Entity.entity(markMessageAsReadByDateReq,
                                MediaType.APPLICATION_JSON), Boolean.class);
    }

    @Override
    public String getConnectorName() {
        return "GeoPlatform OAuth2 Core Client Connector";
    }
}
