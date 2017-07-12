package nl.lakedigital.it;

import nl.lakedigital.as.messaging.domain.Polis;
import nl.lakedigital.as.messaging.request.PolisOpslaanRequest;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.messaging.sender.AbstractSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-it.xml")
public class PolisTest {
    private final Long ingegelogdeGebruiker = 46L;
    private final String trackAndTraceId = UUID.randomUUID().toString();

    //    private PolisClient polisClient = new PolisClient();
    private final String datumFormaat = "yyyy-MM-dd";
    @Inject
    private JmsTemplate polisOpslaanRequestTemplate;

    //        <bean id="polisOpslaanRequestDestination" class="org.apache.activemq.command.ActiveMQQueue">
    //        <constructor-arg index="0" value="rb.pa.polisOpslaanRequestQueue"/>
    //    </bean>
    //    <bean id="polisOpslaanRequestTemplate" class="org.springframework.jms.core.JmsTemplate">
    //        <property name="connectionFactory" ref="connectionFactory"/>
    //        <property name="defaultDestination" ref="polisOpslaanRequestDestination"/>
    //    </bean>
    //    <bean id="polisOpslaanRequestSender" class="nl.dias.messaging.sender.PolisOpslaanRequestSender">
    //        <constructor-arg index="0" ref="polisOpslaanRequestTemplate"/>
    //    </bean>


    @Test
    public void test() {

        AbstractSender sender = new AbstractSender<PolisOpslaanRequest, JsonPolis>() {
            @Override
            public PolisOpslaanRequest maakMessage(JsonPolis jsonPolis) {
                System.out.println("abcdef");
                PolisOpslaanRequest polisOpslaanRequest = new PolisOpslaanRequest();
                polisOpslaanRequest.setIngelogdeGebruiker(ingegelogdeGebruiker);
                polisOpslaanRequest.setTrackAndTraceId(trackAndTraceId);

                polisOpslaanRequest.getPolissen().add(new Polis());

                return polisOpslaanRequest;
            }
        };

        sender.setClazz(PolisOpslaanRequest.class);
        sender.setJmsTemplate(polisOpslaanRequestTemplate);
        sender.send(new JsonPolis());

    }
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
