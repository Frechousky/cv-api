package com.frechousky.cvapi.rest.controller;

import com.frechousky.cvapi.rest.CRUDSpringRestController;
import com.frechousky.cvapi.model.Company;
import com.frechousky.cvapi.repository.CompanyRepository;
import com.frechousky.cvapi.rest.controller.constants.RestControllerPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestControllerPath.COMPANIES_REST_CONTROLLER_PATH)
public class CompaniesRestController extends CRUDSpringRestController<Company> {

    @Autowired
    CompanyRepository companyRepository;

    @Override
    protected JpaRepository<Company, Integer> getRepository() {
        return companyRepository;
    }

    @Override
    protected Company onDeleteBuildEntityToDelete(Integer id) {
        return Company.builder().id(id).build();
    }

    @Override
    public Integer getEntityId(Company entity) {
        return entity.getId();
    }

    @Override
    public void setEntityId(Company entity, Integer id) {
        entity.setId(id);
    }

}
