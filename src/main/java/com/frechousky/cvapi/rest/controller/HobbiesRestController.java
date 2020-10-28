package com.frechousky.cvapi.rest.controller;

import com.frechousky.cvapi.model.Hobby;
import com.frechousky.cvapi.repository.HobbyRepository;
import com.frechousky.cvapi.rest.CRUDSpringRestController;
import com.frechousky.cvapi.rest.controller.constants.RestControllerPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestControllerPath.HOBBIES_CONTROLLER_PATH)
public class HobbiesRestController extends CRUDSpringRestController<Hobby> {

    @Autowired
    HobbyRepository repository;

    @Override
    protected JpaRepository<Hobby, Integer> getRepository() {
        return repository;
    }

    @Override
    protected Hobby onDeleteBuildEntityToDelete(Integer id) {
        return Hobby.builder().id(id).build();
    }

    @Override
    public Integer getEntityId(Hobby entity) {
        return entity.getId();
    }

    @Override
    public void setEntityId(Hobby entity, Integer id) {
        entity.setId(id);
    }
}
