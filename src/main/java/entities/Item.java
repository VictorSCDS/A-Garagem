package entities;

import java.math.BigDecimal;
import java.util.Objects;

public class Item {
	private int id;
	private String nome;
	private int codigo;
	private String marca;
	private int quantidade;
	private BigDecimal valorCompra;
	private BigDecimal valorVenda;
	
	public Item() {}
	
	public Item(String nome, int codigo, String marca, int quantidade, BigDecimal valorCompra, BigDecimal valorVenda) {
		this.nome = nome;
		this.codigo = codigo;
		this.marca = marca;
		this.quantidade = quantidade;
		this.valorCompra = valorCompra;
		this.valorVenda = valorVenda;
	}

	public Item(int id, String nome, int codigo, String marca, int quantidade, BigDecimal valorCompra, BigDecimal valorVenda) {
		this.id = id;
		this.nome = nome;
		this.codigo = codigo;
		this.marca = marca;
		this.quantidade = quantidade;
		this.valorCompra = valorCompra;
		this.valorVenda = valorVenda;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo, id, marca, nome, quantidade, valorCompra, valorVenda);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return codigo == other.codigo && id == other.id && Objects.equals(marca, other.marca)
				&& Objects.equals(nome, other.nome) && quantidade == other.quantidade
				&& Objects.equals(valorCompra, other.valorCompra) && Objects.equals(valorVenda, other.valorVenda);
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", nome=" + nome + ", codigo=" + codigo + ", marca=" + marca + ", quantidade="
				+ quantidade + ", valorCompra=" + valorCompra + ", valorVenda=" + valorVenda + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorCompra() {
		return valorCompra;
	}

	public void setValorCompra(BigDecimal valorCompra) {
		this.valorCompra = valorCompra;
	}

	public BigDecimal getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(BigDecimal valorVenda) {
		this.valorVenda = valorVenda;
	}
	
}
