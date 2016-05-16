package br.jus.trt12.paulopinheiro.sati.calendario.jsf;

import br.jus.trt12.paulopinheiro.sati.calendario.ejb.ExcecaoFeriadoNacionalFacade;
import br.jus.trt12.paulopinheiro.sati.calendario.model.ExcecaoFeriadoNacional;
import br.jus.trt12.paulopinheiro.sati.calendario.model.Feriado;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.MunicipioFacade;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.comum.AbListaRestritaMB;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ExcecaoFeriadoMB extends AbListaRestritaMB<ExcecaoFeriadoNacional> implements Serializable {
    @EJB private ExcecaoFeriadoNacionalFacade excecaoFeriadoFacade;
    @EJB private MunicipioFacade municipioFacade;

    private Feriado feriado;
    private String linkVoltar;

    private List<Municipio> municipios;

    public ExcecaoFeriadoMB() {}

    public String voltar() {
        return getLinkVoltar();
    }

    public Feriado getFeriado() {
        return feriado;
    }

    public void setFeriado(Feriado feriado) {
        this.feriado = feriado;
    }

    public ExcecaoFeriadoNacional getExcecaoFeriadoNacional() {
        return this.getElemento();
    }

    public void setExcecaoFeriadoNacional(ExcecaoFeriadoNacional excecaoFeriadoNacional) {
        this.setElemento(excecaoFeriadoNacional);
    }

    public List<ExcecaoFeriadoNacional> getListaExcecoesFeriados() {
        return this.getLista();
    }

    public void setListaExcecoesFeriados(List<ExcecaoFeriadoNacional> listaExcecoesFeriados) {
        this.setLista(listaExcecoesFeriados);
    }

    public List<Municipio> getMunicipios() {
        if (this.municipios==null) this.municipios=municipioFacade.findAll();
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }

    public String getLinkVoltar() {
        return linkVoltar;
    }

    public void setLinkVoltar(String linkVoltar) {
        this.linkVoltar = linkVoltar;
    }

    @Override
    protected List<ExcecaoFeriadoNacional> getListaRestrita() {
        return this.excecaoFeriadoFacade.findByFeriado(this.getFeriado());
    }

    @Override
    protected AbstractFacade getFacade() {
        return this.excecaoFeriadoFacade;
    }

    @Override
    public boolean isNovoElemento() {
        return this.getExcecaoFeriadoNacional().getCodigo()==null || this.getExcecaoFeriadoNacional().getCodigo()==0;
    }

    @Override
    protected ExcecaoFeriadoNacional novainstanciaElemento() {
        return new ExcecaoFeriadoNacional(getFeriado());
    }
}
