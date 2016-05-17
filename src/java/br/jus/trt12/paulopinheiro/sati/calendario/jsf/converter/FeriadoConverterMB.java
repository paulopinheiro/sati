package br.jus.trt12.paulopinheiro.sati.calendario.jsf.converter;

import br.jus.trt12.paulopinheiro.sati.calendario.ejb.FeriadoFacade;
import br.jus.trt12.paulopinheiro.sati.calendario.model.Feriado;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;


@Model
public class FeriadoConverterMB  implements Converter, Serializable  {
    @EJB private FeriadoFacade feriadoFacade;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            if (value==null) return null;
            return (Feriado) feriadoFacade.find(Integer.valueOf(value));
        } catch (Exception ex) {
            throw new ConverterException(new FacesMessage(String.format("Não foi possível determinar o feriado de código %s", value)), ex);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            if (!(value instanceof Feriado)||((Feriado)value).getCodigo()==null) return null;
            return String.valueOf(((Feriado)value).getCodigo());
        } catch (Exception ex) {
            throw new ConverterException(new FacesMessage(String.format("Não foi possível determinar o feriado de código %s", value)), ex);
        }
    }

}
