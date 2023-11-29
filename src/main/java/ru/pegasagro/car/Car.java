package ru.pegasagro.car;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pegasagro.owner.Owner;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "car")
@JsonIgnoreProperties("owner")
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
    @JsonBackReference
    private Owner owner;
}