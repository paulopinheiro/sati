package br.jus.trt12.paulopinheiro.sati.geral.jsf.converter;

import br.jus.trt12.paulopinheiro.sati.redes.model.TipoConector;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converteTipoConector", forClass = TipoConector.class)
public class TipoConectorConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) return null;

        int id = Integer.parseInt(value);
        UISelectOne soTipoConector = (UISelectOne) component;

        return tipoConectorById(listaTiposConector(soTipoConector), id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof String) return "-1";

        TipoConector tipoConector = (TipoConector) value;

        return String.valueOf(tipoConector.getCodigo());
    }

    private TipoConector tipoConectorById(List<TipoConector> listaTiposConector, int id) {
        TipoConector resposta = null;

        for (TipoConector tp : listaTiposConector) {
            if (tp.getCodigo() == id) {
                resposta = tp;
                break;
            }
        }
        return resposta;
    }

    private List<TipoConector> listaTiposConector(UISelectOne selectOne) {
        UISelectItems siTipoConector = null;

        for (UIComponent ui : selectOne.getChildren()) {
            if (ui instanceof UISelectItems) {
                siTipoConector = (UISelectItems) ui;
                break;
            }
        }
        if (siTipoConector == null) {
            throw new RuntimeException("Problemas para validar objeto TipoConector");
        }

        return (List<TipoConector>) siTipoConector.getValue();
    }
}
