package nl.lakedigital.djfc.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.envers.Audited;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Audited
@Entity
@Table(name = "SCHADE")
@NamedQueries({@NamedQuery(name = "Schade.zoekOpschadeNummerMaatschappij", query = "select s from Schade s where s.schadeNummerMaatschappij = :schadeNummerMaatschappij"),//
        @NamedQuery(name = "Schade.allesBijPolis", query = "select s from Schade s where s.polis = :polis")})
public class Schade implements Serializable {
    private static final long serialVersionUID = -8340805705038811388L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "POLIS", nullable = true)
    private Long polis;

    @Column(length = 25, name = "SCHADENUMMERMAATSCHAPPIJ", nullable = false)
    private String schadeNummerMaatschappij;

    @Column(length = 25, name = "SCHADENUMMERTUSSENPERSOON")
    private String schadeNummerTussenPersoon;

    @JoinColumn(name = "SOORT", nullable = true)
    @ManyToOne
    private SoortSchade soortSchade;

    @Column(name = "SOORTSCHADEONGEDEFINIEERD", length = 100)
    private String soortSchadeOngedefinieerd;

    @Column(length = 50, name = "LOCATIE")
    private String locatie;

    @JoinColumn(name = "STATUS", nullable = false)
    @ManyToOne
    private StatusSchade statusSchade;

    @Column(name = "DATUMTIJD", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumTijdSchade;

    @Column(name = "DATUMTIJDMELDING", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumTijdMelding;

    @Column(name = "DATUMAFGEHANDELD")
    @Temporal(TemporalType.DATE)
    private Date datumAfgehandeld;

    @AttributeOverride(name = "bedrag", column = @Column(name = "EIGENRISICO"))
    private Bedrag eigenRisico;

    @Column(length = 1000, name = "OMSCHRIJVING")
    private String omschrijving;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPolis() {
        return polis;
    }

    public void setPolis(Long polis) {
        this.polis = polis;
    }

    public String getSchadeNummerMaatschappij() {
        return schadeNummerMaatschappij;
    }

    public void setSchadeNummerMaatschappij(String schadeNummerMaatschappij) {
        this.schadeNummerMaatschappij = schadeNummerMaatschappij;
    }

    public String getSchadeNummerTussenPersoon() {
        return schadeNummerTussenPersoon;
    }

    public void setSchadeNummerTussenPersoon(String schadeNummerTussenPersoon) {
        this.schadeNummerTussenPersoon = schadeNummerTussenPersoon;
    }

    public SoortSchade getSoortSchade() {
        return soortSchade;
    }

    public void setSoortSchade(SoortSchade soortSchade) {
        this.soortSchade = soortSchade;
    }

    public String getSoortSchadeOngedefinieerd() {
        return soortSchadeOngedefinieerd;
    }

    public void setSoortSchadeOngedefinieerd(String soortSchadeOngedefinieerd) {
        this.soortSchadeOngedefinieerd = soortSchadeOngedefinieerd;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public StatusSchade getStatusSchade() {
        return statusSchade;
    }

    public void setStatusSchade(StatusSchade statusSchade) {
        this.statusSchade = statusSchade;
    }

    public LocalDateTime getDatumTijdSchade() {
        return new LocalDateTime(datumTijdSchade);
    }

    public void setDatumTijdSchade(LocalDateTime datumTijdSchade) {
        this.datumTijdSchade = datumTijdSchade.toDate();
    }

    public LocalDateTime getDatumTijdMelding() {
        return new LocalDateTime(datumTijdMelding);
    }

    public void setDatumTijdMelding(LocalDateTime datumTijdMelding) {
        this.datumTijdMelding = datumTijdMelding.toDate();
    }

    public LocalDate getDatumAfgehandeld() {
        if (datumAfgehandeld != null) {
            return

                    new LocalDate(datumAfgehandeld);
        }
        return null;
    }

    public void setDatumAfgehandeld(LocalDate datumAfgehandeld) {
        this.datumAfgehandeld = datumAfgehandeld.toDate();
    }

    public Bedrag getEigenRisico() {
        return eigenRisico;
    }

    public void setEigenRisico(Bedrag eigenRisico) {
        this.eigenRisico = eigenRisico;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Schade)) {
            return false;
        }

        Schade schade = (Schade) o;

        LocalDateTime datumTijdSchade1 =

                getDatumTijdSchade();
        LocalDateTime datumTijdSchade2 = schade.getDatumTijdSchade();
        LocalDateTime datumTijdMelding1 = getDatumTijdMelding();
        LocalDateTime datumTijdMelding2 = schade.getDatumTijdMelding();
        LocalDate datumAfgehandeld1 = getDatumAfgehandeld();
        LocalDate datumAfgehandeld2 = schade.getDatumAfgehandeld();
        return new EqualsBuilder().append(getId(), schade.getId()).append(getPolis(), schade.getPolis()).append(getSchadeNummerMaatschappij(), schade.getSchadeNummerMaatschappij()).append(getSchadeNummerTussenPersoon(), schade.getSchadeNummerTussenPersoon()).append(getSoortSchade(), schade.getSoortSchade()).append(getSoortSchadeOngedefinieerd(), schade.getSoortSchadeOngedefinieerd()).append(getLocatie(), schade.getLocatie()).append(getStatusSchade(), schade.getStatusSchade()).append(datumTijdSchade1, datumTijdSchade2).append(datumTijdMelding1, datumTijdMelding2).append(datumAfgehandeld1, datumAfgehandeld2).append(getEigenRisico(), schade.getEigenRisico()).append(getOmschrijving(), schade.getOmschrijving()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).append(getPolis()).append(getSchadeNummerMaatschappij()).append(getSchadeNummerTussenPersoon()).append(getSoortSchade()).append(getSoortSchadeOngedefinieerd()).append(getLocatie()).append(getStatusSchade()).append(getDatumTijdSchade()).append(getDatumTijdMelding()).append(getDatumAfgehandeld()).append(getEigenRisico()).append(getOmschrijving()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("polis", polis).append("schadeNummerMaatschappij", schadeNummerMaatschappij).append("schadeNummerTussenPersoon", schadeNummerTussenPersoon).append("soortSchade", soortSchade).append("soortSchadeOngedefinieerd", soortSchadeOngedefinieerd).append("locatie", locatie).append("statusSchade", statusSchade).append("datumTijdSchade", datumTijdSchade).append("datumTijdMelding", datumTijdMelding).append("datumAfgehandeld", datumAfgehandeld).append("eigenRisico", eigenRisico).append("omschrijving", omschrijving).toString();
    }
}
