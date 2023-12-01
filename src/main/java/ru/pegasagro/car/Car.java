package ru.pegasagro.car;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import ru.pegasagro.owner.Owner;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCar;

    @Column
    private LocalDate assemblyDate;

    @Column
    private String uniqueNumber;

    @ManyToOne
    @JoinColumn(name = "idowner")
    private Owner owner;
}