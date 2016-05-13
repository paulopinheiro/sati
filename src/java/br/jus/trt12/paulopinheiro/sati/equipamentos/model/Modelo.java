package br.jus.trt12.paulopinheiro.sati.equipamentos.model;

import java.io.Serializable;
import java.text.Collator;
import java.util.List;
import java.util.Locale;
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
@Table(name = "modelo", catalog = "sati", schema = "equipamentos")
@NamedQueries({
    @NamedQuery(name="Modelo.findAll", query="Select m From Modelo m order by m.nome, m.descricao"),
    @NamedQuery(name="Modelo.findByTipoEquipamento", query="Select m From Modelo m where m.tipoEquipamento = :tipoEquipamento order by m.nome, m.descricao")
})
public class Modelo implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="Modelo_Gen",sequenceName="equipamentos.modelo_codigo_seq", allocationSize=1)
    @GeneratedValue(generator="Modelo_Gen")
    private Integer codigo;
    private String nome;
    private String descricao;
    @ManyToOne(optional=false)
    @JoinColumn(name="cod_tipo_equipamento")
    private TipoEquipamento tipoEquipamento;
    @OneToMany(mappedBy="modelo")
    private List<Lote> listaLotes;

    public Modelo() {}

    public Modelo(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoEquipamento getTipoEquipamento() {
        return tipoEquipamento;
    }

    public void setTipoEquipamento(TipoEquipamento tipoEquipamento) {
        this.tipoEquipamento = tipoEquipamento;
    }

    public List<Lote> getListaLotes() {
        return listaLotes;
    }

    public void setListaLotes(List<Lote> listaLotes) {
        this.listaLotes = listaLotes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Modelo)) {
            return false;
        }
        Modelo other = (Modelo) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nome;
    }

    @Override
    public int compareTo(Object o) {
        Modelo outro = (Modelo) o;
        if (!this.getTipoEquipamento().equals(outro.getTipoEquipamento())) return this.getTipoEquipamento().compareTo(outro.getTipoEquipamento());
        Collator col = Collator.getInstance(Locale.getDefault());
        return col.compare(this.getNome().toUpperCase(), outro.getNome().toUpperCase());
    }

}
