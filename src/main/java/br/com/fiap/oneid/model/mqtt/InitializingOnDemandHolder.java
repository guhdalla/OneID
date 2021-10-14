package br.com.fiap.oneid.model.mqtt;

import java.util.HashMap;
import java.util.Map;

public final class InitializingOnDemandHolder{

    Map<String, Integer> valuesCompact = new HashMap<>();

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
    public void setContext(String codigoDispositivo, Integer value){
        getInstance().valuesCompact.put(codigoDispositivo, value);
    }

    public Map<String, Integer> getContext(){
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
