package it.epicode.B_S5_E5.bean;

import it.epicode.B_S5_E5.enumeration.TipoPostazione;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Postazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String codice;
    private String descrizione;

    @Enumerated(EnumType.STRING)
    private TipoPostazione tipo;

    private int numeroMassimoOccupanti;

    @OneToMany(mappedBy = "postazione", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Prenotazione> prenotazioni;

    @ManyToOne
    @JoinColumn(name = "edificio_id")
    private Edificio edificio;

}
