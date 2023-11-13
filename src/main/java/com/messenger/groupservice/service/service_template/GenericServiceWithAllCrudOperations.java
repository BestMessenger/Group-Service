package com.messenger.groupservice.service.service_template;

import java.util.List;

public interface GenericServiceWithAllCrudOperations<Model, Request, Response> {
    Response add(Request request);

    Response getById(Long id);

    List<Response> getAll();

    void deleteById(Long id);

    Response update(Model model);
}
