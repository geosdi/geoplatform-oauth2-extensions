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

import java.security.Principal;
import java.util.List;
import org.geosdi.geoplatform.core.model.GPBBox;
import org.geosdi.geoplatform.core.model.GPLayerInfo;
import org.geosdi.geoplatform.core.model.GPRasterLayer;
import org.geosdi.geoplatform.core.model.GPVectorLayer;
import org.geosdi.geoplatform.gui.shared.GPLayerType;
import org.geosdi.geoplatform.request.layer.InsertLayerRequest;
import org.geosdi.geoplatform.request.layer.WSAddLayerAndTreeModificationsRequest;
import org.geosdi.geoplatform.request.layer.WSAddLayersAndTreeModificationsRequest;
import org.geosdi.geoplatform.request.layer.WSDDLayerAndTreeModificationsRequest;
import org.geosdi.geoplatform.request.layer.WSDeleteLayerAndTreeModificationsRequest;
import org.geosdi.geoplatform.response.GetDataSourceResponse;
import org.geosdi.geoplatform.response.RasterPropertiesDTO;
import org.geosdi.geoplatform.response.ShortLayerDTO;
import org.geosdi.geoplatform.response.collection.LongListStore;
import org.geosdi.geoplatform.services.core.api.resources.GPLayerResource;

/**
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public interface SecureLayerResource extends GPLayerResource {

    Long insertLayer(Principal principal, InsertLayerRequest layerRequest)
            throws Exception;

    Long updateRasterLayer(Principal principal, GPRasterLayer layer) throws
            Exception;

    Long updateVectorLayer(Principal principal, GPVectorLayer layer) throws
            Exception;

    Boolean deleteLayer(Principal principal, Long layerID) throws Exception;

    Long saveAddedLayerAndTreeModifications(Principal principal,
            WSAddLayerAndTreeModificationsRequest addLayerRequest)
            throws Exception;

    LongListStore saveAddedLayersAndTreeModifications(Principal principal,
            WSAddLayersAndTreeModificationsRequest addLayersRequest)
            throws Exception;

    Boolean saveDeletedLayerAndTreeModifications(Principal principal,
            WSDeleteLayerAndTreeModificationsRequest deleteLayerRequest)
            throws Exception;

    Boolean saveCheckStatusLayerAndTreeModifications(Principal principal,
            Long layerID, boolean checked) throws Exception;

    Boolean saveDragAndDropLayerAndTreeModifications(Principal principal,
            WSDDLayerAndTreeModificationsRequest ddLayerReq)
            throws Exception;

    Boolean saveLayerProperties(Principal principal,
            RasterPropertiesDTO layerProperties) throws Exception;

    GPRasterLayer getRasterLayer(Principal principal, Long layerID)
            throws Exception;

    GPVectorLayer getVectorLayer(Principal principal, Long layerID)
            throws Exception;

    ShortLayerDTO getShortLayer(Principal principal, Long layerID)
            throws Exception;

    List<ShortLayerDTO> getLayers(Principal principal, Long projectID);

    GPBBox getBBox(Principal principal, Long layerID) throws Exception;

    GPLayerInfo getLayerInfo(Principal principal, Long layerID)
            throws Exception;

    GPLayerType getLayerType(Principal principal, Long layerID)
            throws Exception;

    GetDataSourceResponse getLayersDataSourceByProjectID(Principal principal,
            Long projectID) throws Exception;
}
