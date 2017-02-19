package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.request.PolisOpslaanRequest;
import nl.lakedigital.as.messaging.response.PolisOpslaanResponse;
import nl.lakedigital.djfc.domain.Polis;
import nl.lakedigital.djfc.messaging.mapper.DomainPolisNaarMessagingPolisMapper;
import nl.lakedigital.djfc.messaging.mapper.MessagingPolisNaarDomainPolisMapper;
import nl.lakedigital.djfc.service.PolisService;
import nl.lakedigital.djfc.service.envers.SessieHolder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

public class PolisOpslaanRequestReciever extends AbstractReciever<PolisOpslaanRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PolisOpslaanRequestReciever.class);

    @Inject
    private PolisService polisService;
    @Inject
    private List<nl.lakedigital.djfc.domain.Polis> polissen;

    public PolisOpslaanRequestReciever() {
        super(PolisOpslaanRequest.class, LOGGER);
    }

    @Override
    public void verwerkMessage(PolisOpslaanRequest polisOpslaanRequest) {
        List<Polis> polissenOpslaan = polisOpslaanRequest.getPolissen().stream().map(new MessagingPolisNaarDomainPolisMapper(polisService, polissen))//
                .map(polis -> {
                    LOGGER.debug(ReflectionToStringBuilder.toString(polis));
                    return polis;
                }).collect(Collectors.toList());

        polisService.opslaan(polissenOpslaan);

        if (replyTo != null) {
            PolisOpslaanResponse polisOpslaanResponse = new PolisOpslaanResponse();
            polisOpslaanResponse.setAntwoordOp(polisOpslaanRequest);
            polisOpslaanResponse.setPolissen(polissenOpslaan.stream().map(new DomainPolisNaarMessagingPolisMapper()).collect(Collectors.toList()));

            try {
                setupMessageQueueConsumer();

                polisOpslaanResponse.setTrackAndTraceId(SessieHolder.get().getTrackAndTraceId());
                polisOpslaanResponse.setIngelogdeGebruiker(SessieHolder.get().getIngelogdeGebruiker());

                JAXBContext jaxbContext = JAXBContext.newInstance(PolisOpslaanResponse.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                StringWriter sw = new StringWriter();

                jaxbMarshaller.marshal(polisOpslaanResponse, sw);

                TextMessage message = session.createTextMessage(sw.toString());

                LOGGER.debug("Verzenden message {}", message.getText());
                this.replyProducer.send(replyTo, message);
            } catch (JMSException | JAXBException e) {
                LOGGER.error("{}", e);
            }
        }

    }
}