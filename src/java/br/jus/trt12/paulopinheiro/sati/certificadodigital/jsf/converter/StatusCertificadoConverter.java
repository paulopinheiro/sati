package br.jus.trt12.paulopinheiro.sati.certificadodigital.jsf.converter;

import br.jus.trt12.paulopinheiro.sati.certificadodigital.model.StatusCertificado;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converteStatusCertificado", forClass = StatusCertificado.class)
public class StatusCertificadoConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) return null;

        int id = Integer.parseInt(value);
        UISelectOne soStatus = (UISelectOne) component;

        return statusById(listaStatus(soStatus), id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof String) return "-1";

        StatusCertificado status = (StatusCertificado) value;

        return String.valueOf(status.getId());
    }

    private StatusCertificado statusById(List<StatusCertificado> listaStatus, int id) {
        StatusCertificado resposta = null;

        for (StatusCertificado u : listaStatus) {
            if (u.getId() == id) {
                resposta = u;
                break;
            }
        }
        return resposta;
    }

    private List<StatusCertificado> listaStatus(UISelectOne selectOne) {
        UISelectItems siStatus = null;

        for (UIComponent ui : selectOne.getChildren()) {
            if (ui instanceof UISelectItems) {
                siStatus = (UISelectItems) ui;
                break;
            }
        }
        if (siStatus == null) {
            throw new RuntimeException("Problemas para validar objeto StatusCertificado");
        }

        return (List<StatusCertificado>) siStatus.getValue();
    }    
}
