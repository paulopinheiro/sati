package br.jus.trt12.paulopinheiro.sati.certificadodigital.model;

import java.io.Serializable;
import java.text.Collator;
import java.util.Locale;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "marca_etoken", catalog = "sati", schema = "certificacao")
@NamedQueries({
    @NamedQuery(name = "MarcaEtoken.findAll", query = "SELECT m FROM MarcaEtoken m order by m.nome")
})
public class MarcaEtoken implements Serializable, Comparable {
    @Id
    private Integer id;
    @Basic(optional=false)
    private String nome;

    public MarcaEtoken(String nome) {
        this.nome = nome;
    }

    public MarcaEtoken() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return this.nome;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final MarcaEtoken other = (MarcaEtoken) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Object o) {
        MarcaEtoken outro = (MarcaEtoken) o;
        if ((this.getNome()==null)||(outro.getNome()==null)) return 0;
        Collator col = Collator.getInstance(Locale.getDefault());
        return col.compare(this.getNome().toUpperCase(), outro.getNome().toUpperCase());
    }
}
