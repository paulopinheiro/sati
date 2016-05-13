package br.jus.trt12.paulopinheiro.sati.backup.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "designacao_fita", catalog = "sati", schema = "backup")
public class DesignacaoFita implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private String nome;
    @OneToMany(mappedBy = "designacaoFita")
    private List<ConjuntoFitas> conjuntos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<ConjuntoFitas> getConjuntos() {
        return conjuntos;
    }

    public void setConjuntos(List<ConjuntoFitas> conjuntos) {
        this.conjuntos = conjuntos;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DesignacaoFita other = (DesignacaoFita) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 97 * hash + (this.nome != null ? this.nome.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return this.getNome();
    }
}
