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

import io.micronaut.views.thymeleaf.LinkBuilder;
import org.thymeleaf.context.IExpressionContext;

import java.util.Map;

/**
 * Workaround LinkBuilder for the following issue:
 * https://github.com/micronaut-projects/micronaut-core/issues/1619
 *
 * @author Mitz Shiiba
 */
public class WorkaroundLinkBuilder extends LinkBuilder {
    @Override
    protected String computeContextPath(IExpressionContext context, String base, Map<String, Object> parameters) {
        return null;
    }
}
