package com.frechousky.cvapi.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@With
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Company name must not be blank")
    private String name;

    @NotBlank(message = "Company field must not be blank")
    private String field;

    @NotBlank(message = "Company country must not be blank")
    private String country;

    @NotBlank(message = "Company city must not be blank")
    private String city;
}
