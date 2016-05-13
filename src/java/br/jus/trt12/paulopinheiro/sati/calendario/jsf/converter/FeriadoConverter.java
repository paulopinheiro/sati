package br.jus.trt12.paulopinheiro.sati.calendario.jsf.converter;

import br.jus.trt12.paulopinheiro.sati.calendario.model.Feriado;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converteFeriado", forClass = Feriado.class)
public class FeriadoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) return null;

        int id = Integer.parseInt(value);
        UISelectOne soFeriado = (UISelectOne) component;

        return feriadoById(listaFeriados(soFeriado), id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof String) return "-1";

        Feriado feriado = (Feriado) value;

        return String.valueOf(feriado.getCodigo());
    }

    private Feriado feriadoById(List<Feriado> listaFeriados, int id) {
        Feriado resposta = null;

        for (Feriado f : listaFeriados) {
            if (f.getCodigo() == id) {
                resposta = f;
                break;
            }
        }
        return resposta;
    }

    private List<Feriado> listaFeriados(UISelectOne selectOne) {
        UISelectItems siFeriado = null;

        for (UIComponent ui : selectOne.getChildren()) {
            if (ui instanceof UISelectItems) {
                siFeriado = (UISelectItems) ui;
                break;
            }
        }
        if (siFeriado == null) {
            throw new RuntimeException("Problemas para validar objeto Feriado");
        }

        return (List<Feriado>) siFeriado.getValue();
    }
}
