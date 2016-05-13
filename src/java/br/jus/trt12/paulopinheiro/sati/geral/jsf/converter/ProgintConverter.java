package br.jus.trt12.paulopinheiro.sati.geral.jsf.converter;

import br.jus.trt12.paulopinheiro.sati.geral.model.Progint;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converteProgint", forClass = Progint.class)
public class ProgintConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) return null;

        int id = Integer.parseInt(value);
        UISelectOne soProgint = (UISelectOne) component;

        return progintById(listaProgints(soProgint), id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof String) return "-1";

        Progint progint = (Progint) value;

        return String.valueOf(progint.getCodigo());
    }

    private Progint progintById(List<Progint> listaProgints, int id) {
        Progint resposta = null;

        for (Progint p : listaProgints) {
            if (p.getCodigo() == id) {
                resposta = p;
                break;
            }
        }
        return resposta;
    }

    private List<Progint> listaProgints(UISelectOne selectOne) {
        UISelectItems siProgint = null;

        for (UIComponent ui : selectOne.getChildren()) {
            if (ui instanceof UISelectItems) {
                siProgint = (UISelectItems) ui;
                break;
            }
        }
        if (siProgint == null) {
            throw new RuntimeException("Problemas para validar objeto Progint");
        }

        return (List<Progint>) siProgint.getValue();
    }
}
