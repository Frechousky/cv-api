package com.frechousky.cvapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Company company;

    @NotBlank(message = "Work experience position must not be blank")
    private String position;

    @ElementCollection
    @NotEmpty(message = "Work experience description must not be empty")
    private List<String> description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/yyyy")
    @NotNull(message = "Work experience starting date must not be null")
    private Date start;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/yyyy")
    private Date end;
}