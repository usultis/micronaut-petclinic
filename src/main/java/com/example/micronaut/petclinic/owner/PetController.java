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

import io.micronaut.core.convert.format.Format;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.views.View;
import org.springframework.util.StringUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Mitz Shiiba
 */
@Controller("/owners/{ownerId}")
class PetController {

    private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";

    private final PetRepository pets;

    private final OwnerRepository owners;

    public PetController(PetRepository pets, OwnerRepository owners) {
        this.pets = pets;
        this.owners = owners;
    }

    public Map<String, Object> load(int ownerId) {
        Map<String, Object> model = new HashMap<>();
        model.put("owner", this.owners.findById(ownerId));
        model.put("types", this.pets.findPetTypes());
        return model;
    }

    @View(VIEWS_PETS_CREATE_OR_UPDATE_FORM)
    @Get("/pets/new")
    public HttpResponse initCreationForm(int ownerId) {
        Map<String, Object> model = load(ownerId);
        Owner owner = (Owner) model.get("owner");

        Pet pet = new Pet();
        owner.addPet(pet);

        model.put("pet", pet);
        return HttpResponse.ok(model);
    }

    @Post(value = "/pets/new",
        consumes = MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse processCreationForm(int ownerId,
                                            String name,
                                            @Format("yyyy-MM-dd") LocalDate birthDate,
                                            PetType type) {
        Pet pet = new Pet();
        pet.setName(name);
        pet.setBirthDate(birthDate);
        pet.setType(type);

        Map<String, Object> model = load(ownerId);
        Owner owner = (Owner) model.get("owner");

        if (StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(), true) != null) {
            // TODO: error handling
//            result.rejectValue("name", "duplicate", "already exists");
            throw new RuntimeException("result.rejectValue(\"name\", \"duplicate\", \"already exists\")");
        }
        owner.addPet(pet);
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(pet, "pet");
        new PetValidator().validate(pet, errors);
        if (errors.hasErrors()) {
            throw new RuntimeException(new BindException(errors));
        }
        // TODO: error handling
//        if (result.hasErrors()) {
//            model.put("pet", pet);
//            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
//        } else {
        this.pets.save(pet);
        return HttpResponse.seeOther(URI.create("/owners/" + ownerId));
//        }
    }

    @View(VIEWS_PETS_CREATE_OR_UPDATE_FORM)
    @Get("/pets/{petId}/edit")
    public Map<String, Object> initUpdateForm(int ownerId, int petId) {
        Map<String, Object> model = load(ownerId);
        Pet pet = this.pets.findById(petId);
        model.put("pet", pet);
        return model;
    }

    @Post(value = "/pets/{petId}/edit",
        consumes = MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse processUpdateForm(int ownerId, int petId,
                                          String name,
                                          @Format("yyyy-MM-dd") LocalDate birthDate,
                                          PetType type) {
        Pet pet = new Pet();
        pet.setId(petId);
        pet.setName(name);
        pet.setBirthDate(birthDate);
        pet.setType(type);

        Map<String, Object> model = load(ownerId);
        Owner owner = (Owner) model.get("owner");

        // TODO: error handling
//        if (result.hasErrors()) {
//            pet.setOwner(owner);
//            model.put("pet", pet);
//            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
//        } else {
        owner.addPet(pet);
        this.pets.save(pet);
        return HttpResponse.seeOther(URI.create("/owners/" + ownerId));
//        }
    }
}
