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

import io.dropwizard.auth.Auth;
import java.security.Principal;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.geosdi.geoplatform.core.model.GPMessage;
import org.geosdi.geoplatform.request.message.MarkMessageReadByDateRequest;
import org.geosdi.geoplatform.response.MessageDTO;
import org.geosdi.geoplatform.response.message.GetMessageResponse;
import org.geosdi.geoplatform.services.rs.path.GPServiceRSPathConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Path(value = GPServiceRSPathConfig.GP_SECURE_MESSAGE_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Component(value = "secureMessageResource")
public class GPSecureMessageResource extends BaseMessageResource {

    private static final Logger logger = LoggerFactory.getLogger(
            GPSecureMessageResource.class);

    @POST
    @Path(value = GPServiceRSPathConfig.INSERT_MESSAGE_PATH)
    @Override
    public Long insertMessage(@Auth Principal principal, GPMessage message)
            throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@@@@Executing secure insertMessage - "
                + "Principal : {}\n\n", principal.getName());
        return super.insertMessage(message);
    }

    @POST
    @Path(value = GPServiceRSPathConfig.INSERT_MULTI_MESSAGE_PATH)
    @Override
    public Boolean insertMultiMessage(@Auth Principal principal,
            MessageDTO messageDTO) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@Executing secure insertMultiMessage - "
                + "Principal : {}\n\n", principal.getName());
        return super.insertMultiMessage(messageDTO);
    }

    @DELETE
    @Path(value = GPServiceRSPathConfig.DELETE_MESSAGE_PATH)
    @Override
    public Boolean deleteMessage(@Auth Principal principal,
            @PathParam(value = "messageID") Long messageID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@Executing secure deleteMessage - "
                + "Principal : {}\n\n", principal.getName());
        return super.deleteMessage(messageID);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_MESSAGE_DETAIL_PATH)
    @Override
    public GPMessage getMessageDetail(@Auth Principal principal,
            @PathParam(value = "messageID") Long messageID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@Executing secure getMessageDetail - "
                + "Principal : {}\n\n", principal.getName());
        return super.getMessageDetail(messageID);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_ALL_MESSAGES_BY_RECIPIENT_PATH)
    @Override
    public GetMessageResponse getAllMessagesByRecipient(
            @Auth Principal principal,
            @PathParam(value = "recipientID") Long recipientID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@Executing secure getMessageDetail - "
                + "Principal : {}\n\n", principal.getName());
        return super.getAllMessagesByRecipient(recipientID);
    }

    @GET
    @Path(value = GPServiceRSPathConfig.GET_UNREAD_MESSAGES_BY_RECIPIENT_PATH)
    @Override
    public GetMessageResponse getUnreadMessagesByRecipient(
            @Auth Principal principal,
            @PathParam(value = "recipientID") Long recipientID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@Executing secure "
                + "getUnreadMessagesByRecipient - Principal : {}\n\n",
                principal.getName());
        return super.getUnreadMessagesByRecipient(recipientID);
    }

    @PUT
    @Path(value = GPServiceRSPathConfig.MARK_MESSAGE_AS_READ_PATH)
    @Override
    public Boolean markMessageAsRead(@Auth Principal principal,
            Long messageID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@Executing secure markMessageAsRead - "
                + "Principal : {}\n\n", principal.getName());
        return super.markMessageAsRead(messageID);
    }

    @PUT
    @Path(value = GPServiceRSPathConfig.MARK_ALL_MESSAGES_AS_READ_BY_RECIPIENT_PATH)
    @Override
    public Boolean markAllMessagesAsReadByRecipient(@Auth Principal principal,
            Long recipientID) throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@Executing secure"
                + " markAllMessagesAsReadByRecipient - Principal : {}\n\n",
                principal.getName());
        return super.markAllMessagesAsReadByRecipient(recipientID);
    }

    @PUT
    @Path(value = GPServiceRSPathConfig.MARK_MESSAGES_AS_READ_BY_DATE_PATH)
    @Override
    public Boolean markMessagesAsReadByDate(@Auth Principal principal,
            MarkMessageReadByDateRequest markMessageAsReadByDateReq)
            throws Exception {
        logger.debug("\n\n@@@@@@@@@@@@@@Executing secure"
                + " markMessagesAsReadByDate - Principal : {}\n\n",
                principal.getName());
        return super.markMessagesAsReadByDate(markMessageAsReadByDateReq);
    }
}
