package br.jus.trt12.paulopinheiro.sati.redes.model;

import br.jus.trt12.paulopinheiro.sati.geral.model.Unidade;
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
@Table(name = "modulo", catalog = "sati", schema = "redes")
@NamedQueries(
    @NamedQuery(name="Modulo.modulosByMunicipio", query = "Select m from Modulo m where m.unidade.municipio = :municipio order by m.unidade.nome, m.identificacao")
)
public class Modulo implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="Modulo_Gen",sequenceName="redes.modulotelematica_codigo_seq",allocationSize=1)
    @GeneratedValue(generator="Modulo_Gen")
    private Long codigo;
    @Basic(optional = false)
    private String identificacao;
    @Basic(optional = false)
    private String localizacao;
    private String descricao;
    private String observacao;
    @ManyToOne(optional = false)
    @JoinColumn(name="cod_tipomodulo")
    private TipoModulo tipoModulo;
    @ManyToOne(optional = false)
    @JoinColumn(name="cod_unidade")
    private Unidade unidade;

    public Modulo() {}

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
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

    public TipoModulo getTipoModulo() {
        return tipoModulo;
    }

    public void setTipoModulo(TipoModulo tipoModulo) {
        this.tipoModulo = tipoModulo;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
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
        if (!(object instanceof Modulo)) {
            return false;
        }
        Modulo other = (Modulo) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getIdentificacao();
    }

    @Override
    public int compareTo(Object o) {
        Modulo outro = (Modulo) o;
        if ((this==null)||(outro==null)) return 0;
        if ((this.getUnidade()!=null)&&outro.getUnidade()!=null) {
            if (!this.getUnidade().equals(outro.getUnidade()))
                return this.getUnidade().compareTo(outro.getUnidade());
        }
        Collator col = Collator.getInstance(Locale.getDefault());
        if ((this.getIdentificacao()!=null)&&(outro.getIdentificacao()!= null))
            return col.compare(this.getIdentificacao().toUpperCase(), outro.getIdentificacao().toUpperCase());
        else return 0;
    }

}
