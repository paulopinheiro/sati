package br.jus.trt12.paulopinheiro.sati.geral.model;

import java.io.Serializable;
import java.text.Collator;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "municipio", catalog = "sati", schema = "public")
@NamedQueries({
    @NamedQuery(name="Municipio.todosMunicipios", query = "Select m from Municipio m order by m.nome"),
    @NamedQuery(name="Municipio.municipioByNome", query = "Select m from Municipio m where m.nome = :nome order by m.nome"),
    @NamedQuery(name="Municipio.municipiosByAreaTI", query = "Select m from Municipio m where m.areaTI=:areaTI order by m.nome")
})
public class Municipio implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="Municipio_Gen",sequenceName="municipio_codigo_seq", allocationSize=1)
    @GeneratedValue(generator="Municipio_Gen")
    private Integer codigo;
    @Basic(optional = false)
    private String nome;
    @Temporal(TemporalType.DATE)
    private Date dtFundacaoUnidade;
    private String observacao;
    @JoinColumn(name = "cod_areati")
    @ManyToOne(optional = false)
    private AreaTI areaTI;

    public Municipio() {}

    public Municipio(String nome, AreaTI areaTI) {
        this.nome = nome;
        this.areaTI = areaTI;
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

    public Date getDtFundacaoUnidade() {
        return dtFundacaoUnidade;
    }

    public void setDtFundacaoUnidade(Date dtFundacaoUnidade) {
        this.dtFundacaoUnidade = dtFundacaoUnidade;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public AreaTI getAreaTI() {
        return areaTI;
    }

    public void setAreaTI(AreaTI areaTI) {
        this.areaTI = areaTI;
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
        if (!(object instanceof Municipio)) {
            return false;
        }
        Municipio other = (Municipio) object;
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
        Municipio outro = (Municipio) o;
        if (outro==null) return 0;
        Collator col = Collator.getInstance(Locale.getDefault());
        if ((this.getNome()!=null)&&(outro.getNome()!=null))
            return col.compare(this.getNome().toUpperCase(), outro.getNome().toUpperCase());
        return 0;
    }

}
