package br.jus.trt12.paulopinheiro.sati.redes.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "segmento", catalog = "sati", schema = "redes")
@NamedQueries({
    @NamedQuery(name="Segmento.findByTomada", query = "Select s from Segmento s where s.tomada1 = :tomada or s.tomada2 = :tomada"),
    @NamedQuery(name="Segmento.findOutraPontaTomada", query = "Select s.tomada1 from Segmento s where s.tomada2 = :tomada UNION Select s.tomada2 from Segmento s where s.tomada1 = :tomada")
})
public class Segmento implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="Segmento_Gen",sequenceName="redes.segmento_codigo_seq",allocationSize=1)
    @GeneratedValue(generator="Segmento_Gen")
    private Long codigo;
    private Integer extensao;
    private String observacao;
    @OneToOne(optional=false)
    @JoinColumn(name="cod_tomada1")
    private Tomada tomada1;
    @OneToOne(optional=false)
    @JoinColumn(name="cod_tomada2")
    private Tomada tomada2;

    public Integer getExtensao() {
        return extensao;
    }

    public void setExtensao(Integer extensao) {
        this.extensao = extensao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Tomada getTomada1() {
        return tomada1;
    }

    public void setTomada1(Tomada tomada1) {
        this.tomada1 = tomada1;
    }

    public Tomada getTomada2() {
        return tomada2;
    }

    public void setTomada2(Tomada tomada2) {
        this.tomada2 = tomada2;
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
        if (!(object instanceof Segmento)) {
            return false;
        }
        Segmento other = (Segmento) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if (getTomada1()==null||getTomada2()==null) return null;
        return "Segmento tomada " + getTomada1().getNome() + " a " + getTomada2().getNome();
    }

    @Override
    public int compareTo(Object o) {
        if (o==null) return 0;
        Segmento outro = (Segmento) o;
        if (this.getExtensao()==null || outro.getExtensao()==null) return 0;
        return this.getExtensao().compareTo(outro.getExtensao());
    }

}
