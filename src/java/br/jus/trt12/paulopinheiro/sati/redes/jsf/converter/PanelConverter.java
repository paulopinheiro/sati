package br.jus.trt12.paulopinheiro.sati.redes.jsf.converter;

import br.jus.trt12.paulopinheiro.sati.redes.model.Panel;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "convertePanel", forClass = Panel.class)
public class PanelConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) return null;

        int id = Integer.parseInt(value);
        UISelectOne soPanel = (UISelectOne) component;

        return panelById(listaPanels(soPanel), id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof String) return "-1";

        Panel panel = (Panel) value;

        return String.valueOf(panel.getCodigo());
    }

    private Panel panelById(List<Panel> listaPanels, int id) {
        Panel resposta = null;

        for (Panel u : listaPanels) {
            if (u.getCodigo() == id) {
                resposta = u;
                break;
            }
        }
        return resposta;
    }

    private List<Panel> listaPanels(UISelectOne selectOne) {
        UISelectItems siPanel = null;

        for (UIComponent ui : selectOne.getChildren()) {
            if (ui instanceof UISelectItems) {
                siPanel = (UISelectItems) ui;
                break;
            }
        }
        if (siPanel == null) {
            throw new RuntimeException("Problemas para validar objeto Panel");
        }

        return (List<Panel>) siPanel.getValue();
    }    
}
