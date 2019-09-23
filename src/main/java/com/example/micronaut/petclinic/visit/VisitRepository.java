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
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.GenericRepository;
import io.micronaut.spring.tx.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Repository class for <code>Visit</code> domain objects.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Mitz Shiiba
 */
@Repository
public abstract class VisitRepository implements GenericRepository<Visit, Integer> {

    private final EntityManager entityManager;

    public VisitRepository(@CurrentSession EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Save a <code>Visit</code> to the data store, either inserting or updating it.
     *
     * @param visit the <code>Visit</code> to save
     * @see com.example.micronaut.petclinic.model.BaseEntity#isNew
     */
    @Transactional
    public void save(Visit visit) {
        if (visit.isNew()) {
            entityManager.persist(visit);
        } else {
            entityManager.merge(visit);
        }
    }

    @Transactional(readOnly = true)
    public abstract List<Visit> findByPetId(Integer petId);
}
