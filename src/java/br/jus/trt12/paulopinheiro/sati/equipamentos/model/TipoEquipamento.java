package br.jus.trt12.paulopinheiro.sati.equipamentos.model;

import java.io.Serializable;
import java.text.Collator;
import java.util.Locale;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tipo_equipamento", catalog = "sati", schema = "equipamentos")
@NamedQueries({
    @NamedQuery(name = "TipoEquipamento.findAll", query = "SELECT t FROM TipoEquipamento t"),
    @NamedQuery(name = "TipoEquipamento.findByNome", query = "SELECT t FROM TipoEquipamento t WHERE t.nome = :nome")})
public class TipoEquipamento implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="TipoEquipamento_Gen",sequenceName="equipamentos.tipo_equipamento_codigo_seq", allocationSize=1)
    @GeneratedValue(generator="TipoEquipamento_Gen")
    private Integer codigo;
    private String nome;

    public TipoEquipamento() {
    }

    public TipoEquipamento(Integer codigo) {
        this.codigo = codigo;
    }

    public TipoEquipamento(Integer codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
        if (!(object instanceof TipoEquipamento)) {
            return false;
        }
        TipoEquipamento other = (TipoEquipamento) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getNome();
    }

    @Override
    public int compareTo(Object o) {
        TipoEquipamento outro = (TipoEquipamento) o;
        Collator col = Collator.getInstance(Locale.getDefault());
        return col.compare(this.getNome().toUpperCase(), outro.getNome().toUpperCase());
    }
    
}
