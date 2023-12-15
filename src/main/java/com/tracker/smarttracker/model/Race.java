package com.tracker.smarttracker.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Builder
@Table(name = "runs")
public class Race {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "runs_id_generator")
    @SequenceGenerator(name = "runs_id_generator", sequenceName = "runs_seq")
    private long id;

    @Column(name = "start_latitude", precision = 6, scale = 4)
    private BigDecimal startLatitude;

    @Column(name = "finish_latitude", precision = 6, scale = 4)
    private BigDecimal finishLatitude;

    @Column(name = "start_longitude", precision = 7, scale = 4)
    private BigDecimal startLongitude;

    @Column(name = "finish_longitude", precision = 7, scale = 4)
    private BigDecimal finishLongitude;

    @Column(name = "start_datetime")
    private LocalDateTime startDatetime;

    @Column(name = "finish_datetime")
    private LocalDateTime finishDatetime;

    @Column(name = "distance", precision = 10, scale = 4)
    private BigDecimal distance;

    @ManyToOne(optional = false)
    private User user;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "average_speed", precision = 10, scale = 4)
    private BigDecimal averageSpeed;


}
