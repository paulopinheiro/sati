package br.jus.trt12.paulopinheiro.sati.calendario.jsf;

import br.jus.trt12.paulopinheiro.sati.calendario.ejb.FeriadoFacade;
import br.jus.trt12.paulopinheiro.sati.calendario.model.FeriadoMovel;
import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.MunicipioFacade;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.comum.AbListaRestritaMB;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

@Named
@ViewScoped
public class FeriadoMovelMB extends AbListaRestritaMB<FeriadoMovel> implements Serializable {
    @EJB private FeriadoFacade feriadoFacade;
    @EJB private MunicipioFacade municipioFacade;

    private List<Municipio> municipios;
    
    public FeriadoMovelMB() {}

    @Override
    public void salvar(ActionEvent event) {
        try {
            feriadoFacade.salvarFeriadoMovel(getFeriadoMovel());
            setFeriadoMovel(null);
            setListaFeriadosMoveis(null);
            mensagemSucesso("Registro salvo com sucesso");
        } catch (SatiLogicalException ex) {
            mensagemErro(ex.getMessage());
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
            Logger.getLogger("FeriadoMovelMB.java").log(Level.SEVERE, "Erro ao salvar", ex);
        }
    }

    public FeriadoMovel getFeriadoMovel() {
        return this.getElemento();
    }

    public void setFeriadoMovel(FeriadoMovel feriadoMovel) {
        this.setElemento(feriadoMovel);
    }

    public List<FeriadoMovel> getListaFeriadosMoveis() {
        return this.getLista();
    }

    public void setListaFeriadosMoveis(List<FeriadoMovel> listaFeriadosMoveis) {
        this.setLista(listaFeriadosMoveis);
    }

    public List<Municipio> getMunicipios() {
        if (this.municipios==null) this.municipios = municipioFacade.findAll();
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }

    @Override
    protected List<FeriadoMovel> getListaRestrita() {
        return this.feriadoFacade.findFeriadosMoveis();
    }

    @Override
    protected AbstractFacade getFacade() {
        return this.feriadoFacade;
    }

    @Override
    public boolean isNovoElemento() {
        return this.getFeriadoMovel().getCodigo()==null || this.getFeriadoMovel().getCodigo()==0;
    }

    @Override
    protected FeriadoMovel novainstanciaElemento() {
        return new FeriadoMovel();
    }
}
