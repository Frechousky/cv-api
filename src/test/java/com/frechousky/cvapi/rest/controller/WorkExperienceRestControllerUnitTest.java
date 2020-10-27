package com.frechousky.cvapi.rest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.frechousky.cvapi.model.Company;
import com.frechousky.cvapi.model.WorkExperience;
import com.frechousky.cvapi.model.WorkExperienceDescription;
import com.frechousky.cvapi.repository.WorkExperienceRepository;
import com.frechousky.cvapi.rest.controller.constants.RestPath;
import lombok.SneakyThrows;
import org.assertj.core.util.Lists;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebMvcTest(WorkExperiencesRestController.class)
public class WorkExperienceRestControllerUnitTest extends CRUDSpringRestControllerTest<WorkExperience, WorkExperienceRepository> {

    final List<WorkExperience> workExperiences;

    public WorkExperienceRestControllerUnitTest() {
        Company company = Company.builder().id(1).city("city").country("country").field("field").name("company").build();

        List<WorkExperienceDescription> workExperienceDescriptions = Lists.newArrayList(
                WorkExperienceDescription.builder().id(1).label("label 1").build(),
                WorkExperienceDescription.builder().id(2).label("label 2").build());

        workExperiences = Lists.newArrayList(
                WorkExperience.builder().id(1).company(company).description(workExperienceDescriptions).end(new Date()).start(new Date()).position("position 1").build(),
                WorkExperience.builder().id(2).company(company).description(workExperienceDescriptions).end(new Date()).start(new Date()).position("position 2").build());
    }

    @Override
    public List<WorkExperience> getAllEntities() {
        return workExperiences;
    }

    @Override
    public WorkExperience getEntityToCreate(WorkExperience entity) {
        return entity.withId(null);
    }

    @Override
    public WorkExperience getEntityToUpdate(WorkExperience entity) {
        return entity.withPosition("updated position");
    }

    @Override
    public Integer getIdFromEntity(WorkExperience entity) {
        return entity.getId();
    }

    @SneakyThrows
    @Override
    public String getInvalidJsonData() {
        return mapper.writeValueAsString(WorkExperience.builder().build());
    }

    @Override
    public String getRestControllerUrl() {
        return RestPath.WORK_EXPERIENCES_REST_CONTROLLER_PATH;
    }

    @Override
    public List<WorkExperience> deserializeJsonArray(String json) throws Exception {
        return mapper.readValue(json, new TypeReference<ArrayList<WorkExperience>>() {
        });
    }

    @Override
    public WorkExperience deserializeJsonObject(String json) throws Exception {
        return mapper.readValue(json, WorkExperience.class);
    }


}
