package br.jus.trt12.paulopinheiro.sati.redes.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tipoconector", catalog = "sati", schema = "redes")
@NamedQueries(
    @NamedQuery(name="TipoConector.todosTiposConector", query = "Select t from TipoConector t order by t.nome")
)
public class TipoConector implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="TipoConector_Gen",sequenceName="redes.tipoconector_codigo_seq",allocationSize=1)
    @GeneratedValue(generator="TipoConector_Gen")
    private Long codigo;
    @Basic(optional = false)
    private String nome;

    public TipoConector() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
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
        if (!(object instanceof TipoConector)) {
            return false;
        }
        TipoConector other = (TipoConector) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNome();
    }

}
