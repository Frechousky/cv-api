package com.frechousky.cvapi.rest.controller;

import com.frechousky.cvapi.rest.CRUDSpringRestController;
import com.frechousky.cvapi.model.WorkExperienceDescription;
import com.frechousky.cvapi.repository.WorkExperienceDescriptionRepository;
import com.frechousky.cvapi.rest.controller.constants.RestControllerPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestControllerPath.WORK_EXPERIENCE_DESCRIPTIONS_REST_CONTROLLER_PATH)
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
