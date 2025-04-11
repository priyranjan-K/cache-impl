package com.sample.cache_impl.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

import static com.sample.cache_impl.util.Constants.EMPLOYEE;

@Entity
@Table(name = EMPLOYEE)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class EmployeeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private EmployeePK employeePK;

    @Column(length = 11, nullable = false)
    private String dob;

    @Column(nullable = false)
    private double age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Grade grade;

    @CreationTimestamp(source = SourceType.DB)
    @Column(nullable = false, updatable = false)
    private Instant createdDate;

    @UpdateTimestamp(source = SourceType.DB)
    @Column(nullable = false)
    private Instant lastModifiedDate;
}
