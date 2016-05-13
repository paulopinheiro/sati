package br.jus.trt12.paulopinheiro.sati.geral.jsf.comum;

import java.util.List;

public abstract class AbListaRestritaMB<T> extends AbListaMB<T> {
    protected abstract List<T> getListaRestrita();

    @Override
    protected List<T> getLista() {
        if (this.listaNula()) setLista(this.getListaRestrita());
        return super.getLista();
    }
}
