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
package com.example.micronaut.petclinic.system;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.View;

/**
 * Controller used to showcase what happens when an exception is thrown
 *
 * @author Michael Isvy
 * @author Mitz Shiiba
 * <p/>
 * Also see how a view that resolves to "error" has been added ("error.html").
 */
@Controller
class CrashController {

    @Get("/oups")
    public HttpResponse triggerException() {
        throw new RuntimeException("Expected: controller used to showcase what "
            + "happens when an exception is thrown");
    }

    // TODO: Make error handling implementation better.
    @View("error")
    @Error(global = true)
    public HttpResponse error(HttpRequest request, Throwable e) {
        // TODO: use logger
        e.printStackTrace();
        return HttpResponse.serverError();
    }
}
