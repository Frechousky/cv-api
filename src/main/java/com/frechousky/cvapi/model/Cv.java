package com.frechousky.cvapi.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@With
public class Cv {

    @OneToOne
    ContactInformation contactInformation;
    @OneToMany
    List<Language> languages;
    @OneToMany
    List<Skill> skills;
    @ElementCollection
    List<String> hobbies;
    @OneToMany
    List<WorkExperience> workExperiences;
    @OneToMany
    List<Study> studies;
    @OneToMany
    List<Project> projects;
    @Id
    @GeneratedValue
    private Integer id;
    @NotBlank(message = "CV job title must not be blank")
    private String jobTitle;

}
