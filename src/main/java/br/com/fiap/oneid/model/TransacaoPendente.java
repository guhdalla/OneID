package br.com.fiap.oneid.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoPendente {
	
	@NotNull
	@Min(1)
    private float valorTransacao;
	
	@NotBlank
    private String codigoDispositivo;
}
