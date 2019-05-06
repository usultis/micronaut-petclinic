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

import io.micronaut.views.thymeleaf.ThymeleafViewsRendererConfiguration;
import org.thymeleaf.messageresolver.StandardMessageResolver;
import org.thymeleaf.templateresource.ClassLoaderTemplateResource;
import org.thymeleaf.templateresource.ITemplateResource;

import java.util.Locale;
import java.util.Map;

/**
 * A message resolver to use Spring style message resource.
 *
 * @author Mitz Shiiba
 */
public class PetClinicMessageResolver extends StandardMessageResolver {

    private static final String MESSAGE_RESOURCE_PATH = "messages/messages";

    private final ThymeleafViewsRendererConfiguration rendererConfiguration;

    public PetClinicMessageResolver(ThymeleafViewsRendererConfiguration rendererConfiguration) {
        super();
        this.rendererConfiguration = rendererConfiguration;
    }

    @Override
    protected Map<String, String> resolveMessagesForTemplate(String template, ITemplateResource
        templateResource, Locale locale) {
        ITemplateResource messageResource = new ClassLoaderTemplateResource(MESSAGE_RESOURCE_PATH,
            rendererConfiguration.getCharacterEncoding());
        // TODO: Micronaut uses US local only. I would like to think about it.
        return super.resolveMessagesForTemplate(template, messageResource, locale);
    }

}
