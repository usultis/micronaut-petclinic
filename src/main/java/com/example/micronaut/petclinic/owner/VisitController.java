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

import com.example.micronaut.petclinic.visit.Visit;
import com.example.micronaut.petclinic.visit.VisitRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.views.View;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @author Dave Syer
 * @author Mitz Shiiba
 */
@Controller
class VisitController {

    private final VisitRepository visits;

    private final PetRepository pets;

    public VisitController(VisitRepository visits, PetRepository pets) {
        this.visits = visits;
        this.pets = pets;
    }

    /**
     * Called in each and every action method.
     * 2 goals:
     * - Make sure we always have fresh data
     * - Since we do not use the session scope, make sure that Pet object always has an id
     * (Even though id is not part of the form fields)
     *
     * @param petId
     * @return model map with pet & visit
     */
    public Map<String, Object> loadPetWithVisit(int petId) {
        Map<String, Object> model = new HashMap<>();
        Pet pet = this.pets.findById(petId);
        model.put("pet", pet);

        Visit visit = new Visit();
        pet.addVisit(visit);
        model.put("visit", visit);

        return model;
    }

    @View("pets/createOrUpdateVisitForm")
    @Get("/owners/{ownerId}/pets/{petId}/visits/new")
    public Map<String, Object> initNewVisitForm(int ownerId, int petId) {
        return loadPetWithVisit(petId);
    }

    @Post(value = "/owners/{ownerId}/pets/{petId}/visits/new",
        consumes = MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse processNewVisitForm(int ownerId, int petId,
                                            @Body @Valid Visit visitParam) {
        Map<String, Object> model = loadPetWithVisit(petId);
        Visit visit = (Visit) model.get("visit");
        visit.setDate(visitParam.getDate());
        visit.setDescription(visitParam.getDescription());
        // TODO: error handling
//      if (result.hasErrors()) {
//            return "pets/createOrUpdateVisitForm";
//        } else {
        this.visits.save(visit);
        return HttpResponse.seeOther(URI.create("/owners/" + ownerId));
//        }
    }

}
