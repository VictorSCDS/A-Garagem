package entities;

import java.util.Objects;

public class Cliente {
	private int id;
	private String nome;
	private String cpf;
	private String telefone;
	private String email;
	
	public Cliente() {}
	
	public Cliente(String nome, String cpf, String telefone, String email) {
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
		this.email = email;
	}

	public Cliente(int id, String nome, String cpf, String telefone, String email) {
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
		this.email = email;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf, email, id, nome, telefone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(cpf, other.cpf) && Objects.equals(email, other.email) && id == other.id
				&& Objects.equals(nome, other.nome) && Objects.equals(telefone, other.telefone);
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", telefone=" + telefone + ", email=" + email
				+ "]";
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

	public String getCpf() {
		return this.cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
