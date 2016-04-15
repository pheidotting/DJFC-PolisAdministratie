package nl.lakedigital.djfc.web.controller;

import com.google.common.collect.Lists;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.domain.Polis;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.SoortVerzekering;
import nl.lakedigital.djfc.mapper.Mapper;
import nl.lakedigital.djfc.service.PolisService;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/polis")
@Controller
public class PolisController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PolisController.class);

    @Inject
    private PolisService polisService;
    @Inject
    private List<Polis> polissen;
    @Inject
    private Mapper mapper;

    @RequestMapping(method = RequestMethod.GET, value = "/alleParticulierePolisSoorten")
    @ResponseBody
    public List<String> alleParticulierePolisSoorten() {
        return polisService.allePolisSoorten(SoortVerzekering.PARTICULIER);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/alleZakelijkePolisSoorten")
    @ResponseBody
    public List<String> alleZakelijkePolisSoorten() {
        return polisService.allePolisSoorten(SoortVerzekering.ZAKELIJK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lees/{id}")
    @ResponseBody
    public JsonPolis lees(@PathVariable("id") String id) {
        LOGGER.debug("ophalen Polis met id " + id);
        if (id != null && !"".equals(id) && !"0".equals(id)) {
            LOGGER.debug("ophalen Polis");
            return mapper.map(polisService.lees(Long.valueOf(id)), JsonPolis.class);
        } else {
            LOGGER.debug("Nieuwe Polis tonen");
            return new JsonPolis();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/beeindigen/{id}")
    @ResponseBody
    public void beeindigen(@PathVariable("id") Long id) {
        LOGGER.debug("beeindigen Polis met id " + id);
        polisService.beeindigen(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijst/{relatieId}")
    @ResponseBody
    public List<JsonPolis> lijst(@PathVariable("relatieId") String relatieId) {
        LOGGER.debug("Ophalen alle polissen voor Relatie " + relatieId);
        //        Relatie relatie = (Relatie) gebruikerService.lees(Long.valueOf(relatieId));
        Long relatie = Long.valueOf(relatieId);

        List<JsonPolis> result = Lists.newArrayList();
        for (Polis polis : polisService.alles(SoortEntiteit.RELATIE, relatie)) {
            result.add(mapper.map(polis, JsonPolis.class));
        }

        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijstBijBedrijf/{bedrijfId}")
    @ResponseBody
    public List<JsonPolis> lijstBijBedrijf(@PathVariable("bedrijfId") Long bedrijfId) {
        LOGGER.debug("Ophalen alle polissen voor Bedrijf " + bedrijfId);

        List<JsonPolis> polissen = new ArrayList<>();
        for (Polis polis : polisService.alles(SoortEntiteit.BEDRIJF, bedrijfId)) {
            polissen.add(mapper.map(polis, JsonPolis.class));
        }

        return polissen;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan")
    @ResponseBody
    public Long opslaan(@RequestBody JsonPolis jsonPolis) {
        LOGGER.debug("Opslaan " + ReflectionToStringBuilder.toString(jsonPolis));

        Polis polis = mapper.map(jsonPolis, Polis.class);
        polisService.opslaan(polis);

        return polis.getId();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/verwijder/{id}")
    @ResponseBody
    public void verwijder(@PathVariable("id") Long id) {
        LOGGER.debug("verwijderen Polis met id " + id);
        polisService.verwijder(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/zoekOpPolisNummer/{polisNummer}")
    @ResponseBody
    public JsonPolis zoekOpPolisNummer(@PathVariable("polisNummer") String polisNummer) {
        return mapper.map(polisService.zoekOpPolisNummer(polisNummer), JsonPolis.class);
    }
}
