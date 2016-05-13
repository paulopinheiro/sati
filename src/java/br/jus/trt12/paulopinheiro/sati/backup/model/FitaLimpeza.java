package br.jus.trt12.paulopinheiro.sati.backup.model;

import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "fita_limpeza", catalog = "sati", schema = "backup")
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue("L")
@NamedQueries(value={
    @NamedQuery(name="FitaLimpeza.findAtivasByMunicipio", query="select f from FitaLimpeza f where f.dataBaixa is not null and f.municipio = :municipio order by f.serie")
})
public class FitaLimpeza extends Fita implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Column(name="maximo_limpezas")
    private Integer maximoLimpezas;
    @JoinColumn(name="municipio_id")
    @ManyToOne
    private Municipio municipio;

    public Integer getMaximoLimpezas() {
        return maximoLimpezas;
    }

    public void setMaximoLimpezas(Integer maximoLimpezas) {
        this.maximoLimpezas = maximoLimpezas;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    @Override
    public String toString() {
        return "Fita limpeza " + this.getSerie();
    }

    @Override
    public int compareTo(Object o) {
        FitaLimpeza outra = (FitaLimpeza) o;
        if (this.getMunicipio()!=outra.getMunicipio()) return this.getMunicipio().compareTo(outra.getMunicipio());
        return super.compareTo((Fita)outra);
    }

}
