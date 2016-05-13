package br.jus.trt12.paulopinheiro.sati.calendario.jsf.converter;

import br.jus.trt12.paulopinheiro.sati.calendario.ejb.TransferenciaFacade;
import br.jus.trt12.paulopinheiro.sati.calendario.model.Transferencia;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

@ManagedBean
@RequestScoped
public class TransferenciaConverterMB  implements Converter, Serializable {
    @EJB private TransferenciaFacade transferenciaFacade;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            if (value==null) return null;
            return (Transferencia) transferenciaFacade.find(Integer.valueOf(value));
        } catch (Exception ex) {
            throw new ConverterException(new FacesMessage(String.format("Não foi possível determinar a transferência de código %s", value)), ex);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            if (!(value instanceof Transferencia)||((Transferencia)value).getCodigo()==null) return null;
            return String.valueOf(((Transferencia)value).getCodigo());
        } catch (Exception ex) {
            throw new ConverterException(new FacesMessage(String.format("Não foi possível determinar a transferência de código %s", value)), ex);
        }
    }
    
}
