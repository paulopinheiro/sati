package br.jus.trt12.paulopinheiro.sati.viagem.jsf.converter;

import br.jus.trt12.paulopinheiro.sati.viagem.model.TipoEventoReqViagem;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converteTipoEventoReqViagem", forClass = TipoEventoReqViagem.class)
public class TipoEventoReqViagemConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) return null;

        int id = Integer.parseInt(value);
        UISelectOne soTipoEventoReqViagem = (UISelectOne) component;

        return tipoEventoReqViagemById(listaTiposEventoReqViagem(soTipoEventoReqViagem), id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof String) return "-1";

        TipoEventoReqViagem tipoEventoReqViagem = (TipoEventoReqViagem) value;

        return String.valueOf(tipoEventoReqViagem.getCodigo());
    }

    private TipoEventoReqViagem tipoEventoReqViagemById(List<TipoEventoReqViagem> listaTiposEventoReqViagem, int id) {
        TipoEventoReqViagem resposta = null;

        for (TipoEventoReqViagem tp : listaTiposEventoReqViagem) {
            if (tp.getCodigo() == id) {
                resposta = tp;
                break;
            }
        }
        return resposta;
    }

    private List<TipoEventoReqViagem> listaTiposEventoReqViagem(UISelectOne selectOne) {
        UISelectItems siTipoEventoReqViagem = null;

        for (UIComponent ui : selectOne.getChildren()) {
            if (ui instanceof UISelectItems) {
                siTipoEventoReqViagem = (UISelectItems) ui;
                break;
            }
        }
        if (siTipoEventoReqViagem == null) {
            throw new RuntimeException("Problemas para validar objeto TipoEventoReqViagem");
        }

        return (List<TipoEventoReqViagem>) siTipoEventoReqViagem.getValue();
    }
}
