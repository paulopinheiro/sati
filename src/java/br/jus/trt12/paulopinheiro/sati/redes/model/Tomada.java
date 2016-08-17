package br.jus.trt12.paulopinheiro.sati.redes.model;

import java.io.Serializable;
import java.text.Collator;
import java.util.Locale;
import javax.persistence.Basic;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tomada", catalog = "sati", schema = "redes")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="tipo", discriminatorType=DiscriminatorType.STRING, length=1)
public class Tomada implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="Tomada_Gen",sequenceName="redes.tomada_codigo_seq",allocationSize=1)
    @GeneratedValue(generator="Tomada_Gen")
    private Long codigo;
    @Basic(optional = false)
    private String nome;
    private String descricao;
    private String observacao;
    @ManyToOne
    @JoinTable(name = "redes.segmento_tomada", joinColumns = {@JoinColumn(name = "cod_tomada")},
                                               inverseJoinColumns = {@JoinColumn(name = "cod_segmento")
    })
    private Segmento segmento;

    public Tomada() {}

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

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Segmento getSegmento() {
        return segmento;
    }

    public void setSegmento(Segmento segmento) {
        this.segmento = segmento;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
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
        if (!(object instanceof Tomada)) {
            return false;
        }
        Tomada other = (Tomada) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNome();
    }

    @Override
    public int compareTo(Object o) {
        if (o==null) return 0;
        Tomada t = (Tomada) o;
        if (this.getNome()==null || t.getNome()==null) return 0;
        Collator col = Collator.getInstance(Locale.getDefault());
        return col.compare(this.getNome().toUpperCase(), t.getNome().toUpperCase());
    }
}
