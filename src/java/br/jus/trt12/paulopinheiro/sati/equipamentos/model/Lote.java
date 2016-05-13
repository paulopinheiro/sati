package br.jus.trt12.paulopinheiro.sati.equipamentos.model;

import br.jus.trt12.paulopinheiro.sati.util.Util;
import java.io.Serializable;
import java.text.Collator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "lote", catalog = "sati", schema = "equipamentos")
@NamedQueries({
    @NamedQuery(name="Lote.findAll", query="Select l From Lote l order by l.modelo.nome,l.dataFimGarantia,l.nome"),
    @NamedQuery(name="Lote.findByModelo", query="Select l From Lote l where l.modelo = :modelo order by l.modelo.nome,l.dataFimGarantia,l.nome")
})
public class Lote implements Serializable, Comparable {
    @OneToMany(mappedBy = "lote")
    private List<Equipamento> equipamentos;
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="Lote_Gen",sequenceName="equipamentos.lote_codigo_seq", allocationSize=1)
    @GeneratedValue(generator="Lote_Gen")
    private Integer codigo;
    private String nome;
    @Column(name="datafimgarantia")
    @Temporal(TemporalType.DATE)
    private Date dataFimGarantia;
    @JoinColumn(name="cod_modelo")
    @ManyToOne
    private Modelo modelo;
    @OneToMany (mappedBy="lote")
    private List<Equipamento> listaEquipamentos;

    public Lote() {}

    public Lote(Modelo modelo) {
        this.modelo = modelo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Date getDataFimGarantia() {
        return dataFimGarantia;
    }

    public void setDataFimGarantia(Date dataFimGarantia) {
        this.dataFimGarantia = dataFimGarantia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public List<Equipamento> getEquipamentos() {
        return equipamentos;
    }

    public void setEquipamentos(List<Equipamento> equipamentos) {
        this.equipamentos = equipamentos;
    }

    public List<Equipamento> getListaEquipamentos() {
        return listaEquipamentos;
    }

    public void setListaEquipamentos(List<Equipamento> listaEquipamentos) {
        this.listaEquipamentos = listaEquipamentos;
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
        if (!(object instanceof Lote)) {
            return false;
        }
        Lote other = (Lote) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nome + " (" + this.modelo.getNome() + " - Garantia até "+ Util.dataString(this.getDataFimGarantia()) + ")";
    }

    @Override
    public int compareTo(Object o) {
        Lote outro = (Lote) o;
        if (!this.getModelo().equals(outro.getModelo())) return this.getModelo().compareTo(outro.getModelo());
        Collator col = Collator.getInstance(Locale.getDefault());
        return col.compare(this.getNome().toUpperCase(), outro.getNome().toUpperCase());
    }

}
