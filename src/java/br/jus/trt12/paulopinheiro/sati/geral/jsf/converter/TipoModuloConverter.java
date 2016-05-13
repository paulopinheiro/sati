package br.jus.trt12.paulopinheiro.sati.geral.jsf.converter;

import br.jus.trt12.paulopinheiro.sati.redes.model.TipoModulo;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converteTipoModulo", forClass = TipoModulo.class)
public class TipoModuloConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) return null;

        int id = Integer.parseInt(value);
        UISelectOne soTipoModulo = (UISelectOne) component;

        return tipoModuloById(listaTiposModulo(soTipoModulo), id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof String) return "-1";

        TipoModulo tipoModulo = (TipoModulo) value;

        return String.valueOf(tipoModulo.getCodigo());
    }

    private TipoModulo tipoModuloById(List<TipoModulo> listaTiposModulo, int id) {
        TipoModulo resposta = null;

        for (TipoModulo tp : listaTiposModulo) {
            if (tp.getCodigo() == id) {
                resposta = tp;
                break;
            }
        }
        return resposta;
    }

    private List<TipoModulo> listaTiposModulo(UISelectOne selectOne) {
        UISelectItems siTipoModulo = null;

        for (UIComponent ui : selectOne.getChildren()) {
            if (ui instanceof UISelectItems) {
                siTipoModulo = (UISelectItems) ui;
                break;
            }
        }
        if (siTipoModulo == null) {
            throw new RuntimeException("Problemas para validar objeto TipoModulo");
        }

        return (List<TipoModulo>) siTipoModulo.getValue();
    }
}
