package com.frechousky.cvapi.rest.controller;

import com.frechousky.cvapi.model.Language;
import com.frechousky.cvapi.repository.LanguageRepository;
import com.frechousky.cvapi.rest.CRUDSpringRestController;
import com.frechousky.cvapi.rest.controller.constants.RestControllerPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestControllerPath.LANGUAGES_CONTROLLER_PATH)
public class LanguagesRestController  extends CRUDSpringRestController<Language> {

    @Autowired
    LanguageRepository repository;

    @Override
    protected JpaRepository<Language, Integer> getRepository() {
        return repository;
    }

    @Override
    protected Language onDeleteBuildEntityToDelete(Integer id) {
        return Language.builder().id(id).build();
    }

    @Override
    public Integer getEntityId(Language entity) {
        return entity.getId();
    }

    @Override
    public void setEntityId(Language entity, Integer id) {
        entity.setId(id);
    }
}
