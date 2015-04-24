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
package org.geosdi.geoplatform.experimental.dropwizard.resources.secure.account;

import com.codahale.metrics.annotation.Timed;
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
import org.geosdi.geoplatform.core.model.GPUser;
import org.geosdi.geoplatform.request.InsertAccountRequest;
import org.geosdi.geoplatform.request.LikePatternType;
import org.geosdi.geoplatform.request.PaginatedSearchRequest;
import org.geosdi.geoplatform.request.SearchRequest;
import org.geosdi.geoplatform.response.SearchUsersResponseWS;
import org.geosdi.geoplatform.response.ShortAccountDTOContainer;
import org.geosdi.geoplatform.response.UserDTO;
import org.geosdi.geoplatform.response.authority.GetAuthoritiesResponseWS;
import org.geosdi.geoplatform.response.authority.GetAuthorityResponse;
import org.geosdi.geoplatform.services.rs.path.GPServiceRSPathConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Path(value = GPServiceRSPathConfig.GP_SECURE_ACCOUNTS_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Component(value = "secureAccountResource")
public class GPSecureAccountResource extends BaseAccountResource {

    private static final Logger logger = LoggerFactory.getLogger(
            GPSecureAccountResource.class);

    @POST
    @Path(value = GPServiceRSPathConfig.INSERT_ACCOUNT_PATH)
    @Override
    public Long insertAccount(@Auth Principal principal,
            InsertAccountRequest insertAccountRequest) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@@Executing secure insertAccount - "
                + "Principal : {}\n\n", principal.getName());
        return super.insertAccount(insertAccountRequest);
    }

    @PUT
    @Path(value = GPServiceRSPathConfig.UPDATE_USER_PATH)
    @Override
    public Long updateUser(@Auth Principal principal,
            GPUser user) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@@Executing secure updateUser - "
                + "Principal : {}\n\n", principal.getName());
        return super.updateUser(user);
    }

    @DELETE
    @Path(value = GPServiceRSPathConfig.DELETE_ACCOUNT_PATH)
    @Override
    public Boolean deleteAccount(@Auth Principal principal,
            @QueryParam(value = "accountID") Long accountID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@@Executing secure deleteAccount - "
                + "Principal : {}\n\n", principal.getName());
        return super.deleteAccount(accountID);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_USER_DETAIL_BY_ID_PATH)
    @Override
    public GPUser getUserDetail(@Auth Principal principal,
            @PathParam(value = "userID") Long userID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@@Executing secure getUserDetail - "
                + "Principal : {}\n\n", principal.getName());
        return super.getUserDetail(userID);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_USER_DETAIL_BY_USERNAME_PATH)
    @Override
    public GPUser getUserDetailByUsername(@Auth Principal principal,
            @QueryParam("nameLike") String nameLike,
            @QueryParam("likeType") LikePatternType likeType)
            throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@@Executing secure "
                + "getUserDetailByUsername - Principal : {}\n\n",
                principal.getName());
        return super.getUserDetailByUsername(new SearchRequest(nameLike,
                likeType));
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_USER_DETAIL_BY_USERNAME_AND_PASSWORD_PATH)
    @Override
    public GPUser getUserDetailByUsernameAndPassword(@Auth Principal principal,
            @QueryParam(value = "username") String username,
            @QueryParam(value = "plainPassword") String plainPassword)
            throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@@Executing secure "
                + "getUserDetailByUsernameAndPassword - Principal : {}\n\n",
                principal.getName());
        return super.getUserDetailByUsernameAndPassword(username, plainPassword);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_SHORT_USER_BY_ID_PATH)
    @Timed
    @Override
    public UserDTO getShortUser(@Auth Principal principal, @PathParam(
            value = "userID") Long userID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@@Executing secure getShortUser "
                + "- Principal : {}\n\n", principal.getName());
        return super.getShortUser(userID);
    }

    @Override
    public UserDTO getShortUserByUsername(Principal principal,
            SearchRequest request) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@@Executing secure "
                + "getShortUserByUsername - Principal : {}\n\n",
                principal.getName());
        return super.getShortUserByUsername(request);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_SHORT_USER_BY_USERNAME_PATH)
    @Override
    public UserDTO getShortUserByUsername(@Auth Principal principal,
            @QueryParam("nameLike") String nameLike,
            @QueryParam("likeType") LikePatternType likeType) throws Exception {
        return getShortUserByUsername(principal, new SearchRequest(nameLike,
                likeType));
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_ALL_ACCOUNTS_PATH)
    @Timed
    @Override
    public ShortAccountDTOContainer getAllAccounts(@Auth Principal principal) {
        logger.debug("\n\n@@@@@@@@@@@@@@@@@Executing secure getAllAccounts "
                + "- Principal : {}\n\n", principal.getName());
        return super.getAllAccounts();
    }

    /**
     * <p>
     * Do not use this method to do a REST request but use
     * {@link #searchUsers(java.security.Principal, java.lang.Long, java.lang.Integer, java.lang.Integer, java.lang.String) searchUsers}
     * </p>
     *
     * @param principal
     * @param userID
     * @param request
     * @return SearchUsersResponseWS
     *
     * @throws Exception
     */
    @Override
    public SearchUsersResponseWS searchUsers(Principal principal, Long userID,
            PaginatedSearchRequest request) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@@Executing secure searchUsers "
                + "- Principal : {}\n\n", principal.getName());
        return super.searchUsers(userID, request);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.SEARCH_USERS_PATH)
    @Override
    public SearchUsersResponseWS searchUsers(@Auth Principal principal,
            @QueryParam(value = "userID") Long userID,
            @QueryParam(value = "num") Integer num,
            @QueryParam(value = "page") Integer page,
            @QueryParam(value = "nameLike") String nameLike) throws Exception {
        return searchUsers(userID, new PaginatedSearchRequest(nameLike, num,
                page));
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_ALL_ORGANIZATION_ACCOUNTS_PATH)
    @Override
    public ShortAccountDTOContainer getAccounts(@Auth Principal principal,
            @PathParam(value = "organization") String organization)
            throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@@Executing secure getAccounts "
                + "- Principal : {}\n\n", principal.getName());
        return super.getAccounts(organization);
    }

    @Override
    public Long getAccountsCount(@Auth Principal principal,
            SearchRequest request) {
        logger.debug("\n\n@@@@@@@@@@@@@@@@@Executing secure getAccountsCount "
                + "- Principal : {}\n\n", principal.getName());
        return super.getAccountsCount(request);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_ACCOUNTS_COUNT_PATH)
    @Override
    public Long getAccountsCount(@Auth Principal principal,
            @QueryParam(value = "nameLike") String nameLike,
            @QueryParam(value = "likeType") LikePatternType likeType) {
        return getAccountsCount(new SearchRequest(nameLike));
    }

    @Override
    public Long getUsersCount(Principal principal, String organization,
            SearchRequest request) {
        logger.debug("\n\n@@@@@@@@@@@@@@@@@Executing secure getUsersCount "
                + "- Principal : {}\n\n", principal.getName());
        return super.getUsersCount(organization, request);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_USERS_COUNT_PATH)
    @Override
    public Long getUsersCount(@Auth Principal principal,
            @QueryParam(value = "organization") String organization,
            @QueryParam("nameLike") String nameLike) {
        return this.getUsersCount(organization, new SearchRequest(nameLike));
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_AUTHORITIES_PATH)
    @Override
    public GetAuthorityResponse getAuthorities(@Auth Principal principal,
            @PathParam(value = "accountID") Long accountID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@@Executing secure getAuthorities "
                + "- Principal : {}\n\n", principal.getName());
        return super.getAuthorities(accountID);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_AUTHORITIES_BY_ACCOUNT_NATURAL_ID)
    @Override
    public GetAuthoritiesResponseWS getAuthoritiesDetail(
            @Auth Principal principal,
            @PathParam(value = "accountNaturalID") String accountNaturalID)
            throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@@Executing secure "
                + "getAuthoritiesDetail - Principal : {}\n\n",
                principal.getName());
        return super.getAuthoritiesDetail(accountNaturalID);
    }

    @PUT
    @Path(value = GPServiceRSPathConfig.FORCE_TEMPORARY_ACCOUNT_PATH)
    @Override
    public void forceTemporaryAccount(@Auth Principal principal,
            Long accountID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@@Executing secure "
                + "forceTemporaryAccount - Principal : {}\n\n",
                principal.getName());
        super.forceTemporaryAccount(accountID);
    }

    @PUT
    @Path(value = GPServiceRSPathConfig.FORCE_EXPIRED_TEMPORARY_ACCOUNT_PATH)
    @Override
    public void forceExpiredTemporaryAccount(@Auth Principal principal,
            Long accountID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@@Executing secure "
                + "forceExpiredTemporaryAccount - Principal : {}\n\n",
                principal.getName());
        super.forceExpiredTemporaryAccount(accountID);
    }

}
