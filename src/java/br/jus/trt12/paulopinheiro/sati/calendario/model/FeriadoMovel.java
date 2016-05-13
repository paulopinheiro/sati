package br.jus.trt12.paulopinheiro.sati.calendario.model;

import br.jus.trt12.paulopinheiro.sati.util.DatasMoveis;
import br.jus.trt12.paulopinheiro.sati.util.Util;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "feriado_movel", catalog = "sati", schema = "calendario")
@PrimaryKeyJoinColumn(name="codigo")
@DiscriminatorValue("M")
@NamedQueries(value={
    @NamedQuery(name="FeriadoMovel.findAll", query="select f from FeriadoMovel f order by f.diasPascoa")
})
public class FeriadoMovel extends Feriado implements Serializable {
    @Column(name="dias_pascoa")
    private int diasPascoa;

    public FeriadoMovel() {}

    @Override
    protected Date dataAno(int ano) {
        return Util.somaData(DatasMoveis.pascoa(ano), this.diasPascoa);
    }

    public int getDiasPascoa() {
        return diasPascoa;
    }

    public void setDiasPascoa(int diasPascoa) {
        this.diasPascoa = diasPascoa;
    }

    @Override
    public int compareTo(Object o) {
        FeriadoMovel outro = (FeriadoMovel) o;
        return Integer.valueOf(this.getDiasPascoa()).compareTo(Integer.valueOf(outro.getDiasPascoa()));
    }

}
