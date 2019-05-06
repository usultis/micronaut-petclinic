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

import io.micronaut.spring.tx.annotation.Transactional;

import java.util.List;

/**
 * Repository class for <code>Pet</code> domain objects.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Mitz Shiiba
 */
public interface PetRepository {

    /**
     * Retrieve all {@link PetType}s from the data store.
     *
     * @return a Collection of {@link PetType}s.
     */
    @Transactional(readOnly = true)
    List<PetType> findPetTypes();

    /**
     * Retrieve a {@link Pet} from the data store by id.
     *
     * @param id the id to search for
     * @return the {@link Pet} if found
     */
    @Transactional(readOnly = true)
    Pet findById(Integer id);

    /**
     * Save a {@link Pet} to the data store, either inserting or updating it.
     *
     * @param pet the {@link Pet} to save
     */
    @Transactional
    void save(Pet pet);

}

