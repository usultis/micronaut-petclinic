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
package com.example.micronaut.petclinic.owner;

import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.core.annotation.TypeHint;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;

import static io.micronaut.core.annotation.TypeHint.AccessType.*;

/**
 * Repository implementation for <code>Owner</code> domain objects.
 *
 * @author Mitz Shiiba
 */
@Singleton
@TypeHint(accessType = {ALL_PUBLIC_CONSTRUCTORS, ALL_DECLARED_FIELDS, ALL_PUBLIC_METHODS})
public class OwnerRepositoryImpl implements OwnerRepository {

    private final EntityManager entityManager;

    public OwnerRepositoryImpl(@CurrentSession EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Collection<Owner> findByLastName(String lastName) {
        String qlString = "SELECT DISTINCT owner FROM Owner owner left join fetch owner.pets WHERE owner.lastName LIKE :lastName";
        TypedQuery<Owner> query = entityManager.createQuery(qlString, Owner.class)
            .setParameter("lastName", lastName + "%");
        return query.getResultList();
    }

    @Override
    public Owner findById(Integer id) {
        String qlString = "SELECT owner FROM Owner owner left join fetch owner.pets WHERE owner.id =:id";
        TypedQuery<Owner> query = entityManager.createQuery(qlString, Owner.class)
            .setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public void save(Owner owner) {
        if (owner.isNew()) {
            entityManager.persist(owner);
        } else {
            entityManager.merge(owner);
        }
    }
}
