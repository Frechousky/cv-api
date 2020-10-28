package com.frechousky.cvapi.rest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.frechousky.cvapi.model.WorkExperienceDescription;
import com.frechousky.cvapi.repository.WorkExperienceDescriptionRepository;
import com.frechousky.cvapi.rest.controller.constants.RestControllerPath;
import lombok.SneakyThrows;
import org.assertj.core.util.Lists;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(WorkExperienceDescriptionsRestController.class)
public class WorkExperienceDescriptionRestControllerUnitTest extends CRUDSpringRestControllerTest<WorkExperienceDescription, WorkExperienceDescriptionRepository> {

    final List<WorkExperienceDescription> workExperienceDescriptions;

    public WorkExperienceDescriptionRestControllerUnitTest() {
        workExperienceDescriptions = Lists.newArrayList(
                WorkExperienceDescription.builder().id(1).label("label 1").build(),
                WorkExperienceDescription.builder().id(2).label("label 2").build());
    }


    @Override
    public @NotEmpty List<WorkExperienceDescription> getAllEntities() {
        return workExperienceDescriptions;
    }

    @Override
    public @NotNull WorkExperienceDescription getEntityToCreate(WorkExperienceDescription entity) {
        return entity.withId(null);
    }

    @Override
    public @NotNull WorkExperienceDescription getEntityToUpdate(WorkExperienceDescription entity) {
        return entity.withLabel("updated label");
    }

    @Override
    public @NotNull Integer getIdFromEntity(WorkExperienceDescription entity) {
        return entity.getId();
    }

    @SneakyThrows
    @Override
    public @NotBlank String getInvalidJsonData() {
        return mapper.writeValueAsString(WorkExperienceDescription.builder().build());
    }

    @Override
    public @NotBlank String getRestControllerUrl() {
        return RestControllerPath.WORK_EXPERIENCE_DESCRIPTIONS_REST_CONTROLLER_PATH;
    }

    @Override
    public @NotEmpty List<WorkExperienceDescription> deserializeJsonArray(@NotBlank String json) throws Exception {
        return mapper.readValue(json, new TypeReference<ArrayList<WorkExperienceDescription>>() {
        });
    }

    @Override
    public @NotNull WorkExperienceDescription deserializeJsonObject(@NotBlank String json) throws Exception {
        return mapper.readValue(json, WorkExperienceDescription.class);
    }
}
