package br.jus.trt12.paulopinheiro.sati.equipamentos.jsf.converter;

import br.jus.trt12.paulopinheiro.sati.equipamentos.model.TipoEquipamento;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converteTipoEquipamento", forClass = TipoEquipamento.class)
public class TipoEquipamentoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) return null;

        int id = Integer.parseInt(value);
        UISelectOne soTipoEquipamento = (UISelectOne) component;

        return tipoEquipamentoById(listaTiposEquipamento(soTipoEquipamento), id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof String) return "-1";

        TipoEquipamento tipoEquipamento = (TipoEquipamento) value;

        return String.valueOf(tipoEquipamento.getCodigo());
    }

    private TipoEquipamento tipoEquipamentoById(List<TipoEquipamento> listaTiposEquipamento, int id) {
        TipoEquipamento resposta = null;

        for (TipoEquipamento t : listaTiposEquipamento) {
            if (t.getCodigo() == id) {
                resposta = t;
                break;
            }
        }
        return resposta;
    }

    private List<TipoEquipamento> listaTiposEquipamento(UISelectOne selectOne) {
        UISelectItems siTipoEquipamento = null;

        for (UIComponent ui : selectOne.getChildren()) {
            if (ui instanceof UISelectItems) {
                siTipoEquipamento = (UISelectItems) ui;
                break;
            }
        }
        if (siTipoEquipamento == null) {
            throw new RuntimeException("Problemas para validar objeto TipoEquipamento");
        }

        return (List<TipoEquipamento>) siTipoEquipamento.getValue();
    }
}
