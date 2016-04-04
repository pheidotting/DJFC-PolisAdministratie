package nl.lakedigital.djfc.domain.zakelijk;

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
@DiscriminatorValue(value = "CV")
public class CarVerzekering extends Polis {
    public CarVerzekering() {
    }

    @Override
    public SoortVerzekering getSoortVerzekering() {
        return SoortVerzekering.ZAKELIJK;
    }

    @Override
    public String getSchermNaam() {
        return this.getSchermNaamDefault(this.getClass().getCanonicalName());
    }

    @Override
    public Polis nieuweInstantie(SoortEntiteit soortEntiteit, Long entiteitId) {
        return new CarVerzekering(soortEntiteit,entiteitId);
    }

    public CarVerzekering(SoortEntiteit soortEntiteit, Long entiteitId) {
        super(soortEntiteit, entiteitId);
    }
}
