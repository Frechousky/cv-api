package com.frechousky.cvapi.rest.controller;

import com.frechousky.cvapi.rest.CRUDSpringRestController;
import com.frechousky.cvapi.model.WorkExperience;
import com.frechousky.cvapi.repository.WorkExperienceRepository;
import com.frechousky.cvapi.rest.controller.constants.RestPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestPath.WORK_EXPERIENCES_REST_CONTROLLER_PATH)
public class WorkExperiencesRestController extends CRUDSpringRestController<WorkExperience> {

    @Autowired
    WorkExperienceRepository repository;

    @Override
    protected JpaRepository<WorkExperience, Integer> getRepository() {
        return repository;
    }


    @Override
    protected WorkExperience onDeleteBuildEntityToDelete(Integer id) {
        return WorkExperience.builder().id(id).build();
    }

    @Override
    public Integer getEntityId(WorkExperience entity) {
        return entity.getId();
    }

    @Override
    public void setEntityId(WorkExperience entity, Integer id) {
        entity.setId(id);
    }

}
