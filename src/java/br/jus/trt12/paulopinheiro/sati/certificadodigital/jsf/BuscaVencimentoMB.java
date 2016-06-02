package br.jus.trt12.paulopinheiro.sati.certificadodigital.jsf;

import br.jus.trt12.paulopinheiro.sati.certificadodigital.ejb.CertificadoFacade;
import br.jus.trt12.paulopinheiro.sati.certificadodigital.model.Certificado;
import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.MunicipioFacade;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.GeralMB;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import br.jus.trt12.paulopinheiro.sati.util.ContextoJSF;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named
@ViewScoped
public class BuscaVencimentoMB implements Serializable {
    @EJB private CertificadoFacade certificadoFacade;
    @EJB private MunicipioFacade municipioFacade;
    @Inject private GeralMB geralMB;

    private Municipio municipio;
    private Date dataMin;
    private Date dataMax;
    private Integer escolhaPeriodo;

    private List<Certificado> listaCertificados;

    public BuscaVencimentoMB() {}

    public void buscarCertificados(ActionEvent evt) {
        try {
            setListaCertificados(certificadoFacade.findByMunicipioVencimentoDeAte(getMunicipio(), getDataMin(), getDataMax()));
        } catch (SatiLogicalException ex) {
            mensagemErro(ex.getMessage());
        } catch (Exception ex) {
            mensagemErro("Erro de sistema: " + ex.getMessage());
            Logger.getLogger("BuscaVencimentoMB").log(Level.SEVERE, ex.getMessage());
        }
    }

    public void alteraEscolhaPeriodo() {
        if (this.getEscolhaPeriodo()==0) {
            setDataMin(null);
            setDataMax(null);
        }
    }
    public Date getDataMin() {
        if (dataMin==null) dataMin = new Date();
        return dataMin;
    }

    public void setDataMin(Date dataMin) {
        this.dataMin = dataMin;
    }

    public Date getDataMax() {
        if (dataMax==null) dataMax = somaData(getDataMin(),30);
        return dataMax;
    }

    public void setDataMax(Date dataMax) {
        this.dataMax = dataMax;
    }

    public List<Certificado> getListaCertificados() {
        if (listaCertificados==null) listaCertificados = new ArrayList<Certificado>();
        return listaCertificados;
    }

    public int getQuantLista() {
        return this.getListaCertificados().size();
    }

    public void setListaCertificados(List<Certificado> listaCertificados) {
        this.listaCertificados = listaCertificados;
    }

    public Municipio getMunicipio() {
        if (municipio==null) municipio = geralMB.getMunicipioSessao();
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public List<Municipio> getListaMunicipios() {
        return municipioFacade.findAll();
    }

    public Integer getEscolhaPeriodo() {
        if (escolhaPeriodo==null) escolhaPeriodo = 0;
        return escolhaPeriodo;
    }

    public void setEscolhaPeriodo(Integer escolhaPeriodo) {
        this.escolhaPeriodo = escolhaPeriodo;
    }

    private static Date somaData(Date data,int dias) {
        Calendar c = new GregorianCalendar();
        c.setTime(data);
        c.add(Calendar.DATE, dias);
        return c.getTime();
    }

    protected void mensagemErro(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,mensagem,null));
    }
}
