package br.jus.trt12.paulopinheiro.sati.equipamentos.jsf.converter;

import br.jus.trt12.paulopinheiro.sati.equipamentos.ejb.ModeloFacade;
import br.jus.trt12.paulopinheiro.sati.equipamentos.model.Modelo;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

@Model
public class ModeloConverterMB implements Converter, Serializable {
@EJB private ModeloFacade modeloFacade;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            if (value==null) return null;
            return (Modelo) modeloFacade.find(Integer.valueOf(value));
        } catch (Exception ex) {
            throw new ConverterException(new FacesMessage(String.format("Não foi possível determinar o modelo de código %s", value)), ex);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            if (!(value instanceof Modelo)||((Modelo)value).getCodigo()==null) return null;
            return String.valueOf(((Modelo)value).getCodigo());
        } catch (Exception ex) {
            throw new ConverterException(new FacesMessage(String.format("Não foi possível determinar o modelo de código %s", value)), ex);
        }
    }
    
}
