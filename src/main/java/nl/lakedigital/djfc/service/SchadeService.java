package nl.lakedigital.djfc.service;

import org.springframework.stereotype.Service;

@Service
public class SchadeService {
    //    private static final Logger LOGGER = LoggerFactory.getLogger(SchadeService.class);
    //
    //    @Inject
    //    private SchadeRepository schadeRepository;
    //    @Inject
    //    private PolisService polisService;
    //
    //    public List<SoortSchade> soortenSchade() {
    //        return schadeRepository.soortenSchade();
    //    }
    //
    //    public List<SoortSchade> soortenSchade(String omschrijving) {
    //        return schadeRepository.soortenSchade(omschrijving);
    //    }
    //
    //    public StatusSchade getStatussen(String status) {
    //        return schadeRepository.getStatussen(status);
    //    }
    //
    //    public List<StatusSchade> getStatussen() {
    //        return schadeRepository.getStatussen();
    //    }
    //
    //    public Schade zoekOpSchadeNummerMaatschappij(String schadeNummer) {
    //        return schadeRepository.zoekOpSchadeNummerMaatschappij(schadeNummer);
    //    }
    //
    //    public void verwijder(Long id) {
    //        Schade schade = lees(id);
    //        schadeRepository.verwijder(schade);
    //    }
    //
    //    public void opslaan(Schade schade) {
    //        schadeRepository.opslaan(schade);
    //    }
    //
    //    public void opslaan(Schade schadeIn, String soortSchade, String polisId, String statusSchade) {
    //        LOGGER.debug("Opslaan schade");
    //        LOGGER.debug("{}", schadeIn);
    //
    //        Schade schade = schadeIn;
    //
    //        LOGGER.debug("Soort schade opzoeken " + soortSchade);
    //        List<SoortSchade> soorten = schadeRepository.soortenSchade(soortSchade);
    //
    //        if (StringUtils.isNotEmpty(statusSchade) && !"Kies een status uit de lijst..".equals(statusSchade)) {
    //            LOGGER.debug("Status schade opzoeken " + statusSchade);
    //            StatusSchade status = schadeRepository.getStatussen(statusSchade);
    //
    //            schade.setStatusSchade(status);
    //        }
    //
    //        if (!soorten.isEmpty()) {
    //            LOGGER.debug("Soort schade gevonden in database (" + soorten.size() + ")");
    //            schade.setSoortSchade(soorten.get(0));
    //        } else {
    //            LOGGER.debug("Geen soort schade gevonden in database, tekst dus opslaan");
    //            schade.setSoortSchadeOngedefinieerd(soortSchade);
    //        }
    //
    //        if (polisId != null && !"Kies een polis uit de lijst..".equals(polisId)) {
    //            //            LOGGER.debug("Polis opzoeken, id : " + polisId);
    //            //            Polis polis = polisService.lees(Long.valueOf(polisId));
    //            //            schade.setPolis(polis);
    //            schade.setPolis(Long.valueOf(polisId));
    //        }
    //
    //        // Bijlages er bij zoeken
    //        //        List<Bijlage> bijlages = schadeRepository.zoekBijlagesBijSchade(schade);
    //        //        schade.getBijlages().addAll(bijlages);
    //
    //        LOGGER.debug("Schade opslaan");
    //        schadeRepository.opslaan(schade);
    //
    //        //        LOGGER.debug("Opmerkingen bij de schade zoeken en er weer bij plaatsen");
    //        //        Schade schadeOorspronkelijk = schadeRepository.lees(schade.getId());
    //        //        schade.setOpmerkingen(schadeOorspronkelijk.getOpmerkingen());
    //    }
    //
    //    public List<Schade> alleSchadesBijRelatie(Long relatie) {
    //        List<Schade> schades = new ArrayList<>();
    //
    //        List<Polis> polissen = polisService.allePolissenBijRelatie(relatie);
    //
    //        for (Polis polis : polissen) {
    //            schades.addAll(schadeRepository.allesBijPolis(polis.getId()));
    //        }
    //
    //        return schades;
    //    }
    //
    //    public List<Schade> alleSchadesBijBedrijf(Long bedrijf) {
    //        List<Schade> schades = new ArrayList<>();
    //
    //        List<Polis> polissen = polisService.allePolissenBijBedrijf(bedrijf);
    //
    //        for (Polis polis : polissen) {
    //            schades.addAll(schadeRepository.allesBijPolis(polis.getId()));
    //        }
    //
    //        return schades;
    //    }
    //
    //    public Schade lees(Long id) {
    //        LOGGER.debug("{}", id);
    //        Schade schade = schadeRepository.lees(id);
    //        LOGGER.debug(ReflectionToStringBuilder.toString(schade));
    //        return schade;
    //    }
}