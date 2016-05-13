package br.jus.trt12.paulopinheiro.sati.calendario.model;

import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import br.jus.trt12.paulopinheiro.sati.util.Util;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@Table(name = "transferencia", catalog = "sati", schema = "calendario")
@NamedQueries(value={
    @NamedQuery(name="Transferencia.transferenciasByAno", query="select t from Transferencia t where t.ano= :ano"),
    @NamedQuery(name="Transferencia.transferenciaByFeriadoAno", query="select t from Transferencia t where t.feriado = :feriado and t.ano = :ano")
})
public class Transferencia implements Serializable, Comparable {
    @Id
    @SequenceGenerator(name="Transferencia_Gen",sequenceName="calendario.transferencia_codigo_seq",allocationSize=1)
    @GeneratedValue(generator="Transferencia_Gen")
    private Integer codigo;

    @ManyToOne
    @JoinColumn(nullable=false, name="cod_feriado")
    private Feriado feriado;
    private int ano;
    @Column(nullable=false,name="novo_dia")
    private int novoDia;
    @Column(nullable=false,name="novo_mes")
    private int novoMes;
    private String legislacao;
    @ManyToOne
    @JoinColumn(name="cod_municipio")
    private Municipio municipio;

    @OneToMany(mappedBy = "transferencia", cascade=CascadeType.ALL)
    private List<ExcecaoTransferencia> excecoes;

    public Transferencia() {}

    public Transferencia(int ano) {
        this.ano = ano;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Feriado getFeriado() {
        return feriado;
    }

    public void setFeriado(Feriado feriado) {
        this.feriado = feriado;
    }

    public String getLegislacao() {
        return legislacao;
    }

    public void setLegislacao(String legislacao) {
        this.legislacao = legislacao;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public int getNovoDia() {
        return novoDia;
    }

    public void setNovoDia(int novoDia) {
        this.novoDia = novoDia;
    }

    public int getNovoMes() {
        return novoMes;
    }

    public void setNovoMes(int novoMes) {
        this.novoMes = novoMes;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Transferencia other = (Transferencia) obj;
        if (this.codigo != other.codigo && (this.codigo == null || !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + (this.codigo != null ? this.codigo.hashCode() : 0);
        return hash;
    }

    public Date getNovaData() {
        return Util.dataPura(this.ano, this.novoMes, this.novoDia);
    }

    public boolean validaPara(Municipio municipio) {
        //Se existe uma exclusividade ativa
        if (!(this.getMunicipio()==null)) {
            //Se for ativamente exclusiva ao município, é válida, senão é inválida
            if (this.getMunicipio().equals(municipio)) return true;
            else return false;
        //Não há exclusividade ativa
        } else {
            //Está na lista de exceções? se estiver é inválida, senão é válida
            if (isExcecao(municipio)) return false;
            else return true;
        }
    }

    private boolean isExcecao(Municipio municipio) {
        if (!(this.excecoes==null)) return false;
        for (ExcecaoTransferencia e:this.excecoes) {
            if (e.getMunicipio().equals(municipio)) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return getFeriado().toString() + " para " + Util.dataString(getNovaData());
    }

    @Override
    public int compareTo(Object o) {
        Transferencia outra = (Transferencia) o;
        if (this.getAno() != outra.getAno()) return Integer.valueOf(this.getAno()).compareTo(Integer.valueOf(outra.getAno()));
        if (this.getNovoMes() != outra.getNovoMes()) return Integer.valueOf(this.getNovoMes()).compareTo(Integer.valueOf(outra.getNovoMes()));
        if (this.getNovoDia() != outra.getNovoDia()) return Integer.valueOf(this.getNovoDia()).compareTo(Integer.valueOf(outra.getNovoDia()));
        return this.getFeriado().compareTo(outra.getFeriado());
    }
}
