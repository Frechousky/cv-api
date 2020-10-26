package com.frechousky.cvapi.controller;

import com.frechousky.cvapi.CRUDSpringRestController;
import com.frechousky.cvapi.constant.RestPath;
import com.frechousky.cvapi.model.WorkExperience;
import com.frechousky.cvapi.repository.WorkExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

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
