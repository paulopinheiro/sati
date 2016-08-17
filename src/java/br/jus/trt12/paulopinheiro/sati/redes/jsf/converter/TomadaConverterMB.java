package br.jus.trt12.paulopinheiro.sati.redes.jsf.converter;

import br.jus.trt12.paulopinheiro.sati.redes.ejb.TomadaFacade;
import br.jus.trt12.paulopinheiro.sati.redes.model.Tomada;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

@Model
public class TomadaConverterMB implements Serializable, Converter {
    @EJB private TomadaFacade tomadaFacade;
    public TomadaConverterMB() {}

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            if (value==null) return null;
            return (Tomada) tomadaFacade.find(Long.valueOf(value));
        } catch (Exception ex) {
            throw new ConverterException(new FacesMessage(String.format("Não foi possível determinar a tomada de código %s", value)), ex);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            if (!(value instanceof Tomada)||((Tomada)value).getCodigo()==null) return null;
            return String.valueOf(((Tomada)value).getCodigo());
        } catch (Exception ex) {
            throw new ConverterException(new FacesMessage(String.format("Não foi possível determinar a tomada de código %s", value)), ex);
        }
    }
    
}
