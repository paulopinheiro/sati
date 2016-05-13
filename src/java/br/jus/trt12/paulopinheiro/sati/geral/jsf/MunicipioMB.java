package br.jus.trt12.paulopinheiro.sati.geral.jsf;

import br.jus.trt12.paulopinheiro.sati.geral.ejb.AreaTIFacade;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.MunicipioFacade;
import br.jus.trt12.paulopinheiro.sati.geral.model.AreaTI;
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
public class MunicipioMB implements Serializable {
    @EJB private MunicipioFacade municipioFacade;
    @EJB private AreaTIFacade areaTIFacade;

    private List<Municipio> listaMunicipios;
    private Municipio municipio;

    private List<AreaTI> areasTI;

    public MunicipioMB() {}

    public void salvar(ActionEvent event) {
        try {
            municipioFacade.salvar(getMunicipio());
            setMunicipio(null);
            setListaMunicipios(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public void novo(ActionEvent event) {
        setMunicipio(null);
    }

    public void excluir(ActionEvent event) {
        try {
            municipioFacade.remove(getMunicipio());
            setMunicipio(null);
            setListaMunicipios(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public int getQuantMunicipios() {
        return getListaMunicipios().size();
    }

    public List<AreaTI> getAreasTI() {
        if (this.areasTI==null) this.areasTI = areaTIFacade.findAll();
        return areasTI;
    }

    public void setAreasTI(List<AreaTI> areasTI) {
        this.areasTI = areasTI;
    }

    public List<Municipio> getListaMunicipios() {
        if (this.listaMunicipios==null) this.listaMunicipios = municipioFacade.findAll();
        return listaMunicipios;
    }

    public void setListaMunicipios(List<Municipio> listaMunicipios) {
        this.listaMunicipios = listaMunicipios;
    }

    public Municipio getMunicipio() {
        if (this.municipio==null) this.municipio = new Municipio();
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    private void mensagemErro(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,mensagem,null));
    }
}
