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
package org.geosdi.geoplatform.experimental.dropwizard.app;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.jersey.jackson.JacksonMessageBodyProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.geosdi.geoplatform.experimental.dropwizard.auth.model.GPAuthenticatedPrincipal;
import org.geosdi.geoplatform.experimental.dropwizard.auth.provider.exception.OAuth2ExceptionProvider;
import org.geosdi.geoplatform.experimental.dropwizard.config.CoreServiceConfig;
import org.geosdi.geoplatform.experimental.dropwizard.config.spring.CoreOAuth2ServiceLoader;
import org.geosdi.geoplatform.experimental.dropwizard.health.CoreServiceHealthCheck;
import org.geosdi.geoplatform.experimental.dropwizard.oauth.CoreOAuthAuthenticator;
import org.geosdi.geoplatform.support.jackson.GPJacksonSupport;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.ws.rs.Path;
import java.security.Principal;
import java.util.Map;

/**
 * <p>
 * The mail Class for Dropwizard. TODO : try to use Spring Boot to start the
 * application
 * </p>
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class CoreServiceApp extends Application<CoreServiceConfig> {

    public static void main(String[] args) throws Exception {
        new CoreServiceApp().run(args);
    }

    @Override
    public void initialize(Bootstrap<CoreServiceConfig> bootstrap) {
    }

    @Override
    public void run(CoreServiceConfig t, Environment e) throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(CoreOAuth2ServiceLoader.class);
        ctx.registerShutdownHook();
        ctx.start();

        e.jersey().register(new JacksonMessageBodyProvider(new GPJacksonSupport().getDefaultMapper()));
        e.jersey().register(new OAuth2ExceptionProvider());

        e.jersey().register(new AuthDynamicFeature(
                new OAuthCredentialAuthFilter.Builder<GPAuthenticatedPrincipal>()
                        .setAuthenticator(new CoreOAuthAuthenticator(t))
                        .setPrefix("Bearer")
                        .buildAuthFilter()));
        e.jersey().register(RolesAllowedDynamicFeature.class);
        e.jersey().register(new AuthValueFactoryProvider.Binder<>(Principal.class));

        e.healthChecks().register("service-health-check",
                new CoreServiceHealthCheck());

        Map<String, Object> resources = ctx.getBeansWithAnnotation(Path.class);

        for (Map.Entry<String, Object> entry : resources.entrySet()) {
            e.jersey().register(entry.getValue());
        }
    }

    @Override
    public String getName() {
        return "GeoPlatform OAUTH2 Core Service Extension";
    }

}
