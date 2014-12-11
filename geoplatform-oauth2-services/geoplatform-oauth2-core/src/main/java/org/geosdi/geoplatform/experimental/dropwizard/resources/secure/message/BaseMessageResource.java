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
package org.geosdi.geoplatform.experimental.dropwizard.resources.secure.message;

import java.util.List;
import javax.annotation.Resource;
import org.geosdi.geoplatform.core.model.GPMessage;
import org.geosdi.geoplatform.experimental.dropwizard.delegate.SecureCoreDelegate;
import org.geosdi.geoplatform.request.message.MarkMessageReadByDateRequest;
import org.geosdi.geoplatform.response.MessageDTO;

/**
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
abstract class BaseMessageResource implements SecureMessageResource {
    
    @Resource(name = "gpSecureCoreDelegate")
    protected SecureCoreDelegate gpSecureCoreDelegate;

    @Override
    public Long insertMessage(GPMessage message) throws Exception {
        return this.gpSecureCoreDelegate.insertMessage(message);
    }

    @Override
    public Boolean insertMultiMessage(MessageDTO messageDTO) throws Exception {
        return this.gpSecureCoreDelegate.insertMultiMessage(messageDTO);
    }

    @Override
    public Boolean deleteMessage(Long messageID) throws Exception {
        return this.gpSecureCoreDelegate.deleteMessage(messageID);
    }

    @Override
    public GPMessage getMessageDetail(Long messageID) throws Exception {
        return this.gpSecureCoreDelegate.getMessageDetail(messageID);
    }

    @Override
    public List<GPMessage> getAllMessagesByRecipient(Long recipientID)
            throws Exception {
        return this.gpSecureCoreDelegate.getAllMessagesByRecipient(recipientID);
    }

    @Override
    public List<GPMessage> getUnreadMessagesByRecipient(Long recipientID)
            throws Exception {
        return this.gpSecureCoreDelegate.getUnreadMessagesByRecipient(recipientID);
    }

    @Override
    public Boolean markMessageAsRead(Long messageID) throws Exception {
        return this.gpSecureCoreDelegate.markMessageAsRead(messageID);
    }

    @Override
    public Boolean markAllMessagesAsReadByRecipient(Long recipientID)
            throws Exception {
        return this.gpSecureCoreDelegate.markAllMessagesAsReadByRecipient(
                recipientID);
    }

    @Override
    public Boolean markMessagesAsReadByDate(
            MarkMessageReadByDateRequest markMessageAsReadByDateReq)
            throws Exception {
        return this.gpSecureCoreDelegate.markMessagesAsReadByDate(
                markMessageAsReadByDateReq);
    }
}
