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

import io.micronaut.core.convert.ConversionContext;
import io.micronaut.core.convert.TypeConverter;

import javax.inject.Singleton;
import java.util.Collection;
import java.util.Optional;

/**
 * Use Micronaut TypeConverter instead of Spring Formatter.
 *
 * @author Mark Fisher
 * @author Juergen Hoeller
 * @author Michael Isvy
 * @author Mitz Shiiba
 */
@Singleton
public class PetTypeFormatter implements TypeConverter<String, PetType> {

    private final PetRepository pets;

    public PetTypeFormatter(PetRepository pets) {
        this.pets = pets;
    }

    @Override
    public Optional<PetType> convert(String object, Class<PetType> targetType, ConversionContext context) {
        Collection<PetType> findPetTypes = this.pets.findPetTypes();
        for (PetType type : findPetTypes) {
            if (type.getName().equals(object)) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }

}
