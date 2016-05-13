package br.jus.trt12.paulopinheiro.sati.backup.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "falha_hardware", catalog = "sati", schema = "backup")
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue("F")
public class FalhaHardware extends Ocorrencia implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name="detalhes_falha")
    private String detalhesFalha;

    @Override
    public Boolean isProblema() {
        return true;
    }

    @Override
    public String getDescricao() {
        return "Falha de hardware";
    }

    public String getDetalhesFalha() {
        return detalhesFalha;
    }

    public void setDetalhesFalha(String detalhesFalha) {
        this.detalhesFalha = detalhesFalha;
    }
}
