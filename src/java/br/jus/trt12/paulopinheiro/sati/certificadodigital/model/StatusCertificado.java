package br.jus.trt12.paulopinheiro.sati.certificadodigital.model;

import java.io.Serializable;
import java.text.Collator;
import java.util.Locale;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "status_certificado", catalog = "sati", schema = "certificacao")
public class StatusCertificado implements Serializable, Comparable {
    @Id
    private Integer id;
    @Basic(optional=false)
    private String descricao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StatusCertificado other = (StatusCertificado) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getDescricao();
    }

    @Override
    public int compareTo(Object o) {
        StatusCertificado outro = (StatusCertificado) o;
        if ((this.getDescricao()==null)||(outro.getDescricao()==null)) return 0;
        Collator col = Collator.getInstance(Locale.getDefault());
        return col.compare(this.getDescricao().toUpperCase(), outro.getDescricao().toUpperCase());
    }
}
