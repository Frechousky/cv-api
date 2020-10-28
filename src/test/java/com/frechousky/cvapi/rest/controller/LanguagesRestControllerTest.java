package com.frechousky.cvapi.rest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.frechousky.cvapi.model.Language;
import com.frechousky.cvapi.repository.LanguageRepository;
import com.frechousky.cvapi.rest.controller.constants.RestControllerPath;
import lombok.SneakyThrows;
import org.assertj.core.util.Lists;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(LanguagesRestController.class)
public class LanguagesRestControllerTest extends CRUDSpringRestControllerTest<Language, LanguageRepository> {

    List<Language> languages;

    public LanguagesRestControllerTest() {
        languages = Lists.newArrayList(
                Language.builder().id(1).label("label 1").level(1).build(),
                Language.builder().id(2).label("label 2").level(2).build()
        );
    }

    @Override
    public @NotEmpty List<Language> getAllEntities() {
        return languages;
    }

    @Override
    public @NotNull Language getEntityToCreate(@NotNull Language entity) {
        return entity.withId(null);
    }

    @Override
    public @NotNull Language getEntityToUpdate(@NotNull Language entity) {
        return entity.withLabel("updated label");
    }

    @Override
    public @NotNull Integer getIdFromEntity(@NotNull Language entity) {
        return entity.getId();
    }

    @SneakyThrows
    @Override
    public @NotBlank String getInvalidJsonData() {
        return mapper.writeValueAsString(Language.builder().build());
    }

    @Override
    public @NotBlank String getRestControllerUrl() {
        return RestControllerPath.LANGUAGES_CONTROLLER_PATH;
    }

    @Override
    public @NotEmpty List<Language> deserializeJsonArray(@NotBlank String json) throws Exception {
        return mapper.readValue(json, new TypeReference<ArrayList<Language>>() {
        });
    }

    @Override
    public @NotNull Language deserializeJsonObject(@NotBlank String json) throws Exception {
        return mapper.readValue(json, Language.class);
    }
}