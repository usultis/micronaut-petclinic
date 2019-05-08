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
package com.example.micronaut.petclinic.system.thymeleaf;

import io.micronaut.context.event.BeanCreatedEvent;
import io.micronaut.context.event.BeanCreatedEventListener;
import io.micronaut.views.thymeleaf.ThymeleafViewsRendererConfiguration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import javax.inject.Singleton;

/**
 * Customize Thymeleaf engine.
 */
@Singleton
public class ThymeleafCustomizer implements BeanCreatedEventListener<TemplateEngine> {

    private ThymeleafViewsRendererConfiguration rendererConfiguration;

    public ThymeleafCustomizer(ThymeleafViewsRendererConfiguration rendererConfiguration) {
        this.rendererConfiguration = rendererConfiguration;
    }

    @Override
    public TemplateEngine onCreated(BeanCreatedEvent<TemplateEngine> event) {
        TemplateEngine engine = event.getBean();
        engine.addDialect(new Java8TimeDialect());
        engine.addDialect(new PetClinicExpressionObjectDialect());
        engine.setMessageResolver(new PetClinicMessageResolver(rendererConfiguration));
        return engine;
    }
}
