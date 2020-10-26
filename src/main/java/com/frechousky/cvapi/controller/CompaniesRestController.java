package com.frechousky.cvapi.controller;

import com.frechousky.cvapi.CRUDSpringRestController;
import com.frechousky.cvapi.constant.RestPath;
import com.frechousky.cvapi.model.Company;
import com.frechousky.cvapi.repository.CompanyRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(RestPath.COMPANIES_REST_CONTROLLER_PATH)
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
