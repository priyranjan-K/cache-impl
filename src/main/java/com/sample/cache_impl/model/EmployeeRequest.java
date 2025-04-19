package com.sample.cache_impl.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.time.Instant;

@Builder
@Data
public class EmployeeRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size.List({
            @Size(min = 3, message = "Minimun length of FirstName should be 3."),
            @Size(max = 20, message = "Maximun length of FirstName should be 20.")
    })
    @NotBlank(message = "FirstName is mandatory.")
    private String firstName;

    @Size.List({
            @Size(min = 3, message = "Minimun length of LastName should be 3."),
            @Size(max = 20, message = "Maximun length of LastName should be 20.")
    })
    @NotBlank(message = "LastName is mandatory.")
    private String lastName;

    @NotBlank(message = "DOB is mandatory.")
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$", message = "Invalid DOB format, expected dd/MM/yyyy")
    private String dob;

    @Range(min = 0, max = 110, message = "Employee's age must be  between 0-100.")
    private double age;

    private Grade grade;

    private Instant createdDate;

    private Instant lastModifiedDate;
}
