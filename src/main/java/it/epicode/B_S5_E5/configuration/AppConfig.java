package it.epicode.B_S5_E5.configuration;

import it.epicode.B_S5_E5.bean.Edificio;
import it.epicode.B_S5_E5.bean.Postazione;
import it.epicode.B_S5_E5.enumeration.TipoPostazione;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AppConfig {

    @Bean("edificio1")
    public Edificio getEdificio1() {
        Edificio edificio1 = new Edificio();
        edificio1.setNome("EdificioA");
        edificio1.setIndirizzo("Via Roma 1");
        edificio1.setCitta("Milano");

        Postazione post1 = new Postazione();
        post1.setCodice("POST1");
        post1.setDescrizione("Postazione privata Milano");
        post1.setTipo(TipoPostazione.PRIVATO);
        post1.setNumeroMassimoOccupanti(1);
        post1.setEdificio(edificio1);

        Postazione post2 = new Postazione();
        post2.setCodice("POST2");
        post2.setDescrizione("Sala riunioni Milano");
        post2.setTipo(TipoPostazione.SALA_RIUNIONI);
        post2.setNumeroMassimoOccupanti(10);
        post2.setEdificio(edificio1);

        edificio1.setPostazioni(List.of(post1, post2));

        return edificio1;
    }

    @Bean("edificio2")
    public Edificio getEdificio2() {
        Edificio edificio2 = new Edificio();
        edificio2.setNome("EdificioB");
        edificio2.setIndirizzo("Via Torino 5");
        edificio2.setCitta("Roma");

        Postazione post3 = new Postazione();
        post3.setCodice("POST3");
        post3.setDescrizione("Postazione openspace Roma");
        post3.setTipo(TipoPostazione.OPENSPACE);
        post3.setNumeroMassimoOccupanti(6);
        post3.setEdificio(edificio2);

        edificio2.setPostazioni(List.of(post3));

        return edificio2;
    }
}