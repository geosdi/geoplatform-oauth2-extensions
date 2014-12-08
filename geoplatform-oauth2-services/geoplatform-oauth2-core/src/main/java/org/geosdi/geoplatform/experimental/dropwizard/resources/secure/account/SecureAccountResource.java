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

import java.security.Principal;
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
import org.geosdi.geoplatform.services.core.api.resources.GPAccountResource;

/**
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public interface SecureAccountResource extends GPAccountResource {

    Long insertAccount(Principal principal,
            InsertAccountRequest insertAccountRequest) throws Exception;

    Long updateUser(Principal principal, GPUser user) throws Exception;

    Boolean deleteAccount(Principal principal, Long accountID)
            throws Exception;

    GPUser getUserDetail(Principal principal, Long userID) throws
            Exception;

    GPUser getUserDetailByUsername(Principal principal,
            String nameLike, LikePatternType likeType) throws Exception;

    GPUser getUserDetailByUsernameAndPassword(Principal principal,
            String username, String plainPassword) throws Exception;

    UserDTO getShortUser(Principal principal, Long userID) throws Exception;

    UserDTO getShortUserByUsername(Principal principal,
            SearchRequest request) throws Exception;

    UserDTO getShortUserByUsername(Principal principal, String nameLike,
            LikePatternType likeType) throws Exception;

    ShortAccountDTOContainer getAllAccounts(Principal principal);

    SearchUsersResponseWS searchUsers(Principal principal, Long userID,
            PaginatedSearchRequest request) throws Exception;

    SearchUsersResponseWS searchUsers(Principal principal, Long userID,
            Integer num, Integer page, String nameLike) throws Exception;

    ShortAccountDTOContainer getAccounts(Principal principal,
            String organization) throws Exception;

    Long getAccountsCount(Principal principal, SearchRequest request);

    Long getAccountsCount(Principal principal, String nameLike,
            LikePatternType likeType);

    Long getUsersCount(Principal principal, String organization,
            SearchRequest request);

    Long getUsersCount(Principal principal, String organization,
            String nameLike);

    GetAuthorityResponse getAuthorities(Principal principal,
            Long accountID) throws Exception;

    GetAuthoritiesResponseWS getAuthoritiesDetail(Principal principal,
            String accountNaturalID) throws Exception;

    void forceTemporaryAccount(Principal principal, Long accountID)
            throws Exception;

    void forceExpiredTemporaryAccount(Principal principal, Long accountID)
            throws Exception;

}
