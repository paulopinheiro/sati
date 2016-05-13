package br.jus.trt12.paulopinheiro.sati.equipamentos.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "manutencao", catalog = "sati", schema = "equipamentos")
@NamedQueries({
    @NamedQuery(name="Manutencao.findMostRecentEquipamento", query="Select m From Manutencao m where m.agendamento.equipamento=:equipamento having m.dataManutencao = max(m.dataManutencao)")
})
public class Manutencao implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="codigo")
    @SequenceGenerator(name="Manutencao_Gen",sequenceName="equipamentos.manutencao_codigo_seq", allocationSize=1)
    @GeneratedValue(generator="Manutencao_Gen")
    private Integer codigo;
    @Column(name="datamanutencao")
    @Temporal(TemporalType.DATE)
    private Date dataManutencao;
    private String observacao;
    @JoinColumn(name="cod_agendamento")
    @OneToOne
    private Agendamento agendamento;
    @Column(name="incidenteritm")
    private String incidenteRitm;

    public Manutencao() {}

    public Manutencao(Agendamento agendamento) {
        this.agendamento = agendamento;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Agendamento getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
    }

    public Date getDataManutencao() {
        return dataManutencao;
    }

    public void setDataManutencao(Date dataManutencao) {
        this.dataManutencao = dataManutencao;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the codigo fields are not set
        if (!(object instanceof Manutencao)) {
            return false;
        }
        Manutencao other = (Manutencao) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Manutenção na data " + this.dataManutencao + ", Equipamento: " + this.agendamento.getEquipamento();
    }

    @Override
    public int compareTo(Object o) {
        Manutencao outra = (Manutencao) o;
        return this.getAgendamento().compareTo(outra);
    }

}
