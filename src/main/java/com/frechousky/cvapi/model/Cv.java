package com.frechousky.cvapi.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@With
public class Cv {

    ContactInformation contactInformation;
    List<Language> languages;
    List<Skill> skills;
    List<String> hobbies;
    List<WorkExperience> workExperiences;
    List<Study> studies;
    List<Project> projects;
    @Id
    @GeneratedValue
    private Integer id;
    @NotBlank(message = "CV job title must not be blank")
    private String jobTitle;

}
