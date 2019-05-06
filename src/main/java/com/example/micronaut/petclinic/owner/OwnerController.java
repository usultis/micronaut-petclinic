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

import io.micronaut.core.util.CollectionUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.views.ModelAndView;
import io.micronaut.views.View;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @author Mitz Shiiba
 */
@Controller
class OwnerController {

    private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";

    private final OwnerRepository owners;

    public OwnerController(OwnerRepository ownerRepository) {
        this.owners = ownerRepository;
    }

    @View(VIEWS_OWNER_CREATE_OR_UPDATE_FORM)
    @Get("/owners/new")
    public HttpResponse initCreationForm() {
        return HttpResponse.ok(CollectionUtils.mapOf("owner", new Owner()));
    }

    @Post(value = "/owners/new", consumes = MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse processCreationForm(@Body @Valid Owner owner) {
        // TODO: Validation
        owner.setId(null);
        this.owners.save(owner);
        return HttpResponse.redirect(URI.create("/owners/" + owner.getId()));
    }

    @View("owners/findOwners")
    @Get("/owners/find")
    public HttpResponse initFindForm() {
        return HttpResponse.ok(CollectionUtils.mapOf("owner", new Owner()));
    }

    @Get("/owners")
    public HttpResponse processFindForm(String lastName) {
        Owner owner = new Owner();
        owner.setLastName(lastName);

        // allow parameterless GET request for /owners to return all records
        if (owner.getLastName() == null) {
            owner.setLastName(""); // empty string signifies broadest possible search
        }

        // find owners by last name
        Collection<Owner> results = this.owners.findByLastName(owner.getLastName());
        if (results.isEmpty()) {
            // no owners found
            // TODO: error handling
            // result.rejectValue("lastName", "notFound", "not found");
            return HttpResponse.ok(new ModelAndView<>("owners/findOwners", CollectionUtils.mapOf("owner", owner)));
        } else if (results.size() == 1) {
            // 1 owner found
            owner = results.iterator().next();
            return HttpResponse.seeOther(URI.create("/owners/" + owner.getId()));
        } else {
            // multiple owners found
            return HttpResponse.ok(new ModelAndView<>("owners/ownersList", CollectionUtils.mapOf("selections", results)));
        }
    }

    @View(VIEWS_OWNER_CREATE_OR_UPDATE_FORM)
    @Get("/owners/{ownerId}/edit")
    public HttpResponse initUpdateOwnerForm(int ownerId) {
        Owner owner = this.owners.findById(ownerId);
        return HttpResponse.ok(CollectionUtils.mapOf("owner", owner));
    }

    @Post(value = "/owners/{ownerId}/edit",
        consumes = MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse processUpdateOwnerForm(@Body @Valid Owner owner, int ownerId) {
        // TODO: error handling
//        if (result.hasErrors()) {
//            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
//        } else {
        owner.setId(ownerId);
        this.owners.save(owner);
        return HttpResponse.seeOther(URI.create("/owners/" + ownerId));
//        }
    }

    /**
     * Custom handler for displaying an owner.
     *
     * @param ownerId the ID of the owner to display
     * @return a ModelMap with the model attributes for the view
     */
    @View("owners/ownerDetails")
    @Get("/owners/{ownerId}")
    public HttpResponse showOwner(int ownerId) {
        Owner owner = this.owners.findById(ownerId);
        return HttpResponse.ok(CollectionUtils.mapOf("owner", owner));
    }

}
