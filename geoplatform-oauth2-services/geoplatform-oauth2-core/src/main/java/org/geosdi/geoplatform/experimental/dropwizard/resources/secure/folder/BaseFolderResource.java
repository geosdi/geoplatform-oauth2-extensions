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
package org.geosdi.geoplatform.experimental.dropwizard.resources.secure.folder;

import java.util.List;
import javax.annotation.Resource;
import org.geosdi.geoplatform.core.model.GPFolder;
import org.geosdi.geoplatform.experimental.dropwizard.delegate.SecureCoreDelegate;
import org.geosdi.geoplatform.request.folder.InsertFolderRequest;
import org.geosdi.geoplatform.request.folder.WSAddFolderAndTreeModificationsRequest;
import org.geosdi.geoplatform.request.folder.WSDDFolderAndTreeModifications;
import org.geosdi.geoplatform.request.folder.WSDeleteFolderAndTreeModifications;
import org.geosdi.geoplatform.response.FolderDTO;
import org.geosdi.geoplatform.response.collection.TreeFolderElementsStore;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
abstract class BaseFolderResource implements SecureFolderResource {

    @Resource(name = "gpSecureCoreDelegate")
    protected SecureCoreDelegate gpSecureCoreDelegate;

    @Override
    public Long insertFolder(InsertFolderRequest insertFolderRequest) throws
            Exception {
        return this.gpSecureCoreDelegate.insertFolder(insertFolderRequest);
    }

    @Override
    public Long updateFolder(GPFolder folder) throws Exception {
        return this.gpSecureCoreDelegate.updateFolder(folder);
    }

    @Override
    public Boolean deleteFolder(Long folderID) throws Exception {
        return this.gpSecureCoreDelegate.deleteFolder(folderID);
    }

    @Override
    public Long saveFolderProperties(Long folderID, String folderName,
            boolean checked, boolean expanded) throws Exception {
        return this.gpSecureCoreDelegate.saveFolderProperties(folderID, folderName,
                checked, expanded);
    }

    @Override
    public Long saveAddedFolderAndTreeModifications(
            WSAddFolderAndTreeModificationsRequest sftModificationRequest)
            throws Exception {
        return this.gpSecureCoreDelegate.saveAddedFolderAndTreeModifications(
                sftModificationRequest);
    }

    @Override
    public Boolean saveDeletedFolderAndTreeModifications(
            WSDeleteFolderAndTreeModifications sdfModificationRequest) throws
            Exception {
        return this.gpSecureCoreDelegate.saveDeletedFolderAndTreeModifications(
                sdfModificationRequest);
    }

    @Override
    public Boolean saveDragAndDropFolderAndTreeModifications(
            WSDDFolderAndTreeModifications sddfTreeModificationRequest) throws
            Exception {
        return this.gpSecureCoreDelegate.saveDragAndDropFolderAndTreeModifications(
                sddfTreeModificationRequest);
    }

    @Override
    public FolderDTO getShortFolder(Long folderID) throws Exception {
        return this.gpSecureCoreDelegate.getShortFolder(folderID);
    }

    @Override
    public GPFolder getFolderDetail(Long folderID) throws Exception {
        return this.gpSecureCoreDelegate.getFolderDetail(folderID);
    }

    @Override
    public List<FolderDTO> getChildrenFolders(Long folderID) {
        return this.gpSecureCoreDelegate.getChildrenFolders(folderID);
    }

    @Override
    public TreeFolderElementsStore getChildrenElements(Long folderID) {
        return this.gpSecureCoreDelegate.getChildrenElements(folderID);
    }

}
