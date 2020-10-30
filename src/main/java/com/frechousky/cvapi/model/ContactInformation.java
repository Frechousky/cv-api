package com.frechousky.cvapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@With
public class ContactInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Contact information first name must not be blank")
    private String firstName;

    @NotBlank(message = "Contact information last name must not be blank")
    private String lastName;

    @NotBlank(message = "Contact information address must not be blank")
    private String address;

    @NotBlank(message = "Contact information city must not be blank")
    private String city;

    @NotBlank(message = "Contact information zip code must not be blank")
    private String zipCode;

    @NotBlank(message = "Contact information phone must not be blank")
    private String phone;

    @NotBlank(message = "Contact information mail must not be blank")
    @Email
    private String mail;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @NotNull(message = "Contact information date of birth must not be null")
    private Date dateOfBirth;

    @NotBlank(message = "Contact information driver licence must not be blank")
    private String driverLicence;

}
