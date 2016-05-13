package br.jus.trt12.paulopinheiro.sati.geral.jsf.converter;

import br.jus.trt12.paulopinheiro.sati.geral.model.AreaTI;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converteAreaTI", forClass = AreaTI.class)
public class AreaTIConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) return null;

        int id = Integer.parseInt(value);
        UISelectOne soAreaTI = (UISelectOne) component;

        return areaTIById(listaAreasTI(soAreaTI), id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof String) return "-1";

        AreaTI areaTI = (AreaTI) value;

        return String.valueOf(areaTI.getCodigo());
    }

    private AreaTI areaTIById(List<AreaTI> listaAreasTI, int id) {
        AreaTI resposta = null;

        for (AreaTI a : listaAreasTI) {
            if (a.getCodigo() == id) {
                resposta = a;
                break;
            }
        }
        return resposta;
    }

    private List<AreaTI> listaAreasTI(UISelectOne selectOne) {
        UISelectItems siAreaTI = null;

        for (UIComponent ui : selectOne.getChildren()) {
            if (ui instanceof UISelectItems) {
                siAreaTI = (UISelectItems) ui;
                break;
            }
        }
        if (siAreaTI == null) {
            throw new RuntimeException("Problemas para validar objeto AreaTI");
        }

        return (List<AreaTI>) siAreaTI.getValue();
    }
}
