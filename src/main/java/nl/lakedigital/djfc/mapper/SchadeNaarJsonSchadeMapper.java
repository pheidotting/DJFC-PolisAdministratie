package nl.lakedigital.djfc.mapper;

import nl.lakedigital.djfc.commons.json.JsonSchade;
import nl.lakedigital.djfc.domain.Schade;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SchadeNaarJsonSchadeMapper extends AbstractMapper<Schade, JsonSchade> implements JsonMapper {
    private final static Logger LOGGER = LoggerFactory.getLogger(SchadeNaarJsonSchadeMapper.class);

    @Override
    public JsonSchade map(Schade schade, Object parent, Object bestaandObject) {
        JsonSchade jsonSchade = new JsonSchade();

        if (schade.getDatumAfgehandeld() != null) {
            jsonSchade.setDatumAfgehandeld(schade.getDatumAfgehandeld().toString("dd-MM-yyyy"));
        }
        jsonSchade.setDatumTijdMelding(schade.getDatumTijdMelding().toString("dd-MM-yyyy HH:mm"));
        jsonSchade.setDatumTijdSchade(schade.getDatumTijdSchade().toString("dd-MM-yyyy HH:mm"));
        if (schade.getEigenRisico() != null) {
            jsonSchade.setEigenRisico(schade.getEigenRisico().getBedrag().toString());
        }
        jsonSchade.setId(schade.getId());
        jsonSchade.setLocatie(schade.getLocatie());
        jsonSchade.setOmschrijving(schade.getOmschrijving());
        jsonSchade.setSchadeNummerMaatschappij(schade.getSchadeNummerMaatschappij());
        jsonSchade.setSchadeNummerTussenPersoon(schade.getSchadeNummerTussenPersoon());
        if (schade.getSoortSchade() != null) {
            jsonSchade.setSoortSchade(schade.getSoortSchade().getOmschrijving());
        } else {
            jsonSchade.setSoortSchade(schade.getSoortSchadeOngedefinieerd());
        }
        if (schade.getStatusSchade() != null) {
            jsonSchade.setStatusSchade(schade.getStatusSchade().getStatus());
        }
        jsonSchade.setPolis(jsonSchade.getPolis());

        LOGGER.debug("{}", ReflectionToStringBuilder.toString(jsonSchade, ToStringStyle.SHORT_PREFIX_STYLE));

        return jsonSchade;
    }

    @Override
    public boolean isVoorMij(Object object) {
        return object instanceof Schade;
    }
}
