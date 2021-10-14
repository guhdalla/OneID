package br.com.fiap.oneid.model.mqtt;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.oneid.model.TransacaoPendente;

public final class InitializingOnDemandHolder{

    List<TransacaoPendente> valuesCompact = new ArrayList<TransacaoPendente>();

    /**
     * Private constructor.
     */
    private InitializingOnDemandHolder() {
    }

    /**
     * Singleton instance.
     *
     * @return Singleton instance
     */
    public static InitializingOnDemandHolder getInstance() {
        return HelperHolder.INSTANCE;
    }

    /**
     *  @param codigoDispositivo
     * @param value
     */
    public void setContext(TransacaoPendente transacaoPendente){
        this.valuesCompact.add(transacaoPendente);
    }

    public List<TransacaoPendente> getContext(){
        return this.valuesCompact;
    }


    /**
     * Provides the lazy-loaded Singleton instance.
     */
    private static class HelperHolder {
        private static final InitializingOnDemandHolder INSTANCE =
                new InitializingOnDemandHolder();
    }


}
