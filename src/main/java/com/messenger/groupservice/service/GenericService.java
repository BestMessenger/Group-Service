package com.messenger.groupservice.service;

import java.util.List;

public interface GenericService<Model, Request, Response> {
    Response add(Request request);

    Response getById(Long id);

    List<Response> getAll();

    void deleteById(Long id);

    Response update(Model model);
}
