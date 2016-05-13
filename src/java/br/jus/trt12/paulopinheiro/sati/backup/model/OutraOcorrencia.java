package br.jus.trt12.paulopinheiro.sati.backup.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "outra_ocorrencia", catalog = "sati", schema = "backup")
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue("O")
public class OutraOcorrencia extends Ocorrencia implements Serializable {
    private static final long serialVersionUID = 1L;

    private String descricao;

    @Override
    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("E, dd/MM/yyyy");
        return this.getDescricao() + " em " + sdf.format(this.getDataOcorrencia());
    }

}
