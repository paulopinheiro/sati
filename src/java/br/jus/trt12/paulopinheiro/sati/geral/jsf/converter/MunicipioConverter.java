package br.jus.trt12.paulopinheiro.sati.geral.jsf.converter;

import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converteMunicipio", forClass = Municipio.class)
public class MunicipioConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) return null;

        int id = Integer.parseInt(value);
        UISelectOne soMunicipio = (UISelectOne) component;

        return municipioById(listaMunicipios(soMunicipio), id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof String) return "-1";

        Municipio municipio = (Municipio) value;

        return String.valueOf(municipio.getCodigo());
    }

    private Municipio municipioById(List<Municipio> listaMunicipios, int id) {
        Municipio resposta = null;

        for (Municipio m : listaMunicipios) {
            if (m.getCodigo() == id) {
                resposta = m;
                break;
            }
        }
        return resposta;
    }

    private List<Municipio> listaMunicipios(UISelectOne selectOne) {
        UISelectItems siMunicipio = null;

        for (UIComponent ui : selectOne.getChildren()) {
            if (ui instanceof UISelectItems) {
                siMunicipio = (UISelectItems) ui;
                break;
            }
        }
        if (siMunicipio == null) {
            throw new RuntimeException("Problemas para validar objeto Municipio");
        }

        return (List<Municipio>) siMunicipio.getValue();
    }
}
