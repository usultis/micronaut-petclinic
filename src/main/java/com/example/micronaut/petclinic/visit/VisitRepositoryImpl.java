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
package com.example.micronaut.petclinic.visit;

import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.core.annotation.TypeHint;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import static io.micronaut.core.annotation.TypeHint.AccessType.*;

/**
 * Repository implementation for <code>Visit</code> domain objects.
 *
 * @author Mitz Shiiba
 */
@Singleton
@TypeHint(accessType = {ALL_PUBLIC_CONSTRUCTORS, ALL_DECLARED_FIELDS, ALL_PUBLIC_METHODS})
public class VisitRepositoryImpl implements VisitRepository {

    private final EntityManager entityManager;

    public VisitRepositoryImpl(@CurrentSession EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Visit visit) {
        entityManager.persist(visit);
    }

    @Override
    public List<Visit> findByPetId(Integer petId) {
        String qlString = "SELECT v FROM Visit as v WHERE petId = :petId";
        TypedQuery<Visit> query = entityManager.createQuery(qlString, Visit.class)
            .setParameter("petId", petId);
        return query.getResultList();
    }
}
