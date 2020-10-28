package com.frechousky.cvapi.model;

import lombok.*;

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

    @Min(value = 0L, message = "Language level must be greater than or equal to 0")
    @Max(value = 5L, message = "Language level must be lesser than or equal to 5")
    @NotNull(message = "Language level must be not null")
    private Integer level;

}
