package it.epicode.B_S5_E5.repository;

import it.epicode.B_S5_E5.bean.Postazione;
import it.epicode.B_S5_E5.bean.Prenotazione;
import it.epicode.B_S5_E5.bean.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {

    // Verifica se un utente ha già una prenotazione in quella data
    List<Prenotazione> findByUtenteAndData(Utente utente, LocalDate data);

    // Verifica se una postazione è già prenotata in quella data
    Optional<Prenotazione> findByPostazioneAndData(Optional<Postazione> postazione, LocalDate data);

    // Prenotazioni future di un utente
    @Query("SELECT p FROM Prenotazione p WHERE p.utente = :utente AND p.data >= :data")
    List<Prenotazione> findFuturePrenotazioniByUtente(Utente utente, LocalDate data);

    // Prenotazioni per una determinata data
    @Query("SELECT p FROM Prenotazione p WHERE p.data = :data")
    List<Prenotazione> findPrenotazioniByData(LocalDate data);
}
