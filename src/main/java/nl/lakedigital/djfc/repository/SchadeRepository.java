package nl.lakedigital.djfc.repository;

import nl.lakedigital.djfc.domain.Schade;
import nl.lakedigital.djfc.domain.SoortSchade;
import nl.lakedigital.djfc.domain.StatusSchade;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Repository
public class SchadeRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchadeRepository.class);

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public Transaction getTransaction() {
        Transaction transaction = getSession().getTransaction();
        if (transaction.getStatus() != TransactionStatus.ACTIVE) {
            transaction.begin();
        }

        return transaction;
    }

    public List<SoortSchade> soortenSchade() {
        getTransaction().begin();

        Query query = getSession().getNamedQuery("SoortSchade.alles");

        List<SoortSchade> result = query.list();

        getTransaction().commit();

        return result;
    }

    public List<SoortSchade> soortenSchade(String omschrijving) {
        getTransaction().begin();

        Query query = getSession().getNamedQuery("SoortSchade.zoekOpOmschrijving");
        query.setParameter("omschrijving", "%" + omschrijving + "%");

        List<SoortSchade> result = query.list();

        getTransaction().commit();

        return result;
    }

    public void opslaan(StatusSchade statusSchade) {
        getTransaction().begin();

        LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(statusSchade, ToStringStyle.SHORT_PREFIX_STYLE));
        if (statusSchade.getId() == null) {
            getSession().save(statusSchade);
        } else {
            getSession().merge(statusSchade);
        }

        getTransaction().commit();
    }

    public List<Schade> alleSchades(Long polis) {
        getTransaction().begin();

        Query query = getSession().getNamedQuery("Schade.allesBijPolis");
        query.setParameter("polis", polis);

        List<Schade> result = query.list();

        getTransaction().commit();

        return result;
    }

    public StatusSchade getStatussen(String status) {
        getTransaction().begin();

        Query query = getSession().getNamedQuery("StatusSchade.zoekOpSoort");
        query.setParameter("status", status);

        StatusSchade result = (StatusSchade) query.uniqueResult();

        getTransaction().commit();

        return result;
    }

    public List<StatusSchade> getStatussen() {
        getTransaction().begin();

        Query query = getSession().createQuery("select s from StatusSchade s where s.ingebruik = '1'");

        List<StatusSchade> result = query.list();

        getTransaction().commit();

        return result;
    }

    //    public Schade zoekOpSchadeNummerMaatschappij(String schadeNummerMaatschappij) {
    //        TypedQuery<Schade> query = getEm().createNamedQuery("Schade.zoekOpschadeNummerMaatschappij", Schade.class);
    //        query.setParameter("schadeNummerMaatschappij", schadeNummerMaatschappij);
    //
    //        return query.getSingleResult();
    //    }
    //
    //    public List<Schade> alleSchadesBijRelatie(Long relatie) {
    //        getTransaction().begin();
    //
    //        Query query = getSession().getNamedQuery("Schade.allesVanRelatie");
    //        query.setParameter("relatie", relatie);
    //
    //        List<Schade> result = query.list();
    //
    //        getTransaction().commit();
    //
    //        return result;
    //    }

    //    public List<Schade> allesBijSchade(Long schade) {
    //        TypedQuery<Schade> query = getEm().createNamedQuery("Schade.allesBijSchade", Schade.class);
    //        query.setParameter("schade", schade);
    //
    //        return query.getResultList();
    //    }

    public void verwijder(Schade schade) {
        List<Schade> schadesen = new ArrayList();
        schadesen.add(schade);

        verwijder(schadesen);
    }

    public void verwijder(List<Schade> schades) {
        getTransaction().begin();

        for (Schade schade : schades) {
            getSession().delete(schade);
        }

        getTransaction().commit();
    }

    public void verwijderSoortenSchade(List<SoortSchade> schades) {
        getTransaction().begin();

        for (SoortSchade schade : schades) {
            getSession().delete(schade);
        }

        getTransaction().commit();
    }

    public void verwijderStatusSchade(List<StatusSchade> schades) {
        getTransaction().begin();

        for (StatusSchade schade : schades) {
            getSession().delete(schade);
        }

        getTransaction().commit();
    }

    public void opslaan(Schade schade) {
        opslaan(newArrayList(schade));
    }

    public void opslaan(List<Schade> schades) {
        getTransaction().begin();

        for (Schade t : schades) {
            LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(t, ToStringStyle.SHORT_PREFIX_STYLE));
            if (t.getId() == null) {
                getSession().save(t);
            } else {
                getSession().merge(t);
            }
        }

        getTransaction().commit();
        getSession().close();
    }

    public Schade lees(Long id) {
        getTransaction().begin();

        Schade t = getSession().get(Schade.class, id);

        getTransaction().commit();

        return t;
    }
}
