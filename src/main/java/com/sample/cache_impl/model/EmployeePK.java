package com.sample.cache_impl.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
@EqualsAndHashCode
public class EmployeePK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(length = 20, nullable = false,columnDefinition = "VARCHAR(20) COLLATE utf8mb4_bin")
    private String firstName;

    @Column(length = 20, nullable = false, columnDefinition = "VARCHAR(20) COLLATE utf8mb4_bin")
    private String lastName;
}
