package br.jus.trt12.paulopinheiro.sati.geral.model;

import java.io.Serializable;
import java.text.Collator;
import java.util.Locale;
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
@Table(name = "unidade", catalog = "sati", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Unidade.findAll", query = "SELECT u FROM Unidade u order by u.municipio.nome, u.nome"),
    @NamedQuery(name = "Unidade.findByMunicipio", query = "SELECT u FROM Unidade u where u.municipio = :municipio order by u.nome")
})
public class Unidade implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="Unidade_Gen",sequenceName="unidade_codigo_seq", allocationSize=1)
    @GeneratedValue(generator="Unidade_Gen")
    private Integer codigo;
    @Basic(optional = false)
    private String nome;
    @Basic(optional = false)
    private String sigla;
    @Basic(optional = false)
    private String prefixo;
    private String localizacao;
    private String observacao;
    @JoinColumn(name = "cod_municipio")
    @ManyToOne(optional = false)
    private Municipio municipio;

    public Unidade() {}

    public Unidade(Municipio municipio) {
        this.municipio = municipio;
    }

    public Unidade(String nome, String sigla, String prefixo) {
        this.nome = nome;
        this.sigla = sigla;
        this.prefixo = prefixo;
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

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getPrefixo() {
        return prefixo;
    }

    public void setPrefixo(String prefixo) {
        this.prefixo = prefixo;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
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
        if (!(object instanceof Unidade)) {
            return false;
        }
        Unidade other = (Unidade) object;
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
        Unidade outra = (Unidade) o;
        if (!this.getMunicipio().equals(outra.getMunicipio())) return this.getMunicipio().compareTo(outra.getMunicipio());
        Collator col = Collator.getInstance(Locale.getDefault());
        return col.compare(this.getNome().toUpperCase(), outra.getNome().toUpperCase());
    }

}
