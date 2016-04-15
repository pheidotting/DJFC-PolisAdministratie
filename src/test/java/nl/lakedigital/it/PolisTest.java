package nl.lakedigital.it;

import nl.lakedigital.djfc.client.polisadministratie.PolisClient;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.domain.particulier.AutoVerzekering;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PolisTest {
    private PolisClient polisClient = new PolisClient();
    private final String datumFormaat = "yyyy-MM-dd";


    @Test
    public void test() {
        JsonPolis polis = new JsonPolis();
        polis.setSoort(new AutoVerzekering().getSchermNaam());
        polis.setPolisNummer(UUID.randomUUID().toString().replace("-", "").substring(0, 25));
        polis.setMaatschappij("123");

        Long polisId = polisClient.opslaan(polis);

        polis.setId(polisId);

        polis.setIngangsDatum(LocalDate.now().toString(datumFormaat));

        controleerOpgeslagenPolis(polisId, polis);

        polis.setBedrijf("3");

        controleerOpgeslagenPolis(polisId, polis);

        polis.setVerzekerdeZaak(UUID.randomUUID().toString());

        controleerOpgeslagenPolis(polisId, polis);

        polis.setKenmerk(UUID.randomUUID().toString());

        controleerOpgeslagenPolis(polisId, polis);

        polis.setBetaalfrequentie("Jaar");

        controleerOpgeslagenPolis(polisId, polis);

        polis.setProlongatieDatum(LocalDateTime.now().toString(datumFormaat));

        controleerOpgeslagenPolis(polisId, polis);
    }

    private void controleerOpgeslagenPolis(Long id, JsonPolis polis) {
        polisClient.opslaan(polis);

        JsonPolis p = polisClient.lees(id.toString());

        System.out.println(ReflectionToStringBuilder.toString(polis, ToStringStyle.SHORT_PREFIX_STYLE));
        System.out.println(ReflectionToStringBuilder.toString(p, ToStringStyle.SHORT_PREFIX_STYLE));

        assertThat(p, is(polis));
    }
}
