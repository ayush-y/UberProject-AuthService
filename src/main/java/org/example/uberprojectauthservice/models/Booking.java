package org.example.uberprojectauthservice.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Booking extends BaseModel {

    @Enumerated(value = EnumType.STRING)
    private BookingStatus bookingStatus;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date startTime;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date endTime;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Long totalDistance;

    @ManyToOne
    private Driver driver;

    @ManyToOne(fetch = FetchType.LAZY)
    private Passenger passenger; //we define 1-to-1 relationship between Booking and Passenger
}
