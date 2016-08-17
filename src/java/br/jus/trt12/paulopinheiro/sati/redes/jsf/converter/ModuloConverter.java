package br.jus.trt12.paulopinheiro.sati.redes.jsf.converter;

import br.jus.trt12.paulopinheiro.sati.redes.model.Modulo;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converteModulo", forClass = Modulo.class)
public class ModuloConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) return null;

        int id = Integer.parseInt(value);
        UISelectOne soModulo = (UISelectOne) component;

        return moduloById(listaModulos(soModulo), id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof String) return "-1";

        Modulo modulo = (Modulo) value;

        return String.valueOf(modulo.getCodigo());
    }

    private Modulo moduloById(List<Modulo> listaModulos, int id) {
        Modulo resposta = null;

        for (Modulo u : listaModulos) {
            if (u.getCodigo() == id) {
                resposta = u;
                break;
            }
        }
        return resposta;
    }

    private List<Modulo> listaModulos(UISelectOne selectOne) {
        UISelectItems siModulo = null;

        for (UIComponent ui : selectOne.getChildren()) {
            if (ui instanceof UISelectItems) {
                siModulo = (UISelectItems) ui;
                break;
            }
        }
        if (siModulo == null) {
            throw new RuntimeException("Problemas para validar objeto Modulo");
        }

        return (List<Modulo>) siModulo.getValue();
    }    
}
