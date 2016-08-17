package br.jus.trt12.paulopinheiro.sati.redes.jsf.converter;

import br.jus.trt12.paulopinheiro.sati.redes.model.Tomada;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converteTomada", forClass = Tomada.class)
public class TomadaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) return null;

        int id = Integer.parseInt(value);
        UISelectOne soTomada = (UISelectOne) component;

        return tomadaById(listaTomadas(soTomada), id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof String) return "-1";

        Tomada tomada = (Tomada) value;

        return String.valueOf(tomada.getCodigo());
    }

    private Tomada tomadaById(List<Tomada> listaTomadas, int id) {
        Tomada resposta = null;

        for (Tomada u : listaTomadas) {
            if (u.getCodigo() == id) {
                resposta = u;
                break;
            }
        }
        return resposta;
    }

    private List<Tomada> listaTomadas(UISelectOne selectOne) {
        UISelectItems siTomada = null;

        for (UIComponent ui : selectOne.getChildren()) {
            if (ui instanceof UISelectItems) {
                siTomada = (UISelectItems) ui;
                break;
            }
        }
        if (siTomada == null) {
            throw new RuntimeException("Problemas para validar objeto Tomada");
        }

        return (List<Tomada>) siTomada.getValue();
    }    
}
