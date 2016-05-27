package br.jus.trt12.paulopinheiro.sati.geral.model;

import br.jus.trt12.paulopinheiro.sati.certificadodigital.model.Certificado;
import java.io.Serializable;
import java.text.Collator;
import java.util.List;
import java.util.Locale;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "usuario_final", catalog = "sati", schema = "public")
@NamedQueries({
    @NamedQuery(name = "UsuarioFinal.findByMunicipio", query = "SELECT u FROM UsuarioFinal u WHERE u.unidade.municipio = :municipio order by u.nome"),
    @NamedQuery(name = "UsuarioFinal.findByUnidade", query = "SELECT u FROM UsuarioFinal u WHERE u.unidade = :unidade order by u.nome")
})
public class UsuarioFinal implements Serializable, Comparable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "UsuarioFinal_Gen", sequenceName = "usuario_final_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "UsuarioFinal_Gen")
    private Integer id;
    @Basic(optional = false)
    private String nome;
    private String email;
    private String matricula;
    private String fonecontato;
    private String observacao;
    private boolean ativo;
    @JoinColumn(name = "cod_unidade")
    @ManyToOne(optional = false)
    private Unidade unidade;
    @OneToMany(mappedBy = "usuario")
    private List<Certificado> certificados;

    public UsuarioFinal() {
        this.ativo = true;
    }

    public UsuarioFinal(String nome, Unidade unidade) {
        this.nome = nome;
        this.unidade = unidade;
        this.ativo = true;
    }

    public UsuarioFinal(Unidade unidade) {
        this.unidade=unidade;
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

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public List<Certificado> getCertificados() {
        return certificados;
    }

    public void setCertificados(List<Certificado> certificados) {
        this.certificados = certificados;
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
        if (!(object instanceof UsuarioFinal)) {
            return false;
        }
        UsuarioFinal other = (UsuarioFinal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getNome() + " (" + this.getUnidade() + ")";
    }

    @Override
    public int compareTo(Object o) {
        UsuarioFinal outro = (UsuarioFinal) o;
        if ((this.getUnidade() == null) || outro.getUnidade() == null) return 0;
        if ((this.getNome() == null) || (outro.getNome() == null)) return 0;

        if (!this.getUnidade().equals(outro.getUnidade())) {
            return this.getUnidade().compareTo(outro.getUnidade());
        }
        Collator col = Collator.getInstance(Locale.getDefault());
        return col.compare(this.getNome(), outro.getNome());
    }
}
