package br.jus.trt12.paulopinheiro.sati.geral.jsf.converter;

import br.jus.trt12.paulopinheiro.sati.geral.ejb.MunicipioFacade;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

@Model
public class MunicipioConverterMB implements Converter, Serializable {
    @EJB private MunicipioFacade municipioFacade;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            if (value==null) return null;
            return (Municipio) municipioFacade.find(Integer.valueOf(value));
        } catch (Exception ex) {
            throw new ConverterException(new FacesMessage(String.format("Não foi possível determinar o município de código %s", value)), ex);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            if (!(value instanceof Municipio)||((Municipio)value).getCodigo()==null) return null;
            return String.valueOf(((Municipio)value).getCodigo());
        } catch (Exception ex) {
            throw new ConverterException(new FacesMessage(String.format("Não foi possível determinar o município de código %s", value)), ex);
        }
    }        
}
