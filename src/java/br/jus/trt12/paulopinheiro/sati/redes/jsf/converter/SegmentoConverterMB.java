package br.jus.trt12.paulopinheiro.sati.redes.jsf.converter;

import br.jus.trt12.paulopinheiro.sati.redes.ejb.SegmentoFacade;
import br.jus.trt12.paulopinheiro.sati.redes.model.Segmento;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

@Model
public class SegmentoConverterMB implements Converter, Serializable {
    @EJB private SegmentoFacade segmentoFacade;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            if (value==null) return null;
            return (Segmento) segmentoFacade.find(Long.valueOf(value));
        } catch (Exception ex) {
            throw new ConverterException(new FacesMessage(String.format("Não foi possível determinar o segmento de código %s", value)), ex);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            if (!(value instanceof Segmento)||((Segmento)value).getCodigo()==null) return null;
            return String.valueOf(((Segmento)value).getCodigo());
        } catch (Exception ex) {
            throw new ConverterException(new FacesMessage(String.format("Não foi possível determinar o segmento de código %s", value)), ex);
        }
    }        
}
