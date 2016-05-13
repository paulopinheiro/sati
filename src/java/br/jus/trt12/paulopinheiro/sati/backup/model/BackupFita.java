package br.jus.trt12.paulopinheiro.sati.backup.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "backup_fita", catalog = "sati", schema = "backup")
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue("B")
public class BackupFita extends Ocorrencia implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer tamanho;
    private Integer tempo;
    @ManyToMany
    @JoinTable(name="backup.backup_fita_fita_dados",
               joinColumns= @JoinColumn(name="backup_fita_id"),
               inverseJoinColumns=@JoinColumn(name="fita_dados_id"))
    private List<FitaDados> fitas;

    public Integer getTamanho() {
        return tamanho;
    }

    public void setTamanho(Integer tamanho) {
        this.tamanho = tamanho;
    }

    public Integer getTempo() {
        return tempo;
    }

    public void setTempo(Integer tempo) {
        this.tempo = tempo;
    }

    public List<FitaDados> getFitas() {
        return fitas;
    }

    public void setFitas(List<FitaDados> fitas) {
        this.fitas = fitas;
    }

    @Override
    public String getDescricao() {
        return "Backup em fita";
    }
}
