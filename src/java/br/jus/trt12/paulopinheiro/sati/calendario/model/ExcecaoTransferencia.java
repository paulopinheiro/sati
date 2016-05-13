package br.jus.trt12.paulopinheiro.sati.calendario.model;

import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import java.io.Serializable;
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
@Table(name = "excecao_transferencia", catalog = "sati", schema = "calendario")
@NamedQueries(value={
    @NamedQuery(name="ExcecaoTransferencia.findByTransferencia", query="select e from ExcecaoTransferencia e where e.transferencia = :transferencia"),
    @NamedQuery(name="ExcecaoTransferencia.excecaoTransferenciaByTransferenciaMunicipio", query="select e from ExcecaoTransferencia e where e.transferencia = :transferencia and e.municipio = :municipio")
})
public class ExcecaoTransferencia implements Serializable {
    @Id
    @SequenceGenerator(name="ExcecaoTransferencia_Gen",sequenceName="calendario.excecao_transferencia_codigo_seq",allocationSize=1)
    @GeneratedValue(generator="ExcecaoTransferencia_Gen")
    private Integer codigo;
    @ManyToOne
    @JoinColumn(nullable=false,name="cod_transferencia")
    private Transferencia transferencia;
    private String observacao;
    @ManyToOne
    @JoinColumn(nullable=false,name="cod_municipio")
    private Municipio municipio;

    public ExcecaoTransferencia() {}
    public ExcecaoTransferencia(Transferencia transferencia) {
        this.transferencia = transferencia;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Transferencia getTransferencia() {
        return transferencia;
    }

    public void setTransferencia(Transferencia transferencia) {
        this.transferencia = transferencia;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ExcecaoTransferencia other = (ExcecaoTransferencia) obj;
        if (this.codigo != other.codigo && (this.codigo == null || !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (this.codigo != null ? this.codigo.hashCode() : 0);
        return hash;
    }


    @Override
    public String toString() {
        return this.municipio.getNome();
    }
}
