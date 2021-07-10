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
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Study degree must not be blank")
    @Enumerated(EnumType.STRING)
    private Degree degree;

    @NotBlank(message = "Field must not be blank")
    private String field;

    @NotBlank(message = "Study specialization must not be blank")
    private String specialization;

    private URL url;

    @NotBlank(message = "Study school must not be blank")
    private String school;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
    @NotNull(message = "Study starting date must be not null")
    private Date start;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
    private Date end;

    @NotBlank(message = "Study city must not be blank")
    private String city;

    @ElementCollection
    @NotEmpty(message = "Study description must be not empty")
    private List<String> description;

    public enum Degree {
        BACHELOR,
        MASTER;
    }

}

