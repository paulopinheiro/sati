package br.jus.trt12.paulopinheiro.sati.backup.model;

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
@Table(name = "fita_dados", catalog = "sati", schema = "backup")
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue("D")
@NamedQueries(value={
    @NamedQuery(name="FitaDados.findAtivasByMunicipio", query="select f from FitaDados f where f.dataBaixa is not null and f.conjuntoFita.municipio = :municipio order by f.serie")
})
public class FitaDados extends Fita implements Serializable {
    private static final long serialVersionUID = 1L;
    private String capacidade;
    @Column(name="maximo_gravacoes")
    private Integer maximoGravacoes;
    @JoinColumn(name="categoria_id")
    @ManyToOne
    private CategoriaFitaDados categoriaFitaDados;
    @JoinColumn (name="conjunto_fitas_id")
    @ManyToOne
    private ConjuntoFitas conjuntoFita;

    public String getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(String capacidade) {
        this.capacidade = capacidade;
    }

    public CategoriaFitaDados getCategoriaFitaDados() {
        return categoriaFitaDados;
    }

    public void setCategoriaFitaDados(CategoriaFitaDados categoriaFitaDados) {
        this.categoriaFitaDados = categoriaFitaDados;
    }

    public ConjuntoFitas getConjuntoFita() {
        return conjuntoFita;
    }

    public void setConjuntoFita(ConjuntoFitas conjuntoFita) {
        this.conjuntoFita = conjuntoFita;
    }

    public Integer getMaximoGravacoes() {
        return maximoGravacoes;
    }

    public void setMaximoGravacoes(Integer maximoGravacoes) {
        this.maximoGravacoes = maximoGravacoes;
    }

    public boolean isConsistente() {
        if ((this.getConjuntoFita()!=null)&&(!this.isAtiva())) return false;
        else return true;
    }

    public String getStatus() {
        if (this.isAtiva()) {
            if (this.getConjuntoFita()== null) return "ociosa";
            else return "em uso";
        } else {
            if (this.getConjuntoFita()==null) return "baixada";
            else return "inconsistente";
        }
    }

    @Override
    public String toString() {
        return "Fita " + this.getConjuntoFita().getDesignacaoFita()
                       + " - " + this.getSerie();
    }
}
