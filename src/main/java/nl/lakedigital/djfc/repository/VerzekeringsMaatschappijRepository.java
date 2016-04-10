package nl.lakedigital.djfc.repository;

import nl.lakedigital.djfc.domain.VerzekeringsMaatschappij;
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

import java.util.List;

@Repository
public class VerzekeringsMaatschappijRepository {
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

    public void opslaan(VerzekeringsMaatschappij verzekeringsMaatschappij) {
        getTransaction().begin();

        LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(verzekeringsMaatschappij, ToStringStyle.SHORT_PREFIX_STYLE));
        if (verzekeringsMaatschappij.getId() == null) {
            getSession().save(verzekeringsMaatschappij);
        } else {
            getSession().merge(verzekeringsMaatschappij);
        }

        getTransaction().commit();
    }

    public VerzekeringsMaatschappij zoekOpNaam(String naam) {
        getTransaction().begin();

        Query query = getSession().getNamedQuery("VerzekeringsMaatschappij.zoekOpNaam");
        query.setParameter("naam", naam);

        VerzekeringsMaatschappij result = (VerzekeringsMaatschappij) query.uniqueResult();

        getTransaction().commit();

        return result;
    }

    public List<VerzekeringsMaatschappij> alles() {
        getTransaction().begin();

        Query query = getSession().getNamedQuery("VerzekeringsMaatschappij.zoekAlles");
        List<VerzekeringsMaatschappij> result = query.list();

        getTransaction().commit();

        return result;
    }
}

