package com.frechousky.cvapi.model;

import lombok.*;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "Work experience company must not be null")
    private Company company;

    @NotBlank(message = "Work experience position must not be blank")
    private String position;

    @OneToMany
    @Lazy
    @NotEmpty
    private List<WorkExperienceDescription> description;

    @NotNull(message = "Work experience starting date must not be null")
    private Date start;

    private Date end;
}