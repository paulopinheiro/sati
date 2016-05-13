package br.jus.trt12.paulopinheiro.sati.redes.model;

import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
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
@Table(name = "rack", catalog = "sati", schema = "redes")
@NamedQueries({
    @NamedQuery(name="Rack.racksByMunicipio", query = "Select r from Rack r where r.municipio = :municipio order by r.identificacao"),
    @NamedQuery(name="Rack.rackByIdentificacaoMunicipio", query = "Select r from Rack r where r.municipio = :municipio and r.identificacao = :identificacao")
})
public class Rack implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="Rack_Gen",sequenceName="redes.rack_codigo_seq",allocationSize=1)
    @GeneratedValue(generator="Rack_Gen")
    private Long codigo;
    @Basic(optional= false)
    private String identificacao;
    @Basic(optional= false)
    private String localizacao;
    private String descricao;
    private String observacao;
    @OneToMany(mappedBy = "rack")
    private List<Panel> panels;
    @ManyToOne(optional= false)
    @JoinColumn(name="cod_municipio")
    private Municipio municipio;

    public Rack() {}

    public Rack(Municipio municipio) {
        this.municipio=municipio;
    }

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

    public List<Panel> getPanels() {
        return panels;
    }

    public void setPanels(List<Panel> panels) {
        this.panels = panels;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
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
        if (!(object instanceof Rack)) {
            return false;
        }
        Rack other = (Rack) object;
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
        Rack outro = (Rack) o;
        if ((this==null)||(outro==null)) return 0;
        if ((this.getMunicipio()!=null)&&(outro.getMunicipio()!=null)) {
            if (!this.getMunicipio().equals(outro.getMunicipio()))
                return this.getMunicipio().compareTo(outro.getMunicipio());
        }
        Collator col = Collator.getInstance(Locale.getDefault());
        if ((this.getIdentificacao()!=null)&&(outro.getIdentificacao()!=null))
            return col.compare(this.getIdentificacao().toUpperCase(), outro.getIdentificacao().toUpperCase());
        return 0;
    }
}
