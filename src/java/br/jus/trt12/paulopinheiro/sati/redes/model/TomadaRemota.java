package br.jus.trt12.paulopinheiro.sati.redes.model;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "tomadaremota", catalog = "sati", schema = "redes")
@DiscriminatorValue("R")
@PrimaryKeyJoinColumn(name="codigo")
@NamedQueries({
    @NamedQuery(name="TomadaRemota.tomadasRemotasByModulo", query = "Select t from TomadaRemota t where t.modulo = :modulo order by t.nome"),
    @NamedQuery(name="TomadaRemota.tomadasRemotasByUnidade", query = "Select t from TomadaRemota t where t.modulo.unidade = :unidade order by t.nome"),
    @NamedQuery(name="TomadaRemota.tomadasRemotasByMunicipio", query = "Select t from TomadaRemota t where t.modulo.unidade.municipio = :municipio order by t.modulo.unidade.nome, t.nome")
})
public class TomadaRemota extends Tomada implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManyToOne(optional=false)
    @JoinColumn(name="cod_tipoconector")
    private TipoConector tipoConector;
    @ManyToOne(optional=false)
    @JoinColumn(name="cod_modulo")
    private Modulo modulo;

    public TomadaRemota() {}
    public TomadaRemota(Modulo modulo) {this.modulo=modulo;}

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public TipoConector getTipoConector() {
        return tipoConector;
    }

    public void setTipoConector(TipoConector tipoConector) {
        this.tipoConector = tipoConector;
    }

    @Override
    public String getDescricao() {
        if ((super.getDescricao()==null)||super.getDescricao().trim().isEmpty()) {
            if ((this.getNome()==null)||this.getNome().trim().isEmpty()) return "";
            else return "Tomada " + this.getNome() + " do módulo " + this.getModulo();
        } else return super.getDescricao();
    }

    @Override
    public String toString() {
        if ((this.getNome()==null||this.getNome().trim().isEmpty())) return this.getDescricao();
        else return this.getNome() + "(" + this.getDescricao() + ")";
    }

    @Override
    public int compareTo(Object o) {
        TomadaRemota outra = (TomadaRemota) o;
        if (!this.getModulo().equals(outra.getModulo())) return this.getModulo().compareTo(outra.getModulo());
        return super.compareTo(o);
    }

}
