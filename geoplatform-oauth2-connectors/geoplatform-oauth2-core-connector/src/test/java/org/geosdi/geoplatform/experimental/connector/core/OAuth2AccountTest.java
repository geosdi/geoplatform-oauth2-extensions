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
package org.geosdi.geoplatform.experimental.connector.core;

import com.sun.jersey.api.client.UniformInterfaceException;
import java.util.List;
import org.geosdi.geoplatform.core.model.GPAuthority;
import org.geosdi.geoplatform.core.model.GPOrganization;
import org.geosdi.geoplatform.core.model.GPUser;
import org.geosdi.geoplatform.exception.rs.GPRestExceptionMessage;
import org.geosdi.geoplatform.gui.shared.GPRole;
import org.geosdi.geoplatform.request.InsertAccountRequest;
import org.geosdi.geoplatform.request.LikePatternType;
import org.geosdi.geoplatform.request.PaginatedSearchRequest;
import org.geosdi.geoplatform.request.SearchRequest;
import org.geosdi.geoplatform.response.ApplicationDTO;
import org.geosdi.geoplatform.response.ShortAccountDTO;
import org.geosdi.geoplatform.response.UserDTO;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class OAuth2AccountTest extends OAuth2CoreServiceTest {

    @Test
    public void testSecureAllAccounts() {
        List<ShortAccountDTO> accountList = oauth2CoreClientConnector
                .getAllAccounts().getAccounts();
        Assert.assertNotNull(accountList);
        logger.info("\n*** Number of Accounts into DB: {} ***",
                accountList.size());
        for (ShortAccountDTO account : accountList) {
            if (account instanceof UserDTO) {
                logger.info("\n*** User into DB:\n{}\n***", (UserDTO) account);
            } else if (account instanceof ApplicationDTO) {
                logger.info("\n*** Application into DB:\n{}\n***",
                        (ApplicationDTO) account);
            }
        }

    }

    @Test
    public void testSecureAllOrganizationAccounts() throws Exception {
        // Initial test
        List<ShortAccountDTO> accountList = oauth2CoreClientConnector.getAccounts(
                organizationNameRSTest).getAccounts();
        Assert.assertNotNull(accountList);
        int numAccounts = accountList.size();
        logger.info("\n*** Number of Accounts for Organization \"{}\": {} ***",
                organizationNameRSTest, numAccounts);
        for (ShortAccountDTO account : accountList) {
            Assert.assertEquals(organizationNameRSTest,
                    account.getOrganization());
        }

        // Insert User of the organization for test
        this.createAndInsertUser("to_search", organizationTest, GPRole.USER);

        // Insert the other Organization and a User for it
        GPOrganization otherOrganization = new GPOrganization(
                "other_organization_ws_test");
        Long otherOrganizationID = oauth2CoreClientConnector.insertOrganization(
                otherOrganization);
        this.createAndInsertUser("none_search", otherOrganization, GPRole.USER);

        // Final test
        accountList = oauth2CoreClientConnector.getAccounts(
                organizationNameRSTest).getAccounts();
        Assert.assertNotNull(accountList);
        Assert.assertEquals(numAccounts + 1, accountList.size());
        for (ShortAccountDTO account : accountList) {
            Assert.assertEquals(organizationNameRSTest,
                    account.getOrganization());
        }

        // Delete the other Organization
        oauth2CoreClientConnector.deleteOrganization(otherOrganizationID);

    }

    @Test(expected = UniformInterfaceException.class)
    public void testSecureAllOrganizationAccountsIncorrect() throws Exception {
        String wrongOrganizationName = organizationNameRSTest + "_";
        oauth2CoreClientConnector.getAccounts(wrongOrganizationName);

    }

    @Test
    public void testSecureRetrieveUser() throws Exception {
        // Number of Account Like
        long numAccountsLike = oauth2CoreClientConnector.getAccountsCount(
                new SearchRequest(usernameTest, LikePatternType.CONTENT_EQUALS));
        Assert.assertEquals("Number of Account Like", 1L, numAccountsLike);

        // Get User from Id
        // Get UserDTO from Id
        UserDTO userDTOFromWS = oauth2CoreClientConnector.getShortUser(
                idUserTest);
        Assert.assertNotNull(userDTOFromWS);
        Assert.assertEquals("Error found UserDTO from Id", idUserTest,
                userDTOFromWS.getId().longValue());
        // Get GPUser from Id
        GPUser userFromWS = oauth2CoreClientConnector.getUserDetail(idUserTest);
        Assert.assertNotNull(userFromWS);
        Assert.assertEquals("Error found GPUser from Id", idUserTest,
                userFromWS.getId().longValue());

        // Get User from Username
        // Get UserDTO from Username
        userDTOFromWS = oauth2CoreClientConnector.getShortUserByUsername(
                new SearchRequest(usernameTest, LikePatternType.CONTENT_EQUALS));
        Assert.assertNotNull(userDTOFromWS);
        Assert.assertEquals("Error found UserDTO from Username", idUserTest,
                userDTOFromWS.getId().longValue());
        // Get GPUser from Username
        userFromWS = oauth2CoreClientConnector.getUserDetailByUsername(
                new SearchRequest(usernameTest, LikePatternType.CONTENT_EQUALS));
        Assert.assertNotNull(userFromWS);
        Assert.assertEquals("Error found GPUser from Username", idUserTest,
                userFromWS.getId().longValue());

    }

    @Test(expected = UniformInterfaceException.class)
    public void testInsertUserWithNoRolesRest() throws Exception {
        super.createAndInsertUser("user-no-roles-rest", organizationTest);

    }

    @Test
    public void testSecureInsertUserWithSingleRole() throws Exception {
        List<GPAuthority> authorities = oauth2CoreClientConnector.getAuthoritiesDetail(
                usernameTest).getAuthorities();
        Assert.assertNotNull("Authorities null", authorities);
        Assert.assertEquals("Number of Authorities of " + usernameTest, 1,
                authorities.size());

        GPAuthority authority = authorities.get(0);
        Assert.assertNotNull(authority);
        Assert.assertEquals("Authority string", GPRole.USER.getRole(),
                authority.getAuthority());
        Assert.assertEquals("Authority level", super.getTrustedLevelByRole(
                GPRole.USER), authority.getTrustedLevel());
        Assert.assertEquals("Authority username", usernameTest,
                authority.getAccountNaturalID());

    }

    @Test
    public void testSecureInsertUserWithMultiRole() throws Exception {
        String usernameMultiRole = "user-multi-role-rs";
        super.createAndInsertUser(usernameMultiRole,
                organizationTest, GPRole.ADMIN, GPRole.VIEWER);

        List<GPAuthority> authorities = oauth2CoreClientConnector
                .getAuthoritiesDetail(usernameMultiRole).getAuthorities();
        Assert.assertNotNull(authorities);
        Assert.assertEquals("Number of Authorities of " + usernameMultiRole,
                2, authorities.size());

        boolean isAdmin = false;
        boolean isViewer = false;
        for (GPAuthority authority : authorities) {
            Assert.assertNotNull(authority);
            Assert.assertEquals("Authority email", usernameMultiRole,
                    authority.getAccountNaturalID());
            if (GPRole.ADMIN.getRole().equals(authority.getAuthority())) {
                isAdmin = true;
            } else if (GPRole.VIEWER.getRole().equals(
                    authority.getAuthority())) {
                isViewer = true;
            }
        }
        Assert.assertTrue("Authority ADMIN string", isAdmin);
        Assert.assertTrue("Authority VIEWER string", isViewer);

    }

    @Test
    public void testSecureInsertDuplicateUserWRTUsername() throws Exception {
        GPUser user = super.createUser(usernameTest, organizationTest,
                GPRole.USER);
        try {
            oauth2CoreClientConnector.insertAccount(new InsertAccountRequest(
                    user, Boolean.FALSE));
            Assert.fail("User already exist wrt username");
        } catch (UniformInterfaceException ex) {
            GPRestExceptionMessage exMess = ex.getResponse().getEntity(
                    GPRestExceptionMessage.class);
            if (!exMess.getMessage().toLowerCase().contains("username")) { // Must be fail for other reasons
                Assert.fail("Not fail for User already exist wrt username,"
                        + " but for: " + exMess.getMessage());
            }
        }

    }

    @Test
    public void testSecureInsertDuplicateUserWRTEmail() throws Exception {
        GPUser user = super.createUser("duplicate-email-rs", organizationTest,
                GPRole.USER);
        user.setEmailAddress(super.userTest.getEmailAddress());
        try {
            oauth2CoreClientConnector.insertAccount(new InsertAccountRequest(
                    user, Boolean.FALSE));
            Assert.fail("User already exist wrt email");
        } catch (UniformInterfaceException ex) {
            GPRestExceptionMessage exMess = ex.getResponse().getEntity(
                    GPRestExceptionMessage.class);
            if (!exMess.getMessage().toLowerCase().contains("email")) { // Must be fail for other reasons
                Assert.fail("Not fail for User already exist wrt email,"
                        + " but for: " + exMess.getMessage());
            }
        }

    }

    @Test
    public void testSecureInsertIncorrectUserWRTUOrganizationRest() throws
            Exception {
        GPUser user = super.createUser("no-organization-rs",
                new GPOrganization("organization-inexistent-rs"), GPRole.USER);

        try {
            oauth2CoreClientConnector.insertAccount(new InsertAccountRequest(
                    user, Boolean.FALSE));
            Assert.fail("User incorrect wrt organization");
        } catch (UniformInterfaceException ex) {
            GPRestExceptionMessage exMess = ex.getResponse().getEntity(
                    GPRestExceptionMessage.class);
            if (!exMess.getMessage().toLowerCase().contains("organization")) { // Must be fail for other reasons
                Assert.fail("Not fail for User incorrect wrt organization,"
                        + " but for: " + exMess.getMessage());
            }
        }

    }

    @Test
    public void testSecureAuthorizationCorrectUsername() throws Exception {
        GPUser user = oauth2CoreClientConnector.getUserDetailByUsernameAndPassword(
                usernameTest,
                passwordTest);
        Assert.assertNotNull("User is null", user);
        Assert.assertEquals(usernameTest, user.getUsername());

    }

    @Test
    public void testSecureAuthorizationCorrectEmail() throws Exception {
        GPUser user = oauth2CoreClientConnector.getUserDetailByUsernameAndPassword(
                emailTest,
                passwordTest);
        Assert.assertNotNull("User is null", user);
        Assert.assertEquals(emailTest, user.getEmailAddress());

    }

    @Test(expected = UniformInterfaceException.class)
    public void testSecureAuthorizationIncorrectUsername() throws Exception {
        String wrongUsername = usernameTest + "_";
        oauth2CoreClientConnector.getUserDetailByUsernameAndPassword(
                wrongUsername, passwordTest);

    }

    @Test(expected = UniformInterfaceException.class)
    public void testSecureAuthorizationIncorrectEmail() throws Exception {
        String wrongEmail = emailTest + "_";
        oauth2CoreClientConnector.getUserDetailByUsernameAndPassword(wrongEmail,
                passwordTest);

    }

    @Test(expected = UniformInterfaceException.class)
    public void testSecureAuthorizationIncorrectPassword() throws Exception {
        String wrongPassword = passwordTest + "_";
        oauth2CoreClientConnector.getUserDetailByUsernameAndPassword(
                usernameTest, wrongPassword);

    }

    @Test(expected = UniformInterfaceException.class)
    public void testSecureLoginFaultUserDisabled() throws Exception {
        // Set disabled user
        userTest.setEnabled(false);
        oauth2CoreClientConnector.updateUser(userTest);

        // Must be throws AccountLoginFault because the user is disabled
        oauth2CoreClientConnector.getUserDetailByUsernameAndPassword(
                usernameTest, passwordTest);
    }

    @Test
    public void searchSecureUsersTest() throws Exception {
        String usernameMultiRole = "user-test1-rs";
        Long idUser = super.createAndInsertUser(usernameMultiRole,
                organizationTest, GPRole.ADMIN, GPRole.VIEWER);

        insertMassiveUsers("-rs");
        List<UserDTO> users = oauth2CoreClientConnector.searchUsers(idUser,
                new PaginatedSearchRequest(25, 0)).getUsers();

        logger.info("################################ {}", users);

        Assert.assertEquals(25, users.size());

        Assert.assertEquals(6, oauth2CoreClientConnector.searchUsers(idUser,
                new PaginatedSearchRequest(25, 1)).getUsers().size());

        Long userCount = oauth2CoreClientConnector.getUsersCount(
                organizationTest.getName(), new SearchRequest());

        Assert.assertEquals(32, userCount.intValue());

    }

    @Test
    public void getSecureAuthoritiesTest() throws Exception {
        String usernameMultiRole = "user-auth-rs";
        Long idUser = super.createAndInsertUser(usernameMultiRole,
                organizationTest, GPRole.ADMIN, GPRole.VIEWER);

        List<String> authorities = oauth2CoreClientConnector.getAuthorities(
                idUser).getAuthorities();
        Assert.assertEquals(2, authorities.size());

        logger.debug("\n@@@@@@@@@@@@@@@@@@@@@@Authorities : {}", authorities);

    }

    @Test
    public void forceSecureTemporaryAccountTest() throws Exception {
        String usernameTmp = "user-tmp-rs";
        Long idUser = super.createAndInsertUser(usernameTmp,
                organizationTest, GPRole.ADMIN, GPRole.VIEWER);

        oauth2CoreClientConnector.forceTemporaryAccount(idUser);

        GPUser tmpUser = oauth2CoreClientConnector.getUserDetail(idUser);
        Assert.assertEquals(Boolean.TRUE, tmpUser.isAccountTemporary());

        oauth2CoreClientConnector.forceExpiredTemporaryAccount(idUser);
        tmpUser = oauth2CoreClientConnector.getUserDetail(idUser);

        Assert.assertEquals(Boolean.FALSE, tmpUser.isAccountNonExpired());

    }
}
