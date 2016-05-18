package br.jus.trt12.paulopinheiro.sati.geral.model;

import java.io.Serializable;
import java.text.Collator;
import java.util.Locale;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "areati", catalog = "sati", schema = "public")
@NamedQueries({
    @NamedQuery(name="AreaTI.todasAreasTI", query = "Select a from AreaTI a order by a.nome")
})
public class AreaTI implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="AreaTI_Gen",sequenceName="areati_codigo_seq",allocationSize=1)
    @GeneratedValue(generator="AreaTI_Gen")
    private Integer codigo;
    @Basic(optional = false)
    private String nome;
    @JoinColumn(name = "cod_municipiosede")
    @OneToOne
    private Municipio municipioSede;

    public AreaTI() {}

    public AreaTI(String nome) {
        this.nome = nome;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Municipio getMunicipioSede() {
        return municipioSede;
    }

    public void setMunicipioSede(Municipio municipioSede) {
        this.municipioSede = municipioSede;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AreaTI)) {
            return false;
        }
        AreaTI other = (AreaTI) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getNome();
    }

    @Override
    public int compareTo(Object o) {
        AreaTI outra = (AreaTI) o;
        Collator col = Collator.getInstance(Locale.getDefault());
        return col.compare(this.getNome().toUpperCase(), outra.getNome().toUpperCase());
    }

}
