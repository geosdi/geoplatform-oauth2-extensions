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
package org.geosdi.geoplatform.services.dropwizard.resources.secure.account;

import java.util.List;
import org.geosdi.geoplatform.core.delegate.api.account.AccountDelegate;
import org.geosdi.geoplatform.core.model.GPAuthority;
import org.geosdi.geoplatform.core.model.GPUser;
import org.geosdi.geoplatform.request.InsertAccountRequest;
import org.geosdi.geoplatform.request.PaginatedSearchRequest;
import org.geosdi.geoplatform.request.SearchRequest;
import org.geosdi.geoplatform.responce.ShortAccountDTOContainer;
import org.geosdi.geoplatform.responce.UserDTO;
import org.geosdi.geoplatform.responce.authority.GetAuthorityResponse;

/**
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
abstract class BaseAccountResource implements SecureAccountResouce {

    protected AccountDelegate gpAccountDelegate;

    @Override
    public Long insertAccount(InsertAccountRequest insertAccountRequest) throws
            Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Long updateUser(GPUser user) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Boolean deleteAccount(Long accountID) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GPUser getUserDetail(Long userID) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GPUser getUserDetailByUsername(SearchRequest request) throws
            Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GPUser getUserDetailByUsernameAndPassword(String username,
            String plainPassword) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public UserDTO getShortUser(Long userID) throws
            Exception {
        return this.gpAccountDelegate.getShortUser(userID);
    }

    @Override
    public UserDTO getShortUserByUsername(SearchRequest request)
            throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<UserDTO> searchUsers(Long userID, PaginatedSearchRequest request)
            throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ShortAccountDTOContainer getAllAccounts() {
        return this.gpAccountDelegate.getAllAccounts();
    }

    @Override
    public ShortAccountDTOContainer getAccounts(String organization) throws
            Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Long getAccountsCount(SearchRequest request) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Long getUsersCount(String organization, SearchRequest request) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GetAuthorityResponse getAuthorities(Long accountID) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<GPAuthority> getAuthoritiesDetail(String accountNaturalID)
            throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void forceTemporaryAccount(Long accountID) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void forceExpiredTemporaryAccount(Long accountID) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @param theGpAccountDelegate the gpAccountDelegate to set
     */
    public void setGpAccountDelegate(AccountDelegate theGpAccountDelegate) {
        this.gpAccountDelegate = theGpAccountDelegate;
    }

}
