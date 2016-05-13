package br.jus.trt12.paulopinheiro.sati.equipamentos.jsf.converter;

import br.jus.trt12.paulopinheiro.sati.equipamentos.model.Modelo;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converteModelo", forClass = Modelo.class)
public class ModeloConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) return null;

        int id = Integer.parseInt(value);
        UISelectOne soModelo = (UISelectOne) component;

        return modeloById(listaModelos(soModelo), id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof String) return "-1";

        Modelo modelo = (Modelo) value;

        return String.valueOf(modelo.getCodigo());
    }

    private Modelo modeloById(List<Modelo> listaModelos, int id) {
        Modelo resposta = null;

        for (Modelo t : listaModelos) {
            if (t.getCodigo() == id) {
                resposta = t;
                break;
            }
        }
        return resposta;
    }

    private List<Modelo> listaModelos(UISelectOne selectOne) {
        UISelectItems siModelo = null;

        for (UIComponent ui : selectOne.getChildren()) {
            if (ui instanceof UISelectItems) {
                siModelo = (UISelectItems) ui;
                break;
            }
        }
        if (siModelo == null) {
            throw new RuntimeException("Problemas para validar objeto Modelo");
        }

        return (List<Modelo>) siModelo.getValue();
    }
}
