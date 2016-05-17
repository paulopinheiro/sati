package br.jus.trt12.paulopinheiro.sati.equipamentos.jsf.converter;

import br.jus.trt12.paulopinheiro.sati.equipamentos.ejb.HistoricoFacade;
import br.jus.trt12.paulopinheiro.sati.equipamentos.model.Historico;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

@Model
public class HistoricoConverterMB implements Converter, Serializable {
    @EJB private HistoricoFacade historicoFacade;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            if (value==null) return null;
            return (Historico) historicoFacade.find(Integer.valueOf(value));
        } catch (Exception ex) {
            throw new ConverterException(new FacesMessage(String.format("Não foi possível determinar o histórico de código %s", value)), ex);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            if (!(value instanceof Historico)||((Historico)value).getCodigo()==null) return null;
            return String.valueOf(((Historico)value).getCodigo());
        } catch (Exception ex) {
            throw new ConverterException(new FacesMessage(String.format("Não foi possível determinar o equipamento de código %s", value)), ex);
        }
    }

}
