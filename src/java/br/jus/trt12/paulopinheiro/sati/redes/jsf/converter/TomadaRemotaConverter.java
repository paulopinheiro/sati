package br.jus.trt12.paulopinheiro.sati.redes.jsf.converter;

import br.jus.trt12.paulopinheiro.sati.redes.model.TomadaRemota;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converteTomadaRemota", forClass = TomadaRemota.class)
public class TomadaRemotaConverter implements Converter {

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

        TomadaRemota tomada = (TomadaRemota) value;

        return String.valueOf(tomada.getCodigo());
    }

    private TomadaRemota tomadaById(List<TomadaRemota> listaTomadas, int id) {
        TomadaRemota resposta = null;

        for (TomadaRemota u : listaTomadas) {
            if (u.getCodigo() == id) {
                resposta = u;
                break;
            }
        }
        return resposta;
    }

    private List<TomadaRemota> listaTomadas(UISelectOne selectOne) {
        UISelectItems siTomada = null;

        for (UIComponent ui : selectOne.getChildren()) {
            if (ui instanceof UISelectItems) {
                siTomada = (UISelectItems) ui;
                break;
            }
        }
        if (siTomada == null) {
            throw new RuntimeException("Problemas para validar objeto TomadaRemota");
        }

        return (List<TomadaRemota>) siTomada.getValue();
    }    
}
