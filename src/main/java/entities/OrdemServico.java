package entities;

import entities.enums.Estado;

import java.sql.Date;
import java.util.Objects;

public class OrdemServico {

    private int id;
    private String problema;
    private Estado estado;
    private String descricao;
    private Date dataRegistro;
    private int idVeiculo;
    private int idFuncionarioResponsavel;

    public OrdemServico(int id, String problema, Estado estado, String descricao,
                        Date dataRegistro, int idVeiculo, int idFuncionarioResponsavel) {
        this.id = id;
        this.problema = problema;
        this.estado = estado;
        this.descricao = descricao;
        this.dataRegistro = dataRegistro;
        this.idVeiculo = idVeiculo;
        this.idFuncionarioResponsavel = idFuncionarioResponsavel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProblema() {
        return problema;
    }

    public void setProblema(String problema) {
        this.problema = problema;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(Date dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public int getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(int idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public int getIdFuncionarioResponsavel() {
        return idFuncionarioResponsavel;
    }

    public void setIdFuncionarioResponsavel(int idFuncionarioResponsavel) {
        this.idFuncionarioResponsavel = idFuncionarioResponsavel;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrdemServico that = (OrdemServico) o;
        return id == that.id && idVeiculo == that.idVeiculo && idFuncionarioResponsavel == that.idFuncionarioResponsavel && Objects.equals(problema, that.problema) && estado == that.estado && Objects.equals(descricao, that.descricao) && Objects.equals(dataRegistro, that.dataRegistro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, problema, estado, descricao, dataRegistro, idVeiculo, idFuncionarioResponsavel);
    }

    @Override
    public String toString() {
        return "OrdemServico{" +
                "id=" + id +
                ", problema='" + problema + '\'' +
                ", estado=" + estado +
                ", descricao='" + descricao + '\'' +
                ", dataRegistro=" + dataRegistro +
                ", idVeiculo=" + idVeiculo +
                ", idFuncionarioResponsavel=" + idFuncionarioResponsavel +
                '}';
    }
}
