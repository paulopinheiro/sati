package br.jus.trt12.paulopinheiro.sati.viagem.model;

import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import br.jus.trt12.paulopinheiro.sati.geral.model.Progint;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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
import javax.persistence.Temporal;

@Entity
@Table(name = "viagem", catalog = "sati", schema = "viagem")
@NamedQueries({
    @NamedQuery(name="Viagem.todasViagens", query = "Select v from Viagem v order by v.progint.nome, v.dataViagem desc, v.dataProgramada desc"),
    @NamedQuery(name="Viagem.viagensByProgint", query = "Select v from Viagem v where v.progint = :progint order by v.progint.nome, v.dataViagem desc, v.dataProgramada desc"),
    @NamedQuery(name="Viagem.viagensByDestino", query = "Select v from Viagem v where v.municipioDestino = :municipioDestino order by v.progint.nome, v.dataViagem desc, v.dataProgramada desc"),
    @NamedQuery(name="Viagem.viagensByProgintDestino", query = "Select v from Viagem v where v.progint = :progint and v.municipioDestino = :municipioDestino order by v.progint.nome, v.dataViagem desc, v.dataProgramada desc")
})
public class Viagem implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="Viagem_Gen", sequenceName="viagem.viagem_codigo_seq", allocationSize=1)
    @GeneratedValue(generator="Viagem_Gen")
    private Long codigo;
    @Basic(optional = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataProgramada;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataViagem;
    @ManyToOne(optional=false)
    @JoinColumn(name="cod_progint")
    private Progint progint;
    @ManyToOne(optional=false)
    @JoinColumn(name="cod_municipio_origem")
    private Municipio municipioOrigem;
    @ManyToOne(optional=false)
    @JoinColumn(name="cod_municipio_destino")
    private Municipio municipioDestino;
    @OneToMany (mappedBy="viagem")
    private List<EventoReqViagem> eventos;
    @OneToMany (mappedBy="viagem")
    private List<Tarefa> tarefas;

    //Criar métodos para adicionar, alterar ou excluir um evento
    //Padrão master-detail?
    public Viagem() {}

    public Viagem(Date dataProgramada, Date dataViagem, Progint progint, Municipio municipioOrigem, Municipio municipioDestino) {
        this.dataProgramada = dataProgramada;
        this.dataViagem = dataViagem;
        this.progint = progint;
        this.municipioOrigem = municipioOrigem;
        this.municipioDestino = municipioDestino;
    }

    public Viagem(Progint progint) {
        this.progint = progint;
    }

    public Date getDataProgramada() {
        return dataProgramada;
    }

    public void setDataProgramada(Date dataProgramada) {
        this.dataProgramada = dataProgramada;
    }

    public Date getDataViagem() {
        return dataViagem;
    }

    public void setDataViagem(Date dataViagem) {
        this.dataViagem = dataViagem;
    }

    public Municipio getMunicipioDestino() {
        return municipioDestino;
    }

    public void setMunicipioDestino(Municipio municipioDestino) {
        this.municipioDestino = municipioDestino;
    }

    public Municipio getMunicipioOrigem() {
        return municipioOrigem;
    }

    public void setMunicipioOrigem(Municipio municipioOrigem) {
        this.municipioOrigem = municipioOrigem;
    }

    public Progint getProgint() {
        return progint;
    }

    public void setProgint(Progint progint) {
        this.progint = progint;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public EventoReqViagem getEventoMaisRecente() {
        if ((eventos==null)||(eventos.size()<1)) return null;
        return Collections.max(eventos);
    }

    public List<EventoReqViagem> getEventos() {
        return eventos;
    }

    public void setEventos(List<EventoReqViagem> eventos) {
        this.eventos = eventos;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Viagem)) {
            return false;
        }
        Viagem other = (Viagem) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        Date dataExibicao = null;
        String dataExibicaoStr ="";
        if (getDataViagem()!=null) dataExibicao=getDataViagem();
        else {
            if (getDataProgramada() != null) {
                dataExibicao=getDataProgramada();
                dataExibicaoStr = " (programada)";
            }
        }
        if (dataExibicao==null) return "Erro na exibição dos dados da viagem";
        SimpleDateFormat sdf = new SimpleDateFormat("E, dd/MM/yyyy");
        dataExibicaoStr = sdf.format(dataExibicao) + dataExibicaoStr;
        return getMunicipioDestino().getNome() + " - " + dataExibicaoStr;
    }

    @Override
    public int compareTo(Object o) {
        Viagem outra = (Viagem) o;
        if ((this.getProgint()==null)||(outra.getProgint()==null)) return 0;
        if (!this.getProgint().equals(outra.getProgint())) return this.getProgint().compareTo(outra.getProgint());
        if ((this.getDataViagem()!=null)&&(outra.getDataViagem()!=null)&&(!this.getDataViagem().equals(outra.getDataViagem()))) return outra.getDataViagem().compareTo(this.getDataViagem());
        if ((outra.getDataProgramada()==null) || (this.getDataProgramada()==null)) return 0;
        return outra.getDataProgramada().compareTo(this.getDataProgramada());
    }
}
