package br.jus.trt12.paulopinheiro.sati.equipamentos.model;

import br.jus.trt12.paulopinheiro.sati.util.Util;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "agendamento", catalog = "sati", schema = "equipamentos")
@NamedQueries({
    @NamedQuery(name="Agendamento.findByEquipamento", query="Select a From Agendamento a where a.equipamento=:equipamento order by a.dataAgendamento desc"),
    @NamedQuery(name="Agendamento.findAll", query="Select a From Agendamento a order by a.dataAgendamento desc"),
    @NamedQuery(name="Agendamento.findByIntervaloData", query="Select a From Agendamento a where a.dataAgendamento >= :dataInicio and a.dataAgendamento <= :dataFim order by a.dataAgendamento")
})
public class Agendamento implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="Agendamento_Gen",sequenceName="equipamentos.agendamento_codigo_seq", allocationSize=1)
    @GeneratedValue(generator="Agendamento_Gen")
    private Integer codigo;
    private String descricao;
    @Temporal(TemporalType.DATE)
    private Date dataAgendamento;
    @JoinColumn(nullable=false, name="cod_equipamento")
    @ManyToOne
    private Equipamento equipamento;
    @OneToOne(mappedBy = "agendamento", cascade={CascadeType.MERGE})
    private Manutencao manutencao;

    public Agendamento() {}

    public Agendamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    public Date getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(Date dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Manutencao getManutencao() {
        return manutencao;
    }

    public void setManutencao(Manutencao manutencao) {
        this.manutencao = manutencao;
    }

    public String getStatus() {
        if (getManutencao()==null) return "Em aberto";
        else return "Realizada em " + Util.dataString(getManutencao().getDataManutencao());
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
        if (!(object instanceof Agendamento)) {
            return false;
        }
        Agendamento other = (Agendamento) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Agendamento para " + this.dataAgendamento + ": " +  this.getDescricao();
    }

    @Override
    public int compareTo(Object o) {
        Agendamento outro = (Agendamento) o;
        if (!this.getEquipamento().equals(outro.getEquipamento())) return this.getEquipamento().compareTo(outro.getEquipamento());
        return this.getDataAgendamento().compareTo(outro.getDataAgendamento());
    }

}
