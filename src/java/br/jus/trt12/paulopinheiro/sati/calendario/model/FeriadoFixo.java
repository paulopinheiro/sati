package br.jus.trt12.paulopinheiro.sati.calendario.model;

import br.jus.trt12.paulopinheiro.sati.util.Util;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "feriado_fixo", catalog = "sati", schema = "calendario")
@PrimaryKeyJoinColumn(name="codigo")
@DiscriminatorValue("F")
@NamedQueries(value={
    @NamedQuery(name="FeriadoFixo.findAll", query="select f from FeriadoFixo f order by f.mes, f.dia")
})
public class FeriadoFixo extends Feriado implements Serializable {
    private int dia;
    private int mes;

    public FeriadoFixo() {}

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    @Override
    public String toString() {
        return this.getDescricao() + " (" + this.getDia()+"/"+this.getMes()+")";
    }

    @Override
    protected Date dataAno(int ano) {
        return Util.dataPura(ano, this.mes, this.dia);
    }

    @Override
    public int compareTo(Object o) {
        FeriadoFixo outro = (FeriadoFixo) o;
        if (this.getMes()!=outro.getMes()) return Integer.valueOf(this.getMes()).compareTo(Integer.valueOf(outro.getMes()));
        return Integer.valueOf(this.getDia()).compareTo(Integer.valueOf(outro.getDia()));
    }
}
