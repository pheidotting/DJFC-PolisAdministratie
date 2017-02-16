package nl.lakedigital.djfc.messaging.mapper;

import nl.lakedigital.as.messaging.domain.Schade;
import nl.lakedigital.djfc.domain.Bedrag;
import nl.lakedigital.djfc.service.SchadeService;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.function.Function;

public class MessagingSchadeNaarDomainSchadeMapper implements Function<Schade, nl.lakedigital.djfc.domain.Schade> {
    private SchadeService schadeService;

    public MessagingSchadeNaarDomainSchadeMapper(SchadeService schadeService) {
        this.schadeService = schadeService;
    }

    @Override
    public nl.lakedigital.djfc.domain.Schade apply(Schade schadeIn) {
        nl.lakedigital.djfc.domain.Schade schade;
        if (schadeIn.getId() == null || schadeIn.getId() == 0L) {
            schade = new nl.lakedigital.djfc.domain.Schade();
        } else {
            schade = schadeService.lees(schadeIn.getId());
        }

        String patternDatum = "dd-MM-yyyy";
        String patternDatumTijd = "dd-MM-yyyy HH:mm";

        LocalDate datumAfgehandeld = null;
        if (schadeIn.getDatumAfgehandeld() != null && !"".equals(schadeIn.getDatumAfgehandeld())) {
            datumAfgehandeld = LocalDate.parse(schadeIn.getDatumAfgehandeld(), DateTimeFormat.forPattern(patternDatum));
        }
        schade.setDatumAfgehandeld(datumAfgehandeld);

        LocalDateTime datumTijdMelding = null;
        if (schadeIn.getDatumTijdMelding() != null && !"".equals(schadeIn.getDatumTijdMelding())) {
            datumTijdMelding = LocalDateTime.parse(schadeIn.getDatumTijdMelding(), DateTimeFormat.forPattern(patternDatumTijd));
        }
        schade.setDatumTijdMelding(datumTijdMelding);

        LocalDateTime datumTijdSchade = null;
        if (schadeIn.getDatumTijdSchade() != null && !"".equals(schadeIn.getDatumTijdSchade())) {
            datumTijdSchade = LocalDateTime.parse(schadeIn.getDatumTijdSchade(), DateTimeFormat.forPattern(patternDatumTijd));
        }
        schade.setDatumTijdSchade(datumTijdSchade);
        if (schadeIn.getEigenRisico() != null) {
            schade.setEigenRisico(new Bedrag(schadeIn.getEigenRisico()));
        }
        schade.setLocatie(schadeIn.getLocatie());
        schade.setOmschrijving(schadeIn.getOmschrijving());
        schade.setSchadeNummerMaatschappij(schadeIn.getSchadeNummerMaatschappij());
        schade.setSchadeNummerTussenPersoon(schadeIn.getSchadeNummerTussenPersoon());
        schade.setIdentificatie(schadeIn.getIdentificatie());

        schadeService.opslaan(schade, schadeIn.getSoortSchade(), schadeIn.getPolis(), schadeIn.getStatusSchade());

        return schade;
    }
}
