package com.frechousky.cvapi.controller;

import com.frechousky.cvapi.CRUDSpringRestController;
import com.frechousky.cvapi.constant.RestPath;
import com.frechousky.cvapi.model.WorkExperienceDescription;
import com.frechousky.cvapi.repository.WorkExperienceDescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(RestPath.WORK_EXPERIENCE_DESCRIPTIONS_REST_CONTROLLER_PATH)
public class WorkExperienceDescriptionsRestController extends CRUDSpringRestController<WorkExperienceDescription> {

    @Autowired
    WorkExperienceDescriptionRepository repository;

    @Override
    protected JpaRepository<WorkExperienceDescription, Integer> getRepository() {
        return repository;
    }


    @Override
    protected WorkExperienceDescription onDeleteBuildEntityToDelete(Integer id) {
        return WorkExperienceDescription.builder().id(id).build();
    }

    @Override
    public Integer getEntityId(WorkExperienceDescription entity) {
        return entity.getId();
    }

    @Override
    public void setEntityId(WorkExperienceDescription entity, Integer id) {
        entity.setId(id);
    }

}
