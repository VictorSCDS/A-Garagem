package dao;

import entities.Cliente;
import entities.Item;
import exceptions.DatabaseException;
import utils.ConectorBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemDAO implements GenericDAO<Item, String> {

    @Override
    public void criar(Item item) throws DatabaseException {
        String query = "INSERT INTO item (nome, codigo, marca, quantidade, valor_compra, valor_venda) " +
                "VALUES (?, ?, ?, ?, ?, ?);";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, item.getNome());
            ps.setString(2, item.getCodigo());
            ps.setString(3, item.getMarca());
            ps.setInt(4, item.getQuantidade());
            ps.setBigDecimal(5, item.getValorCompra());
            ps.setBigDecimal(6, item.getValorVenda());

            ps.executeUpdate();

        } catch(SQLException e){
            e.printStackTrace();
            throw new DatabaseException("Erro ao cadastrar o item", e);
        }
    }

    @Override
    public List<Item> buscarTodos() throws DatabaseException {
        String query = "SELECT * FROM item";
        List<Item> itens = new ArrayList<>();

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()) itens.add(mapearItem(rs));

            return itens;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar os itens", e);
        }
    }

    @Override
    public Optional<Item> buscarPorAtributoIdentificador(String codigo) throws DatabaseException {
        String query = "SELECT * FROM item WHERE codigo = ?";

        try (Connection con = ConectorBD.conectar();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapearItem(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar item de código " + codigo, e);
        }

        return Optional.empty();
    }

    @Override
    public void atualizar(Item item, String codigo) throws DatabaseException {
        String query = "UPDATE item SET nome = ?, codigo = ?, marca = ?, quantidade = ?, " +
                "valor_compra = ?, valor_venda = ? WHERE codigo = ?";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, item.getNome());
            ps.setString(2, item.getCodigo());
            ps.setString(3, item.getMarca());
            ps.setInt(4, item.getQuantidade());
            ps.setBigDecimal(5, item.getValorCompra());
            ps.setBigDecimal(6, item.getValorVenda());
            ps.setString(7, codigo);

            ps.executeUpdate();

        } catch(SQLException e){
            e.printStackTrace();
            throw new DatabaseException("Erro ao editar o item de código " + codigo, e);
        }
    }

    @Override
    public void deletar(String codigo) throws DatabaseException {
        String query = "DELETE FROM item WHERE codigo = ?";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, codigo);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao deletar o item de código " + codigo, e);
        }
    }

    public List<Item> buscarItensPorNome(String nome) throws DatabaseException {
        String query = "SELECT * FROM item WHERE nome LIKE ?";
        List<Item> itens = new ArrayList<>();

        try (Connection con = ConectorBD.conectar();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, "%" + nome + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) itens.add(mapearItem(rs));
            }

            return itens;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar itens pelo nome " + nome, e);
        }
    }

    public List<Item> buscarPecasPorOrdemServico(int idOrdemServico) throws DatabaseException {
        String query = "SELECT item.* FROM item " +
                "INNER JOIN item_servico ON item.id = item_servico.id_item " +
                "WHERE item_servico.id_ordem_servico = ?";
        List<Item> itens = new ArrayList<>();

        try (Connection con = ConectorBD.conectar();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, idOrdemServico);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) itens.add(mapearItem(rs));
            }

            return itens;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar peças da ordem de serviço", e);
        }
    }

    public void diminuirQuantidade(String codigoItem, int quantidadeUsada) throws DatabaseException {
        String query = "UPDATE item SET quantidade = quantidade - ? WHERE codigo = ? AND quantidade >= ?";

        try (Connection con = ConectorBD.conectar();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, quantidadeUsada);
            ps.setString(2, codigoItem);
            ps.setInt(3, quantidadeUsada);

            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new DatabaseException("Estoque insuficiente para o item de código: " + codigoItem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao diminuir a quantidade no estoque", e);
        }
    }

    private Item mapearItem(ResultSet rs) throws SQLException {
        return new Item(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("codigo"),
                rs.getString("marca"),
                rs.getInt("quantidade"),
                rs.getBigDecimal("valor_compra"),
                rs.getBigDecimal("valor_venda")
        );
    }
}
