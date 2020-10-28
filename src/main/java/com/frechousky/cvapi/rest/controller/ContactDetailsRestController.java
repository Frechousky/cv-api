package com.frechousky.cvapi.rest.controller;

import com.frechousky.cvapi.model.ContactDetails;
import com.frechousky.cvapi.repository.ContactDetailsRepository;
import com.frechousky.cvapi.rest.CRUDSpringRestController;
import com.frechousky.cvapi.rest.controller.constants.RestControllerPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestControllerPath.CONTACT_DETAILS_CONTROLLER_PATH)
public class ContactDetailsRestController extends CRUDSpringRestController<ContactDetails> {

    @Autowired
    ContactDetailsRepository repository;

    @Override
    protected JpaRepository<ContactDetails, Integer> getRepository() {
        return repository;
    }

    @Override
    protected ContactDetails onDeleteBuildEntityToDelete(Integer id) {
        return ContactDetails.builder().id(id).build();
    }

    @Override
    public Integer getEntityId(ContactDetails entity) {
        return entity.getId();
    }

    @Override
    public void setEntityId(ContactDetails entity, Integer id) {
        entity.setId(id);
    }
}
