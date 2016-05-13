package br.jus.trt12.paulopinheiro.sati.equipamentos.jsf.converter;

import br.jus.trt12.paulopinheiro.sati.equipamentos.model.Equipamento;
import java.util.Iterator;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

@FacesConverter(value = "converteEquipamentoPL", forClass = Equipamento.class)
public class EquipamentoConverterPL implements Converter {

    /**
     *
     * @param context
     * @param component
     * @param value
     * @return
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Object ret = null;
        if (component instanceof PickList) {
            Object dualList = ((PickList) component).getValue();
            DualListModel dl = (DualListModel) dualList;
            for (Iterator iterator = dl.getSource().iterator(); iterator.hasNext();) {
                Object o = iterator.next();
                String id = (new StringBuilder()).append(((Equipamento) o).getCodigo()).toString();
                if (value.equals(id)) {
                    ret = o;
                    break;
                }
            }

            if (ret == null) {
                for (Iterator iterator1 = dl.getTarget().iterator(); iterator1.hasNext();) {
                    Object o = iterator1.next();
                    String id = (new StringBuilder()).append(
                            ((Equipamento) o).getCodigo()).toString();
                    if (value.equals(id)) {
                        ret = o;
                        break;
                    }
                }

            }
        }
        return ret;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String str = "";
        if (value instanceof Equipamento) {
            str = ((Equipamento) value).getCodigo().toString();
        }
        return str;
    }
}
