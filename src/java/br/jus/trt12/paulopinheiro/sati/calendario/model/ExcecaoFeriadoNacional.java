package br.jus.trt12.paulopinheiro.sati.calendario.model;

import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "excecao_feriado_nacional", catalog = "sati", schema = "calendario")
@NamedQueries(value={
    @NamedQuery(name="ExcecaoFeriadoNacional.findByFeriado", query="select e from ExcecaoFeriadoNacional e where e.feriado = :feriado"),
    @NamedQuery(name="ExcecaoFeriadoNacional.findByFeriadoMunicipio", query="select e from ExcecaoFeriadoNacional e where e.feriado = :feriado and e.municipio= :municipio")
})
public class ExcecaoFeriadoNacional implements Serializable {
    @Id
    @Column(name="codigo")
    @SequenceGenerator(name="ExcecaoFeriadoNacional_Gen",sequenceName="calendario.excecao_feriado_nacional_codigo_seq",allocationSize=1)
    @GeneratedValue(generator="ExcecaoFeriadoNacional_Gen")
    private Integer codigo;
    @ManyToOne
    @JoinColumn(name="cod_feriado",nullable=false)
    private Feriado feriado;

    @ManyToOne
    @JoinColumn(name="cod_municipio",nullable=false)
    private Municipio municipio;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date desde;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date ate;

    public ExcecaoFeriadoNacional() {}
    public ExcecaoFeriadoNacional(Feriado feriado) {
        this.feriado = feriado;
    }

    public Date getAte() {
        return ate;
    }

    public void setAte(Date ate) {
        this.ate = ate;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Feriado getFeriado() {
        return feriado;
    }

    public void setFeriado(Feriado feriado) {
        this.feriado = feriado;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public boolean isValida(int ano) {
        boolean validade;
        boolean aposInicio = true;
        boolean antesFim = true;

        Date dataFeriado = this.getFeriado().dataAno(ano);

        if (!(this.desde==null)) aposInicio = !dataFeriado.before(this.desde);
        if (!(this.ate== null)) antesFim = !dataFeriado.after(this.ate);

        validade = aposInicio && antesFim;

        return validade;
    }
}
