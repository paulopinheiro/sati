package br.jus.trt12.paulopinheiro.sati.geral.jsf.converter;

import br.jus.trt12.paulopinheiro.sati.geral.model.UsuarioFinal;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converteUsuarioFinal", forClass = UsuarioFinal.class)
public class UsuarioFinalConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) return null;

        int id = Integer.parseInt(value);
        UISelectOne soUsuario = (UISelectOne) component;

        return usuarioById(listaUsuarios(soUsuario), id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof String) return "-1";

        UsuarioFinal usuario = (UsuarioFinal) value;

        return String.valueOf(usuario.getId());
    }

    private UsuarioFinal usuarioById(List<UsuarioFinal> listaUsuarios, int id) {
        UsuarioFinal resposta = null;

        for (UsuarioFinal u : listaUsuarios) {
            if (u.getId() == id) {
                resposta = u;
                break;
            }
        }
        return resposta;
    }

    private List<UsuarioFinal> listaUsuarios(UISelectOne selectOne) {
        UISelectItems siUsuario = null;

        for (UIComponent ui : selectOne.getChildren()) {
            if (ui instanceof UISelectItems) {
                siUsuario = (UISelectItems) ui;
                break;
            }
        }
        if (siUsuario == null) {
            throw new RuntimeException("Problemas para validar objeto UsuarioFinal");
        }

        return (List<UsuarioFinal>) siUsuario.getValue();
    }    
}
