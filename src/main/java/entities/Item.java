package entities;

import java.math.BigDecimal;
import java.util.Objects;

public class Item {
    private int id;
    private String nome;
    private String codigo;
    private String marca;
    private int quantidade;
    private BigDecimal valorCompra;
    private BigDecimal valorVenda;

    public Item(int id, String nome, String codigo, String marca, int quantidade, BigDecimal valorCompra, BigDecimal valorVenda) {
        this.id = id;
        this.nome = nome;
        this.codigo = codigo;
        this.marca = marca;
        this.quantidade = quantidade;
        this.valorCompra = valorCompra;
        this.valorVenda = valorVenda;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id && quantidade == item.quantidade && Objects.equals(nome, item.nome) && Objects.equals(codigo, item.codigo) && Objects.equals(marca, item.marca) && Objects.equals(valorCompra, item.valorCompra) && Objects.equals(valorVenda, item.valorVenda);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, codigo, marca, quantidade, valorCompra, valorVenda);
    }

    @Override
    public String toString() {
        return "item{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", codigo='" + codigo + '\'' +
                ", marca='" + marca + '\'' +
                ", quantidade=" + quantidade +
                ", valorCompra=" + valorCompra +
                ", valorVenda=" + valorVenda +
                '}';
    }
}

