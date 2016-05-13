package br.jus.trt12.paulopinheiro.sati.viagem.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
@Table(name = "eventoreqviagem", catalog = "sati", schema = "viagem")
@NamedQueries( {
    @NamedQuery(name="EventoReqViagem.eventosReqViagemByViagem", query = "Select e from EventoReqViagem e where e.viagem = :viagem order by e.dataEvento desc, e.codigo desc"),
    @NamedQuery(name="EventoReqViagem.eventoByViagemTipo", query = "Select e from EventoReqViagem e where e.viagem = :viagem and e.tipoEvento = :tipoEvento")
})
public class EventoReqViagem implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="EventoReqViagem_Gen", sequenceName="viagem.eventoreqviagem_codigo_seq", allocationSize=1)
    @GeneratedValue(generator="EventoReqViagem_Gen")
    private Long codigo;
    @Temporal(TemporalType.DATE)
    private Date dataEvento;
    @Column(name = "observacao")
    private String observacao;
    @ManyToOne
    @JoinColumn(name = "cod_tipoeventoreqviagem")
    private TipoEventoReqViagem tipoEvento;
    @ManyToOne
    @JoinColumn(name = "cod_viagem")
    private Viagem viagem;

    public EventoReqViagem() {}

    public EventoReqViagem(Viagem viagem) {
        this.viagem = viagem;
    }

    public EventoReqViagem(Date dataEvento, TipoEventoReqViagem tipoEvento, Viagem viagem) {
        this.dataEvento = dataEvento;
        this.tipoEvento = tipoEvento;
        this.viagem = viagem;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Date getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(Date dataEvento) {
        this.dataEvento = dataEvento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public TipoEventoReqViagem getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(TipoEventoReqViagem tipoEvento) {
        this.tipoEvento = tipoEvento;
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EventoReqViagem)) {
            return false;
        }
        EventoReqViagem other = (EventoReqViagem) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("E, dd/MM/yyyy");
        return sdf.format(getDataEvento()) +
               " - " +
               getTipoEvento() + ": " +
               getViagem();
    }

    @Override
    public int compareTo(Object o) {
        EventoReqViagem outro = (EventoReqViagem)o;
        if (!this.getViagem().equals(outro.getViagem())) return this.getViagem().compareTo(outro.getViagem());
        if (!this.getDataEvento().equals(outro.getDataEvento())) return this.getDataEvento().compareTo(outro.getDataEvento());
        return (this.getTipoEvento().getCodigo().compareTo(outro.getTipoEvento().getCodigo()));
    }
}
