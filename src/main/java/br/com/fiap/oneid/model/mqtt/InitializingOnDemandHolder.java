package br.com.fiap.oneid.model.mqtt;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public boolean verifyContextExist(String cdDispositivo){
        return getContext().stream().anyMatch(x -> x.getCodigoDispositivo().equals(cdDispositivo));
    }
    
    public Optional<TransacaoPendente> getContextDetail(String cdDispositivo) {
    	return getContext().stream().filter(x -> x.getCodigoDispositivo().equals(cdDispositivo)).findFirst();
    }
    
    public boolean removeContextDetail(TransacaoPendente transacaoPendente) {
    	return this.valuesCompact.remove(transacaoPendente);
    }


    /**
     * Provides the lazy-loaded Singleton instance.
     */
    private static class HelperHolder {
        private static final InitializingOnDemandHolder INSTANCE =
                new InitializingOnDemandHolder();
    }


}
