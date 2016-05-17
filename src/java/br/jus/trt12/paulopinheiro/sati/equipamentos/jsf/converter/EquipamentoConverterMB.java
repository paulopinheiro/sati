package br.jus.trt12.paulopinheiro.sati.equipamentos.jsf.converter;

import br.jus.trt12.paulopinheiro.sati.equipamentos.ejb.EquipamentoFacade;
import br.jus.trt12.paulopinheiro.sati.equipamentos.model.Equipamento;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

@Model
public class EquipamentoConverterMB implements Converter, Serializable {
    @EJB private EquipamentoFacade equipamentoFacade;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            if (value==null) return null;
            return (Equipamento) equipamentoFacade.find(Integer.valueOf(value));
        } catch (Exception ex) {
            throw new ConverterException(new FacesMessage(String.format("Não foi possível determinar o equipamento de código %s", value)), ex);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            if (!(value instanceof Equipamento)||((Equipamento)value).getCodigo()==null) return null;
            return String.valueOf(((Equipamento)value).getCodigo());
        } catch (Exception ex) {
            throw new ConverterException(new FacesMessage(String.format("Não foi possível determinar o equipamento de código %s", value)), ex);
        }
    }
    
}
