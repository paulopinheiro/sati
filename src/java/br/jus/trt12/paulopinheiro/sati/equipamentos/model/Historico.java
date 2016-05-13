package br.jus.trt12.paulopinheiro.sati.equipamentos.model;

import br.jus.trt12.paulopinheiro.sati.util.Util;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
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
@Table(name = "historico", catalog = "sati", schema = "equipamentos")
@NamedQueries({
    @NamedQuery(name="Historico.findByEquipamento", query="Select h From Historico h where h.equipamento=:equipamento order by h.dataHistorico desc"),
    @NamedQuery(name="Historico.findAll", query="Select h From Historico h order by h.dataHistorico desc")
})
public class Historico implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="Historico_Gen",sequenceName="equipamentos.historico_codigo_seq", allocationSize=1)
    @GeneratedValue(generator="Historico_Gen")
    private Integer codigo;
    @Column(name="data_historico")
    @Temporal(TemporalType.DATE)
    private Date dataHistorico;
    private String descricao;
    private String observacao;
    @Column(name="indicente_ritm")
    private String incidenteRitm;
    @ManyToOne(optional=false)
    @JoinColumn(name="cod_equipamento")
    private Equipamento equipamento;

    public Historico() {}

    public Historico(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Date getDataHistorico() {
        return dataHistorico;
    }

    public void setDataHistorico(Date dataHistorico) {
        this.dataHistorico = dataHistorico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getIncidenteRitm() {
        return incidenteRitm;
    }

    public void setIncidenteRitm(String incidenteRitm) {
        this.incidenteRitm = incidenteRitm;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
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
        if (!(object instanceof Historico)) {
            return false;
        }
        Historico other = (Historico) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return Util.dataString(this.getDataHistorico()) + " - " + this.getDescricao();
    }

    @Override
    public int compareTo(Object o) {
        Historico outro = (Historico) o;
        if ((this.getEquipamento()==null)||outro.getEquipamento()==null) return 0;
        if (!this.getEquipamento().equals(outro.getEquipamento())) return this.getEquipamento().compareTo(outro.getEquipamento());
        return this.getDataHistorico().compareTo(outro.getDataHistorico());
    }
    
}
