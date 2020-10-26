package com.frechousky.cvapi.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class ContactDetails {

    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank(message = "Contact details full name must not be blank")
    private String fullName;

    @NotBlank(message = "Contact details address must not be blank")
    private String address;

    @NotBlank(message = "Contact details zip code must not be blank")
    private String zipCode;

    @NotBlank(message = "Contact details city must not be blank")
    private String city;

    @NotBlank(message = "Contact details phone must not be blank")
    private String phone;

    @NotBlank(message = "Contact details mail must not be blank")
    @Email
    private String mail;

    @NotNull(message = "Contact details age must not be not null")
    @Min(value = 0, message = "Contact details age must be greater or equal to 0")
    private Integer age;

    @NotBlank(message = "Contact details driver licence must not be blank")
    private String driverLicence;

}
