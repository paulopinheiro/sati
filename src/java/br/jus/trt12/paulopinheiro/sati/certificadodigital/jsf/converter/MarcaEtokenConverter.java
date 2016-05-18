package br.jus.trt12.paulopinheiro.sati.certificadodigital.jsf.converter;

import br.jus.trt12.paulopinheiro.sati.certificadodigital.model.MarcaEtoken;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converteMarcaEtoken", forClass = MarcaEtoken.class)
public class MarcaEtokenConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) return null;

        int id = Integer.parseInt(value);
        UISelectOne soMarca = (UISelectOne) component;

        return marcaById(listaMarcas(soMarca), id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof String) return "-1";

        MarcaEtoken marca = (MarcaEtoken) value;

        return String.valueOf(marca.getId());
    }

    private MarcaEtoken marcaById(List<MarcaEtoken> listaMarcas, int id) {
        MarcaEtoken resposta = null;

        for (MarcaEtoken u : listaMarcas) {
            if (u.getId() == id) {
                resposta = u;
                break;
            }
        }
        return resposta;
    }

    private List<MarcaEtoken> listaMarcas(UISelectOne selectOne) {
        UISelectItems siMarca = null;

        for (UIComponent ui : selectOne.getChildren()) {
            if (ui instanceof UISelectItems) {
                siMarca = (UISelectItems) ui;
                break;
            }
        }
        if (siMarca == null) {
            throw new RuntimeException("Problemas para validar objeto MarcaEtoken");
        }

        return (List<MarcaEtoken>) siMarca.getValue();
    }    
}
