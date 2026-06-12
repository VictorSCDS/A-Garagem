package entities;

import java.util.Objects;

public class Veiculo {
	private int id;
	private String placa;
	private String marca;
	private String modelo;
	
	public Veiculo() {}

	public Veiculo(String placa, String marca, String modelo) {
		this.placa = placa;
		this.marca = marca;
		this.modelo = modelo;
	}

	public Veiculo(int id, String placa, String marca, String modelo) {
		this.id = id;
		this.placa = placa;
		this.marca = marca;
		this.modelo = modelo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, marca, modelo, placa);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Veiculo other = (Veiculo) obj;
		return id == other.id && Objects.equals(marca, other.marca) && Objects.equals(modelo, other.modelo)
				&& Objects.equals(placa, other.placa);
	}

	@Override
	public String toString() {
		return "Veiculo [id=" + id + ", placa=" + placa + ", marca=" + marca + ", modelo=" + modelo + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
}
