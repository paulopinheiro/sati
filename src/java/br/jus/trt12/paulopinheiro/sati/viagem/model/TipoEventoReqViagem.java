package br.jus.trt12.paulopinheiro.sati.viagem.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tipoeventoreqviagem", catalog = "sati", schema = "viagem")
@NamedQueries(
    @NamedQuery(name="TipoEventoReqViagem.todosTiposEventoReqViagem", query = "Select t from TipoEventoReqViagem t order by t.codigo")
)
public class TipoEventoReqViagem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Integer codigo;
    @Basic(optional = false)
    private String descricao;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the codigo fields are not set
        if (!(object instanceof TipoEventoReqViagem)) {
            return false;
        }
        TipoEventoReqViagem other = (TipoEventoReqViagem) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getDescricao();
    }
}
