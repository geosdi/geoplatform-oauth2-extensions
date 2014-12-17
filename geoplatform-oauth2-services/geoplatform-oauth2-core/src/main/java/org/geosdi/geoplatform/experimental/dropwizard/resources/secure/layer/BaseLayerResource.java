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
package org.geosdi.geoplatform.experimental.dropwizard.resources.secure.layer;

import javax.annotation.Resource;
import org.geosdi.geoplatform.core.model.GPBBox;
import org.geosdi.geoplatform.core.model.GPLayerInfo;
import org.geosdi.geoplatform.core.model.GPRasterLayer;
import org.geosdi.geoplatform.core.model.GPVectorLayer;
import org.geosdi.geoplatform.experimental.dropwizard.delegate.SecureCoreDelegate;
import org.geosdi.geoplatform.gui.shared.GPLayerType;
import org.geosdi.geoplatform.request.layer.InsertLayerRequest;
import org.geosdi.geoplatform.request.layer.WSAddLayerAndTreeModificationsRequest;
import org.geosdi.geoplatform.request.layer.WSAddLayersAndTreeModificationsRequest;
import org.geosdi.geoplatform.request.layer.WSDDLayerAndTreeModificationsRequest;
import org.geosdi.geoplatform.request.layer.WSDeleteLayerAndTreeModificationsRequest;
import org.geosdi.geoplatform.response.GetDataSourceResponse;
import org.geosdi.geoplatform.response.RasterPropertiesDTO;
import org.geosdi.geoplatform.response.ShortLayerDTO;
import org.geosdi.geoplatform.response.ShortLayerDTOContainer;
import org.geosdi.geoplatform.response.collection.LongListStore;

/**
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
abstract class BaseLayerResource implements SecureLayerResource {
    
    @Resource(name = "gpSecureCoreDelegate")
    protected SecureCoreDelegate gpSecureCoreDelegate;

    @Override
    public Long insertLayer(InsertLayerRequest layerRequest) throws Exception {
        return this.gpSecureCoreDelegate.insertLayer(layerRequest);
    }

    @Override
    public Long updateRasterLayer(GPRasterLayer layer) throws Exception {
        return this.gpSecureCoreDelegate.updateRasterLayer(layer);
    }

    @Override
    public Long updateVectorLayer(GPVectorLayer layer) throws Exception {
        return this.gpSecureCoreDelegate.updateVectorLayer(layer);
    }

    @Override
    public Boolean deleteLayer(Long layerID) throws Exception {
        return this.gpSecureCoreDelegate.deleteLayer(layerID);
    }

    @Override
    public Long saveAddedLayerAndTreeModifications(
            WSAddLayerAndTreeModificationsRequest addLayerRequest)
            throws Exception {
        return this.gpSecureCoreDelegate.saveAddedLayerAndTreeModifications(
                addLayerRequest);
    }

    @Override
    public LongListStore saveAddedLayersAndTreeModifications(
            WSAddLayersAndTreeModificationsRequest addLayersRequest)
            throws Exception {
        return this.gpSecureCoreDelegate.saveAddedLayersAndTreeModifications(
                addLayersRequest);
    }

    @Override
    public Boolean saveDeletedLayerAndTreeModifications(
            WSDeleteLayerAndTreeModificationsRequest deleteLayerRequest)
            throws Exception {
        return this.gpSecureCoreDelegate.saveDeletedLayerAndTreeModifications(
                deleteLayerRequest);
    }

    @Override
    public Boolean saveCheckStatusLayerAndTreeModifications(Long layerID,
            boolean checked) throws Exception {
        return this.gpSecureCoreDelegate.saveCheckStatusLayerAndTreeModifications(
                layerID, checked);
    }

    @Override
    public Boolean saveDragAndDropLayerAndTreeModifications(
            WSDDLayerAndTreeModificationsRequest ddLayerReq) throws Exception {
        return this.gpSecureCoreDelegate.saveDragAndDropLayerAndTreeModifications(
                ddLayerReq);
    }

    @Override
    public Boolean saveLayerProperties(RasterPropertiesDTO layerProperties)
            throws Exception {
        return this.gpSecureCoreDelegate.saveLayerProperties(layerProperties);
    }

    @Override
    public GPRasterLayer getRasterLayer(Long layerID) throws Exception {
        return this.gpSecureCoreDelegate.getRasterLayer(layerID);
    }

    @Override
    public GPVectorLayer getVectorLayer(Long layerID) throws Exception {
        return this.gpSecureCoreDelegate.getVectorLayer(layerID);
    }

    @Override
    public ShortLayerDTO getShortLayer(Long layerID) throws Exception {
        return this.gpSecureCoreDelegate.getShortLayer(layerID);
    }

    @Override
    public ShortLayerDTOContainer getLayers(Long projectID) {
        return this.gpSecureCoreDelegate.getLayers(projectID);
    }

    @Override
    public GPBBox getBBox(Long layerID) throws Exception {
        return this.gpSecureCoreDelegate.getBBox(layerID);
    }

    @Override
    public GPLayerInfo getLayerInfo(Long layerID) throws Exception {
        return this.gpSecureCoreDelegate.getLayerInfo(layerID);
    }

    @Override
    public GPLayerType getLayerType(Long layerID) throws Exception {
        return this.gpSecureCoreDelegate.getLayerType(layerID);
    }

    @Override
    public GetDataSourceResponse getLayersDataSourceByProjectID(Long projectID)
            throws Exception {
        return this.gpSecureCoreDelegate.getLayersDataSourceByProjectID(projectID);
    }
}
