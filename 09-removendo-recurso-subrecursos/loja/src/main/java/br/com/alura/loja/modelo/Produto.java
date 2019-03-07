package br.com.alura.loja.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Produto {

	private long id;
	private String nome;
	private double preco;
	private int quantidade;

}
