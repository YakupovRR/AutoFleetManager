package ru.pegasagro.owner;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import ru.pegasagro.car.Car;
import ru.pegasagro.dealer.Dealer;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "owner")
@JsonIgnoreProperties({"dealer", "ownedCars"})
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOwner;

    @Column
    private String fullNameOwner;

    @Column
    private String phoneNumberOwner;

    @Column
    private String emailOwner;

    @ManyToOne
    @JoinColumn(name = "idDealer")
    @JsonBackReference
    private Dealer dealer;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Car> ownedCars;
}
