package br.jus.trt12.paulopinheiro.sati.backup.model;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "limpeza", catalog = "sati", schema = "backup")
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue("L")
public class Limpeza extends Ocorrencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @JoinColumn (name="fita_limpeza_id")
    @ManyToOne
    private FitaLimpeza fitaLimpeza;

    public FitaLimpeza getFitaLimpeza() {
        return fitaLimpeza;
    }

    public void setFitaLimpeza(FitaLimpeza fitaLimpeza) {
        this.fitaLimpeza = fitaLimpeza;
    }

    @Override
    public String getDescricao() {
        return "Limpeza (fita: " + this.getFitaLimpeza() + ")";
    }
    
}
