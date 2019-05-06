/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.micronaut.petclinic.system.micronaut;

import io.micronaut.configuration.hibernate.jpa.EntityManagerFactoryBean;
import io.micronaut.configuration.hibernate.jpa.JpaConfiguration;
import io.micronaut.context.annotation.EachBean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Parameter;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.env.Environment;
import io.micronaut.core.beans.BeanIntrospection;
import io.micronaut.core.beans.BeanIntrospector;
import io.micronaut.core.util.ArrayUtils;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;

import javax.annotation.Nonnull;
import javax.persistence.Entity;

/**
 * Replace MetadataSources generation to use BeanIntrospector instead of Environment for Entity scan.
 * <p>
 * That's because Environment scan doesn't work with native image.
 *
 * @author Mitz Shiiba
 */
@Factory
public class NoScanMetadataSourcesFactoryBean {

    private Environment environment;

    public NoScanMetadataSourcesFactoryBean(Environment environment) {
        this.environment = environment;
    }

    @EachBean(StandardServiceRegistry.class)
    @Replaces(value = MetadataSources.class, factory = EntityManagerFactoryBean.class)
    protected MetadataSources hibernateMetadataSources(
        @Parameter JpaConfiguration jpaConfiguration,
        StandardServiceRegistry standardServiceRegistry) {

        // Environment scan doesn't work with native image.
        MetadataSources metadataSources = createMetadataSources(standardServiceRegistry);
        String[] packagesToScan = getPackagesToScan(jpaConfiguration);
        BeanIntrospector.SHARED
            .findIntrospections(Entity.class, packagesToScan).stream()
            .map(BeanIntrospection::getBeanType)
            .forEach(metadataSources::addAnnotatedClass);
        return metadataSources;
    }

    private String[] getPackagesToScan(JpaConfiguration jpaConfiguration) {
        String[] packagesToScan = jpaConfiguration.getPackagesToScan();
        if (ArrayUtils.isNotEmpty(packagesToScan)) {
            return packagesToScan;
        }
        return environment.getPackages().toArray(new String[0]);
    }

    private MetadataSources createMetadataSources(@Nonnull StandardServiceRegistry serviceRegistry) {
        return new MetadataSources(serviceRegistry);
    }

}
