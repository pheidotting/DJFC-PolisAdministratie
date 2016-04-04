package nl.lakedigital.djfc.domain.particulier;

import nl.lakedigital.djfc.domain.Polis;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.SoortVerzekering;
import org.hibernate.envers.Audited;
import org.springframework.stereotype.Component;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
@Audited

@Component
@Entity
@Table(name = "POLIS")
@DiscriminatorValue(value = "SV")
public class SviSchadeverzekerininzittende extends Polis {
    public SviSchadeverzekerininzittende() {
    }

    @Override
    public SoortVerzekering getSoortVerzekering() {
        return SoortVerzekering.PARTICULIER;
    }

    @Override
    public String getSchermNaam() {
        return "SVI Schadeverzekeringinzittende";
    }

    @Override
    public Polis nieuweInstantie(SoortEntiteit soortEntiteit, Long entiteitId) {
        return new SviSchadeverzekerininzittende(soortEntiteit,entiteitId);
    }

    public SviSchadeverzekerininzittende(SoortEntiteit soortEntiteit, Long entiteitId) {
        super(soortEntiteit, entiteitId);
    }
}
