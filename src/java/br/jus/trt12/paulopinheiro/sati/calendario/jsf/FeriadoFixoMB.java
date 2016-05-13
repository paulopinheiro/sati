package br.jus.trt12.paulopinheiro.sati.calendario.jsf;

import br.jus.trt12.paulopinheiro.sati.calendario.ejb.FeriadoFacade;
import br.jus.trt12.paulopinheiro.sati.calendario.model.FeriadoFixo;
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
public class FeriadoFixoMB implements Serializable {
    @EJB private FeriadoFacade feriadoFacade;
    @EJB private MunicipioFacade municipioFacade;

    private FeriadoFixo feriadoFixo;
    private List<FeriadoFixo> listaFeriadosFixos;

    private List<Municipio> municipios;

    public FeriadoFixoMB() {}


    public void salvar(ActionEvent event) {
        try {
            feriadoFacade.salvarFeriadoFixo(getFeriadoFixo());
            setFeriadoFixo(null);
            setListaFeriadosFixos(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public void novo(ActionEvent event) {
        setFeriadoFixo(null);
        setListaFeriadosFixos(null);
    }

    public void excluir(ActionEvent event) {
        try {
            feriadoFacade.remove(getFeriadoFixo());
            setFeriadoFixo(null);
            setListaFeriadosFixos(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public int getQuantFeriadosFixos() {
        return getListaFeriadosFixos().size();
    }

    public FeriadoFixo getFeriadoFixo() {
        if (this.feriadoFixo==null) this.feriadoFixo = new FeriadoFixo();
        return feriadoFixo;
    }

    public void setFeriadoFixo(FeriadoFixo feriadoFixo) {
        this.feriadoFixo = feriadoFixo;
    }

    public List<FeriadoFixo> getListaFeriadosFixos() {
        if (this.listaFeriadosFixos==null) this.listaFeriadosFixos = feriadoFacade.findFeriadosFixos();
        return listaFeriadosFixos;
    }

    public void setListaFeriadosFixos(List<FeriadoFixo> listaFeriadosFixos) {
        this.listaFeriadosFixos = listaFeriadosFixos;
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
