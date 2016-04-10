package nl.lakedigital.djfc.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/overig")
@Controller
public class JsonController {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonController.class);

    //    @Inject
    //    private VerzekeringsMaatschappijService maatschappijService;
    //    @Inject
    //    private SchadeService schadeService;
    //    @Inject
    //    private SoortSchadeMapper soortSchadeMapper;
    //    @Inject
    //    private PostcodeService postcodeService;
    //
    //    @RequestMapping(method = RequestMethod.GET, value = "/lijstVerzekeringsMaatschappijen")
    //    @ResponseBody
    //    public Map<Long, String> lijstVerzekeringsMaatschappijen() {
    //
    //        LOGGER.debug("ophalen lijst met VerzekeringsMaatschappijen");
    //
    //        List<VerzekeringsMaatschappij> lijst = maatschappijService.alles();
    //
    //        LOGGER.debug("Gevonden, " + lijst.size() + " VerzekeringsMaatschappijen");
    //
    //        Map<Long, String> ret = new HashMap<>();
    //        ret.put(0L, "Kies een maatschappij...");
    //
    //        for (VerzekeringsMaatschappij vm : lijst) {
    //            ret.put(vm.getId(), vm.getNaam());
    //        }
    //
    //        LOGGER.debug("{}", ret);
    //
    //        return ret;
    //    }
    //
    //    @RequestMapping(method = RequestMethod.GET, value = "/extraInfo")
    //    @ResponseBody
    //    public String extraInfo() {
    //
    //        String omgeving = System.getProperty("omgeving");
    //
    //        LOGGER.debug("omgeving " + omgeving);
    //
    //        return omgeving;
    //    }
    //
    //    @RequestMapping(method = RequestMethod.GET, value = "/soortenSchade")
    //    @ResponseBody
    //    public List<JsonSoortSchade> soortenSchade(@QueryParam("query") String query) {
    //        return soortSchadeMapper.mapAllNaarJson(schadeService.soortenSchade(query));
    //    }
    //
    //    @RequestMapping(method = RequestMethod.GET, value = "/lijstStatusSchade")
    //    @ResponseBody
    //    public List<String> lijstStatusSchade() {
    //        List<StatusSchade> lijst = schadeService.getStatussen();
    //
    //        List<String> ret = new ArrayList<String>();
    //
    //        for (StatusSchade statusSchade : lijst) {
    //            ret.add(statusSchade.getStatus());
    //        }
    //
    //        return ret;
    //    }
}
