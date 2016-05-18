package br.jus.trt12.paulopinheiro.sati.certificadodigital.jsf;

import br.jus.trt12.paulopinheiro.sati.certificadodigital.ejb.CertificadoFacade;
import br.jus.trt12.paulopinheiro.sati.certificadodigital.model.Certificado;
import br.jus.trt12.paulopinheiro.sati.certificadodigital.model.MarcaEtoken;
import br.jus.trt12.paulopinheiro.sati.certificadodigital.model.StatusCertificado;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.UsuarioFinalFacade;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.GeralMB;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.comum.AbListaRestritaMB;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import br.jus.trt12.paulopinheiro.sati.geral.model.UsuarioFinal;
import br.jus.trt12.paulopinheiro.sati.geral.model.Unidade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named
@ViewScoped
public class CertificadoMB extends AbListaRestritaMB<Certificado> implements Serializable {
    @EJB private CertificadoFacade certificadoFacade;
    @EJB private UsuarioFinalFacade usuarioFinalFacade;
    @Inject private GeralMB geralMB;

    public CertificadoMB() {}

    public Certificado getCertificado() {
        return this.getElemento();
    }

    public void setCertificado(Certificado certificado) {
        this.setElemento(certificado);
    }

    public List<Certificado> getListaCertificados() {
        return this.getLista();
    }

    public void setListaCertificados(List<Certificado> listaCertificados) {
        this.setLista(listaCertificados);
    }

    public List<UsuarioFinal> getUsuarios() {
        return this.usuarioFinalFacade.findByMunicipio(this.getMunicipio());
    }

    public List<MarcaEtoken> getMarcasEtoken() {
        return this.certificadoFacade.getListaMarcaEtoken();
    }

    public List<StatusCertificado> getStatusCertificados() {
        return this.certificadoFacade.getListaStatusCertificado();
    }

    @Override
    protected List<Certificado> getListaRestrita() {
        return this.certificadoFacade.findByMunicipio(this.getMunicipio());
    }

    public Municipio getMunicipio() {
        return this.geralMB.getMunicipioSessao();
    }

    @Override
    protected AbstractFacade getFacade() {
        return this.certificadoFacade;
    }

    @Override
    public boolean isNovoElemento() {
        return this.getCertificado().getId()==null || this.getCertificado().getId()==0;
    }

    @Override
    protected Certificado novainstanciaElemento() {
        Certificado certificado = new Certificado();
        certificado.setStatus(this.certificadoFacade.getStatusDesconhecido());
        return certificado;
    }

    @Override
    public void filtrar(ActionEvent evt) {
        if (this.getCertificado().getUsuario()==null)
            this.getCertificado().setUsuario(new UsuarioFinal(new Unidade(this.getMunicipio())));
        super.filtrar(evt);
        this.limparElemento(evt);
    }
}
