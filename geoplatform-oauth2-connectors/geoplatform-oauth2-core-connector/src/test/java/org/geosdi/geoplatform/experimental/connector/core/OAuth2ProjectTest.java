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

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.geosdi.geoplatform.core.model.GPAccount;
import org.geosdi.geoplatform.core.model.GPAccountProject;
import org.geosdi.geoplatform.core.model.GPFolder;
import org.geosdi.geoplatform.core.model.GPLayer;
import org.geosdi.geoplatform.core.model.GPProject;
import org.geosdi.geoplatform.core.model.GPRasterLayer;
import org.geosdi.geoplatform.core.model.GPUser;
import org.geosdi.geoplatform.core.model.GPVectorLayer;
import org.geosdi.geoplatform.gui.shared.GPRole;
import org.geosdi.geoplatform.request.PutAccountsProjectRequest;
import org.geosdi.geoplatform.request.RequestByAccountProjectIDs;
import org.geosdi.geoplatform.request.SearchRequest;
import org.geosdi.geoplatform.request.project.ImportProjectRequest;
import org.geosdi.geoplatform.request.project.SaveProjectRequest;
import org.geosdi.geoplatform.response.AccountProjectPropertiesDTO;
import org.geosdi.geoplatform.response.FolderDTO;
import org.geosdi.geoplatform.response.IElementDTO;
import org.geosdi.geoplatform.response.ProjectDTO;
import org.geosdi.geoplatform.response.RasterLayerDTO;
import org.geosdi.geoplatform.response.ShortAccountDTO;
import org.geosdi.geoplatform.response.VectorLayerDTO;
import org.geosdi.geoplatform.response.collection.TreeFolderElements;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.acls.domain.BasePermission;

/**
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class OAuth2ProjectTest extends OAuth2CoreServiceTest {
    
    Map<String, Object> fixture = new HashMap<>();
    // Folder
    private static final String nameFolder1A = "folder1A_oauth2";
    private static final String nameFolder1B = "folder1B_oauth2";
    private static final String nameFolder1C = "folder1C_oauth2";
    private static final String nameFolder2A = "folder2A_oauth2";
    private static final String nameFolder2B = "folder2B_oauth2";
    private static final String nameFolder2C = "folder2C_oauth2";
    private static final String nameFolder3A = "folder3A_oauth2";
    private static final String nameFolder3B = "folder3B_oauth2";
    private static final String nameFolder3C = "folder3C_oauth2";
    private GPFolder folder1A;
    private GPFolder folder1B;
    private GPFolder folder1C;
    private GPFolder folder2A;
    private GPFolder folder2B;
    private GPFolder folder2C;
    private GPFolder folder3A;
    private GPFolder folder3B;
    private GPFolder folder3C;
    // Layer
    private final String titleRaster = "T-raster-oauth2-";
    private final String nameRaster = "N-raster-oauth2-";
    private final String titleVector = "T-vector-oauth2-";
    private final String nameVector = "N-vector-oauth2-";
    GPRasterLayer rasterRootFolderA;
    GPRasterLayer rasterFolder1B;
    GPRasterLayer rasterFolder2C;
    GPVectorLayer vectorFolder3A;
    GPVectorLayer vectorRootFolderB;
    
    @Override
    public void setUp() throws Exception {
        super.setUp();
        
        Long idRasterRootFolderA = super.createAndInsertRasterLayer(
                super.rootFolderA, titleRaster + nameRootFolderA,
                nameRaster + nameRootFolderA, "", 15, "", "");
        rasterRootFolderA = oauth2CoreClientConnector.getRasterLayer(
                idRasterRootFolderA);
        this.fixture.put(rasterRootFolderA.getName(), rasterRootFolderA);
        // "rootFolderA" ---> "folder1(A|B|C)"
        Long idFolder1A = super.createAndInsertFolder(nameFolder1A, projectTest,
                14, rootFolderA, 8);
        folder1A = oauth2CoreClientConnector.getFolderDetail(idFolder1A);
        this.fixture.put(folder1A.getName(), folder1A);
        //
        Long idFolder1B = super.createAndInsertFolder(nameFolder1B, projectTest,
                5, rootFolderA, 1);
        folder1B = oauth2CoreClientConnector.getFolderDetail(idFolder1B);
        this.fixture.put(folder1B.getName(), folder1B);
        //
        Long idRasterFolder1B = super.createAndInsertRasterLayer(folder1B,
                titleRaster + nameFolder1B,
                nameRaster + nameFolder1B, "", 4, "", "");
        rasterFolder1B = oauth2CoreClientConnector.getRasterLayer(
                idRasterFolder1B);
        this.fixture.put(rasterFolder1B.getName(), rasterFolder1B);
        //
        Long idFolder1C = super.createAndInsertFolder(nameFolder1C, projectTest,
                3, rootFolderA);
        folder1C = oauth2CoreClientConnector.getFolderDetail(idFolder1C);
        this.fixture.put(folder1C.getName(), folder1C);
        //
        // "folder1A" ---> "folder2(A|B|C)"
        Long idFolder2A = super.createAndInsertFolder(nameFolder2A, projectTest,
                13, folder1A, 4);
        folder2A = oauth2CoreClientConnector.getFolderDetail(idFolder2A);
        this.fixture.put(folder2A.getName(), folder2A);
        //
        Long idFolder2B = super.createAndInsertFolder(nameFolder2B, projectTest,
                8, folder1A);
        folder2B = oauth2CoreClientConnector.getFolderDetail(idFolder2B);
        this.fixture.put(folder2B.getName(), folder2B);
        //
        Long idFolder2C = super.createAndInsertFolder(nameFolder2C, projectTest,
                7, folder1A, 1);
        folder2C = oauth2CoreClientConnector.getFolderDetail(idFolder2C);
        this.fixture.put(folder2C.getName(), folder2C);
        //
        Long idRasterFolder2C = super.createAndInsertRasterLayer(folder2C,
                titleRaster + nameFolder2C,
                nameRaster + nameFolder2C, "", 6, "", "");
        rasterFolder2C = oauth2CoreClientConnector.getRasterLayer(
                idRasterFolder2C);
        this.fixture.put(rasterFolder2C.getName(), rasterFolder2C);
        //
        // "folder2A" ---> "folder3(A|B|C)"
        Long idFolder3A = super.createAndInsertFolder(nameFolder3A, projectTest,
                12, folder2A, 1);
        folder3A = oauth2CoreClientConnector.getFolderDetail(idFolder3A);
        this.fixture.put(folder3A.getName(), folder3A);
        //
        Long idVectorFolder3A = super.createAndInsertVectorLayer(folder3A,
                titleVector + nameFolder3A,
                nameVector + nameFolder3A, "", 11, "", "");
        vectorFolder3A = oauth2CoreClientConnector.getVectorLayer(
                idVectorFolder3A);
        this.fixture.put(vectorFolder3A.getName(), vectorFolder3A);
        //
        Long idFolder3B = super.createAndInsertFolder(nameFolder3B, projectTest,
                10, folder2A);
        folder3B = oauth2CoreClientConnector.getFolderDetail(idFolder3B);
        this.fixture.put(folder3B.getName(), folder3B);
        //
        Long idFolder3C = super.createAndInsertFolder(nameFolder3C, projectTest,
                9, folder2A);
        folder3C = oauth2CoreClientConnector.getFolderDetail(idFolder3C);
        this.fixture.put(folder3C.getName(), folder3C);
        //        
        super.rootFolderA.setNumberOfDescendants(13);
        super.rootFolderA.setPosition(16);
        oauth2CoreClientConnector.updateFolder(super.rootFolderA);
        
        Long idVectorRootFolderB = super.createAndInsertVectorLayer(
                super.rootFolderB, titleVector + nameRootFolderB,
                nameVector + nameRootFolderB, "", 1, "", "");
        vectorRootFolderB = oauth2CoreClientConnector.getVectorLayer(
                idVectorRootFolderB);
        this.fixture.put(vectorRootFolderB.getName(), vectorRootFolderB);
        //
        super.rootFolderB.setNumberOfDescendants(1);
        super.rootFolderB.setPosition(2);
        oauth2CoreClientConnector.updateFolder(super.rootFolderB);
        
        super.projectTest.setNumberOfElements(projectTest.getNumberOfElements()
                + super.rootFolderA.getNumberOfDescendants() + super.rootFolderB.getNumberOfDescendants());
        oauth2CoreClientConnector.updateProject(projectTest);
    }
    
    @Test
    public void testSecureFixtureNotNull() {
        for (Map.Entry<String, Object> entry : fixture.entrySet()) {
            Assert.assertNotNull(entry.getKey() + " is NULL", entry.getValue());
        }
    }
    
    @Test
    public void testSecureExportProject() throws Exception {
        ProjectDTO project = oauth2CoreClientConnector.exportProject(
                super.idProjectTest);
        
        Assert.assertEquals("project name", super.projectTest.getName(),
                project.getName());
        Assert.assertEquals("project elements",
                super.projectTest.getNumberOfElements(),
                project.getNumberOfElements().intValue());
        
        List<FolderDTO> rootFolders = project.getRootFolders();
        Assert.assertEquals("#root", 2, rootFolders.size());
        Assert.assertEquals("A", nameRootFolderA, rootFolders.get(0).getName());
        Assert.assertEquals("B", nameRootFolderB, rootFolders.get(1).getName());
        
        List<IElementDTO> childRootFolderA = rootFolders.get(0).getElementList();
        Assert.assertEquals("#A", 4, childRootFolderA.size());
        Assert.assertEquals("R-A", nameRaster + nameRootFolderA,
                childRootFolderA.get(0).getName());
        Assert.assertEquals("1A", nameFolder1A,
                childRootFolderA.get(1).getName());
        Assert.assertEquals("1B", nameFolder1B,
                childRootFolderA.get(2).getName());
        Assert.assertEquals("1C", nameFolder1C,
                childRootFolderA.get(3).getName());
        
        List<IElementDTO> childFolder1A = ((FolderDTO) childRootFolderA.get(1)).getElementList();
        Assert.assertEquals("#1A", 3, childFolder1A.size());
        Assert.assertEquals("2A", nameFolder2A, childFolder1A.get(0).getName());
        Assert.assertEquals("2B", nameFolder2B, childFolder1A.get(1).getName());
        Assert.assertEquals("2C", nameFolder2C, childFolder1A.get(2).getName());
        
        List<IElementDTO> childFolder2A = ((FolderDTO) childFolder1A.get(0)).getElementList();
        Assert.assertEquals("#2A", 3, childFolder2A.size());
        Assert.assertEquals("3A", nameFolder3A, childFolder2A.get(0).getName());
        Assert.assertEquals("3B", nameFolder3B, childFolder2A.get(1).getName());
        Assert.assertEquals("3C", nameFolder3C, childFolder2A.get(2).getName());
        
        FolderDTO f1B = (FolderDTO) childRootFolderA.get(2);
        Assert.assertEquals("#1B", 1, f1B.getElementList().size());
        Assert.assertEquals("R-1B", nameRaster + nameFolder1B,
                f1B.getElementList().get(0).getName());
        
        FolderDTO f2C = (FolderDTO) childFolder1A.get(2);
        Assert.assertEquals("#2C", 1, f2C.getElementList().size());
        Assert.assertEquals("R-2C", nameRaster + nameFolder2C,
                f2C.getElementList().get(0).getName());
        
        FolderDTO f3A = (FolderDTO) childFolder2A.get(0);
        Assert.assertEquals("#3A", 1, f3A.getElementList().size());
        Assert.assertEquals("V-3A", nameVector + nameFolder3A,
                f3A.getElementList().get(0).getName());
        
        List<IElementDTO> childRootFolderB = rootFolders.get(1).getElementList();
        Assert.assertEquals("#B", 1, childRootFolderB.size());
        Assert.assertEquals("V-B", nameVector + nameRootFolderB,
                childRootFolderB.get(0).getName());
    }
    
    @Test
    public void testSecureOnlyFirstLevelFolder() throws Exception {
        oauth2CoreClientConnector.deleteFolder(super.idRootFolderA);
        
        ProjectDTO project = oauth2CoreClientConnector.exportProject(
                super.idProjectTest);
        Assert.assertEquals("project name", super.projectTest.getName(),
                project.getName());
        
        List<FolderDTO> rootFolders = project.getRootFolders();
        Assert.assertEquals("#root", 1, rootFolders.size());
        Assert.assertEquals("B", nameRootFolderB, rootFolders.get(0).getName());
        
        List<IElementDTO> childRootFolderB = rootFolders.get(0).getElementList();
        Assert.assertEquals("#B", 1, childRootFolderB.size());
        Assert.assertEquals("V-B", nameVector + nameRootFolderB,
                childRootFolderB.get(0).getName());
    }
    
    @Test
    public void testSecureImportProject() throws Exception {
        // Create ProjectDTO to import
        ProjectDTO projectDTO = new ProjectDTO(super.projectTest);
        
        List<GPFolder> rootFolders = Arrays.asList(super.rootFolderA,
                super.rootFolderB);
        List<FolderDTO> rootFoldersDTO = FolderDTO.convertToFolderDTOList(
                rootFolders);
        projectDTO.setRootFolders(rootFoldersDTO);
        
        FolderDTO rootFolderADTO = rootFoldersDTO.get(0);
        rootFolderADTO.addLayer(new RasterLayerDTO(rasterRootFolderA));
        List<FolderDTO> childRootFolderA = FolderDTO.convertToFolderDTOList(
                Arrays.asList(folder1A, folder1B, folder1C));
        rootFolderADTO.addFolders(childRootFolderA);
        
        List<FolderDTO> childFolder1A = FolderDTO.convertToFolderDTOList(
                Arrays.asList(folder2A, folder2B, folder2C));
        childRootFolderA.get(0).addFolders(childFolder1A);
        childRootFolderA.get(1).addLayer(new RasterLayerDTO(rasterFolder1B));
        
        List<FolderDTO> childFolder2A = FolderDTO.convertToFolderDTOList(
                Arrays.asList(folder3A, folder3B, folder3C));
        childFolder1A.get(0).addFolders(childFolder2A);
        childFolder1A.get(2).addLayer(new RasterLayerDTO(rasterFolder2C));
        
        childFolder2A.get(0).addLayer(new VectorLayerDTO(vectorFolder3A));
        
        FolderDTO rootFolderBDTO = rootFoldersDTO.get(1);
        rootFolderBDTO.addLayer(new VectorLayerDTO(vectorRootFolderB));
        
        projectDTO.setId(null); // Entity passed must not containd an ID, otherwise Hibernate throws PersistentObjectException
        // Import ProjectDTO

        Long projectID = oauth2CoreClientConnector
                .importProject(new ImportProjectRequest(projectDTO,
                                super.idUserTest));
        // Check imported Project
        Assert.assertTrue("Check importProject", projectID > 0);
        logger.debug("*** ID project imported: {} ***", projectID);
        
        GPProject projectAdded = oauth2CoreClientConnector.getProjectDetail(
                projectID);
        Assert.assertEquals("project name", super.projectTest.getName(),
                projectAdded.getName());
        Assert.assertEquals("project elements",
                super.projectTest.getNumberOfElements(),
                projectAdded.getNumberOfElements());
        
        ProjectDTO projectWithRootFolders = oauth2CoreClientConnector
                .getProjectWithRootFolders(projectID, super.idUserTest);
        Assert.assertNotNull("projectWithRootFolders null",
                projectWithRootFolders);
        
        rootFoldersDTO = projectWithRootFolders.getRootFolders();
        Assert.assertNotNull("rootFolders null", rootFoldersDTO);
        Assert.assertEquals("#root", 2, rootFoldersDTO.size());
        rootFolderADTO = rootFoldersDTO.get(0);
        rootFolderBDTO = rootFoldersDTO.get(1);
        this.assertFolderRest("A", super.rootFolderA, rootFolderADTO);
        this.assertFolderRest("B", super.rootFolderB, rootFolderBDTO);
        
        TreeFolderElements elemRootFolderA = oauth2CoreClientConnector
                .getChildrenElements(rootFolderADTO.getId())
                .getFolderElements();
        Assert.assertNotNull("elem-A null", elemRootFolderA);
        Assert.assertEquals("#A", 4, elemRootFolderA.size());
        IElementDTO rasterRootFolderADTO = elemRootFolderA.pollFirst();
        FolderDTO folder1ADTO = (FolderDTO) elemRootFolderA.pollFirst();
        FolderDTO folder1BDTO = (FolderDTO) elemRootFolderA.pollFirst();
        FolderDTO folder1CDTO = (FolderDTO) elemRootFolderA.pollFirst();
        this.assertLayerRest("R-A", rasterRootFolderA, rasterRootFolderADTO);
        this.assertFolderRest("1A", folder1A, folder1ADTO);
        this.assertFolderRest("1B", folder1B, folder1BDTO);
        this.assertFolderRest("1C", folder1C, folder1CDTO);
        
        TreeFolderElements elemFolder1A = oauth2CoreClientConnector
                .getChildrenElements(folder1ADTO.getId()).getFolderElements();
        Assert.assertNotNull("elem-1A null", elemFolder1A);
        Assert.assertEquals("#1A", 3, elemFolder1A.size());
        FolderDTO folder2ADTO = (FolderDTO) elemFolder1A.pollFirst();
        FolderDTO folder2BDTO = (FolderDTO) elemFolder1A.pollFirst();
        FolderDTO folder2CDTO = (FolderDTO) elemFolder1A.pollFirst();
        this.assertFolderRest("2A", folder2A, folder2ADTO);
        this.assertFolderRest("2B", folder2B, folder2BDTO);
        this.assertFolderRest("2C", folder2C, folder2CDTO);
        
        TreeFolderElements elemFolder2A = oauth2CoreClientConnector
                .getChildrenElements(folder2ADTO.getId()).getFolderElements();
        Assert.assertNotNull("elem-2A null", elemFolder2A);
        Assert.assertEquals("#2A", 3, elemFolder2A.size());
        FolderDTO folder3ADTO = (FolderDTO) elemFolder2A.pollFirst();
        FolderDTO folder3BDTO = (FolderDTO) elemFolder2A.pollFirst();
        FolderDTO folder3CDTO = (FolderDTO) elemFolder2A.pollFirst();
        this.assertFolderRest("3A", folder3A, folder3ADTO);
        this.assertFolderRest("3B", folder3B, folder3BDTO);
        this.assertFolderRest("3C", folder3C, folder3CDTO);
        
        TreeFolderElements elemFolder3A = oauth2CoreClientConnector
                .getChildrenElements(folder3ADTO.getId()).getFolderElements();
        Assert.assertNotNull("elem-3A null", elemFolder3A);
        Assert.assertEquals("#3A", 1, elemFolder3A.size());
        IElementDTO vectorFolder3ADTO = elemFolder3A.pollFirst();
        this.assertLayerRest("V-3A", vectorFolder3A, vectorFolder3ADTO);
        
        TreeFolderElements elemFolder2C = oauth2CoreClientConnector
                .getChildrenElements(folder2CDTO.getId()).getFolderElements();
        Assert.assertNotNull("elem-2C null", elemFolder2C);
        Assert.assertEquals("#2C", 1, elemFolder2C.size());
        IElementDTO rasterFolder2CDTO = elemFolder2C.pollFirst();
        this.assertLayerRest("R-2C", rasterFolder2C, rasterFolder2CDTO);
        
        TreeFolderElements elemFolder1B = oauth2CoreClientConnector
                .getChildrenElements(folder1BDTO.getId()).getFolderElements();
        Assert.assertNotNull("elem-1B null", elemFolder1B);
        Assert.assertEquals("#1B", 1, elemFolder1B.size());
        IElementDTO rasterFolder1BDTO = elemFolder1B.pollFirst();
        this.assertLayerRest("R-1B", rasterFolder1B, rasterFolder1BDTO);
        
        TreeFolderElements elemRootFolderB = oauth2CoreClientConnector
                .getChildrenElements(rootFolderBDTO.getId()).getFolderElements();
        Assert.assertNotNull("elem-B null", elemRootFolderB);
        Assert.assertEquals("#B", 1, elemRootFolderB.size());
        IElementDTO vectorRootFolderBDTO = elemRootFolderB.pollFirst();
        this.assertLayerRest("V-B", vectorRootFolderB, vectorRootFolderBDTO);
    }
    
    @Test
    public void testSecureExpandedElementsProject() throws Exception {
        super.rootFolderA.setExpanded(true);
        super.rootFolderB.setExpanded(true);
        folder1A.setExpanded(true);
        folder2C.setExpanded(true);
        
        oauth2CoreClientConnector.updateFolder(super.rootFolderA);
        oauth2CoreClientConnector.updateFolder(super.rootFolderB);
        oauth2CoreClientConnector.updateFolder(folder1A);
        oauth2CoreClientConnector.updateFolder(folder2C);
        
        ProjectDTO project = oauth2CoreClientConnector
                .getProjectWithExpandedFolders(super.idProjectTest,
                        super.idUserTest);
        
        Assert.assertEquals("project name", super.projectTest.getName(),
                project.getName());
        Assert.assertEquals("project elements",
                super.projectTest.getNumberOfElements(),
                project.getNumberOfElements().intValue());
        
        List<FolderDTO> rootFolders = project.getRootFolders();
        Assert.assertEquals("#root", 2, rootFolders.size());
        Assert.assertEquals("A", nameRootFolderA, rootFolders.get(0).getName());
        Assert.assertEquals("B", nameRootFolderB, rootFolders.get(1).getName());
        
        List<IElementDTO> childRootFolderA = rootFolders.get(0).getElementList();
        Assert.assertEquals("#A", 4, childRootFolderA.size());
        Assert.assertEquals("R-A", nameRaster + nameRootFolderA,
                childRootFolderA.get(0).getName());
        Assert.assertEquals("1A", nameFolder1A,
                childRootFolderA.get(1).getName());
        Assert.assertEquals("1B", nameFolder1B,
                childRootFolderA.get(2).getName());
        Assert.assertEquals("1C", nameFolder1C,
                childRootFolderA.get(3).getName());
        
        List<IElementDTO> childFolder1A = ((FolderDTO) childRootFolderA.get(1)).getElementList();
        Assert.assertEquals("#1A", 3, childFolder1A.size());
        Assert.assertEquals("2A", nameFolder2A, childFolder1A.get(0).getName());
        Assert.assertEquals("2B", nameFolder2B, childFolder1A.get(1).getName());
        Assert.assertEquals("2C", nameFolder2C, childFolder1A.get(2).getName());
        
        List<IElementDTO> childFolder2A = ((FolderDTO) childFolder1A.get(0)).getElementList();
        Assert.assertEquals("#2A", 0, childFolder2A.size()); // NO elements

        FolderDTO f1B = (FolderDTO) childRootFolderA.get(2);
        Assert.assertEquals("#1B", 0, f1B.getElementList().size()); // NO elements

        FolderDTO f2C = (FolderDTO) childFolder1A.get(2);
        Assert.assertEquals("#2C", 1, f2C.getElementList().size());
        Assert.assertEquals("R-2C", nameRaster + nameFolder2C,
                f2C.getElementList().get(0).getName());
        
        List<IElementDTO> childRootFolderB = rootFolders.get(1).getElementList();
        Assert.assertEquals("#B", 1, childRootFolderB.size());
        Assert.assertEquals("V-B", nameVector + nameRootFolderB,
                childRootFolderB.get(0).getName());
    }
    
    @Test
    public void testAccountsBySharedProjectIDRest() throws Exception {
        // Set shared the Project test
        projectTest.setShared(true);
        projectTest.setName("shared_project_oauyh2_test");
        oauth2CoreClientConnector.updateProject(projectTest);

        // Initial test
        List<ShortAccountDTO> accountsToShare = oauth2CoreClientConnector
                .getAccountsByProjectID(idProjectTest).getAccounts();
        Assert.assertNotEquals(accountsToShare.isEmpty(), Boolean.TRUE);
        Assert.assertEquals(1, accountsToShare.size());
        Assert.assertEquals(idUserTest,
                accountsToShare.get(0).getId().longValue());

        // Insert Users to which the Project is shared
        Long firstUserID = this.createAndInsertUser(
                "first_to_share_oauth2_project",
                organizationTest, GPRole.USER);
        Long latterUserID = this.createAndInsertUser(
                "latter_to_share_oauth2_project",
                organizationTest, GPRole.VIEWER);
        
        GPUser firstUser = oauth2CoreClientConnector.getUserDetail(firstUserID);
        GPUser latterUser = oauth2CoreClientConnector.getUserDetail(latterUserID);

        // Insert the Users as viewers of Project
        this.createAndInsertAccountProject(firstUser, projectTest,
                BasePermission.READ);
        this.createAndInsertAccountProject(latterUser, projectTest,
                BasePermission.READ);

        // Final test
        accountsToShare = oauth2CoreClientConnector
                .getAccountsByProjectID(idProjectTest).getAccounts();
        Assert.assertNotEquals(accountsToShare.isEmpty(), Boolean.TRUE);
        Assert.assertEquals(3, accountsToShare.size());
    }
    
    @Test
    public void testSecureAccountsToShareByProjectID() throws Exception {
        // Set shared the Project test
        projectTest.setShared(true);
        projectTest.setName("shared_project_to_share_oauth2_test");
        oauth2CoreClientConnector.updateProject(projectTest);

        // Initial test
        List<ShortAccountDTO> accountsToShare = oauth2CoreClientConnector
                .getAccountsToShareByProjectID(idProjectTest).getAccounts();
        Assert.assertEquals(accountsToShare.isEmpty(), Boolean.TRUE);

        // Insert a User to which the Project is shared as viewer
        Long newUserID = this.createAndInsertUser("user_to_share_oauth2_project",
                organizationTest, GPRole.USER);
        GPUser newUser = oauth2CoreClientConnector.getUserDetail(newUserID);
        this.createAndInsertAccountProject(newUser, projectTest,
                BasePermission.READ);

        // Insert Users to which it possible to share the Project
        this.createAndInsertUser("first_possible_to_share_oauth2_project",
                organizationTest, GPRole.USER);
        this.createAndInsertUser("latter_possible_to_share_oauth2_project",
                organizationTest, GPRole.VIEWER);

        // Final test
        accountsToShare = oauth2CoreClientConnector
                .getAccountsToShareByProjectID(idProjectTest).getAccounts();
        Assert.assertNotEquals(accountsToShare.isEmpty(), Boolean.TRUE);
        Assert.assertEquals(2, accountsToShare.size());
    }
    
    @Test
    public void testSecureProjectOwner() throws Exception {
        // Set shared the Project test
        projectTest.setShared(true);
        projectTest.setName("shared_project_owner_oauth2_test");
        oauth2CoreClientConnector.updateProject(projectTest);

        // Insert a User to which the Project is shared as viewer
        Long newOwnerID = this.createAndInsertUser(
                "user_to_share_oauth2_project",
                organizationTest, GPRole.USER);
        GPUser newOwner = oauth2CoreClientConnector.getUserDetail(newOwnerID);
        this.createAndInsertAccountProject(newOwner, projectTest,
                BasePermission.READ);

        // Initial test
        GPAccount owner = oauth2CoreClientConnector
                .getProjectOwner(idProjectTest).getAccount();
        Assert.assertNotNull(owner);
        Assert.assertEquals(userTest, owner);

        // Change the Account owner
        RequestByAccountProjectIDs request = new RequestByAccountProjectIDs(
                newOwnerID, idProjectTest);
        boolean result = oauth2CoreClientConnector.setProjectOwner(request);
        Assert.assertTrue(result);

        // Final test
        owner = oauth2CoreClientConnector
                .getProjectOwner(idProjectTest).getAccount();
        Assert.assertNotNull(owner);
        Assert.assertEquals(newOwnerID, owner.getId());
    }
    
    @Test
    public void testSecureProjectNewOwner() throws Exception {
        // Initial test
        GPAccount owner = oauth2CoreClientConnector
                .getProjectOwner(idProjectTest).getAccount();
        Assert.assertNotNull(owner);
        Assert.assertEquals(userTest, owner);

        // Change the Account owner
        Long newOwnerID = this.createAndInsertUser("new_owner_oauth2",
                organizationTest, GPRole.ADMIN);
        
        RequestByAccountProjectIDs request = new RequestByAccountProjectIDs(
                newOwnerID, idProjectTest);
        boolean result = oauth2CoreClientConnector.setProjectOwner(request);
        Assert.assertTrue(result);

        // Final test
        owner = oauth2CoreClientConnector.getProjectOwner(idProjectTest)
                .getAccount();
        Assert.assertNotNull(owner);
        Assert.assertEquals(newOwnerID, owner.getId());
    }
    
    @Test
    public void testSecureUpdateAccountsProjectSharingCreate() throws Exception {
        // Initial test
        GPProject project = oauth2CoreClientConnector
                .getProjectDetail(idProjectTest);
        Assert.assertFalse(project.isShared());
        
        List<ShortAccountDTO> accountsToShare = oauth2CoreClientConnector
                .getAccountsByProjectID(idProjectTest).getAccounts();
        Assert.assertNotNull(accountsToShare);
        Assert.assertEquals(1, accountsToShare.size());
        Assert.assertEquals(idUserTest,
                accountsToShare.get(0).getId().longValue());

        // Insert User to which the Project will be share
        Long newUserID = this.createAndInsertUser("user_to_share_project_rs",
                organizationTest, GPRole.USER);

        // Test add user for sharing
        Boolean result = oauth2CoreClientConnector
                .updateAccountsProjectSharing(new PutAccountsProjectRequest(
                                idProjectTest, Arrays.asList(idUserTest,
                                        newUserID)));
        Assert.assertTrue(result);
        
        project = oauth2CoreClientConnector.getProjectDetail(idProjectTest);
        Assert.assertTrue(project.isShared());
        
        accountsToShare = oauth2CoreClientConnector
                .getAccountsByProjectID(idProjectTest).getAccounts();
        Assert.assertNotNull(accountsToShare);
        Assert.assertEquals(2, accountsToShare.size());
        boolean check = false;
        for (ShortAccountDTO accountDTO : accountsToShare) {
            if (newUserID.equals(accountDTO.getId())) {
                check = true;
                break;
            }
        }
        Assert.assertTrue(check);
    }
    
    @Test
    public void testSecureUpdateAccountsProjectSharingRemoveAll() throws
            Exception {
        // Insert a User to which the Project is shared as viewer
        Long newUserID = this.createAndInsertUser("user_to_share_oauth2_project",
                organizationTest, GPRole.USER);
        GPUser newUser = oauth2CoreClientConnector.getUserDetail(newUserID);
        this.createAndInsertAccountProject(newUser, projectTest,
                BasePermission.READ);

        // Set the Project as share
        projectTest.setShared(true);
        oauth2CoreClientConnector.updateProject(projectTest);

        // Initial test
        GPProject project = oauth2CoreClientConnector.getProjectDetail(
                idProjectTest);
        Assert.assertTrue(project.isShared());
        
        List<ShortAccountDTO> accountsToShare = oauth2CoreClientConnector
                .getAccountsByProjectID(idProjectTest).getAccounts();
        Assert.assertNotNull(accountsToShare);
        Assert.assertEquals(2, accountsToShare.size());
        Assert.assertEquals(2, accountsToShare.size());
        boolean check = false;
        for (ShortAccountDTO accountDTO : accountsToShare) {
            if (newUserID.equals(accountDTO.getId())) {
                check = true;
                break;
            }
        }
        Assert.assertTrue(check);

        // Test delete user for sharing
        boolean result = oauth2CoreClientConnector
                .updateAccountsProjectSharing(new PutAccountsProjectRequest(
                                idProjectTest, Arrays.asList(idUserTest)));
        Assert.assertTrue(result);
        
        project = oauth2CoreClientConnector.getProjectDetail(idProjectTest);
        Assert.assertFalse(project.isShared());
        
        accountsToShare = oauth2CoreClientConnector
                .getAccountsByProjectID(idProjectTest).getAccounts();
        Assert.assertNotNull(accountsToShare);
        Assert.assertEquals(1, accountsToShare.size());
        Assert.assertEquals(idUserTest,
                accountsToShare.get(0).getId().longValue());
    }
    
    @Test
    public void testSecureUpdateAccountsProjectSharingManage() throws Exception {
        // Insert a User to which the Project is shared as viewer
        Long firstUserID = this.createAndInsertUser(
                "first_to_share_oauth2_project",
                organizationTest, GPRole.USER);
        Long latterUserID = this.createAndInsertUser(
                "latter_to_share_oauth2_project",
                organizationTest, GPRole.VIEWER);
        GPUser newUser = oauth2CoreClientConnector.getUserDetail(firstUserID);
        this.createAndInsertAccountProject(newUser, projectTest,
                BasePermission.READ);

        // Set the Project as share
        projectTest.setShared(true);
        oauth2CoreClientConnector.updateProject(projectTest);

        // Initial test
        GPProject project = oauth2CoreClientConnector.getProjectDetail(
                idProjectTest);
        Assert.assertTrue(project.isShared());
        
        List<ShortAccountDTO> accountsToShare = oauth2CoreClientConnector
                .getAccountsByProjectID(idProjectTest).getAccounts();
        Assert.assertNotNull(accountsToShare);
        Assert.assertEquals(2, accountsToShare.size());
        Assert.assertEquals(2, accountsToShare.size());
        boolean checkFirst = false;
        for (ShortAccountDTO accountDTO : accountsToShare) {
            if (firstUserID.equals(accountDTO.getId())) {
                checkFirst = true;
                break;
            }
        }
        Assert.assertTrue(checkFirst);

        // Test add latter user for sharing
        boolean result = oauth2CoreClientConnector
                .updateAccountsProjectSharing(new PutAccountsProjectRequest(
                                idProjectTest, Arrays.asList(idUserTest,
                                        firstUserID, latterUserID)));
        Assert.assertTrue(result);
        
        project = oauth2CoreClientConnector.getProjectDetail(idProjectTest);
        Assert.assertTrue(project.isShared());
        
        accountsToShare = oauth2CoreClientConnector
                .getAccountsByProjectID(idProjectTest).getAccounts();
        Assert.assertNotNull(accountsToShare);
        Assert.assertEquals(3, accountsToShare.size());
        checkFirst = false;
        boolean checkLatter = false;
        for (ShortAccountDTO accountDTO : accountsToShare) {
            if (firstUserID.equals(accountDTO.getId())) {
                checkFirst = true;
            }
            if (latterUserID.equals(accountDTO.getId())) {
                checkLatter = true;
            }
        }
        Assert.assertTrue(checkFirst);
        Assert.assertTrue(checkLatter);

        // Test delete first user for sharing
        result = oauth2CoreClientConnector.updateAccountsProjectSharing(
                new PutAccountsProjectRequest(idProjectTest,
                        Arrays.asList(idUserTest, latterUserID)));
        Assert.assertTrue(result);
        
        project = oauth2CoreClientConnector.getProjectDetail(idProjectTest);
        Assert.assertTrue(project.isShared());
        
        accountsToShare = oauth2CoreClientConnector
                .getAccountsByProjectID(idProjectTest).getAccounts();
        Assert.assertNotNull(accountsToShare);
        Assert.assertEquals(2, accountsToShare.size());
        checkLatter = false;
        for (ShortAccountDTO accountDTO : accountsToShare) {
            if (latterUserID.equals(accountDTO.getId())) {
                checkLatter = true;
                break;
            }
        }
        Assert.assertTrue(checkLatter);
    }
    
    @Test
    public void testSecureUpdateAccountsProjectSharingOwner() throws Exception {
        // Initial test
        GPProject project = oauth2CoreClientConnector.getProjectDetail(
                idProjectTest);
        Assert.assertFalse(project.isShared());
        
        List<ShortAccountDTO> accountsToShare = oauth2CoreClientConnector
                .getAccountsByProjectID(idProjectTest).getAccounts();
        Assert.assertNotNull(accountsToShare);
        Assert.assertEquals(1, accountsToShare.size());
        Assert.assertEquals(idUserTest,
                accountsToShare.get(0).getId().longValue());

        // Test pass owner
        boolean result = oauth2CoreClientConnector.updateAccountsProjectSharing(
                new PutAccountsProjectRequest(idProjectTest,
                        Arrays.asList(idUserTest)));
        Assert.assertTrue(result);
        
        project = oauth2CoreClientConnector.getProjectDetail(idProjectTest);
        Assert.assertFalse(project.isShared());
        
        accountsToShare = oauth2CoreClientConnector
                .getAccountsByProjectID(idProjectTest).getAccounts();
        Assert.assertNotNull(accountsToShare);
        Assert.assertEquals(1, accountsToShare.size());
        Assert.assertEquals(idUserTest,
                accountsToShare.get(0).getId().longValue());
    }
    
    @Test
    public void updateSecureAccountProjectTest() throws Exception {
        String username = "new-user-oauth2";
        Long idUser = super.createAndInsertUser(username,
                organizationTest, GPRole.ADMIN, GPRole.VIEWER);
        
        GPUser user = oauth2CoreClientConnector.getUserDetail(idUser);
        
        long idProject = super.createAndInsertProject("_newproject_test_oauth2",
                false, 6, new Date(System.currentTimeMillis()));
        
        GPProject project = oauth2CoreClientConnector.getProjectDetail(idProject);
        
        Long idAccountProject = super.createAndInsertAccountProject(user,
                project, BasePermission.ADMINISTRATION);
        
        GPAccountProject accountProject = oauth2CoreClientConnector
                .getAccountProject(idAccountProject);
        
        accountProject.setDefaultProject(Boolean.TRUE);
        
        idAccountProject = oauth2CoreClientConnector
                .updateAccountProject(accountProject);
        
        accountProject = oauth2CoreClientConnector
                .getAccountProject(idAccountProject);
        
        Assert.assertEquals(Boolean.TRUE, accountProject.isDefaultProject());
        Assert.assertEquals(Boolean.TRUE, oauth2CoreClientConnector
                .deleteAccount(idUser));
    }
    
    @Test
    public void retrieveSecureAccountProjectsTest() throws Exception {
        String username = "first-user-oauth2";
        Long idUser = super.createAndInsertUser(username,
                organizationTest, GPRole.ADMIN, GPRole.VIEWER);
        
        GPUser user = oauth2CoreClientConnector.getUserDetail(idUser);
        
        String username1 = "second-user-oauth2";
        Long idUser1 = super.createAndInsertUser(username1,
                organizationTest, GPRole.ADMIN);
        
        GPUser user1 = oauth2CoreClientConnector.getUserDetail(idUser1);
        
        long idProject = super.createAndInsertProject(
                "first_project_test_oauth2",
                false, 116, new Date(System.currentTimeMillis()));
        
        GPProject project = oauth2CoreClientConnector.getProjectDetail(idProject);
        
        long idProject1 = super.createAndInsertProject(
                "second_project_test_oauth2",
                false, 11, new Date(System.currentTimeMillis()));
        
        GPProject project1 = oauth2CoreClientConnector.getProjectDetail(
                idProject1);
        
        super.createAndInsertAccountProject(user,
                project, BasePermission.ADMINISTRATION);
        
        super.createAndInsertAccountProject(user1,
                project, BasePermission.ADMINISTRATION);
        
        super.createAndInsertAccountProject(user,
                project1, BasePermission.ADMINISTRATION);
        
        logger.info("\n\n###########################HERE {}",
                oauth2CoreClientConnector
                .getAccountProjectsByProjectID(idProject)
                .getAccountProjects());
        
        Assert.assertEquals(2, oauth2CoreClientConnector
                .getAccountProjectsByProjectID(idProject)
                .getAccountProjects().size());
        
        Assert.assertEquals(116, oauth2CoreClientConnector
                .getAccountProjectByAccountAndProjectIDs(idUser, idProject)
                .getProject().getNumberOfElements());
        
        Assert.assertEquals(2, oauth2CoreClientConnector
                .getAccountProjectsCount(idUser, new SearchRequest()).intValue());
        
        Assert.assertEquals(Boolean.TRUE, oauth2CoreClientConnector
                .deleteAccount(idUser));
        Assert.assertEquals(Boolean.TRUE, oauth2CoreClientConnector
                .deleteAccount(idUser1));
    }
    
    @Test
    public void saveSecureAccountProjectProperties() throws Exception {
        AccountProjectPropertiesDTO apDTO = new AccountProjectPropertiesDTO(
                idUserTest, idProjectTest, "Project-New-OAuth2", 4, Boolean.TRUE,
                Boolean.TRUE);
        Assert.assertTrue(oauth2CoreClientConnector
                .saveAccountProjectProperties(apDTO));
        
        GPProject project = oauth2CoreClientConnector
                .getProjectDetail(idProjectTest);
        
        logger.debug("\n\n@@@@@@@@@@@@@@@@@@@@@@Project_Updated : {}\n\n"
                + "@@@@@@@@@@@@@@@@@@", project);
        
        Assert.assertEquals(Boolean.TRUE, project.isShared());
        Assert.assertEquals(4, project.getVersion());
        
        GPProject defaultProject = oauth2CoreClientConnector
                .getDefaultProject(idUserTest);
        
        Assert.assertEquals(defaultProject.getId(), project.getId());
    }
    
    @Test
    public void saveSecureProjectTest() throws Exception {
        GPProject project = super.createProject("Save-Project-OAuth2",
                Boolean.FALSE, 200, new Date(System.currentTimeMillis()));
        Long idProject = oauth2CoreClientConnector
                .saveProject(new SaveProjectRequest(usernameTest, project,
                                Boolean.TRUE));
        
        GPProject loadProject = oauth2CoreClientConnector
                .getProjectDetail(idProject);
        
        Assert.assertEquals(200, loadProject.getNumberOfElements());
        Assert.assertEquals("Save-Project-OAuth2", loadProject.getName());
        
        GPAccountProject owner = oauth2CoreClientConnector
                .getProjectOwner(idProject);
        
        Assert.assertEquals(usernameTest, owner.getAccount().getNaturalID());
        Assert.assertEquals(Boolean.TRUE, oauth2CoreClientConnector
                .deleteAccount(owner.getAccount().getId()));
    }
    
    @Test
    public void setSecureProjectSharedTest() throws Exception {
        Long idProject = super.createAndInsertProject("Project-OAuth2-to-Share",
                Boolean.FALSE, 2000, new Date(System.currentTimeMillis()));
        
        oauth2CoreClientConnector.setProjectShared(idProject);
        
        GPProject project = oauth2CoreClientConnector
                .getProjectDetail(idProject);
        
        Assert.assertNotNull(project);
        Assert.assertEquals(Boolean.TRUE, project.isShared());
        Assert.assertEquals(Boolean.TRUE, oauth2CoreClientConnector
                .deleteProject(idProject));
    }
    
    @Test
    public void testSecureNumberOfProjectElementTest() throws Exception {
        Long idProject = super.createAndInsertProject("Project-OAuth2-to-Check",
                Boolean.FALSE, 6000, new Date(System.currentTimeMillis()));
        
        Assert.assertEquals(6000, oauth2CoreClientConnector
                .getNumberOfElementsProject(idProject).intValue());
    }
    
    private void assertLayerRest(String msg, GPLayer layer,
            IElementDTO layerToCheck) {
        Assert.assertEquals(msg, layer.getName(), layerToCheck.getName());
        Assert.assertEquals("Position-" + msg, layer.getPosition(),
                layerToCheck.getPosition().intValue());
    }
    
    private void assertFolderRest(String msg, GPFolder folder,
            FolderDTO folderToCheck) {
        Assert.assertEquals(msg, folder.getName(), folderToCheck.getName());
        Assert.assertEquals("Position-" + msg, folder.getPosition(),
                folderToCheck.getPosition().intValue());
        Assert.assertEquals("Descendats-" + msg, folder.getNumberOfDescendants(),
                folderToCheck.getNumberOfDescendants().intValue());
    }
    
}
