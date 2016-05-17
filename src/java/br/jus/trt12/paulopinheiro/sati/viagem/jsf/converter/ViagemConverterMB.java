package br.jus.trt12.paulopinheiro.sati.viagem.jsf.converter;

import br.jus.trt12.paulopinheiro.sati.viagem.ejb.ViagemFacade;
import br.jus.trt12.paulopinheiro.sati.viagem.model.Viagem;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

@Model
public class ViagemConverterMB implements Converter, Serializable {
    @EJB private ViagemFacade viagemFacade;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            if (value==null) return null;
            return (Viagem) viagemFacade.find(Long.valueOf(value));
        } catch (Exception ex) {
            throw new ConverterException(new FacesMessage(String.format("Não foi possível determinar a viagem de código %s", value)), ex);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            if (!(value instanceof Viagem)||((Viagem)value).getCodigo()==null) return null;
            return String.valueOf(((Viagem)value).getCodigo());
        } catch (Exception ex) {
            throw new ConverterException(new FacesMessage(String.format("Não foi possível determinar a viagem de código %s", value)), ex);
        }
    }
    
}
