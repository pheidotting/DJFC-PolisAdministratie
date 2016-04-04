package nl.lakedigital.djfc.service;

import com.google.common.collect.Lists;
import nl.lakedigital.djfc.domain.Polis;
import nl.lakedigital.djfc.domain.SoortVerzekering;
import nl.lakedigital.djfc.predicates.PolisOpSchermNaamPredicate;
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
import static com.google.common.collect.Iterables.getOnlyElement;
import static com.google.common.collect.Iterables.transform;

@Service
public class PolisService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PolisService.class);

    @Inject
    private PolisRepository polisRepository;
    @Inject
    private List<Polis> polissen;

    public List<String> allePolisSoorten(final SoortVerzekering soortVerzekering) {
        Iterable<Polis> poli = filter(polissen, new PolissenOpSoortPredicate(soortVerzekering));

        Iterable<String> polisString = transform(poli, new PolisToSchermNaamTransformer());

        return Lists.newArrayList(polisString);
    }

//    public List<Polis> allePolissenVanRelatieEnZijnBedrijf(Relatie relatie) {
//        return polisRepository.allePolissenVanRelatieEnZijnBedrijf(relatie);
//    }

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

    public Polis lees(Long id) {
        return polisRepository.lees(id);
    }

//    public Polis zoekOpPolisNummer(String polisNummer) {
//        try {
//            return polisRepository.zoekOpPolisNummer(polisNummer, kantoorRepository.lees(1L));
//        } catch (NoResultException e) {
//            LOGGER.debug("Niks gevonden ", e);
//            return null;
//        }
//    }

    public void verwijder(Long id) {
        LOGGER.debug("Ophalen Polis");
        Polis polis = polisRepository.lees(id);

        if (polis == null) {
            throw new IllegalArgumentException("Geen Polis gevonden met id " + id);
        }
        LOGGER.debug("Polis gevonden : " + polis);

        polisRepository.verwijder(polis);
    }

//    public List<Polis> allePolissenBijRelatie(Long relatie) {
//        return polisRepository.allePolissenBijRelatie(relatie);
//    }
//
//    public List<Polis> allePolissenBijBedrijf(Long bedrijf) {
//        return polisRepository.allePolissenBijBedrijf(bedrijf);
//    }


}
