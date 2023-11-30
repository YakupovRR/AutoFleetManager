package ru.pegasagro.dealer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import ru.pegasagro.owner.Owner;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dealer")
@JsonIgnoreProperties("owners")
public class Dealer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDealer;

    @Column
    private String nameDealer;

    @Column
    private String emailDealer;

    @Column
    private String representativeName;

    @OneToMany(mappedBy = "dealer", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Owner> owners;
}