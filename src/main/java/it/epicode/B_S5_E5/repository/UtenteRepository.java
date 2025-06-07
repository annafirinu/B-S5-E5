package it.epicode.B_S5_E5.repository;

import it.epicode.B_S5_E5.bean.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtenteRepository extends JpaRepository<Utente, String> {
}
