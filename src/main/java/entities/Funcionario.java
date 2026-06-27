package entities;

import java.sql.Date;
import java.util.Objects;

public class Funcionario {
    private int id;
    private String nome;
    private String cpf;
    private Cargo cargo;
    private String telefone;
    private String email;
    private Date dataAdmissao;
    private String senhaHash;

    public Funcionario(int id, String nome, String cpf, Cargo cargo, String telefone, String email, Date dataAdmissao, String senhaHash) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.cargo = cargo;
        this.telefone = telefone;
        this.email = email;
        this.dataAdmissao = dataAdmissao;
        this.senhaHash = senhaHash;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
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

    public Date getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(Date dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public String getSenhaHash(){ return this.senhaHash; }

    public void setSenhaHash(String senhaHash){ this.senhaHash = senhaHash; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Funcionario that = (Funcionario) o;
        return id == that.id && Objects.equals(nome, that.nome) && Objects.equals(cpf, that.cpf) && cargo == that.cargo && Objects.equals(telefone, that.telefone) && Objects.equals(email, that.email) && Objects.equals(dataAdmissao, that.dataAdmissao) && Objects.equals(senhaHash, that.senhaHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, cpf, cargo, telefone, email, dataAdmissao, senhaHash);
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", cargo=" + cargo +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", dataAdmissao=" + dataAdmissao +
                ", senhaHash='" + senhaHash + '\'' +
                '}';
    }
}
