package br.jus.trt12.paulopinheiro.sati.redes.model;

import java.io.Serializable;
import java.text.Collator;
import java.util.List;
import java.util.Locale;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "panel", catalog = "sati", schema = "redes")
@NamedQueries({
    @NamedQuery(name="Panel.panelsByMunicipio", query = "Select p from Panel p where p.rack.municipio = :municipio order by p.rack.identificacao, p.nome"),
    @NamedQuery(name="Panel.panelsByRack", query = "Select p from Panel p where p.rack = :rack order by p.rack.identificacao, p.nome"),
    @NamedQuery(name="Panel.panelByRackNomePanel", query = "Select p from Panel p where p.nome = :nome and p.rack = :rack")
})
public class Panel implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="Panel_Gen",sequenceName="redes.panel_codigo_seq",allocationSize=1)
    @GeneratedValue(generator="Panel_Gen")
    private Long codigo;
    @Basic(optional = false)
    private String nome;
    @Basic(optional = false)
    private Integer quantPortas;
    private String descricao;
    private String observacao;
    @ManyToOne(optional = false)
    @JoinColumn(name="cod_rack")
    private Rack rack;
    @OneToMany(mappedBy = "panel", cascade=CascadeType.ALL)
    private List<TomadaPanel> tomadas;

    public Panel() {}

    public Panel(Rack rack) {
        this.rack = rack;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Integer getQuantPortas() {
        return quantPortas;
    }

    public void setQuantPortas(Integer quantPortas) {
        this.quantPortas = quantPortas;
    }

    public Rack getRack() {
        return rack;
    }

    public void setRack(Rack rack) {
        this.rack = rack;
    }

    public List<TomadaPanel> getTomadas() {
        return tomadas;
    }

    public void setTomadas(List<TomadaPanel> tomadas) {
        this.tomadas = tomadas;
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
        if (!(object instanceof Panel)) {
            return false;
        }
        Panel other = (Panel) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNome();
    }

    @Override
    public int compareTo(Object o) {
        Panel outro = (Panel) o;
        if ((this==null)||(outro==null)) return 0;
        if ((this.getRack()!=null)&&(outro.getRack()!=null)) {
            if (!this.getRack().equals(outro.getRack()))
                return this.getRack().compareTo(outro.getRack());
        }
        Collator col = Collator.getInstance(Locale.getDefault());
        if ((this.getNome()!=null)&&(outro.getNome()!=null)) {
            return col.compare(this.getNome().toUpperCase(), outro.getNome().toUpperCase());
        }
        return 0;
    }
}
