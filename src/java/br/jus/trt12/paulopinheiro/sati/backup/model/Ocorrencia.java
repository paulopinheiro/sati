package br.jus.trt12.paulopinheiro.sati.backup.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name = "ocorrencia", catalog = "sati", schema = "backup")
@DiscriminatorColumn(name="dtype")
public class Ocorrencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="Ocorrencia_Gen",sequenceName="backup.ocorrencia_id_seq",allocationSize=1)
    @GeneratedValue(generator="Ocorrencia_Gen")
    private Long id;
    @Column(name="data_ocorrencia")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataOcorrencia;
    private String observacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(Date dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getDescricao() {
        return "Ocorrência número " + this.getId();
    }
    
    public Boolean isProblema() {
        return false;
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
        if (!(object instanceof Ocorrencia)) {
            return false;
        }
        Ocorrencia other = (Ocorrencia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("E, dd/MM/yyyy");
        return this.getDescricao() + " em " + sdf.format(this.getDataOcorrencia());
    }
}
