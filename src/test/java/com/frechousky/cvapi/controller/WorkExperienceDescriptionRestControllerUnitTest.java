package com.frechousky.cvapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frechousky.cvapi.constant.CustomRestExceptionHandlerString;
import com.frechousky.cvapi.constant.RestPath;
import com.frechousky.cvapi.model.WorkExperienceDescription;
import com.frechousky.cvapi.repository.WorkExperienceDescriptionRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.stringtemplate.v4.ST;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(WorkExperienceDescriptionsRestController.class)
public class WorkExperienceDescriptionRestControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    WorkExperienceDescriptionRepository repository;

    @Autowired
    ServletContext context;

    List<WorkExperienceDescription> workExperienceDescriptions;
    ObjectMapper mapper = new ObjectMapper();

    public WorkExperienceDescriptionRestControllerUnitTest() {
        workExperienceDescriptions = Lists.newArrayList(
                WorkExperienceDescription.builder().id(1).label("Label 1").build(),
                WorkExperienceDescription.builder().id(2).label("Label 2").build());
    }

    @Test
    public void create_ifInvalidData_returnsHttp400() throws Exception {
        mockMvc.perform(post(RestPath.WORK_EXPERIENCE_DESCRIPTIONS_REST_CONTROLLER_PATH)
                .content("")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_ifValidData_returnsHttp201AndHasLocationHeader() throws Exception {
        WorkExperienceDescription toCreate = WorkExperienceDescription.builder().label(workExperienceDescriptions.get(0).getLabel()).build();
        when(repository.save(toCreate)).thenReturn(workExperienceDescriptions.get(0));

        // Location without host and port
        String locationSubStr = RestPath.WORK_EXPERIENCE_DESCRIPTIONS_REST_CONTROLLER_PATH + "/" + workExperienceDescriptions.get(0).getId();

        mockMvc.perform(post(RestPath.WORK_EXPERIENCE_DESCRIPTIONS_REST_CONTROLLER_PATH)
                .content(mapper.writeValueAsString(toCreate))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", stringContainsInOrder(locationSubStr)));
    }

    @Test
    public void delete_ifNoUrlPathIdProvided_returnsHttp400_andErrorMessage() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = delete(RestPath.WORK_EXPERIENCE_DESCRIPTIONS_REST_CONTROLLER_PATH);
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
        MockHttpServletRequestBuilder requestBuilder = delete(RestPath.WORK_EXPERIENCE_DESCRIPTIONS_REST_CONTROLLER_PATH + "/a");
        String expectedMessage = new ST(CustomRestExceptionHandlerString.PARAMETER_TYPE_MISMATCH_ERROR)
                .add("httpMethod", HttpMethod.DELETE.name())
                .add("requestUrl", requestBuilder.buildRequest(context).getRequestURL().toString())
                .render();
        String expectedErrorMessage = new ST(CustomRestExceptionHandlerString.PARAMETER_TYPE_MISMATCH_ERROR_EXPLICIT_MESSAGE)
                .add("expectedType", Integer.class.getSimpleName())
                .add("receivedValue", "a")
                .render();

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(expectedMessage)))
                .andExpect(jsonPath("$.errors[0]", is(expectedErrorMessage)));
    }

    @Test
    public void delete_ifUrlPathIdProvided_returnsHttp204() throws Exception {
        mockMvc.perform(delete(RestPath.WORK_EXPERIENCE_DESCRIPTIONS_REST_CONTROLLER_PATH + "/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void findAll_ifEntitiesExist_returnsHttp200AndEntities() throws Exception {
        when(repository.findAll()).thenReturn(workExperienceDescriptions);

        mockMvc.perform(get(RestPath.WORK_EXPERIENCE_DESCRIPTIONS_REST_CONTROLLER_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(workExperienceDescriptions.get(0).getId())))
                .andExpect(jsonPath("$[0].label", is(workExperienceDescriptions.get(0).getLabel())))
                .andExpect(jsonPath("$[1].id", is(workExperienceDescriptions.get(1).getId())))
                .andExpect(jsonPath("$[1].label", is(workExperienceDescriptions.get(1).getLabel())));
    }

    @Test
    public void findAll_ifNoEntityExists_returnsHttp404() throws Exception {
        mockMvc.perform(get(RestPath.WORK_EXPERIENCE_DESCRIPTIONS_REST_CONTROLLER_PATH))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findById_ifEntityDoesNotExist_returnsHttp404() throws Exception {
        mockMvc.perform(get(RestPath.WORK_EXPERIENCE_DESCRIPTIONS_REST_CONTROLLER_PATH + "/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findById_ifEntityExists_returnsHttp200AndEntity() throws Exception {
        when(repository.findById(1)).thenReturn(Optional.ofNullable(workExperienceDescriptions.get(0)));

        mockMvc.perform(get(RestPath.WORK_EXPERIENCE_DESCRIPTIONS_REST_CONTROLLER_PATH + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(workExperienceDescriptions.get(0).getId())))
                .andExpect(jsonPath("$.label", is(workExperienceDescriptions.get(0).getLabel())));
    }

    @Test
    public void findById_ifUrlPathIdIsNotInteger_returnsHttp400_andErrorMessage() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get(RestPath.WORK_EXPERIENCE_DESCRIPTIONS_REST_CONTROLLER_PATH + "/a");
        String expectedMessage = new ST(CustomRestExceptionHandlerString.PARAMETER_TYPE_MISMATCH_ERROR)
                .add("httpMethod", HttpMethod.GET.name())
                .add("requestUrl", requestBuilder.buildRequest(context).getRequestURL().toString())
                .render();
        String expectedErrorMessage = new ST(CustomRestExceptionHandlerString.PARAMETER_TYPE_MISMATCH_ERROR_EXPLICIT_MESSAGE)
                .add("expectedType", Integer.class.getSimpleName())
                .add("receivedValue", "a")
                .render();

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(expectedMessage)))
                .andExpect(jsonPath("$.errors[0]", is(expectedErrorMessage)));
    }

    @Test
    public void notSupportedHttpMethod_returnsHttp405_andErrorMessage() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = patch(RestPath.WORK_EXPERIENCE_DESCRIPTIONS_REST_CONTROLLER_PATH);
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
                .andExpect(jsonPath("$.errors[0]", is(expectedErrorMessage)));;
    }

    @Test
    public void update_ifNoUrlPathIdProvided_returnsHttp400_andErrorMessage() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = put(RestPath.WORK_EXPERIENCE_DESCRIPTIONS_REST_CONTROLLER_PATH);
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
        MockHttpServletRequestBuilder requestBuilder = put(RestPath.WORK_EXPERIENCE_DESCRIPTIONS_REST_CONTROLLER_PATH + "/a");
        String expectedMessage = new ST(CustomRestExceptionHandlerString.PARAMETER_TYPE_MISMATCH_ERROR)
                .add("httpMethod", HttpMethod.PUT.name())
                .add("requestUrl", requestBuilder.buildRequest(context).getRequestURL().toString())
                .render();
        String expectedErrorMessage = new ST(CustomRestExceptionHandlerString.PARAMETER_TYPE_MISMATCH_ERROR_EXPLICIT_MESSAGE)
                .add("expectedType", Integer.class.getSimpleName())
                .add("receivedValue", "a")
                .render();

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(expectedMessage)))
                .andExpect(jsonPath("$.errors[0]", is(expectedErrorMessage)));
    }

    @Test
    public void update_ifUrlPathIdProvided_andInvalidData_returnsHttp400() throws Exception {
        mockMvc.perform(put(RestPath.WORK_EXPERIENCE_DESCRIPTIONS_REST_CONTROLLER_PATH + "/1")
                .content("").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_ifUrlPathIdProvided_andValidData_andEntityDoesNotExist_returnsHttp404() throws Exception {
        WorkExperienceDescription toCreate = WorkExperienceDescription.builder().label(workExperienceDescriptions.get(0).getLabel()).build();
        when(repository.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(put(RestPath.WORK_EXPERIENCE_DESCRIPTIONS_REST_CONTROLLER_PATH + "/1")
                .content(mapper.writeValueAsString(toCreate))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
