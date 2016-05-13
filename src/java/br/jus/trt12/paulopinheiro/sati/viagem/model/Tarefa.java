package br.jus.trt12.paulopinheiro.sati.viagem.model;

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
@Table(name = "tarefa", catalog = "sati", schema = "viagem")
@NamedQueries({
    @NamedQuery(name="Tarefa.tarefasByViagem", query = "Select t from Tarefa t where t.viagem = :viagem order by t.tomboequip, t.codigo")
})
public class Tarefa implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="Tarefa_Gen", sequenceName="viagem.tarefa_codigo_seq",allocationSize=1)
    @GeneratedValue(generator="Tarefa_Gen")
    private Long codigo;
    private String tomboequip;
    @Basic(optional = false)
    private String descricao;
    private String nroTarefaRITM;
    @ManyToOne(optional = false)
    @JoinColumn(name="cod_viagem")
    private Viagem viagem;

    public Tarefa() {
    }

    public Tarefa(String tomboequip, String descricao, String nroTarefaRITM, Viagem viagem) {
        this.tomboequip = tomboequip;
        this.descricao = descricao;
        this.nroTarefaRITM = nroTarefaRITM;
        this.viagem = viagem;
    }

    public Tarefa(Viagem viagem) {
        this.viagem = viagem;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNroTarefaRITM() {
        return nroTarefaRITM;
    }

    public void setNroTarefaRITM(String nroTarefaRITM) {
        this.nroTarefaRITM = nroTarefaRITM;
    }

    public String getTomboequip() {
        return tomboequip;
    }

    public void setTomboequip(String tomboequip) {
        this.tomboequip = tomboequip;
    }

    public Viagem getViagem() {
        return viagem;
    }

    public void setViagem(Viagem viagem) {
        this.viagem = viagem;
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
        if (!(object instanceof Tarefa)) {
            return false;
        }
        Tarefa other = (Tarefa) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getViagem().toString() + " - " + getDescricao();
    }

    @Override
    public int compareTo(Object o) {
        Tarefa outra = (Tarefa) o;
        Collator col = Collator.getInstance(Locale.getDefault());

        if (!this.getViagem().equals(outra.getViagem())) return this.getViagem().compareTo(outra.getViagem());
        if ((this.getTomboequip()!=null)&&(outra.getTomboequip()!=null)&&(!this.getTomboequip().equals(outra.getTomboequip()))) return col.compare(this.getTomboequip(), outra.getTomboequip());
        if ((this.getNroTarefaRITM()!=null)&&(outra.getNroTarefaRITM()!=null)&&(!this.getNroTarefaRITM().equals(outra.getNroTarefaRITM()))) return col.compare(this.getNroTarefaRITM(), outra.getNroTarefaRITM());
        if ((this.getCodigo()!=null)&&(outra.getCodigo()!=null)&&(!this.getCodigo().equals(outra.getCodigo()))) return this.getCodigo().compareTo(outra.getCodigo());
        return col.compare(this.getDescricao(),outra.getDescricao());
    }

}
