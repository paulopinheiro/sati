package br.jus.trt12.paulopinheiro.sati.geral.jsf.converter;

import br.jus.trt12.paulopinheiro.sati.geral.model.Unidade;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converteUnidade", forClass = Unidade.class)
public class UnidadeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) return null;

        int id = Integer.parseInt(value);
        UISelectOne soUnidade = (UISelectOne) component;

        return unidadeById(listaUnidades(soUnidade), id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof String) return "-1";

        Unidade unidade = (Unidade) value;

        return String.valueOf(unidade.getCodigo());
    }

    private Unidade unidadeById(List<Unidade> listaUnidades, int id) {
        Unidade resposta = null;

        for (Unidade u : listaUnidades) {
            if (u.getCodigo() == id) {
                resposta = u;
                break;
            }
        }
        return resposta;
    }

    private List<Unidade> listaUnidades(UISelectOne selectOne) {
        UISelectItems siUnidade = null;

        for (UIComponent ui : selectOne.getChildren()) {
            if (ui instanceof UISelectItems) {
                siUnidade = (UISelectItems) ui;
                break;
            }
        }
        if (siUnidade == null) {
            throw new RuntimeException("Problemas para validar objeto Unidade");
        }

        return (List<Unidade>) siUnidade.getValue();
    }
}
