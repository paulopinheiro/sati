package br.jus.trt12.paulopinheiro.sati.calendario.jsf;

import br.jus.trt12.paulopinheiro.sati.calendario.ejb.FeriadoFacade;
import br.jus.trt12.paulopinheiro.sati.calendario.model.FeriadoFixo;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.MunicipioFacade;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.comum.AbListaRestritaMB;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

@Named
@ViewScoped
public class FeriadoFixoMB extends AbListaRestritaMB<FeriadoFixo> implements Serializable {
    @EJB private FeriadoFacade feriadoFacade;
    @EJB private MunicipioFacade municipioFacade;

    private List<Municipio> municipios;

    public FeriadoFixoMB() {}


    @Override
    public void salvar(ActionEvent event) {
        try {
            feriadoFacade.salvarFeriadoFixo(getFeriadoFixo());
            setFeriadoFixo(null);
            setListaFeriadosFixos(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public FeriadoFixo getFeriadoFixo() {
        return this.getElemento();
    }

    public void setFeriadoFixo(FeriadoFixo feriadoFixo) {
        this.setElemento(feriadoFixo);
    }

    public List<FeriadoFixo> getListaFeriadosFixos() {
        return this.getLista();
    }

    public void setListaFeriadosFixos(List<FeriadoFixo> listaFeriadosFixos) {
        this.setLista(listaFeriadosFixos);
    }

    public List<Municipio> getMunicipios() {
        if (this.municipios==null) this.municipios = municipioFacade.findAll();
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }

    @Override
    protected List<FeriadoFixo> getListaRestrita() {
        return this.feriadoFacade.findFeriadosFixos();
    }

    @Override
    protected AbstractFacade getFacade() {
        return this.feriadoFacade;
    }

    @Override
    public boolean isNovoElemento() {
        return this.getFeriadoFixo().getCodigo()==null || this.getFeriadoFixo().getCodigo()==0;
    }

    @Override
    protected FeriadoFixo novainstanciaElemento() {
        return new FeriadoFixo();
    }
}
