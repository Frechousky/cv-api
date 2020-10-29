package com.frechousky.cvapi.model;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@With
public class Language {

    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank(message = "Language label must not be blank")
    private String label;

    @Range(min = 0, max = 5, message = "Language level must be >= 0 and <= 5")
    @NotNull(message = "Language level must be not null")
    private Double level;

}
