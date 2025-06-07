package it.epicode.B_S5_E5.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utente {

    @Id
    private String username;

    private String nomeCompleto;
    private String email;

    @OneToMany(mappedBy = "utente", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Prenotazione> prenotazioni;
}
