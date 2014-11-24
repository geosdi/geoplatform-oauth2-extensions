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

import java.util.List;
import org.geosdi.geoplatform.core.delegate.api.project.ProjectDelegate;
import org.geosdi.geoplatform.core.model.GPAccountProject;
import org.geosdi.geoplatform.core.model.GPProject;
import org.geosdi.geoplatform.exception.IllegalParameterFault;
import org.geosdi.geoplatform.exception.ResourceNotFoundFault;
import org.geosdi.geoplatform.request.PaginatedSearchRequest;
import org.geosdi.geoplatform.request.PutAccountsProjectRequest;
import org.geosdi.geoplatform.request.RequestByAccountProjectIDs;
import org.geosdi.geoplatform.request.SearchRequest;
import org.geosdi.geoplatform.request.project.ImportProjectRequest;
import org.geosdi.geoplatform.request.project.SaveProjectRequest;
import org.geosdi.geoplatform.responce.AccountProjectPropertiesDTO;
import org.geosdi.geoplatform.responce.ProjectDTO;
import org.geosdi.geoplatform.responce.ShortAccountDTOContainer;
import org.geosdi.geoplatform.responce.WSGetAccountProjectsResponse;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
abstract class BaseProjectResource implements SecureProjectResource {

    @Autowired
    protected ProjectDelegate gpProjectDelegate;

    //<editor-fold defaultstate="collapsed" desc="Project">
    // =========================================================================
    // === Project
    // ========================================================================= 
    @Override
    public Long saveProject(SaveProjectRequest saveProjectRequest)
            throws Exception {
        return this.gpProjectDelegate.saveProject(saveProjectRequest);
    }

    @Override
    public Long insertProject(GPProject project) throws Exception {
        return this.gpProjectDelegate.insertProject(project);
    }

    @Override
    public Long updateProject(GPProject project) throws Exception {
        return this.gpProjectDelegate.updateProject(project);
    }

    @Override
    public Boolean deleteProject(Long projectID) throws Exception {
        return this.gpProjectDelegate.deleteProject(projectID);
    }

    @Override
    public GPProject getProjectDetail(Long projectID) throws Exception {
        return this.gpProjectDelegate.getProjectDetail(projectID);
    }

    @Override
    public Integer getNumberOfElementsProject(Long projectID) throws Exception {
        return this.gpProjectDelegate.getNumberOfElementsProject(projectID);
    }

    @Override
    public void setProjectShared(Long projectID) throws Exception {
        this.gpProjectDelegate.setProjectShared(projectID);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="AccountProject">
    // =========================================================================
    // === AccountProject
    // =========================================================================
    @Override
    public Long insertAccountProject(GPAccountProject accountProject) throws
            IllegalParameterFault {
        return this.gpProjectDelegate.insertAccountProject(accountProject);
    }

    @Override
    public Long updateAccountProject(GPAccountProject accountProject)
            throws ResourceNotFoundFault, IllegalParameterFault {
        return this.gpProjectDelegate.updateAccountProject(accountProject);
    }

    @Override
    public Boolean deleteAccountProject(Long accountProjectID)
            throws ResourceNotFoundFault {
        return this.gpProjectDelegate.deleteAccountProject(accountProjectID);
    }

    @Override
    public GPAccountProject getAccountProject(Long accountProjectID)
            throws ResourceNotFoundFault {
        return this.gpProjectDelegate.getAccountProject(accountProjectID);
    }

    @Override
    public GPAccountProject getProjectOwner(Long projectID) throws Exception {
        return this.gpProjectDelegate.getProjectOwner(projectID);
    }

    @Override
    public Boolean setProjectOwner(RequestByAccountProjectIDs request)
            throws Exception {
        return this.gpProjectDelegate.setProjectOwner(request);
    }

    @Override
    public WSGetAccountProjectsResponse getAccountProjectsByAccountID(
            Long accountID) {
        return this.gpProjectDelegate.getAccountProjectsByAccountID(accountID);
    }

    @Override
    public GPProject getDefaultProject(Long accountID) throws Exception {
        return this.gpProjectDelegate.getDefaultProject(accountID);
    }

    @Override
    public ProjectDTO getDefaultProjectDTO(Long accountID) throws Exception {
        return this.gpProjectDelegate.getDefaultProjectDTO(accountID);
    }

    @Override
    public GPProject updateDefaultProject(Long accountID, Long projectID)
            throws Exception {
        return this.gpProjectDelegate.updateDefaultProject(accountID, projectID);
    }

    @Override
    public WSGetAccountProjectsResponse getAccountProjectsByProjectID(
            Long projectID) {
        return this.gpProjectDelegate.getAccountProjectsByAccountID(projectID);
    }

    @Override
    public GPAccountProject getAccountProjectByAccountAndProjectIDs(
            Long accountID, Long projectID) throws ResourceNotFoundFault {
        return this.gpProjectDelegate.getAccountProjectByAccountAndProjectIDs(
                accountID, projectID);
    }

    @Override
    public Long getAccountProjectsCount(Long accountID, SearchRequest request)
            throws ResourceNotFoundFault {
        return this.gpProjectDelegate.getAccountProjectsCount(accountID, request);
    }

    @Override
    public GPAccountProject getDefaultAccountProject(Long accountID) throws
            ResourceNotFoundFault {
        return this.gpProjectDelegate.getDefaultAccountProject(accountID);
    }

    @Override
    public List<ProjectDTO> searchAccountProjects(Long accountID,
            PaginatedSearchRequest request) throws ResourceNotFoundFault {
        return this.gpProjectDelegate.searchAccountProjects(accountID, request);
    }

    @Override
    public Boolean saveAccountProjectProperties(
            AccountProjectPropertiesDTO accountProjectProperties)
            throws ResourceNotFoundFault, IllegalParameterFault {
        return this.gpProjectDelegate.saveAccountProjectProperties(
                accountProjectProperties);
    }

    @Override
    public ShortAccountDTOContainer getAccountsByProjectID(Long projectID)
            throws ResourceNotFoundFault {
        return this.gpProjectDelegate.getAccountsByProjectID(projectID);
    }

    @Override
    public ShortAccountDTOContainer getAccountsToShareByProjectID(Long projectID)
            throws ResourceNotFoundFault {
        return this.gpProjectDelegate.getAccountsToShareByProjectID(projectID);
    }

    @Override
    public Boolean updateAccountsProjectSharing(
            PutAccountsProjectRequest apRequest)
            throws ResourceNotFoundFault, IllegalParameterFault {
        return this.gpProjectDelegate.updateAccountsProjectSharing(apRequest);
    }
     //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Folder / Project">
    // =========================================================================
    // === Folder / Project
    // =========================================================================
    @Override
    public ProjectDTO getProjectWithRootFolders(Long projectID, Long accountID)
            throws ResourceNotFoundFault {
        return this.gpProjectDelegate.getProjectWithExpandedFolders(projectID,
                accountID);
    }

    @Override
    public ProjectDTO getProjectWithExpandedFolders(Long projectID,
            Long accountID)
            throws ResourceNotFoundFault {
        return this.gpProjectDelegate.getProjectWithExpandedFolders(projectID,
                accountID);
    }

    @Override
    public ProjectDTO exportProject(Long projectID) throws ResourceNotFoundFault {
        return this.gpProjectDelegate.exportProject(projectID);
    }

    @Override
    public Long importProject(ImportProjectRequest impRequest)
            throws IllegalParameterFault, ResourceNotFoundFault {
        return this.gpProjectDelegate.importProject(impRequest);
    }
     //</editor-fold>
}
