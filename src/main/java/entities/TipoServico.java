package entities;

import java.math.BigDecimal;
import java.util.Objects;

public class TipoServico {
    private int id;
    private String descricao;
    private BigDecimal valorServico;

    public TipoServico(int id, String descricao, BigDecimal valorServico) {
        this.id = id;
        this.descricao = descricao;
        this.valorServico = valorServico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValorServico() {
        return valorServico;
    }

    public void setValorServico(BigDecimal valorServico) {
        this.valorServico = valorServico;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TipoServico that = (TipoServico) o;
        return id == that.id && Objects.equals(descricao, that.descricao) && Objects.equals(valorServico, that.valorServico);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descricao, valorServico);
    }

    @Override
    public String toString() {
        return "TipoServico{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", valorServico=" + valorServico +
                '}';
    }
}
