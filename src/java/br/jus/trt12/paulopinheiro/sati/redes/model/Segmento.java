package br.jus.trt12.paulopinheiro.sati.redes.model;

import java.io.Serializable;
import java.text.Collator;
import java.util.List;
import java.util.Locale;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "segmento", catalog = "sati", schema = "redes")
public class Segmento implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="Segmento_Gen",sequenceName="redes.segmento_codigo_seq",allocationSize=1)
    @GeneratedValue(generator="Segmento_Gen")
    private Long codigo;
    private Integer extensao;
    private String observacao;
    @OneToMany(mappedBy = "segmento")
    private List<Tomada> tomadas;

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

    //sinalizar quando menos de 2 tomadas
    public List<Tomada> getTomadas() {
        return tomadas;
    }

    //impedir mais de 2 tomadas
    //sinalizar quando menos de 2 tomadas
    public void setTomadas(List<Tomada> tomadas) {
        this.tomadas = tomadas;
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
        return "Segmento tomada " + getTomadas().get(0) + " a " + getTomadas().get(1);
    }

    @Override
    public int compareTo(Object o) {
        if (o==null) return 0;
        Segmento outro = (Segmento) o;
        if (this.getExtensao()==null || outro.getExtensao()==null) return 0;
        return this.getExtensao().compareTo(outro.getExtensao());
    }

}
