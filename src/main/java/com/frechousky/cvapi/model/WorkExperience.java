package com.frechousky.cvapi.model;

import lombok.*;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@With
public class WorkExperience {

    @Id
    @GeneratedValue
    private Integer id;

    @Lazy
    @ManyToOne
    private Company company;

    private String position;

    @OneToMany
    @Lazy
    private List<WorkExperienceDescription> description;

    private Date start;

    private Date end;
}