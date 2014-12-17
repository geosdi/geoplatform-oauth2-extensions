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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.geosdi.geoplatform.core.model.GPAccountProject;
import org.geosdi.geoplatform.core.model.GPBBox;
import org.geosdi.geoplatform.core.model.GPViewport;
import org.geosdi.geoplatform.request.viewport.InsertViewportRequest;
import org.geosdi.geoplatform.request.viewport.ManageViewportRequest;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class OAuth2ViewportTest extends OAuth2CoreServiceTest {

    private GPAccountProject accountProject;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        this.accountProject = oauth2CoreClientConnector
                .getAccountProject(idAccountProject);
        Assert.assertNotNull(accountProject);
    }

    @Test
    public void insertSecureMassiveViewportTest() throws Exception {
        Collection<GPViewport> viewports = super.createMassiveViewports();

        oauth2CoreClientConnector.saveOrUpdateViewportList(
                new ManageViewportRequest(idAccountProject,
                        new ArrayList<>(viewports)));

        Collection<GPViewport> viewportsFound = oauth2CoreClientConnector
                .getAccountProjectViewports(idAccountProject).getViewports();

        logger.trace("\n\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@FOUND {} "
                + "@@@@@@@@@@@@@@@@@@@@@@@@@\n\n", viewportsFound);

        Assert.assertEquals(80, viewportsFound.size());

        GPViewport defaultVieport = oauth2CoreClientConnector
                .getDefaultViewport(idAccountProject);

        logger.trace("\n\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@DEFAULT_VIEWPORT "
                + "Found : {}@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n\n", defaultVieport);

        Assert.assertEquals("Viewport0-Rest", defaultVieport.getName());
        Assert.assertEquals(0.0, defaultVieport.getZoomLevel(), 0.0);
    }

    @Test
    public void updateSecureViewportTest() throws Exception {
        Long idViewportDefault = oauth2CoreClientConnector.insertViewport(
                new InsertViewportRequest(
                        idAccountProject, new GPViewport(
                                "Viewport-To-Save-OAuth2",
                                "This is the viewport to save", 22, new GPBBox(
                                        10, 10,
                                        20, 20), Boolean.TRUE)));

        Assert.assertNotNull(idViewportDefault);

        Long idViewport = oauth2CoreClientConnector.insertViewport(
                new InsertViewportRequest(
                        idAccountProject, new GPViewport(
                                "Viewport-To-Save-New-OAuth2",
                                "This is the viewport to save New", 18,
                                new GPBBox(18,
                                        20,
                                        40, 29), Boolean.FALSE)));

        Assert.assertNotNull(idViewport);

        Long idViewport1 = oauth2CoreClientConnector
                .insertViewport(new InsertViewportRequest(
                                idAccountProject, new GPViewport(
                                        "Viewport-To-Save-New-1-OAuth2",
                                        "This is the viewport to save New", 31,
                                        new GPBBox(21,
                                                33, 50, 79), Boolean.FALSE)));

        Assert.assertNotNull(idViewport1);

        Collection<GPViewport> viewports = oauth2CoreClientConnector
                .getAccountProjectViewports(idAccountProject).getViewports();

        Assert.assertEquals(3, viewports.size());

        GPViewport viewport = oauth2CoreClientConnector
                .getDefaultViewport(idAccountProject);
        Assert.assertEquals(Boolean.TRUE, viewport.isIsDefault());
        Assert.assertEquals("Viewport-To-Save-OAuth2", viewport.getName());

        viewport.setName("Viewport-Updated-OAuth2");
        viewport.setDescription("New Description");
        viewport.setIsDefault(Boolean.FALSE);

        oauth2CoreClientConnector.updateViewport(viewport);

        GPViewport v = oauth2CoreClientConnector.getViewportById(idViewport1);
        v.setIsDefault(Boolean.TRUE);
        oauth2CoreClientConnector.updateViewport(v);

        GPViewport df = oauth2CoreClientConnector
                .getDefaultViewport(idAccountProject);
        Assert.assertEquals(idViewport1, df.getId());
    }

    @Test
    public void deleteSecureViewportTest() throws Exception {
        Long idViewport = oauth2CoreClientConnector.insertViewport(
                new InsertViewportRequest(idAccountProject,
                        new GPViewport("Viewport-To-Delete-OAuth2",
                                "This is the viewport to Delete", 26,
                                new GPBBox(
                                        15, 15, 22, 30), Boolean.TRUE)));

        Assert.assertNotNull(idViewport);

        GPViewport viewport = oauth2CoreClientConnector
                .getViewportById(idViewport);
        Assert.assertNotNull(viewport);

        logger.trace("\n\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@VIEWPORT_FOUND :"
                + " {}\n\n", viewport);

        Assert.assertTrue(oauth2CoreClientConnector.deleteViewport(idViewport));
    }

    @Test
    public void replaceSecureViewportListTest() throws Exception {
        Collection<GPViewport> viewports = super.createMassiveViewports();

        oauth2CoreClientConnector.saveOrUpdateViewportList(new ManageViewportRequest(
                idAccountProject, new ArrayList<>(viewports)));

        Assert.assertEquals(80, oauth2CoreClientConnector
                .getAccountProjectViewports(idAccountProject).getViewports().size());

        oauth2CoreClientConnector.replaceViewportList(new ManageViewportRequest(
                idAccountProject, new ArrayList<>(
                        createViewportListToReplace())));

        Collection<GPViewport> viewportsReplacedFound = oauth2CoreClientConnector
                .getAccountProjectViewports(idAccountProject).getViewports();

        Assert.assertEquals(20, viewportsReplacedFound.size());

        logger.trace("\n\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ COLLECTION_"
                + "VIEWPORT_REPLACED : {}", viewportsReplacedFound);
    }

    Collection<GPViewport> createViewportListToReplace() {
        List<GPViewport> viewports = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            viewports.add(new GPViewport("Viewport_To_Replace" + i + "-Rest",
                    "This is a Generic Viewport to Replace", i,
                    new GPBBox(i, i, i, i),
                    (i == 0) ? Boolean.TRUE : Boolean.FALSE));
        }
        return viewports;
    }

}
