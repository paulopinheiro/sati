package br.jus.trt12.paulopinheiro.sati.backup.model;

import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import java.io.Serializable;
import java.util.List;
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
@Table(name = "conjunto_fita", catalog = "sati", schema = "backup")
@NamedQueries(value={
    @NamedQuery(name="ConjuntoFitas.findByMunicipio", query="select c from ConjuntoFitas c where c.municipio = :municipio order by c.designacaoFita.id"),
    @NamedQuery(name="ConjuntoFitas.findByMunicipioDesignacao", query="select c from ConjuntoFitas c where c.municipio = :municipio and c.designacaoFita = :designacao")
})
public class ConjuntoFitas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="ConjuntoFita_Gen",sequenceName="backup.conjunto_fitas_id_seq",allocationSize=1)
    @GeneratedValue(generator="ConjuntoFita_Gen")
    private Long id;
    @Column(name="quant_fitas")
    private Integer quantFitas;
    @JoinColumn(name="designacao_fita_id")
    @ManyToOne
    private DesignacaoFita designacaoFita;
    @JoinColumn(name="municipio_id")
    @ManyToOne
    private Municipio municipio;
    @OneToMany(mappedBy = "conjuntoFita")
    private List<FitaDados> fitas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DesignacaoFita getDesignacaoFita() {
        return designacaoFita;
    }

    public void setDesignacaoFita(DesignacaoFita designacaoFita) {
        this.designacaoFita = designacaoFita;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public Integer getQuantFitas() {
        return quantFitas;
    }

    public void setQuantFitas(Integer quantFitas) {
        this.quantFitas = quantFitas;
    }

    public List<FitaDados> getFitas() {
        return fitas;
    }

    public void setFitas(List<FitaDados> fitas) {
        this.fitas = fitas;
    }

    public boolean isConsistente() {
        int quantReal = 0;

        if (this.getFitas()!=null) quantReal = this.getFitas().size();

        return this.getQuantFitas()==quantReal;
    }

    public boolean isDuplicidade(ConjuntoFitas compara) throws SatiLogicalException {
        if (compara==null) throw new SatiLogicalException("Comparando nulo");
        if ((this.getMunicipio()==null)||(this.getDesignacaoFita()==null)) throw new SatiLogicalException("Dados insuficientes do comparado");
        if ((compara.getMunicipio()==null)||(compara.getDesignacaoFita()==null)) throw new SatiLogicalException("Dados insuficientes do comparando");
        if ((this.getMunicipio()!=compara.getMunicipio())||(this.getDesignacaoFita()!=compara.getDesignacaoFita())) return false;
        if (this.getId()!=compara.getId()) return true;
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConjuntoFitas)) {
            return false;
        }
        ConjuntoFitas other = (ConjuntoFitas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getDesignacaoFita() + " (" + this.getMunicipio() + ") - " + this.getQuantFitas() + " fita(s)";
    }

}
