package org.example.uberprojectauthservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // This annotation is used to enable auditing features in JPA
public abstract class BaseModel {
    @Id // This annotation indicates that the field is the primary key of the entity
    @GeneratedValue(strategy = GenerationType.IDENTITY) // This annotation is used to specify that the value of the primary key will be generated automatically
    private Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE) // This annotation specifies that the date should be stored as a timestamp
    @CreatedDate
    //This annotation indicates that this field will be automatically populated with the current date and time when the entity is created
    protected Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
// This annotation indicates that this field will be automatically populated with the current date and time when the entity is updated
    private Date updatedAt;
}
