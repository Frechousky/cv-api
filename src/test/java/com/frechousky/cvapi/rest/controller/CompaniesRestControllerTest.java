package com.frechousky.cvapi.rest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.frechousky.cvapi.model.Company;
import com.frechousky.cvapi.repository.CompanyRepository;
import com.frechousky.cvapi.rest.controller.constants.RestControllerPath;
import lombok.SneakyThrows;
import org.assertj.core.util.Lists;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(CompaniesRestController.class)
public class CompaniesRestControllerTest extends CRUDSpringRestControllerTest<Company, CompanyRepository> {

    final List<Company> companies;

    public CompaniesRestControllerTest() {
        companies = Lists.newArrayList(
                Company.builder().id(1).city("city 1").country("country 1").field("field 1").name("company 1").build(),
                Company.builder().id(2).city("city 2").country("country 2").field("field 2").name("company 2").build()
        );
    }

    @Override
    public @NotEmpty List<Company> getAllEntities() {
        return companies;
    }

    @Override
    public @NotNull Company getEntityToCreate(@NotNull Company entity) {
        return entity.withId(null);
    }

    @Override
    public @NotNull Company getEntityToUpdate(@NotNull Company entity) {
        return entity.withCity("updated city");
    }

    @Override
    public @NotNull Integer getIdFromEntity(@NotNull Company entity) {
        return entity.getId();
    }

    @SneakyThrows
    @Override
    public @NotBlank String getInvalidJsonData() {
        return mapper.writeValueAsString(Company.builder().build());
    }

    @Override
    public @NotBlank String getRestControllerUrl() {
        return RestControllerPath.COMPANIES_REST_CONTROLLER_PATH;
    }

    @Override
    public @NotEmpty List<Company> deserializeJsonArray(@NotBlank String json) throws Exception {
        return mapper.readValue(json, new TypeReference<ArrayList<Company>>() {});
    }

    @Override
    public @NotNull Company deserializeJsonObject(@NotBlank String json) throws Exception {
        return mapper.readValue(json, Company.class);
    }
}
