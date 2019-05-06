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

import io.micronaut.core.annotation.TypeHint;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;
import org.thymeleaf.standard.expression.StandardExpressionObjectFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * A temporary workaround for {@code #fields} expression.
 * <p>
 * {@code #fields} feature is only supported in Thymeleaf Spring integration.
 * So for now, I create this class as a temporary workaround.
 * <p>
 * TODO: Handle errors appropriately.
 *
 * @author Mitz Shiiba
 */
public class PetClinicExpressionObjectDialect implements IExpressionObjectDialect {
    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return new PetClinicExpressionObjectFactory();
    }

    @Override
    public String getName() {
        return "My Expression";
    }

    public static class PetClinicExpressionObjectFactory extends StandardExpressionObjectFactory {

        public static final String FIELDS_EXPRESSION_OBJECT_NAME = "fields";

        public static final Set<String> ALL_EXPRESSION_OBJECT_NAMES;

        static {
            final Set<String> allExpressionObjectNames = new LinkedHashSet<String>();
            allExpressionObjectNames.addAll(StandardExpressionObjectFactory.ALL_EXPRESSION_OBJECT_NAMES);
            allExpressionObjectNames.add(FIELDS_EXPRESSION_OBJECT_NAME);

            ALL_EXPRESSION_OBJECT_NAMES = Collections.unmodifiableSet(allExpressionObjectNames);
        }

        @Override
        public Set<String> getAllExpressionObjectNames() {
            final Set<String> allExpressionObjectNames = new LinkedHashSet<String>();
            allExpressionObjectNames.addAll(StandardExpressionObjectFactory.ALL_EXPRESSION_OBJECT_NAMES);
            allExpressionObjectNames.add(FIELDS_EXPRESSION_OBJECT_NAME);
            return allExpressionObjectNames;
        }

        @Override
        public Object buildObject(IExpressionContext context, String expressionObjectName) {
            if (FIELDS_EXPRESSION_OBJECT_NAME.equals(expressionObjectName)) {
                return new Fields(context);
            }
            return super.buildObject(context, expressionObjectName);
        }
    }

    @TypeHint(accessType = TypeHint.AccessType.ALL_PUBLIC_METHODS)
    public static class Fields {

        private final IExpressionContext context;

        public boolean hasAnyErrors() {
            return false;
        }

        public boolean hasErrors(final String field) {
            return false;
        }

        public List<String> allErrors() {
            return new ArrayList<>();
        }

        public Fields(final IExpressionContext context) {
            super();
            this.context = context;
        }
    }

}
