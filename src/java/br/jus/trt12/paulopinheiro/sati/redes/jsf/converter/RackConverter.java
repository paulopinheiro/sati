package br.jus.trt12.paulopinheiro.sati.redes.jsf.converter;

import br.jus.trt12.paulopinheiro.sati.redes.model.Rack;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converteRack", forClass = Rack.class)
public class RackConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) return null;

        int id = Integer.parseInt(value);
        UISelectOne soRack = (UISelectOne) component;

        return rackById(listaRacks(soRack), id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof String) return "-1";

        Rack rack = (Rack) value;

        return String.valueOf(rack.getCodigo());
    }

    private Rack rackById(List<Rack> listaRacks, int id) {
        Rack resposta = null;

        for (Rack u : listaRacks) {
            if (u.getCodigo() == id) {
                resposta = u;
                break;
            }
        }
        return resposta;
    }

    private List<Rack> listaRacks(UISelectOne selectOne) {
        UISelectItems siRack = null;

        for (UIComponent ui : selectOne.getChildren()) {
            if (ui instanceof UISelectItems) {
                siRack = (UISelectItems) ui;
                break;
            }
        }
        if (siRack == null) {
            throw new RuntimeException("Problemas para validar objeto Rack");
        }

        return (List<Rack>) siRack.getValue();
    }    
}
