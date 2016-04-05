package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.domain.Polis;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.SoortVerzekering;
import nl.lakedigital.djfc.domain.particulier.*;
import nl.lakedigital.djfc.domain.zakelijk.AanhangerVerzekering;
import nl.lakedigital.djfc.domain.zakelijk.GeldVerzekering;
import nl.lakedigital.djfc.repository.PolisRepository;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(EasyMockRunner.class)
public class PolisServiceTest extends EasyMockSupport {
    @TestSubject
    private PolisService polisService = new PolisService();

    @Mock
    private PolisRepository polisRepository;

    @After
    public void after() {
        verifyAll();
    }

    @Test
    public void testBeeindigen() {
        Polis polis = createMock(Polis.class);

        expect(polisRepository.lees(2L)).andReturn(polis);
        polis.setEindDatum(LocalDate.now());
        expectLastCall();

        polisRepository.opslaan(polis);
        expectLastCall();

        replayAll();

        polisService.beeindigen(2L);
    }

    @Test
    public void testOpslaanPolis() {
        final Long id = 58L;

        AutoVerzekering polis = new AutoVerzekering();

        polisRepository.opslaan(polis);
        expectLastCall().andDelegateTo(new PolisRepository() {
            @Override
            public void opslaan(Polis o) {
                o.setId(id);
            }
        });

        expect(polisRepository.lees(id)).andReturn(polis);

        replayAll();

        polisService.opslaan(polis);
    }

    @Test
    public void testZoekOpPolisNummer() {
        CamperVerzekering camperVerzekering = new CamperVerzekering();

        expect(polisRepository.zoekOpPolisNummer("1234")).andReturn(camperVerzekering);

        replayAll();

        assertEquals(camperVerzekering, polisService.zoekOpPolisNummer("1234"));
    }

    @Test
    public void testZoekOpPolisNummerMetException() {
        expect(polisRepository.zoekOpPolisNummer("1234")).andThrow(new NoResultException());

        replayAll();

        assertNull(polisService.zoekOpPolisNummer("1234"));
    }


    @Test
    public void testVerwijder() {
        MobieleApparatuurVerzekering verzekering = new MobieleApparatuurVerzekering();
        expect(polisRepository.lees(1L)).andReturn(verzekering);

        polisRepository.verwijder(verzekering);
        expectLastCall();

        replayAll();

        polisService.verwijder(1L);
    }

    @Test
    public void testAllePolisSoorten() {
        AnnuleringsVerzekering annuleringsVerzekering = new AnnuleringsVerzekering();
        InboedelVerzekering inboedelVerzekering = new InboedelVerzekering();

        AanhangerVerzekering aanhangerVerzekering = new AanhangerVerzekering();
        GeldVerzekering geldVerzekering = new GeldVerzekering();

        List<Polis> polissen = new ArrayList<>();
        polissen.add(annuleringsVerzekering);
        polissen.add(inboedelVerzekering);
        polissen.add(aanhangerVerzekering);
        polissen.add(geldVerzekering);

        List<String> verwachtParticulier = new ArrayList<>();
        verwachtParticulier.add(annuleringsVerzekering.getSchermNaam());
        verwachtParticulier.add(inboedelVerzekering.getSchermNaam());

        List<String> verwachtZakelijk = new ArrayList<>();
        verwachtZakelijk.add(aanhangerVerzekering.getSchermNaam());
        verwachtZakelijk.add(geldVerzekering.getSchermNaam());

        ReflectionTestUtils.setField(polisService, "polissen", polissen);

        replayAll();

        assertEquals(verwachtParticulier, polisService.allePolisSoorten(SoortVerzekering.PARTICULIER));
        assertEquals(verwachtZakelijk, polisService.allePolisSoorten(SoortVerzekering.ZAKELIJK));
    }


    @Test
    public void testAlles() throws Exception {
        SoortEntiteit soortEntiteit = SoortEntiteit.RELATIE;
        Long entiteitId = 44L;

        List<Polis> polissen = new ArrayList<>();

        expect(polisService.alles(soortEntiteit, entiteitId)).andReturn(polissen);

        replayAll();

        assertThat(polisService.alles(soortEntiteit, entiteitId), is(polissen));
    }
}