package com.frechousky.cvapi.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TEST")
@With
public class WorkExperienceDescription {

    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank(message = "Work experience description label must not be blank")
    private String label;
}
