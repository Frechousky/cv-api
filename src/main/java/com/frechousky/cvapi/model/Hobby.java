package com.frechousky.cvapi.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@With
public class Hobby {

    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank(message = "Hobby label must not be blank")
    private String label;

}
