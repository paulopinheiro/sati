package br.jus.trt12.paulopinheiro.sati.geral.model;

import java.io.Serializable;
import java.text.Collator;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "progint", catalog = "sati", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Progint.findAll", query = "SELECT p FROM Progint p order by p.nome"),
    @NamedQuery(name = "Progint.findAtivos", query = "SELECT p FROM Progint p where p.ativo=true order by p.nome")
})
public class Progint implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="Progint_Gen",sequenceName="progint_codigo_seq",allocationSize=1)
    @GeneratedValue(generator="Progint_Gen")
    private Integer codigo;
    @Basic(optional = false)
    private String nome;
    @Basic(optional = false)
    private String email;
    @Basic(optional = false)
    private String matricula;
    private String fonecontato;
    private String observacao;
    private boolean ativo;
    @JoinColumn(name="cod_areati")
    @ManyToOne
    private AreaTI areaAtuacao;
    @JoinColumn(name="cod_unidade")
    @ManyToOne
    private Unidade unidade;

    public Progint() {}

    public Progint(String nome, String email, String matricula) {
        this.nome = nome;
        this.email = email;
        this.matricula = matricula;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getFonecontato() {
        return fonecontato;
    }

    public void setFonecontato(String fonecontato) {
        this.fonecontato = fonecontato;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public AreaTI getAreaAtuacao() {
        return areaAtuacao;
    }

    public void setAreaAtuacao(AreaTI areaAtuacao) {
        this.areaAtuacao = areaAtuacao;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
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
        if (!(object instanceof Progint)) {
            return false;
        }
        Progint other = (Progint) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getNome() + " - " + this.getMatricula();
    }

    @Override
    public int compareTo(Object o) {
        Progint outro = (Progint) o;
        Collator col = Collator.getInstance(Locale.getDefault());
        try {
            return col.compare(this.getNome(), outro.getNome());
        } catch (NullPointerException ex) {
            Logger.getAnonymousLogger().log(Level.ALL, "Deu NullPointerException");
            return 1;
        }
    }

}
