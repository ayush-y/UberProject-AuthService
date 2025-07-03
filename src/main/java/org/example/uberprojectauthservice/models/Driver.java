package org.example.uberprojectauthservice.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Builder
@NoArgsConstructor
@Getter
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "bookings"})

public class Driver extends BaseModel{

    private String name;

    @Column(nullable = false, unique = true)
    private String licenseNumber;

    private String phoneNumber;

    //1 : n mapping -> driver : Booking
    @OneToMany(mappedBy = "driver", fetch  = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @Builder.Default
    private List<Booking> bookings = new ArrayList<>();
}
