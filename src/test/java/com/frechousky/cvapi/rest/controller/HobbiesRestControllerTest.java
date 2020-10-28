package com.frechousky.cvapi.rest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.frechousky.cvapi.model.Hobby;
import com.frechousky.cvapi.repository.HobbyRepository;
import com.frechousky.cvapi.rest.controller.constants.RestControllerPath;
import lombok.SneakyThrows;
import org.assertj.core.util.Lists;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(HobbiesRestController.class)
public class HobbiesRestControllerTest extends CRUDSpringRestControllerTest<Hobby, HobbyRepository> {

    List<Hobby> hobbies;

    public HobbiesRestControllerTest() {
        hobbies = Lists.newArrayList(
                Hobby.builder().id(1).label("label 1").build(),
                Hobby.builder().id(2).label("label 2").build()
        );
    }

    @Override
    public @NotEmpty List<Hobby> getAllEntities() {
        return hobbies;
    }

    @Override
    public @NotNull Hobby getEntityToCreate(@NotNull Hobby entity) {
        return entity.withId(null);
    }

    @Override
    public @NotNull Hobby getEntityToUpdate(@NotNull Hobby entity) {
        return entity.withLabel("updated label");
    }

    @Override
    public @NotNull Integer getIdFromEntity(@NotNull Hobby entity) {
        return entity.getId();
    }

    @SneakyThrows
    @Override
    public @NotBlank String getInvalidJsonData() {
        return mapper.writeValueAsString(Hobby.builder().build());
    }

    @Override
    public @NotBlank String getRestControllerUrl() {
        return RestControllerPath.HOBBIES_CONTROLLER_PATH;
    }

    @Override
    public @NotEmpty List<Hobby> deserializeJsonArray(@NotBlank String json) throws Exception {
        return mapper.readValue(json, new TypeReference<ArrayList<Hobby>>() {
        });
    }

    @Override
    public @NotNull Hobby deserializeJsonObject(@NotBlank String json) throws Exception {
        return mapper.readValue(json, Hobby.class);
    }
}