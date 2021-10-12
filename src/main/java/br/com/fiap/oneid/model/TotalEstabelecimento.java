package br.com.fiap.oneid.model;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class TotalEstabelecimento {

	

	@Min(0)
	private int numero;
}
