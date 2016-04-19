package nl.lakedigital.djfc.web.controller;

import nl.lakedigital.djfc.commons.json.JsonSchade;
import nl.lakedigital.djfc.commons.json.JsonSoortSchade;
import nl.lakedigital.djfc.domain.Schade;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.SoortSchade;
import nl.lakedigital.djfc.domain.StatusSchade;
import nl.lakedigital.djfc.mapper.Mapper;
import nl.lakedigital.djfc.service.SchadeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/schade")
@Controller
public class SchadeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchadeController.class);

    @Inject
    private SchadeService schadeService;
    @Inject
    private Mapper mapper;

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan")
    @ResponseBody
    public Long opslaan(@RequestBody JsonSchade jsonSchade) {
        LOGGER.debug("{}", jsonSchade);

        Schade schade = mapper.map(jsonSchade, Schade.class);
        schadeService.opslaan(schade, jsonSchade.getSoortSchade(), jsonSchade.getPolis(), jsonSchade.getStatusSchade());

        return schade.getId();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijst")
    @ResponseBody
    public List<JsonSchade> lijst(@QueryParam("relatieId") Long relatieId) {
        LOGGER.debug("Opzoeken Schades bij Relatie met Id {}", relatieId);

        List<Schade> schades = schadeService.alles(SoortEntiteit.RELATIE, relatieId);
        List<JsonSchade> result = new ArrayList<>();

        for (Schade schade : schades) {
            result.add(mapper.map(schade, JsonSchade.class));
        }

        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijstBijBedrijf")
    @ResponseBody
    public List<JsonSchade> lijstBijBedrijf(@QueryParam("bedrijfId") Long bedrijfId) {
        LOGGER.debug("Opzoeken Schades bij Bedrijf met Id {}", bedrijfId);


        List<Schade> schades = schadeService.alles(SoortEntiteit.SCHADE, bedrijfId);
        List<JsonSchade> result = new ArrayList<>();

        for (Schade schade : schades) {
            result.add(mapper.map(schade, JsonSchade.class));
        }

        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lees/{id}")
    @ResponseBody
    public JsonSchade lees(@PathVariable("id") String id) {
        Schade schade = schadeService.lees(Long.valueOf(id));

        if (schade == null) {
            throw new ResourceNotFoundException();
        }

        return mapper.map(schade, JsonSchade.class);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException {
    }

    @RequestMapping(method = RequestMethod.POST, value = "/verwijder/{id}")
    @ResponseBody
    public void verwijder(@PathVariable("id") Long id) {
        LOGGER.debug("verwijderen Schade met id " + id);
        try {
            schadeService.verwijder(id);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Fout bij verwijderen Schade", e);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/soortenSchade")
    @ResponseBody
    public List<JsonSoortSchade> soortenSchade(@QueryParam("query") String query) {
        List<SoortSchade> lijst = schadeService.soortenSchade(query);
        List<JsonSoortSchade> result = new ArrayList<>();

        for (SoortSchade soortSchade : lijst) {
            result.add(mapper.map(soortSchade, JsonSoortSchade.class));
        }

        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijstStatusSchade")
    @ResponseBody
    public List<String> lijstStatusSchade() {
        List<StatusSchade> lijst = schadeService.getStatussen();

        List<String> ret = new ArrayList<String>();

        for (StatusSchade statusSchade : lijst) {
            ret.add(statusSchade.getStatus());
        }

        return ret;
    }
}
