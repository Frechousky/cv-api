package com.frechousky.cvapi.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frechousky.cvapi.rest.constants.CustomRestExceptionHandlerString;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.validation.annotation.Validated;
import org.stringtemplate.v4.ST;

import javax.servlet.ServletContext;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
public abstract class CRUDSpringRestControllerTest<T, U extends JpaRepository<T, Integer>> {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    U repository;

    @Autowired
    ServletContext context;

    final ObjectMapper mapper = new ObjectMapper();

    /**
     * @return list of entities for database mocking purpose
     */
    public abstract @NotEmpty List<T> getAllEntities();

    /**
     * @param entity an entity to copy data from (optional)
     * @return entity to serialize in JSON and to create in database, must satisfy entity validation rules
     */
    public abstract @NotNull @Validated T getEntityToCreate(@NotNull T entity);

    /**
     * @param entity an entity to copy data from (optional)
     * @return entity to serialize in JSON and to update in database, must satisfy entity validation rules
     */
    public abstract @NotNull @Validated T getEntityToUpdate(@NotNull T entity);

    /**
     * Retrieve id from an entity
     *
     * @param entity entity to retrieve id from
     * @return id of entity
     */
    public abstract @NotNull Integer getIdFromEntity(@NotNull T entity);

    /**
     * @return invalid JSON data to trigger MethodArgumentNotValidException
     */
    public abstract @NotBlank String getInvalidJsonData();


    /**
     * @return REST controller URL (without '/' at the end)
     */
    public abstract @NotBlank String getRestControllerUrl();

    public abstract @NotEmpty List<T> deserializeJsonArray(@NotBlank String json) throws Exception;

    public abstract @NotNull T deserializeJsonObject(@NotBlank String json) throws Exception;

    @Test
    public void create_ifInvalidData_returnsHttp400_andErrorMessage() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post(getRestControllerUrl());

        String expectedMessage = new ST(CustomRestExceptionHandlerString.VALIDATION_ERROR)
                .add("httpMethod", HttpMethod.POST.name())
                .add("requestUrl", requestBuilder.buildRequest(context).getRequestURL())
                .render();

        mockMvc.perform(requestBuilder
                .content(getInvalidJsonData())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(expectedMessage)))
                .andExpect(jsonPath("$.errors", hasSize(greaterThan(0))));
    }

    @Test
    public void create_ifValidData_returnsHttp201AndHasLocationHeader() throws Exception {
        T entityCreated = getAllEntities().get(0);
        T toCreate = getEntityToCreate(entityCreated);
        when(repository.save(toCreate)).thenReturn(entityCreated);

        // Location without host and port
        String locationSubStr = getRestControllerUrl() + "/" + getIdFromEntity(entityCreated);

        mockMvc.perform(post(getRestControllerUrl())
                .content(mapper.writeValueAsString(toCreate))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", stringContainsInOrder(locationSubStr)));
    }

    @Test
    public void delete_ifNoUrlPathIdProvided_returnsHttp400_andErrorMessage() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = delete(getRestControllerUrl());

        String expectedMessage = new ST(CustomRestExceptionHandlerString.MISSING_PATH_VARIABLE_ERROR)
                .add("httpMethod", HttpMethod.DELETE.name())
                .add("requestUrl", requestBuilder.buildRequest(context).getRequestURL().toString())
                .render();

        String validUrlExample = requestBuilder.buildRequest(context).getRequestURL().append("/{id}").toString();
        String expectedErrorMessage = new ST(CustomRestExceptionHandlerString.MISSING_PATH_VARIABLE_ERROR_EXPLICIT_MESSAGE)
                .add("variableName", "id")
                .add("variableType", Integer.class.getSimpleName())
                .add("validUrlExample", validUrlExample)
                .render();

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(expectedMessage)))
                .andExpect(jsonPath("$.errors[0]", is(expectedErrorMessage)));
    }

    @Test
    public void delete_ifUrlPathIdIsNotInteger_returnsHttp400_andErrorMessage() throws Exception {
        String invalidPathId = "a";
        MockHttpServletRequestBuilder requestBuilder = delete(getRestControllerUrl() + "/" + invalidPathId);

        String expectedMessage = new ST(CustomRestExceptionHandlerString.PARAMETER_TYPE_MISMATCH_ERROR)
                .add("httpMethod", HttpMethod.DELETE.name())
                .add("requestUrl", requestBuilder.buildRequest(context).getRequestURL().toString())
                .render();

        String expectedErrorMessage = new ST(CustomRestExceptionHandlerString.PARAMETER_TYPE_MISMATCH_ERROR_EXPLICIT_MESSAGE)
                .add("expectedType", Integer.class.getSimpleName())
                .add("receivedValue", invalidPathId)
                .render();

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(expectedMessage)))
                .andExpect(jsonPath("$.errors[0]", is(expectedErrorMessage)));
    }

    @Test
    public void delete_ifUrlPathIdProvided_andEntityDoesNotExist_returnsHttp204() throws Exception {
        mockMvc.perform(delete(getRestControllerUrl() + "/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void delete_ifUrlPathIdProvided_andEntityExist_returnsHttp204() throws Exception {
        Integer id = getIdFromEntity(getAllEntities().get(0));
        when(repository.findById(id))
                .thenReturn(Optional.of(getAllEntities().get(0)));

        mockMvc.perform(delete(getRestControllerUrl() + "/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    public void findAll_ifEntitiesExist_returnsHttp200AndEntities() throws Exception {
        when(repository.findAll()).thenReturn(getAllEntities());

        String json = mockMvc.perform(get(getRestControllerUrl()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // Convert JSON to list of entities
        List<T> entitiesFromJson = deserializeJsonArray(json);

        Assert.assertEquals(getAllEntities(), entitiesFromJson);
    }

    @Test
    public void findAll_ifNoEntityExists_returnsHttp404() throws Exception {
        mockMvc.perform(get(getRestControllerUrl()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findById_ifEntityDoesNotExist_returnsHttp404() throws Exception {
        mockMvc.perform(get(getRestControllerUrl() + "/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findById_ifEntityExists_returnsHttp200AndEntity() throws Exception {
        T entityToFind = getAllEntities().get(0);
        when(repository.findById(getIdFromEntity(entityToFind))).thenReturn(Optional.ofNullable(entityToFind));

        String json = mockMvc.perform(get(getRestControllerUrl() + "/" + getIdFromEntity(entityToFind)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // Convert JSON string to entity
        T entityFromJson = deserializeJsonObject(json);

        Assert.assertEquals(entityToFind, entityFromJson);
    }

    @Test
    public void findById_ifUrlPathIdIsNotInteger_returnsHttp400_andErrorMessage() throws Exception {
        String invalidPathId = "a";
        MockHttpServletRequestBuilder requestBuilder = get(getRestControllerUrl() + "/" + invalidPathId);

        String expectedMessage = new ST(CustomRestExceptionHandlerString.PARAMETER_TYPE_MISMATCH_ERROR)
                .add("httpMethod", HttpMethod.GET.name())
                .add("requestUrl", requestBuilder.buildRequest(context).getRequestURL().toString())
                .render();

        String expectedErrorMessage = new ST(CustomRestExceptionHandlerString.PARAMETER_TYPE_MISMATCH_ERROR_EXPLICIT_MESSAGE)
                .add("expectedType", Integer.class.getSimpleName())
                .add("receivedValue", invalidPathId)
                .render();

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(expectedMessage)))
                .andExpect(jsonPath("$.errors[0]", is(expectedErrorMessage)));
    }

    @Test
    public void notSupportedHttpMethod_returnsHttp405_andErrorMessage() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = patch(getRestControllerUrl());

        String expectedMessage = new ST(CustomRestExceptionHandlerString.HTTP_METHOD_NOT_SUPPORTED_ERROR)
                .add("httpMethod", HttpMethod.PATCH.name())
                .add("requestUrl", requestBuilder.buildRequest(context).getRequestURL().toString())
                .render();

        String expectedErrorMessage = new ST(CustomRestExceptionHandlerString.HTTP_METHOD_NOT_SUPPORTED_ERROR_EXPLICIT_MESSAGE)
                .add("supportedHttpMethods", "[GET, POST, PUT, DELETE]")
                .render();

        mockMvc.perform(requestBuilder
                .content("")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.message", is(expectedMessage)))
                .andExpect(jsonPath("$.errors[0]", is(expectedErrorMessage)));
    }

    @Test
    public void update_ifNoUrlPathIdProvided_returnsHttp400_andErrorMessage() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = put(getRestControllerUrl());

        String expectedMessage = new ST(CustomRestExceptionHandlerString.MISSING_PATH_VARIABLE_ERROR)
                .add("httpMethod", HttpMethod.PUT.name())
                .add("requestUrl", requestBuilder.buildRequest(context).getRequestURL().toString())
                .render();

        String validUrlExample = requestBuilder.buildRequest(context).getRequestURL().append("/{id}").toString();
        String expectedErrorMessage = new ST(CustomRestExceptionHandlerString.MISSING_PATH_VARIABLE_ERROR_EXPLICIT_MESSAGE)
                .add("variableName", "id")
                .add("variableType", Integer.class.getSimpleName())
                .add("validUrlExample", validUrlExample)
                .render();

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(expectedMessage)))
                .andExpect(jsonPath("$.errors[0]", is(expectedErrorMessage)));
    }

    @Test
    public void update_ifUrlPathIdIsNotInteger_returnsHttp400_andErrorMessage() throws Exception {
        String invalidPathId = "a";
        MockHttpServletRequestBuilder requestBuilder = put(getRestControllerUrl() + "/" + invalidPathId);

        String expectedMessage = new ST(CustomRestExceptionHandlerString.PARAMETER_TYPE_MISMATCH_ERROR)
                .add("httpMethod", HttpMethod.PUT.name())
                .add("requestUrl", requestBuilder.buildRequest(context).getRequestURL().toString())
                .render();

        String expectedErrorMessage = new ST(CustomRestExceptionHandlerString.PARAMETER_TYPE_MISMATCH_ERROR_EXPLICIT_MESSAGE)
                .add("expectedType", Integer.class.getSimpleName())
                .add("receivedValue", invalidPathId)
                .render();

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(expectedMessage)))
                .andExpect(jsonPath("$.errors[0]", is(expectedErrorMessage)));
    }

    @Test
    public void update_ifUrlPathIdProvided_andInvalidData_returnsHttp400() throws Exception {
        mockMvc.perform(put(getRestControllerUrl() + "/" + 1)
                .content(getInvalidJsonData())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_ifUrlPathIdProvided_andValidData_andEntityDoesNotExist_returnsHttp404() throws Exception {
        T toUpdate = getEntityToUpdate(getAllEntities().get(0));

        // Return Option.empty to avoid NPE on update call
        when(repository.findById(getIdFromEntity(toUpdate))).thenReturn(Optional.empty());

        mockMvc.perform(put(getRestControllerUrl() + "/" + getIdFromEntity(toUpdate))
                .content(mapper.writeValueAsString(toUpdate))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void update_ifUrlPathIdProvided_andValidData_andEntityDoesExist_andEntityIdEqualsPathId_returnsHttp204() throws Exception {
        T toUpdate = getEntityToUpdate(getAllEntities().get(0));

        Integer pathId = getIdFromEntity(toUpdate);
        when(repository.findById(pathId)).thenReturn(Optional.of(getAllEntities().get(0)));

        mockMvc.perform(put(getRestControllerUrl() + "/" + pathId)
                .content(mapper.writeValueAsString(toUpdate))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void update_ifUrlPathIdProvided_andValidData_andEntityDoesExist_andEntityIdNotEqualsPathId_returnsHttp400_andErrorMessage() throws Exception {
        T toUpdate = getEntityToUpdate(getAllEntities().get(0));

        // pathId = entity.id+1 to have entity.id != pathId
        Integer pathId = getIdFromEntity(toUpdate) + 1;
        when(repository.findById(pathId)).thenReturn(Optional.of(getAllEntities().get(0)));

        MockHttpServletRequestBuilder requestBuilder = put(getRestControllerUrl() + "/" + pathId);

        String expectedMessage = new ST(CustomRestExceptionHandlerString.VALIDATION_ERROR)
                .add("httpMethod", HttpMethod.PUT.name())
                .add("requestUrl", requestBuilder.buildRequest(context).getRequestURL())
                .render();

        mockMvc.perform(requestBuilder
                .content(mapper.writeValueAsString(toUpdate))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(expectedMessage)))
                .andExpect(jsonPath("$.errors", hasSize(greaterThan(0))));
    }

}
