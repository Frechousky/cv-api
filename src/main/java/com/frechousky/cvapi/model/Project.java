package com.frechousky.cvapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.net.URL;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@With
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Project title must be not blank")
    private String title;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
    @NotNull(message = "Project year must be not null")
    private Date year;

    @ElementCollection
    @NotEmpty(message = "Project description must be not empty")
    private List<String> description;

    private URL url;
}
