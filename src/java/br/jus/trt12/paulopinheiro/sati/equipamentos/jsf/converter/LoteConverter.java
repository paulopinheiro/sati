package br.jus.trt12.paulopinheiro.sati.equipamentos.jsf.converter;

import br.jus.trt12.paulopinheiro.sati.equipamentos.model.Lote;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converteLote", forClass = Lote.class)
public class LoteConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) return null;

        int id = Integer.parseInt(value);
        UISelectOne soLote = (UISelectOne) component;

        return loteById(listaLotes(soLote), id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof String) return "-1";

        Lote lote = (Lote) value;

        return String.valueOf(lote.getCodigo());
    }

    private Lote loteById(List<Lote> listaLotes, int id) {
        Lote resposta = null;

        for (Lote t : listaLotes) {
            if (t.getCodigo() == id) {
                resposta = t;
                break;
            }
        }
        return resposta;
    }

    private List<Lote> listaLotes(UISelectOne selectOne) {
        UISelectItems siLote = null;

        for (UIComponent ui : selectOne.getChildren()) {
            if (ui instanceof UISelectItems) {
                siLote = (UISelectItems) ui;
                break;
            }
        }
        if (siLote == null) {
            throw new RuntimeException("Problemas para validar objeto Lote");
        }

        return (List<Lote>) siLote.getValue();
    }
}
