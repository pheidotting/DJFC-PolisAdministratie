package nl.lakedigital.it;

import nl.lakedigital.djfc.client.polisadministratie.PolisClient;
import org.junit.Ignore;

import java.util.UUID;

@Ignore

public class PolisTest {
    private final Long ingegelogdeGebruiker = 46L;
    private final String trackAndTraceId = UUID.randomUUID().toString();

    private PolisClient polisClient = new PolisClient();
    private final String datumFormaat = "yyyy-MM-dd";

    //    @Test
    //    public void test() {
    //        JsonPolis polis = new JsonPolis();
    //        polis.setSoort(new AutoVerzekering(SoortEntiteit.BEDRIJF, 3L).getSchermNaam());
    //        polis.setPolisNummer(UUID.randomUUID().toString().replace("-", "").substring(0, 25));
    //        polis.setMaatschappij("123");
    //
    ////        Long polisId = polisClient.opslaan(polis, ingegelogdeGebruiker, trackAndTraceId, "");
    //
    //        polis.setId(polisId);
    //
    //        polis.setIngangsDatum(LocalDate.now().toString(datumFormaat));
    //
    //        controleerOpgeslagenPolis(polisId, polis);
    //
    //        //        polis.setBedrijf("3");
    //
    //        controleerOpgeslagenPolis(polisId, polis);
    //
    //        polis.setVerzekerdeZaak(UUID.randomUUID().toString());
    //
    //        controleerOpgeslagenPolis(polisId, polis);
    //
    //        polis.setKenmerk(UUID.randomUUID().toString());
    //
    //        controleerOpgeslagenPolis(polisId, polis);
    //
    //        polis.setBetaalfrequentie("Jaar");
    //
    //        controleerOpgeslagenPolis(polisId, polis);
    //
    //        polis.setProlongatieDatum(LocalDateTime.now().toString(datumFormaat));
    //
    //        controleerOpgeslagenPolis(polisId, polis);
    //    }

    //    private void controleerOpgeslagenPolis(Long id, JsonPolis polis) {
    //        polisClient.opslaan(polis, ingegelogdeGebruiker, trackAndTraceId, "");
    //
    //        JsonPolis p = polisClient.lees(id.toString());
    //
    //        System.out.println(ReflectionToStringBuilder.toString(polis, ToStringStyle.SHORT_PREFIX_STYLE));
    //        System.out.println(ReflectionToStringBuilder.toString(p, ToStringStyle.SHORT_PREFIX_STYLE));
    //
    //        assertThat(p, is(polis));
    //    }
}
