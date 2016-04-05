package nl.lakedigital.djfc.repository;

import nl.lakedigital.djfc.domain.Polis;
import nl.lakedigital.djfc.domain.SoortEntiteit;
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

@Repository
public class PolisRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(PolisRepository.class);

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    protected Transaction getTransaction() {
        Transaction transaction = getSession().getTransaction();
        if (transaction.getStatus() != TransactionStatus.ACTIVE) {
            transaction.begin();
        }

        return transaction;
    }

    public void verwijder(Polis polis) {
        List<Polis> polissen = new ArrayList();
        polissen.add(polis);

        verwijder(polissen);
    }

    public void verwijder(List<Polis> polissen) {
            getTransaction().begin();

        for (Polis polis : polissen) {
            getSession().delete(polis);
        }

        getTransaction().commit();
    }

    public void opslaan(Polis polis) {
        List<Polis> polissen=new ArrayList();
        polissen.add(polis);

        opslaan(polissen);
    }
    public void opslaan(List<Polis> polissen) {
        if (getTransaction().getStatus() != TransactionStatus.ACTIVE) {
            getTransaction().begin();
        }

        for (Polis t : polissen) {
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

    public Polis lees(Long id) {
        getTransaction().begin();

        Polis t = getSession().get(Polis.class, id);

        getTransaction().commit();

        return t;
    }

    public Polis zoekOpPolisNummer(String PolisNummer) {
        getTransaction().begin();

        Query query = getSession().getNamedQuery("Polis.zoekOpPolisNummer");
        query.setParameter("polisNummer", PolisNummer);

        Polis polis = (Polis) query.uniqueResult();

        getTransaction().commit();

        return polis;
    }

    public List<Polis> alles(SoortEntiteit soortEntiteit, Long entiteitId) {
        getTransaction().begin();

        String queryString= null;
        if(soortEntiteit==SoortEntiteit.RELATIE){
            queryString="select p from Polis p where relatie = "+entiteitId;
        }else if(soortEntiteit==SoortEntiteit.BEDRIJF){
            queryString="select p from Polis p where bedrijf = "+entiteitId;
        }

        Query query = getSession().createQuery(queryString);

        List<Polis> lijst = query.list();

        getTransaction().commit();

        return lijst;
    }
}
