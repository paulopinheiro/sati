package br.jus.trt12.paulopinheiro.sati.backup.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name = "fita", catalog = "sati", schema = "backup")
@DiscriminatorColumn(name="dtype")
public abstract class Fita implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="Fita_Gen",sequenceName="backup.fita_id_seq",allocationSize=1)
    @GeneratedValue(generator="Fita_Gen")
    private Long id;
    private String serie;
    @Column(name="marca_modelo")
    private String marcaModelo;
    @Column(name="data_inicio_uso")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataInicioUso;
    @Column(name="data_baixa")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataBaixa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataBaixa() {
        return dataBaixa;
    }

    public void setDataBaixa(Date dataBaixa) {
        this.dataBaixa = dataBaixa;
    }

    public Date getDataInicioUso() {
        return dataInicioUso;
    }

    public void setDataInicioUso(Date dataInicioUso) {
        this.dataInicioUso = dataInicioUso;
    }

    public String getMarcaModelo() {
        return marcaModelo;
    }

    public void setMarcaModelo(String marcaModelo) {
        this.marcaModelo = marcaModelo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public boolean isAtiva() {
        return this.getDataBaixa()!=null;
    }

    public boolean isDatasConsistentes() {
        if ((this.getDataInicioUso()!=null)&&(this.getDataBaixa()!=null))
            return (this.getDataBaixa().after(this.getDataInicioUso()));
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fita)) {
            return false;
        }
        Fita other = (Fita) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Fita " + this.getSerie();
    }

    @Override
    public int compareTo(Object o) {
        FitaLimpeza outra = (FitaLimpeza) o;
        if (this.isAtiva()!=outra.isAtiva()) return Boolean.valueOf(this.isAtiva()).compareTo(Boolean.valueOf(outra.isAtiva()));
        if (this.getDataInicioUso()!=outra.getDataInicioUso()) {
            if (this.getDataInicioUso()!=null) return this.getDataInicioUso().compareTo(outra.getDataInicioUso());
            else return outra.getDataInicioUso().compareTo(this.getDataInicioUso());
        }
        return this.getSerie().compareTo(outra.getSerie());
    }
}
