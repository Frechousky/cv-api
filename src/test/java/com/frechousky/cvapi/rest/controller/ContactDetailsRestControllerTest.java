package com.frechousky.cvapi.rest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.frechousky.cvapi.model.ContactDetails;
import com.frechousky.cvapi.repository.ContactDetailsRepository;
import com.frechousky.cvapi.rest.controller.constants.RestControllerPath;
import lombok.SneakyThrows;
import org.assertj.core.util.Lists;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(ContactDetailsRestController.class)
public class ContactDetailsRestControllerTest extends CRUDSpringRestControllerTest<ContactDetails, ContactDetailsRepository> {

    List<ContactDetails> contactDetails;

    public ContactDetailsRestControllerTest() {
        contactDetails = Lists.newArrayList(
                ContactDetails.builder().address("address 1").age(1).city("city 1").driverLicence("driverLicence 1").fullName("fullName 1").id(1).mail("mail1@email.com").phone("phone 1").zipCode("zipCode 1").build(),
                ContactDetails.builder().address("address 2").age(2).city("city 2").driverLicence("driverLicence 2").fullName("fullName 2").id(2).mail("mail2@email.com").phone("phone 2").zipCode("zipCode 2").build()
        );
    }

    @Override
    public @NotEmpty List<ContactDetails> getAllEntities() {
        return contactDetails;
    }

    @Override
    public @NotNull ContactDetails getEntityToCreate(@NotNull ContactDetails entity) {
        return entity.withId(null);
    }

    @Override
    public @NotNull ContactDetails getEntityToUpdate(@NotNull ContactDetails entity) {
        return entity.withAddress("updated address");
    }

    @Override
    public @NotNull Integer getIdFromEntity(@NotNull ContactDetails entity) {
        return entity.getId();
    }

    @SneakyThrows
    @Override
    public @NotBlank String getInvalidJsonData() {
        return mapper.writeValueAsString(ContactDetails.builder().build());
    }

    @Override
    public @NotBlank String getRestControllerUrl() {
        return RestControllerPath.CONTACT_DETAILS_CONTROLLER_PATH;
    }

    @Override
    public @NotEmpty List<ContactDetails> deserializeJsonArray(@NotBlank String json) throws Exception {
        return mapper.readValue(json, new TypeReference<ArrayList<ContactDetails>>() {
        });
    }

    @Override
    public @NotNull ContactDetails deserializeJsonObject(@NotBlank String json) throws Exception {
        return mapper.readValue(json, ContactDetails.class);
    }
}