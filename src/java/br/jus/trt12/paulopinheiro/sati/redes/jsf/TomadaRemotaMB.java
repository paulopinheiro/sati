package br.jus.trt12.paulopinheiro.sati.redes.jsf;

import br.jus.trt12.paulopinheiro.sati.redes.ejb.TomadaRemotaFacade;
import br.jus.trt12.paulopinheiro.sati.redes.model.Modulo;
import br.jus.trt12.paulopinheiro.sati.redes.model.TipoConector;
import br.jus.trt12.paulopinheiro.sati.redes.model.TomadaRemota;
import br.jus.trt12.paulopinheiro.sati.util.ContextoJSF;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.event.ActionEvent;

@ManagedBean
@ViewScoped
public class TomadaRemotaMB implements Serializable {
    @EJB private TomadaRemotaFacade tomadaRemotaFacade;

    private Modulo modulo;

    private TomadaRemota tomadaRemota;
    private List<TomadaRemota> listaTomadasRemotas;

    private List<TipoConector> tiposConector;

    private HtmlDataTable tabelaTomadas;

    public TomadaRemotaMB() {}

    public void salvar(ActionEvent event) {
        try {
            tomadaRemotaFacade.salvar(getTomadaRemota());
            setTomadaRemota(null);
            setListaTomadasRemotas(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public void nova(ActionEvent event) {
        setTomadaRemota(null);
    }

    public void excluir(ActionEvent event) {
        try {
            tomadaRemotaFacade.remove(getTomadaRemota());
            setTomadaRemota(null);
            setListaTomadasRemotas(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public void editaLinhaTabela(ActionEvent event) {
        setTomadaRemota((TomadaRemota)getTabelaTomadas().getRowData());
    }

    public int getQuantPanels() {
        return getListaTomadasRemotas().size();
    }

    public List<TomadaRemota> getListaTomadasRemotas() {
        if (this.listaTomadasRemotas==null) this.listaTomadasRemotas = tomadaRemotaFacade.findByModulo(getModulo());
        return listaTomadasRemotas;
    }

    public void setListaTomadasRemotas(List<TomadaRemota> listaTomadasRemotas) {
        this.listaTomadasRemotas = listaTomadasRemotas;
    }

    public Modulo getModulo() {
        if (this.modulo==null) this.modulo=new Modulo();
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public HtmlDataTable getTabelaTomadas() {
        return tabelaTomadas;
    }

    public void setTabelaTomadas(HtmlDataTable tabelaTomadas) {
        this.tabelaTomadas = tabelaTomadas;
    }

    public List<TipoConector> getTiposConector() {
        if (this.tiposConector==null) this.tiposConector=tomadaRemotaFacade.tiposConector();
        return tiposConector;
    }

    public void setTiposConector(List<TipoConector> tiposConector) {
        this.tiposConector = tiposConector;
    }

    public TomadaRemota getTomadaRemota() {
        if (this.tomadaRemota==null) this.tomadaRemota = new TomadaRemota(getModulo());
        return tomadaRemota;
    }

    public void setTomadaRemota(TomadaRemota tomadaRemota) {
        this.tomadaRemota = tomadaRemota;
    }

    private void mensagemErro(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,mensagem,null));
    }
}
