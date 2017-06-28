package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.domain.Polis;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.SoortVerzekering;
import nl.lakedigital.djfc.predicates.PolissenOpSoortPredicate;
import nl.lakedigital.djfc.repository.PolisRepository;
import nl.lakedigital.djfc.transformers.PolisToSchermNaamTransformer;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.util.List;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;

@Service
public class PolisService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PolisService.class);

    @Inject
    private PolisRepository polisRepository;
    @Inject
    private List<Polis> polissen;
    @Inject
    private SchadeService schadeService;

    public List<String> allePolisSoorten(final SoortVerzekering soortVerzekering) {
        Iterable<Polis> poli = filter(polissen, new PolissenOpSoortPredicate(soortVerzekering));

        Iterable<String> polisString = transform(poli, new PolisToSchermNaamTransformer());

        return newArrayList(polisString);
    }

    public List<Polis> alles(SoortEntiteit soortEntiteit, Long entiteitId) {
        List<Polis> poli = polisRepository.alles(soortEntiteit, entiteitId);

        for (Polis polis : poli) {
            polis.setSchades(schadeService.allesBijPolis(polis));
        }

        return poli;
    }

    public void beeindigen(Long id) {
        Polis polis = polisRepository.lees(id);

        polis.setEindDatum(new LocalDate());
        polisRepository.opslaan(polis);
    }

    public void opslaan(Polis polis) {
        LOGGER.debug(ReflectionToStringBuilder.toString(polis));

        polisRepository.opslaan(polis);

        LOGGER.debug("{}", lees(polis.getId()));
    }

    public void opslaan(List<Polis> polissen) {
        polisRepository.opslaan(polissen);
    }

    public Polis lees(Long id) {
        return polisRepository.lees(id);
    }


    public List<Polis> zoekOpPolisNummer(String polisNummer) {
        try {
            return polisRepository.zoekOpPolisNummer(polisNummer);
        } catch (NoResultException e) {
            LOGGER.debug("Niks gevonden ", e);
            return null;
        }
    }

    public void verwijder(Long id) {
        LOGGER.debug("Ophalen Polis");
        Polis polis = polisRepository.lees(id);

        if (polis == null) {
            throw new IllegalArgumentException("Geen Polis gevonden met id " + id);
        }
        LOGGER.debug("Polis gevonden : " + polis);

        polisRepository.verwijder(polis);
    }

    public void verwijder(List<Long> ids) {
        LOGGER.debug("Ophalen Polis");
        List<Polis> polissen = newArrayList();
        for (Long id : ids) {
            polissen.add(polisRepository.lees(id));
        }

        polisRepository.verwijder(polissen);
    }
}
