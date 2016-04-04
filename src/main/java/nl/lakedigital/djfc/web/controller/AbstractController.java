//package main.java.nl.lakedigital.djfc.web.controller;
//
//import nl.lakedigital.djfc.commons.json.AbstracteJsonEntiteitMetSoortEnId;
//import nl.lakedigital.djfc.domain.AbstracteEntiteitMetSoortEnId;
//import nl.lakedigital.djfc.domain.SoortEntiteit;
//import nl.lakedigital.djfc.mapper.Mapper;
//import nl.lakedigital.djfc.service.AbstractService;
//import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
//import org.apache.commons.lang3.builder.ToStringStyle;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.inject.Inject;
//import java.util.ArrayList;
//import java.util.List;
//
//public abstract class AbstractController<D extends AbstracteEntiteitMetSoortEnId, J extends AbstracteJsonEntiteitMetSoortEnId> {
//    protected static Logger LOGGER;
//
//    private final Class<D> domainType;
//    private final Class<J> jsonType;
//
//    public AbstractController(Class<D> domainType, Class<J> jsonType) {
//        this.domainType = domainType;
//        this.jsonType = jsonType;
//        LOGGER = LoggerFactory.getLogger(AbstractController.class);
//    }
//
//    @Inject
//    protected Mapper mapper;
//
//    public abstract AbstractService getService();
//
//    @RequestMapping(method = RequestMethod.GET, value = "/alles/{soortentiteit}/{parentid}")
//    @ResponseBody
//    public List<J> alles(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") Long parentid) {
//        LOGGER.debug("alles soortEntiteit {} parentId {}", soortentiteit, parentid);
//
//        List<D> domainEntiteiten = getService().alles(SoortEntiteit.valueOf(soortentiteit), parentid);
//        List<J> jsonEntiteiten = new ArrayList<>();
//
//        for (D entiteit : domainEntiteiten) {
//            jsonEntiteiten.add(mapper.map(entiteit, jsonType));
//        }
//
//        return jsonEntiteiten;
//    }
//
//    public abstract void opslaan(List<J> jsonEntiteiten);
//
//    public void goOpslaan(List<J> jsonEntiteiten) {
//        if (jsonEntiteiten != null && !jsonEntiteiten.isEmpty()) {
//            J eersteEntiteit = jsonEntiteiten.get(0);
//
//            List<D> entiteiten = new ArrayList<>();
//            for (J jsonEntiteit : jsonEntiteiten) {
//                LOGGER.debug(ReflectionToStringBuilder.toString(jsonEntiteit, ToStringStyle.SHORT_PREFIX_STYLE));
//
//                entiteiten.add(mapper.map(jsonEntiteit, domainType));
//            }
//            getService().opslaan(entiteiten, SoortEntiteit.valueOf(eersteEntiteit.getSoortEntiteit()), eersteEntiteit.getEntiteitId());
//        }
//    }
//
//    @RequestMapping(method = RequestMethod.POST, value = "/verwijderen/{soortentiteit}/{parentid}")
//    @ResponseBody
//    public void verwijderen(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") Long parentid) {
//        LOGGER.debug("Verwijderen adressen bij {} en {}", soortentiteit, parentid);
//
//        getService().verwijderen(SoortEntiteit.valueOf(soortentiteit), parentid);
//    }
//}
