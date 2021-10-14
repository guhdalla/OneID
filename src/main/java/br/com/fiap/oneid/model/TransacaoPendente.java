package br.com.fiap.oneid.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoPendente {
    private int value;
    private String codigoDispositivo;
}
