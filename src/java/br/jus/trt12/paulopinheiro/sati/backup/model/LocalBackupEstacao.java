package br.jus.trt12.paulopinheiro.sati.backup.model;

import br.jus.trt12.paulopinheiro.sati.equipamentos.model.Equipamento;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "local_backup_estacao", catalog = "sati", schema = "backup")
public class LocalBackupEstacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="LocalBackupEstacao_Gen",sequenceName="backup.local_backup_estacao_id_seq",allocationSize=1)
    @GeneratedValue(generator="LocalBackupEstacao_Gen")
    private Long id;
    private String diretorio;
    @JoinColumn(name="backup_estacao_id")
    @ManyToOne
    private BackupEstacao backup;
    @ManyToOne
    private Equipamento equipamento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiretorio() {
        return diretorio;
    }

    public void setDiretorio(String diretorio) {
        this.diretorio = diretorio;
    }

    public BackupEstacao getBackup() {
        return backup;
    }

    public void setBackup(BackupEstacao backup) {
        this.backup = backup;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LocalBackupEstacao)) {
            return false;
        }
        LocalBackupEstacao other = (LocalBackupEstacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getEquipamento() + "(" + this.getDiretorio() + ")";
    }
    
}
