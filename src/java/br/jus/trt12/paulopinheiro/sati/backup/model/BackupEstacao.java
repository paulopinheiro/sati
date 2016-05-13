package br.jus.trt12.paulopinheiro.sati.backup.model;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "backup_estacao", catalog = "sati", schema = "backup")
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue("E")
public class BackupEstacao extends Ocorrencia implements Serializable {
    private static final long serialVersionUID = 1L;

    private String detalhes;

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    @Override
    public String getDescricao() {
        return "Backup em estação";
    }
}
