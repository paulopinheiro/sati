package br.jus.trt12.paulopinheiro.sati.backup.model;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "teste_backup", catalog = "sati", schema = "backup")
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue("T")
public class TesteBackup extends Ocorrencia implements Serializable {
    private static final long serialVersionUID = 1L;

    private String detalhes;
    private Boolean sucesso;
    @JoinColumn (name="tipo_teste_backup_id")
    @ManyToOne
    private TipoTesteBackup tipoTeste;
    @JoinColumn (name="backup_fita_id")
    @ManyToOne
    private BackupFita backupFita;

    @Override
    public String getDescricao() {
        return "Teste do backup " + this.getBackupFita();
    }

    public BackupFita getBackupFita() {
        return backupFita;
    }

    public void setBackupFita(BackupFita backupFita) {
        this.backupFita = backupFita;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public Boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(Boolean sucesso) {
        this.sucesso = sucesso;
    }

    public TipoTesteBackup getTipoTeste() {
        return tipoTeste;
    }

    public void setTipoTeste(TipoTesteBackup tipoTeste) {
        this.tipoTeste = tipoTeste;
    }

}
