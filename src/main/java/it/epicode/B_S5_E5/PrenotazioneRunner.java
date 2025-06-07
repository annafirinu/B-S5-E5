package it.epicode.B_S5_E5;

import it.epicode.B_S5_E5.bean.Edificio;
import it.epicode.B_S5_E5.bean.Postazione;
import it.epicode.B_S5_E5.bean.Prenotazione;
import it.epicode.B_S5_E5.bean.Utente;
import it.epicode.B_S5_E5.enumeration.TipoPostazione;
import it.epicode.B_S5_E5.repository.EdificioRepository;
import it.epicode.B_S5_E5.repository.PostazioneRepository;
import it.epicode.B_S5_E5.repository.PrenotazioneRepository;
import it.epicode.B_S5_E5.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class PrenotazioneRunner implements CommandLineRunner {

    @Autowired
    private EdificioRepository edificioRepository;

    @Autowired
    @Qualifier("edificio1")
    private Edificio edificio1;

    @Autowired
    @Qualifier("edificio2")
    private Edificio edificio2;



    @Autowired
    private PostazioneRepository postazioneRepository;

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    private Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args) throws Exception {
        edificioRepository.save(edificio1);
        edificioRepository.save(edificio2);
        boolean running = true;
        while (running) {
            try {
            mostraMenu();
            int scelta = scanner.nextInt();
            scanner.nextLine();
            switch (scelta) {
                case 0:
                    creaNuovoUtente();
                    break;
                case 1:
                    ricercaPostazioni();
                    break;
                case 2:
                    effettuaPrenotazione();
                    break;
                case 3:
                    visualizzaPrenotazioniUtente();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Scelta non valida.");
            }} catch (InputMismatchException e) {
                System.out.println("Inserisci un opzione valida.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void creaNuovoUtente() {
        System.out.print("Inserisci username: ");
        String username = scanner.nextLine();
        if (utenteRepository.existsById(username)) {
            System.out.println("Errore: l'utente esiste già.");
            return;
        }

        System.out.print("Inserisci nome completo: ");
        String nomeCompleto = scanner.nextLine();

        System.out.print("Inserisci email: ");
        String email = scanner.nextLine();

        Utente utente = new Utente();
        utente.setUsername(username);
        utente.setNomeCompleto(nomeCompleto);
        utente.setEmail(email);

        utenteRepository.save(utente);
        System.out.println("Utente creato con successo.");
    }

    private void mostraMenu() {
        System.out.println("\n--- Menu Prenotazioni ---");
        System.out.println("0. Crea nuovo utente");
        System.out.println("1. Ricerca postazioni");
        System.out.println("2. Effettua prenotazione");
        System.out.println("3. Visualizza prenotazioni utente");
        System.out.println("4. Esci");
        System.out.print("Scegli un'opzione: ");
    }

    private void ricercaPostazioni() {
        System.out.print("Inserisci il tipo di postazione (PRIVATO, OPENSPACE, SALA_RIUNIONI): ");
        String tipo = scanner.nextLine();
        System.out.print("Inserisci la città: ");
        String citta = scanner.nextLine();

        List<Postazione> postazioni = postazioneRepository.findByTipoAndEdificio_Citta(TipoPostazione.valueOf(tipo), citta);
        if (postazioni.isEmpty()) {
            System.out.println("Nessuna postazione trovata.");
        } else {
            System.out.println("Postazioni disponibili:");
            postazioni.forEach(postazione -> System.out.println(postazione.getCodice() + " - " + postazione.getDescrizione()));
        }
    }

    private void effettuaPrenotazione() {
        System.out.print("Inserisci il tuo username: ");
        String username = scanner.nextLine();
        Utente utente = utenteRepository.findById(username).orElse(null);
        if (utente == null) {
            System.out.println("Utente non trovato.");
            return;
        }

        System.out.print("Inserisci il codice della postazione: ");
        String codice = scanner.nextLine();
        Optional<Postazione> optPostazione = postazioneRepository.findByCodice(codice);
        if (optPostazione.isEmpty()) {
            System.out.println("Postazione non trovata.");
            return;
        }
        Postazione postazione = optPostazione.get();

        System.out.print("Inserisci la data della prenotazione (YYYY-MM-DD): ");
        LocalDate data = LocalDate.parse(scanner.nextLine());

        if (prenotazioneRepository.findByPostazioneAndData(Optional.of(postazione), data).isPresent()) {
            System.out.println("La postazione è già prenotata per questa data.");
            return;
        }

        if (!prenotazioneRepository.findByUtenteAndData(utente, data).isEmpty()) {
            System.out.println("Hai già una prenotazione per questa data.");
            return;
        }


        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setUtente(utente);
        prenotazione.setPostazione(postazione);
        prenotazione.setData(data);
        prenotazioneRepository.save(prenotazione);
        System.out.println("Prenotazione effettuata con successo.");
    }

    private void visualizzaPrenotazioniUtente() {
        System.out.print("Inserisci il tuo username: ");
        String username = scanner.nextLine();
        Utente utente = utenteRepository.findById(username).orElse(null);
        if (utente == null) {
            System.out.println("Utente non trovato.");
            return;
        }

        List<Prenotazione> prenotazioni = prenotazioneRepository.findFuturePrenotazioniByUtente(utente, LocalDate.now());
        if (prenotazioni.isEmpty()) {
            System.out.println("Nessuna prenotazione futura trovata.");
        } else {
            System.out.println("Le tue prenotazioni future:");
            prenotazioni.forEach(prenotazione -> System.out.println(prenotazione.getPostazione().getDescrizione() + " - " + prenotazione.getData()));
        }
    }
}
