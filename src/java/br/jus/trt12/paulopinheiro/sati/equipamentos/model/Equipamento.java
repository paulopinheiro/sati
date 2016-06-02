package br.jus.trt12.paulopinheiro.sati.equipamentos.model;

import br.jus.trt12.paulopinheiro.sati.geral.model.Unidade;
import br.jus.trt12.paulopinheiro.sati.geral.model.UsuarioFinal;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "equipamento", catalog = "sati", schema = "equipamentos")
@NamedQueries({
    @NamedQuery(name="Equipamento.findByLote", query="Select e From Equipamento e where e.lote=:lote order by e.tombo"),
    @NamedQuery(name="Equipamento.findAll", query="Select e From Equipamento e order by e.unidade.municipio.nome, e.unidade.nome, e.tombo"),
    @NamedQuery(name="Equipamento.findAtivos", query="Select e From Equipamento e where e.ativo=TRUE order by e.unidade.municipio.nome, e.unidade.nome, e.tombo"),
    @NamedQuery(name="Equipamento.findByUnidade", query="Select e From Equipamento e where e.unidade=:unidade order by e.tombo"),
    @NamedQuery(name="Equipamento.findAtivosByUnidade", query="Select e From Equipamento e where e.unidade=:unidade And e.ativo=TRUE order by e.tombo"),
    @NamedQuery(name="Equipamento.findAtivosByMunicipio", query="Select e From Equipamento e where e.unidade.municipio=:municipio And e.ativo=TRUE order by e.tombo"),
    @NamedQuery(name="Equipamento.findAtivosByMunicipioTipoEquipamento", query="Select e From Equipamento e where e.unidade.municipio=:municipio And e.lote.modelo.tipoEquipamento = :tipoEquipamento And e.ativo=TRUE order by e.tombo"),
    @NamedQuery(name="Equipamento.findBaixadosByTipo", query="Select e From Equipamento e where e.ativo=FALSE and e.lote.modelo.tipoEquipamento=:tipoEquipamento order by e.tombo"),
    @NamedQuery(name="Equipamento.findByTombo", query="Select e From Equipamento e WHERE e.tombo=:tombo"),
    @NamedQuery(name="Equipamento.findBaixadoByTombo", query="Select e From Equipamento e where e.ativo=FALSE and e.tombo=:tombo"),
    @NamedQuery(name="Equipamento.findAtivosByTombo", query="SELECT e FROM Equipamento e WHERE UPPER(e.tombo) LIKE :tombo_maiusculo AND e.ativo=true"),
    @NamedQuery(name="Equipamento.findAtivosByTipoEquipamentoTombo", query="SELECT e FROM Equipamento e WHERE UPPER(e.tombo) LIKE :tombo_maiusculo AND e.lote.modelo.tipoEquipamento = :tipoEquipamento AND e.ativo=true"),
    @NamedQuery(name="Equipamento.findAtivosByModeloTombo", query="SELECT e FROM Equipamento e WHERE UPPER(e.tombo) LIKE :tombo_maiusculo AND e.lote.modelo = :modelo AND e.ativo=true"),
    @NamedQuery(name="Equipamento.findAtivosByLoteTombo", query="SELECT e FROM Equipamento e WHERE UPPER(e.tombo) LIKE :tombo_maiusculo AND e.lote = :lote AND e.ativo=true"),

    @NamedQuery(name="Equipamento.findAtivosByTomboMunicipio", query="SELECT e FROM Equipamento e WHERE UPPER(e.tombo) LIKE :tombo_maiusculo AND e.unidade.municipio = :municipio AND e.ativo=true"),
    @NamedQuery(name="Equipamento.findAtivosByTipoEquipamentoTomboMunicipio", query="SELECT e FROM Equipamento e WHERE UPPER(e.tombo) LIKE :tombo_maiusculo AND e.lote.modelo.tipoEquipamento = :tipoEquipamento AND e.unidade.municipio = :municipio AND e.ativo=true"),
    @NamedQuery(name="Equipamento.findAtivosByModeloTomboMunicipio", query="SELECT e FROM Equipamento e WHERE UPPER(e.tombo) LIKE :tombo_maiusculo AND e.lote.modelo = :modelo AND e.unidade.municipio = :municipio AND e.ativo=true"),
    @NamedQuery(name="Equipamento.findAtivosByLoteTomboMunicipio", query="SELECT e FROM Equipamento e WHERE UPPER(e.tombo) LIKE :tombo_maiusculo AND e.lote = :lote AND e.unidade.municipio = :municipio AND e.ativo=true")
    
})
public class Equipamento implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="Equipamento_Gen",sequenceName="equipamentos.equipamento_codigo_seq", allocationSize=1)
    @GeneratedValue(generator="Equipamento_Gen")
    private Integer codigo;
    @Column(nullable=false,length=6)
    private String tombo;
    @JoinColumn(nullable=false, name="cod_lote")
    @ManyToOne
    private Lote lote;
    @ManyToOne
    @JoinTable(name = "equipamentos.equipamento_unidade", joinColumns = {@JoinColumn(name = "cod_equipamento")},
                                                   inverseJoinColumns = {@JoinColumn(name = "cod_unidade")
    })
    private Unidade unidade;
    @Column(name="b_ativo")
    private boolean ativo;
    private String observacao;
    private String localizacao;
    @Column(name="nro_serie")
    private String nroSerie;
    @ManyToOne
    @JoinTable(name = "equipamentos.equipamento_usuario", joinColumns = {@JoinColumn(name = "equipamento_cod")},
                                                   inverseJoinColumns = {@JoinColumn(name = "usuario_id")
    })
    private UsuarioFinal usuarioEquipamento;
    @OneToMany(mappedBy = "equipamento")
    private List<Historico> listaHistoricos;

    public Equipamento() {}
    public Equipamento(Lote lote) {
        this.lote=lote;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public String getTombo() {
        return tombo;
    }

    public void setTombo(String tombo) {
        this.tombo = tombo;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getNroSerie() {
        return nroSerie;
    }

    public void setNroSerie(String nroSerie) {
        this.nroSerie = nroSerie;
    }

    public UsuarioFinal getUsuarioEquipamento() {
        return usuarioEquipamento;
    }

    public void setUsuarioEquipamento(UsuarioFinal usuarioEquipamento) {
        this.usuarioEquipamento = usuarioEquipamento;
    }

    public List<Historico> getListaHistoricos() {
        return listaHistoricos;
    }

    public void setListaHistoricos(List<Historico> listaHistoricos) {
        this.listaHistoricos = listaHistoricos;
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
        if (!(object instanceof Equipamento)) {
            return false;
        }
        Equipamento other = (Equipamento) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if (!this.ativo) return "Baixado: " + this.tombo + " (" + this.getUsuarioEquipamento() + ")";
        if (this.usuarioEquipamento != null) return this.getLote().getModelo() + " tombo " + this.getTombo() + " (" + this.getUsuarioEquipamento().getNome() + ")";
        return this.getLote().getModelo() + " tombo " + this.getTombo();
    }

    @Override
    public int compareTo(Object o) {
        Equipamento outro = (Equipamento)o;
        if ((this.getUnidade()==null)||outro.getUnidade()==null) return 0;
        if (this.getUnidade().equals(outro.getUnidade())) return this.getTombo().compareTo(outro.getTombo());
        return this.getUnidade().compareTo(outro.getUnidade());
    }
}
