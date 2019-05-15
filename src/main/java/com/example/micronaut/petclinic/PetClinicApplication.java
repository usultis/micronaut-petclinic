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
package com.example.micronaut.petclinic;

import com.example.micronaut.petclinic.system.thymeleaf.PetClinicExpressionObjectDialect;

import org.hibernate.dialect.PostgreSQL95Dialect;
import org.thymeleaf.extras.java8time.expression.Temporals;
import org.thymeleaf.standard.expression.AdditionExpression;
import org.thymeleaf.standard.expression.AdditionSubtractionExpression;
import org.thymeleaf.standard.expression.AndExpression;
import org.thymeleaf.standard.expression.DivisionExpression;
import org.thymeleaf.standard.expression.EqualsExpression;
import org.thymeleaf.standard.expression.EqualsNotEqualsExpression;
import org.thymeleaf.standard.expression.GreaterLesserExpression;
import org.thymeleaf.standard.expression.GreaterOrEqualToExpression;
import org.thymeleaf.standard.expression.GreaterThanExpression;
import org.thymeleaf.standard.expression.LessOrEqualToExpression;
import org.thymeleaf.standard.expression.LessThanExpression;
import org.thymeleaf.standard.expression.MultiplicationDivisionRemainderExpression;
import org.thymeleaf.standard.expression.MultiplicationExpression;
import org.thymeleaf.standard.expression.NotEqualsExpression;
import org.thymeleaf.standard.expression.OrExpression;
import org.thymeleaf.standard.expression.RemainderExpression;
import org.thymeleaf.standard.expression.SubtractionExpression;

import io.micronaut.core.annotation.TypeHint;
import io.micronaut.runtime.Micronaut;

/**
 * PetClinic Micronaut Application.
 *
 * @author Dave Syer
 * @author Mitz Shiiba
 */
@TypeHint(value = {

    // Thymeleaf
    MultiplicationDivisionRemainderExpression.class,
    MultiplicationExpression.class,
    RemainderExpression.class,
    DivisionExpression.class,
    AndExpression.class,
    AdditionSubtractionExpression.class,
    AdditionExpression.class,
    SubtractionExpression.class,
    GreaterLesserExpression.class,
    LessOrEqualToExpression.class,
    GreaterOrEqualToExpression.class,
    LessThanExpression.class,
    GreaterThanExpression.class,
    EqualsNotEqualsExpression.class,
    EqualsExpression.class,
    NotEqualsExpression.class,
    OrExpression.class,
    Temporals.class,
    PetClinicExpressionObjectDialect.Fields.class,
    // Others
    PostgreSQL95Dialect.class
}, accessType = {TypeHint.AccessType.ALL_PUBLIC})
public class PetClinicApplication {

    public static void main(String[] args) {
        Micronaut.run(PetClinicApplication.class, args);
    }

}
