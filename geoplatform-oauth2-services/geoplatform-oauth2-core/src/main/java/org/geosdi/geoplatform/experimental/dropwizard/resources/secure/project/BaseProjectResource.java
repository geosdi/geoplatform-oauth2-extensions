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

import org.geosdi.geoplatform.core.model.GPAccountProject;
import org.geosdi.geoplatform.core.model.GPProject;
import org.geosdi.geoplatform.experimental.dropwizard.delegate.SecureCoreDelegate;
import org.geosdi.geoplatform.request.PaginatedSearchRequest;
import org.geosdi.geoplatform.request.PutAccountsProjectRequest;
import org.geosdi.geoplatform.request.RequestByAccountProjectIDs;
import org.geosdi.geoplatform.request.SearchRequest;
import org.geosdi.geoplatform.request.project.CloneProjectRequest;
import org.geosdi.geoplatform.request.project.ImportProjectRequest;
import org.geosdi.geoplatform.request.project.SaveProjectRequest;
import org.geosdi.geoplatform.response.AccountProjectPropertiesDTO;
import org.geosdi.geoplatform.response.ProjectDTO;
import org.geosdi.geoplatform.response.ShortAccountDTOContainer;
import org.geosdi.geoplatform.response.WSGetAccountProjectsResponse;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
abstract class BaseProjectResource implements SecureProjectResource {

    @Resource(name = "gpSecureCoreDelegate")
    protected SecureCoreDelegate gpSecureCoreDelegate;

    //<editor-fold defaultstate="collapsed" desc="Project">
    // =========================================================================
    // === Project
    // ========================================================================= 
    @Override
    public Long saveProject(SaveProjectRequest saveProjectRequest)
            throws Exception {
        return this.gpSecureCoreDelegate.saveProject(saveProjectRequest);
    }

    @Override
    public Long insertProject(GPProject project) throws Exception {
        return this.gpSecureCoreDelegate.insertProject(project);
    }

    @Override
    public Long updateProject(GPProject project) throws Exception {
        return this.gpSecureCoreDelegate.updateProject(project);
    }

    @Override
    public Boolean deleteProject(Long projectID) throws Exception {
        return this.gpSecureCoreDelegate.deleteProject(projectID);
    }

    @Override
    public GPProject getProjectDetail(Long projectID) throws Exception {
        return this.gpSecureCoreDelegate.getProjectDetail(projectID);
    }

    @Override
    public Integer getNumberOfElementsProject(Long projectID) throws Exception {
        return this.gpSecureCoreDelegate.getNumberOfElementsProject(projectID);
    }

    @Override
    public void setProjectShared(Long projectID) throws Exception {
        this.gpSecureCoreDelegate.setProjectShared(projectID);
    }

    /**
     * @param cloneProjectRequest
     * @return
     * @throws Exception
     */
    @Override
    public Long cloneProject(CloneProjectRequest cloneProjectRequest) throws Exception {
        return this.gpSecureCoreDelegate.cloneProject(cloneProjectRequest);
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="AccountProject">
    // =========================================================================
    // === AccountProject
    // =========================================================================
    @Override
    public Long insertAccountProject(GPAccountProject accountProject) throws
            Exception {
        return this.gpSecureCoreDelegate.insertAccountProject(accountProject);
    }

    @Override
    public Long updateAccountProject(GPAccountProject accountProject)
            throws Exception {
        return this.gpSecureCoreDelegate.updateAccountProject(accountProject);
    }

    @Override
    public Boolean deleteAccountProject(Long accountProjectID)
            throws Exception {
        return this.gpSecureCoreDelegate.deleteAccountProject(accountProjectID);
    }

    @Override
    public GPAccountProject getAccountProject(Long accountProjectID)
            throws Exception {
        return this.gpSecureCoreDelegate.getAccountProject(accountProjectID);
    }

    @Override
    public GPAccountProject getProjectOwner(Long projectID) throws Exception {
        return this.gpSecureCoreDelegate.getProjectOwner(projectID);
    }

    @Override
    public Boolean setProjectOwner(RequestByAccountProjectIDs request)
            throws Exception {
        return this.gpSecureCoreDelegate.setProjectOwner(request);
    }

    @Override
    public WSGetAccountProjectsResponse getAccountProjectsByAccountID(
            Long accountID) {
        return this.gpSecureCoreDelegate.getAccountProjectsByAccountID(accountID);
    }

    @Override
    public GPProject getDefaultProject(Long accountID) throws Exception {
        return this.gpSecureCoreDelegate.getDefaultProject(accountID);
    }

    @Override
    public ProjectDTO getDefaultProjectDTO(Long accountID) throws Exception {
        return this.gpSecureCoreDelegate.getDefaultProjectDTO(accountID);
    }

    @Override
    public GPProject updateDefaultProject(Long accountID, Long projectID)
            throws Exception {
        return this.gpSecureCoreDelegate.updateDefaultProject(accountID,
                projectID);
    }

    @Override
    public WSGetAccountProjectsResponse getAccountProjectsByProjectID(
            Long projectID) {
        return this.gpSecureCoreDelegate.getAccountProjectsByProjectID(projectID);
    }

    @Override
    public GPAccountProject getAccountProjectByAccountAndProjectIDs(
            Long accountID, Long projectID) throws Exception {
        return this.gpSecureCoreDelegate.getAccountProjectByAccountAndProjectIDs(
                accountID, projectID);
    }

    @Override
    public Long getAccountProjectsCount(Long accountID, SearchRequest request)
            throws Exception {
        return this.gpSecureCoreDelegate.getAccountProjectsCount(accountID,
                request);
    }

    @Override
    public GPAccountProject getDefaultAccountProject(Long accountID) throws
            Exception {
        return this.gpSecureCoreDelegate.getDefaultAccountProject(accountID);
    }

    @Override
    public List<ProjectDTO> searchAccountProjects(Long accountID,
            PaginatedSearchRequest request) throws Exception {
        return this.gpSecureCoreDelegate.searchAccountProjects(accountID,
                request);
    }

    @Override
    public Boolean saveAccountProjectProperties(
            AccountProjectPropertiesDTO accountProjectProperties)
            throws Exception {
        return this.gpSecureCoreDelegate.saveAccountProjectProperties(
                accountProjectProperties);
    }

    @Override
    public ShortAccountDTOContainer getAccountsByProjectID(Long projectID)
            throws Exception {
        return this.gpSecureCoreDelegate.getAccountsByProjectID(projectID);
    }

    @Override
    public ShortAccountDTOContainer getAccountsToShareByProjectID(Long projectID)
            throws Exception {
        return this.gpSecureCoreDelegate.getAccountsToShareByProjectID(projectID);
    }

    @Override
    public Boolean updateAccountsProjectSharing(
            PutAccountsProjectRequest apRequest)
            throws Exception {
        return this.gpSecureCoreDelegate.updateAccountsProjectSharing(apRequest);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Folder / Project">
    // =========================================================================
    // === Folder / Project
    // =========================================================================
    @Override
    public ProjectDTO getProjectWithRootFolders(Long projectID, Long accountID)
            throws Exception {
        return this.gpSecureCoreDelegate.getProjectWithExpandedFolders(projectID,
                accountID);
    }

    @Override
    public ProjectDTO getProjectWithExpandedFolders(Long projectID,
            Long accountID) throws Exception {
        return this.gpSecureCoreDelegate.getProjectWithExpandedFolders(projectID,
                accountID);
    }

    @Override
    public ProjectDTO exportProject(Long projectID) throws Exception {
        return this.gpSecureCoreDelegate.exportProject(projectID);
    }

    @Override
    public Long importProject(ImportProjectRequest impRequest)
            throws Exception {
        return this.gpSecureCoreDelegate.importProject(impRequest);
    }
    //</editor-fold>
}
