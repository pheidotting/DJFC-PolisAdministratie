package nl.lakedigital.djfc.repository;

import nl.lakedigital.djfc.domain.Schade;
import nl.lakedigital.djfc.domain.SoortSchade;
import nl.lakedigital.djfc.domain.StatusSchade;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-unittest.xml")
public class SchadeRepositoryTest {
    @Inject
    private SchadeRepository repository;

    @Test
    public void testSoortenSchade() {
        SoortSchade soortSchade1 = new SoortSchade();
        soortSchade1.setIngebruik(true);
        soortSchade1.setOmschrijving("bab");

        SoortSchade soortSchade2 = new SoortSchade();
        soortSchade2.setIngebruik(false);
        soortSchade2.setOmschrijving("ccc");

        SoortSchade soortSchade3 = new SoortSchade();
        soortSchade3.setIngebruik(true);
        soortSchade3.setOmschrijving("deb");

        repository.getTransaction().begin();
        repository.getSession().persist(soortSchade1);
        repository.getSession().persist(soortSchade2);
        repository.getSession().persist(soortSchade3);
        repository.getTransaction().commit();

        assertEquals(2, repository.soortenSchade().size());
        assertEquals(2, repository.soortenSchade("b").size());
        assertEquals(1, repository.soortenSchade("a").size());
        assertEquals(1, repository.soortenSchade("e").size());
        assertEquals(0, repository.soortenSchade("c").size());

        List<SoortSchade> soortenSchade = newArrayList(soortSchade1, soortSchade2, soortSchade3);

        repository.verwijderSoortenSchade(soortenSchade);

        AuditReader reader = AuditReaderFactory.get(repository.getSession());

        repository.getTransaction().begin();

        List<Number> revisions = reader.getRevisions(SoortSchade.class, soortSchade1.getId());

        assertThat(revisions.size(), is(2));

        repository.getTransaction().commit();
    }

    @Test
    public void getStatussen() {
        StatusSchade statusSchade1 = new StatusSchade();
        statusSchade1.setStatus("status1");
        statusSchade1.setIngebruik(true);
        StatusSchade statusSchade2 = new StatusSchade();
        statusSchade2.setStatus("status2");
        statusSchade2.setIngebruik(true);
        StatusSchade statusSchade3 = new StatusSchade();
        statusSchade3.setStatus("status3");
        statusSchade3.setIngebruik(true);

        repository.getTransaction().begin();
        repository.getSession().persist(statusSchade1);
        repository.getSession().persist(statusSchade2);
        repository.getSession().persist(statusSchade3);
        repository.getTransaction().commit();

        assertEquals(3, repository.getStatussen().size());
        assertEquals(statusSchade1, repository.getStatussen("status1"));
        assertEquals(statusSchade2, repository.getStatussen("status2"));
        assertEquals(statusSchade3, repository.getStatussen("status3"));

        repository.verwijderStatusSchade(newArrayList(statusSchade1, statusSchade2, statusSchade3));
    }

    @Test
    public void zoekOpSchadeNummerMaatschappij() {
        StatusSchade statusSchade = maakStatusSchade("status");

        repository.getTransaction().begin();
        repository.getSession().persist(statusSchade);
        repository.getTransaction().commit();

        Schade schade1 = maakSchade("schadeNummerMaatschappij1", statusSchade, 1L);
        Schade schade2 = maakSchade("schadeNummerMaatschappij2", statusSchade, 1L);

        repository.opslaan(newArrayList(schade1, schade2));

        assertEquals(schade1.getId(), repository.zoekOpSchadeNummerMaatschappij("schadeNummerMaatschappij1").getId());
        assertEquals(schade2.getId(), repository.zoekOpSchadeNummerMaatschappij("schadeNummerMaatschappij2").getId());

        repository.verwijder(newArrayList(schade1, schade2));
    }

    //    @Test
    //    public void alleSchadesBijRelatie() {
    //        Polis polis1 = new FietsVerzekering();
    //        Polis polis2 = new FietsVerzekering();
    //
    //        StatusSchade statusSchade = maakStatusSchade("status");
    //
    //        repository.getTransaction().begin();
    //        polis1.setMaatschappij(1L);
    //        polis2.setMaatschappij(2L);
    //
    //        repository.getSession().persist(statusSchade);
    //
    //        repository.getSession().persist(polis1);
    //        repository.getSession().persist(polis2);
    //
    //        polis1.setRelatie(1L);
    //        polis2.setRelatie(2L);
    //
    //        repository.getSession().merge(polis1);
    //        repository.getSession().merge(polis2);
    //
    //        repository.getTransaction().commit();
    //
    //        Schade schade1 = maakSchade("schadeNummer1", statusSchade);
    //        Schade schade2 = maakSchade("schadeNummer2", statusSchade);
    //        Schade schade3 = maakSchade("schadeNummer3", statusSchade);
    //
    //        schade1.setPolis(polis1.getId());
    //        polis1.getSchades().add(schade1);
    //
    //        schade2.setPolis(polis2.getId());
    //        polis2.getSchades().add(schade2);
    //
    //        schade3.setPolis(polis2.getId());
    //        polis2.getSchades().add(schade3);
    //
    //        repository.opslaan(schade1);
    //        repository.opslaan(schade2);
    //        repository.opslaan(schade3);
    //
    //        assertEquals(1, repository.alleSchadesBijRelatie(1L).size());
    //        assertEquals(2, repository.alleSchadesBijRelatie(2L).size());
    //
    //    }

    @Test
    public void opslaanEnVerwijderen() {
        assertThat(repository.alleSchades(1L).size(), is(0));

        StatusSchade statusSchade = maakStatusSchade("a");

        repository.opslaan(statusSchade);

        Schade schade1 = maakSchade("1", statusSchade, 1L);
        Schade schade2 = maakSchade("2", statusSchade, 1L);

        repository.opslaan(schade1);

        assertThat(repository.alleSchades(1L).size(), is(1));

        repository.opslaan(schade2);

        assertThat(repository.alleSchades(1L).size(), is(2));

        repository.verwijder(schade1);

        assertThat(repository.alleSchades(1L).size(), is(1));

        repository.verwijder(schade2);

        assertThat(repository.alleSchades(1L).size(), is(0));
        repository.verwijderStatusSchade(newArrayList(statusSchade));
    }

    @Test
    public void zoekOpPolisNummer() {
        assertThat(repository.alleSchades(1L).size(), is(0));

        StatusSchade statusSchade = maakStatusSchade("a");

        repository.opslaan(statusSchade);

        Schade schade1 = maakSchade("1", statusSchade, 1L);
        Schade schade2 = maakSchade("2", statusSchade, 2L);

        repository.opslaan(newArrayList(schade1, schade2));

        assertThat(repository.alleSchades(1L).size(), is(1));
        assertThat(repository.alleSchades(2L).size(), is(1));

        repository.verwijder(newArrayList(schade1, schade2));
        repository.verwijderStatusSchade(newArrayList(statusSchade));
    }

    private Schade maakSchade(String schadeNummer, StatusSchade statusSchade, Long polis) {
        Schade schade1 = new Schade();
        schade1.setSchadeNummerMaatschappij(schadeNummer);
        schade1.setStatusSchade(statusSchade);
        schade1.setDatumTijdSchade(new LocalDateTime());
        schade1.setDatumTijdMelding(new LocalDateTime());
        schade1.setPolis(polis);

        return schade1;
    }

    private StatusSchade maakStatusSchade(String status) {
        StatusSchade statusSchade = new StatusSchade();
        statusSchade.setStatus(status);
        statusSchade.setIngebruik(true);

        return statusSchade;
    }
}
