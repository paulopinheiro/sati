package br.jus.trt12.paulopinheiro.sati.calendario.jsf;

import br.jus.trt12.paulopinheiro.sati.calendario.ejb.FeriadoFacade;
import br.jus.trt12.paulopinheiro.sati.calendario.model.FeriadoMovel;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.MunicipioFacade;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import br.jus.trt12.paulopinheiro.sati.util.ContextoJSF;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

@ManagedBean
@ViewScoped
public class FeriadoMovelMB implements Serializable {
    @EJB private FeriadoFacade feriadoFacade;
    @EJB private MunicipioFacade municipioFacade;

    private FeriadoMovel feriadoMovel;
    private List<FeriadoMovel> listaFeriadosMoveis;

    private List<Municipio> municipios;
    
    public FeriadoMovelMB() {}

    public void diaSeguinte(ActionEvent event) {
        getFeriadoMovel().setDiasPascoa(getFeriadoMovel().getDiasPascoa()+1);
    }

    public void diaAnterior(ActionEvent event) {
        getFeriadoMovel().setDiasPascoa(getFeriadoMovel().getDiasPascoa()-1);
    }

    public void salvar(ActionEvent event) {
        try {
            feriadoFacade.salvarFeriadoMovel(getFeriadoMovel());
            setFeriadoMovel(null);
            setListaFeriadosMoveis(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public void novo(ActionEvent event) {
        setFeriadoMovel(null);
        setListaFeriadosMoveis(null);
    }

    public void excluir(ActionEvent event) {
        try {
            feriadoFacade.remove(getFeriadoMovel());
            setFeriadoMovel(null);
            setListaFeriadosMoveis(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public int getQuantFeriadosMoveis() {
        return getListaFeriadosMoveis().size();
    }

    public FeriadoMovel getFeriadoMovel() {
        if (this.feriadoMovel==null) this.feriadoMovel = new FeriadoMovel();
        return feriadoMovel;
    }

    public void setFeriadoMovel(FeriadoMovel feriadoMovel) {
        this.feriadoMovel = feriadoMovel;
    }

    public List<FeriadoMovel> getListaFeriadosMoveis() {
        if (this.listaFeriadosMoveis==null) this.listaFeriadosMoveis = feriadoFacade.findFeriadosMoveis();
        return listaFeriadosMoveis;
    }

    public void setListaFeriadosMoveis(List<FeriadoMovel> listaFeriadosMoveis) {
        this.listaFeriadosMoveis = listaFeriadosMoveis;
    }

    public List<Municipio> getMunicipios() {
        if (this.municipios==null) this.municipios = municipioFacade.findAll();
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }

    private void mensagemErro(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,mensagem,null));
    }
}
