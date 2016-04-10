package nl.lakedigital.djfc.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/schade")
@Controller
public class SchadeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchadeController.class);

    //    @Inject
    //    private SchadeService schadeService;
    //    @Inject
    //    private SchadeMapper schadeMapper;
    //    @Inject
    //    private GebruikerService gebruikerService;
    //    @Inject
    //    private BedrijfService bedrijfService;
    //
    //    @RequestMapping(method = RequestMethod.POST, value = "/opslaan")
    //    @ResponseBody
    //    public Response opslaan(@RequestBody JsonSchade jsonSchade) {
    //        LOGGER.debug("{}", jsonSchade);
    //
    //        Schade schade = schadeMapper.mapVanJson(jsonSchade);
    //        schadeService.opslaan(schade, jsonSchade.getSoortSchade(), jsonSchade.getPolis(), jsonSchade.getStatusSchade());
    //
    //        return Response.status(202).entity(new JsonFoutmelding(schade.getId().toString())).build();
    //    }
    //
    //    @RequestMapping(method = RequestMethod.GET, value = "/lijst")
    //    @ResponseBody
    //    public List<JsonSchade> lijst(@QueryParam("relatieId") Long relatieId) {
    //        LOGGER.debug("Opzoeken Schades bij Relatie met Id {}", relatieId);
    //
    //        return schadeMapper.mapAllNaarJson(schadeService.alleSchadesBijRelatie(relatieId));
    //    }
    //
    //    @RequestMapping(method = RequestMethod.GET, value = "/lijstBijBedrijf")
    //    @ResponseBody
    //    public List<JsonSchade> lijstBijBedrijf(@QueryParam("bedrijfId") Long bedrijfId) {
    //        LOGGER.debug("Opzoeken Schades bij Bedrijf met Id {}", bedrijfId);
    //
    //        return schadeMapper.mapAllNaarJson(schadeService.alleSchadesBijBedrijf(bedrijfId));
    //    }
    //
    //    @RequestMapping(method = RequestMethod.GET, value = "/lees")
    //    @ResponseBody
    //    public JsonSchade lees(@QueryParam("id") String id) {
    //        return schadeMapper.mapNaarJson(schadeService.lees(Long.valueOf(id)));
    //    }
    //
    //    @RequestMapping(method = RequestMethod.GET, value = "/verwijder")
    //    @ResponseBody
    //    public Response verwijder(@QueryParam("id") Long id) {
    //        LOGGER.debug("verwijderen Schade met id " + id);
    //        try {
    //            schadeService.verwijder(id);
    //        } catch (IllegalArgumentException e) {
    //            LOGGER.error("Fout bij verwijderen Schade", e);
    //            return Response.status(500).entity(new JsonFoutmelding(e.getMessage())).build();
    //        }
    //        return Response.status(202).entity(new JsonFoutmelding()).build();
    //    }

}
