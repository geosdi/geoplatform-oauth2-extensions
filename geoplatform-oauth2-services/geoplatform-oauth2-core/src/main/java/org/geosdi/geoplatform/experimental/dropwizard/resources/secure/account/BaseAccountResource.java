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

import javax.annotation.Resource;
import org.geosdi.geoplatform.core.model.GPUser;
import org.geosdi.geoplatform.experimental.dropwizard.delegate.SecureCoreDelegate;
import org.geosdi.geoplatform.request.InsertAccountRequest;
import org.geosdi.geoplatform.request.PaginatedSearchRequest;
import org.geosdi.geoplatform.request.SearchRequest;
import org.geosdi.geoplatform.response.SearchUsersResponseWS;
import org.geosdi.geoplatform.response.ShortAccountDTOContainer;
import org.geosdi.geoplatform.response.UserDTO;
import org.geosdi.geoplatform.response.authority.GetAuthoritiesResponseWS;
import org.geosdi.geoplatform.response.authority.GetAuthorityResponse;

/**
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
abstract class BaseAccountResource implements SecureAccountResource {

    @Resource(name = "gpSecureCoreDelegate")
    protected SecureCoreDelegate gpSecureCoreDelegate;

    @Override
    public Long insertAccount(InsertAccountRequest insertAccountRequest) throws
            Exception {
        return this.gpSecureCoreDelegate.insertAccount(insertAccountRequest);
    }

    @Override
    public Long updateUser(GPUser user) throws Exception {
        return this.gpSecureCoreDelegate.updateUser(user);
    }

    @Override
    public Boolean deleteAccount(Long accountID) throws Exception {
        return this.gpSecureCoreDelegate.deleteAccount(accountID);
    }

    @Override
    public GPUser getUserDetail(Long userID) throws Exception {
        return this.gpSecureCoreDelegate.getUserDetail(userID);
    }

    @Override
    public GPUser getUserDetailByUsername(SearchRequest request) throws
            Exception {
        return this.gpSecureCoreDelegate.getUserDetailByUsername(request);
    }

    @Override
    public GPUser getUserDetailByUsernameAndPassword(String username,
            String plainPassword) throws Exception {
        return this.gpSecureCoreDelegate.getUserDetailByUsernameAndPassword(
                username, plainPassword);
    }

    @Override
    public UserDTO getShortUser(Long userID) throws
            Exception {
        return this.gpSecureCoreDelegate.getShortUser(userID);
    }

    @Override
    public UserDTO getShortUserByUsername(SearchRequest request)
            throws Exception {
        return this.gpSecureCoreDelegate.getShortUserByUsername(request);
    }

    @Override
    public SearchUsersResponseWS searchUsers(Long userID, PaginatedSearchRequest request)
            throws Exception {
        return this.gpSecureCoreDelegate.searchUsers(userID, request);
    }

    @Override
    public ShortAccountDTOContainer getAllAccounts() {
        return this.gpSecureCoreDelegate.getAllAccounts();
    }

    @Override
    public ShortAccountDTOContainer getAccounts(String organization) throws
            Exception {
        return this.gpSecureCoreDelegate.getAccounts(organization);
    }

    @Override
    public Long getAccountsCount(SearchRequest request) {
        return this.gpSecureCoreDelegate.getAccountsCount(request);
    }

    @Override
    public Long getUsersCount(String organization, SearchRequest request) {
        return this.gpSecureCoreDelegate.getUsersCount(organization, request);
    }

    @Override
    public GetAuthorityResponse getAuthorities(Long accountID) throws Exception {
        return this.gpSecureCoreDelegate.getAuthorities(accountID);
    }

    @Override
    public GetAuthoritiesResponseWS getAuthoritiesDetail(String accountNaturalID)
            throws Exception {
        return this.gpSecureCoreDelegate.getAuthoritiesDetail(accountNaturalID);
    }

    @Override
    public void forceTemporaryAccount(Long accountID) throws Exception {
        this.gpSecureCoreDelegate.forceTemporaryAccount(accountID);
    }

    @Override
    public void forceExpiredTemporaryAccount(Long accountID) throws Exception {
        this.gpSecureCoreDelegate.forceExpiredTemporaryAccount(accountID);
    }
}
