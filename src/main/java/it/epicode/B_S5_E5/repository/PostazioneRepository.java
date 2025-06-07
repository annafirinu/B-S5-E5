package it.epicode.B_S5_E5.repository;

import it.epicode.B_S5_E5.bean.Postazione;
import it.epicode.B_S5_E5.enumeration.TipoPostazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostazioneRepository extends JpaRepository<Postazione, Long> {
    List<Postazione> findByTipoAndEdificio_Citta(TipoPostazione tipo, String citta);
    Optional<Postazione> findByCodice(String codice);
}
