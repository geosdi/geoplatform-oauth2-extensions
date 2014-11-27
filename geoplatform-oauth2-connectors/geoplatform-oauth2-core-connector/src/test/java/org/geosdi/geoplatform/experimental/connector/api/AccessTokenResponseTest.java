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
package org.geosdi.geoplatform.experimental.connector.api;

import org.geosdi.geoplatform.experimental.connector.api.auth.responce.AccessTokenResponse;
import org.geosdi.geoplatform.support.jackson.GPJacksonSupport;
import static org.geosdi.geoplatform.support.jackson.property.GPJacksonSupportEnum.ACCEPT_SINGLE_VALUE_AS_ARRAY_ENABLE;
import static org.geosdi.geoplatform.support.jackson.property.GPJacksonSupportEnum.FAIL_ON_IGNORED_PROPERTIES_DISABLE;
import static org.geosdi.geoplatform.support.jackson.property.GPJacksonSupportEnum.FAIL_ON_NULL_FOR_PRIMITIVES_DISABLE;
import static org.geosdi.geoplatform.support.jackson.property.GPJacksonSupportEnum.INDENT_OUTPUT_ENABLE;
import static org.geosdi.geoplatform.support.jackson.property.GPJacksonSupportEnum.UNWRAP_ROOT_VALUE_DISABLE;
import static org.geosdi.geoplatform.support.jackson.property.GPJacksonSupportEnum.USE_WRAPPER_NAME_AS_PROPERTY_NAME_ENABLE;
import static org.geosdi.geoplatform.support.jackson.property.GPJacksonSupportEnum.WRAP_ROOT_VALUE_ENABLE;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class AccessTokenResponseTest {

    static final Logger logger = LoggerFactory.getLogger(
            AccessTokenResponseTest.class);
    //
    private static final String ACCESS_TOKEN_JSON = "access-token-response.json";

    private static GPJacksonSupport jacksonSupport;

    @BeforeClass
    public static void beforeClass() {
        jacksonSupport = new GPJacksonSupport(UNWRAP_ROOT_VALUE_DISABLE,
                FAIL_ON_IGNORED_PROPERTIES_DISABLE,
                FAIL_ON_NULL_FOR_PRIMITIVES_DISABLE,
                ACCEPT_SINGLE_VALUE_AS_ARRAY_ENABLE,
                WRAP_ROOT_VALUE_ENABLE,
                INDENT_OUTPUT_ENABLE,
                USE_WRAPPER_NAME_AS_PROPERTY_NAME_ENABLE);
    }

    @Test
    public void verifyTokenResponseTest() throws Exception {
        AccessTokenResponse accessTokenResponse = jacksonSupport.getDefaultMapper().readValue(
                Thread.currentThread().getContextClassLoader().getResourceAsStream(
                        ACCESS_TOKEN_JSON), AccessTokenResponse.class);

        logger.info("\n\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@AccessTokenResponse"
                + " : {}\n\n", accessTokenResponse);
    }

}
