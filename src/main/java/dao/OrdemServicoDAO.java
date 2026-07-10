package dao;

import entities.enums.Estado;
import entities.OrdemServico;
import entities.Cliente;
import entities.Funcionario;
import entities.Veiculo;
import exceptions.DatabaseException;
import utils.ConectorBD;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdemServicoDAO implements GenericDAO<OrdemServico, Integer> {

    @Override
    public void criar(OrdemServico ordemServico) throws DatabaseException {
        throw new UnsupportedOperationException("Método inválido para este caso! Utilize cadastrarOrdemServico(...).");
    }

    @Override
    public List<OrdemServico> buscarTodos() throws DatabaseException {
        String query = "SELECT * FROM ordem_servico;";
        List<OrdemServico> ordemServicos = new ArrayList<>();

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()) ordemServicos.add(mapearOrdemServico(rs));

            return ordemServicos;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar ordens de serviço registradas no banco de dados", e);
        }
    }

    @Override
    public Optional<OrdemServico> buscarPorAtributoIdentificador(Integer id) throws DatabaseException {
        String query = "SELECT * FROM ordem_servico WHERE id = ?";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setInt(1, id);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) return Optional.of(mapearOrdemServico(rs));
            }

            return Optional.empty();

        } catch(SQLException e){
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar ordem de serviço registrada no banco de dados", e);
        }
    }

    @Override
    public void atualizar(OrdemServico ordemServico, Integer identificador) throws DatabaseException {
        throw new UnsupportedOperationException("Método inválido para este caso! Utilize cancelarOrdemServico(...) ou cancelarOrdemServico(...).");
    }

    @Override
    public void deletar(Integer id) throws DatabaseException {
        String queryDeleteItens = "DELETE FROM item_servico WHERE id_ordem_servico = ?";
        String queryDeleteServicos = "DELETE FROM servico_aplicado WHERE id_ordem_servico = ?";
        String queryDeleteOrdem = "DELETE FROM ordem_servico WHERE id = ?";

        try (Connection con = ConectorBD.conectar()) {
            con.setAutoCommit(false);

            try (PreparedStatement psDeleteItens = con.prepareStatement(queryDeleteItens);
                 PreparedStatement psDeleteServicos = con.prepareStatement(queryDeleteServicos);
                 PreparedStatement psDeleteOrdem = con.prepareStatement(queryDeleteOrdem)) {

                psDeleteItens.setInt(1, id);
                psDeleteItens.executeUpdate();

                psDeleteServicos.setInt(1, id);
                psDeleteServicos.executeUpdate();

                psDeleteOrdem.setInt(1, id);
                int linhasAfetadas = psDeleteOrdem.executeUpdate();

                if (linhasAfetadas == 0) {
                    con.rollback();
                    throw new DatabaseException("Nenhuma ordem de serviço encontrada para apagar com o ID " + id);
                }

                con.commit();

            } catch (SQLException | DatabaseException e) {
                con.rollback();
                throw e;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao deletar ordem de serviço registrada no banco de dados", e);
        }
    }

public void cadastrarOrdemServico(OrdemServico ordemServico, int idVeiculo, int idFuncionario, List<Integer> idsItensPecas, List<Integer> idsTiposServico) throws DatabaseException {
        
        String queryOrdemServico = "INSERT INTO ordem_servico (problema, estado, descricao, data_registro, id_veiculo, id_funcionario_responsavel) VALUES (?, ?, ?, ?, ?, ?);";
        String queryServicoAplicado = "INSERT INTO servico_aplicado (id_ordem_servico, id_tipo_servico) VALUES (?, ?);";
        String queryItemServico = "INSERT INTO item_servico (id_ordem_servico, id_item, id_tipo_servico) VALUES (?, ?, ?);";

        try (Connection con = ConectorBD.conectar()) {
           
            con.setAutoCommit(false);

            try (PreparedStatement psOS = con.prepareStatement(queryOrdemServico, PreparedStatement.RETURN_GENERATED_KEYS);
                 PreparedStatement psServico = con.prepareStatement(queryServicoAplicado);
                 PreparedStatement psItem = con.prepareStatement(queryItemServico)) {

                if (idsItensPecas != null && !idsItensPecas.isEmpty()
                        && (idsTiposServico == null || idsTiposServico.isEmpty())) {
                    throw new SQLException("Para vincular peças à OS, selecione pelo menos um tipo de serviço.");
                }

                psOS.setString(1, ordemServico.getProblema());
                psOS.setString(2, ordemServico.getEstado().getValorBanco());
                psOS.setString(3, ordemServico.getDescricao());
                psOS.setDate(4, ordemServico.getDataRegistro());
                psOS.setInt(5, idVeiculo);
                psOS.setInt(6, idFuncionario);
                psOS.executeUpdate();

                int idOrdemGerada = -1;
                try (ResultSet rs = psOS.getGeneratedKeys()) {
                    if (rs.next()) {
                        idOrdemGerada = rs.getInt(1);
                    } else {
                        throw new SQLException("Falha ao criar OS: Nenhum ID foi retornado pelo banco.");
                    }
                }

                if (idsTiposServico != null && !idsTiposServico.isEmpty()) {
                    for (Integer idServico : idsTiposServico) {
                        psServico.setInt(1, idOrdemGerada);
                        psServico.setInt(2, idServico);
                        psServico.addBatch();
                    }
                    psServico.executeBatch(); 
                }

                if (idsItensPecas != null && !idsItensPecas.isEmpty()) {
                    int idTipoServicoAssociado = idsTiposServico.get(0);
                    for (Integer idItem : idsItensPecas) {
                        psItem.setInt(1, idOrdemGerada);
                        psItem.setInt(2, idItem);
                        psItem.setInt(3, idTipoServicoAssociado);
                        psItem.addBatch();
                    }
                    psItem.executeBatch();

                    decrementarEstoqueItens(con, idsItensPecas);
                }

                con.commit();

            } catch (SQLException e) {
                con.rollback();
                throw e; 
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao cadastrar ordem de serviço completa no banco de dados: " + e.getMessage(), e);
        }
    }


    public List<OrdemServico> buscarPorCpfCliente(String cpf) throws DatabaseException {
        String query = "SELECT os.* FROM ordem_servico os " +
                "INNER JOIN veiculo v ON os.id_veiculo = v.id " +
                "INNER JOIN cliente_veiculo cv ON v.id = cv.id_veiculo " +
                "INNER JOIN cliente c ON cv.id_cliente = c.id " +
                "WHERE c.cpf = ? " +
                "ORDER BY os.id DESC";
        List<OrdemServico> ordemServicos = new ArrayList<>();

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, cpf);

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()) ordemServicos.add(mapearOrdemServico(rs));
            }

            return ordemServicos;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar ordens de serviço por CPF do cliente", e);
        }
    }

    public List<OrdemServico> buscarPorPlacaVeiculo(String placa) throws DatabaseException {
        String query = "SELECT os.* FROM ordem_servico os " +
                "INNER JOIN veiculo v ON os.id_veiculo = v.id " +
                "WHERE REPLACE(UPPER(v.placa), '-', '') = REPLACE(UPPER(?), '-', '') " +
                "ORDER BY os.id DESC";
        List<OrdemServico> ordemServicos = new ArrayList<>();

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, placa);

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()) ordemServicos.add(mapearOrdemServico(rs));
            }

            return ordemServicos;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar ordens de serviço por placa do veículo", e);
        }
    }

    public List<OrdemServico> buscarPorNomeCliente(String nomeCliente) throws DatabaseException {
        String query = "SELECT DISTINCT os.* FROM ordem_servico os " +
                "INNER JOIN veiculo v ON os.id_veiculo = v.id " +
                "INNER JOIN cliente_veiculo cv ON v.id = cv.id_veiculo " +
                "INNER JOIN cliente c ON cv.id_cliente = c.id " +
                "WHERE LOWER(c.nome) LIKE LOWER(?) " +
                "ORDER BY os.id DESC";
        List<OrdemServico> ordemServicos = new ArrayList<>();

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, "%" + nomeCliente + "%");

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()) ordemServicos.add(mapearOrdemServico(rs));
            }

            return ordemServicos;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar ordens de serviço por nome do cliente", e);
        }
    }

    public Optional<String> buscarNomeFuncionarioResponsavel(int idFuncionarioResponsavel) throws DatabaseException {
        String query = "SELECT nome FROM funcionario WHERE id = ?";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setInt(1, idFuncionarioResponsavel);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) return Optional.ofNullable(rs.getString("nome"));
            }

            return Optional.empty();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar nome do funcionário responsável", e);
        }
    }

    public Optional<String> buscarPlacaVeiculo(int idVeiculo) throws DatabaseException {
        String query = "SELECT placa FROM veiculo WHERE id = ?";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setInt(1, idVeiculo);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) return Optional.ofNullable(rs.getString("placa"));
            }

            return Optional.empty();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar placa do veículo", e);
        }
    }

    public Optional<Cliente> buscarClientePorOrdemServico(int idOrdemServico) throws DatabaseException {
        String query = "SELECT c.* FROM cliente c " +
                "INNER JOIN cliente_veiculo cv ON cv.id_cliente = c.id " +
                "INNER JOIN veiculo v ON v.id = cv.id_veiculo " +
                "INNER JOIN ordem_servico os ON os.id_veiculo = v.id " +
                "WHERE os.id = ? " +
                "LIMIT 1";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setInt(1, idOrdemServico);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) {
                    return Optional.of(new Cliente(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getString("telefone"),
                            rs.getString("email")
                    ));
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar cliente da ordem de serviço", e);
        }
    }

    public Optional<Veiculo> buscarVeiculoPorId(int idVeiculo) throws DatabaseException {
        String query = "SELECT * FROM veiculo WHERE id = ?";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setInt(1, idVeiculo);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) {
                    return Optional.of(new Veiculo(
                            rs.getInt("id"),
                            rs.getString("placa"),
                            rs.getString("marca"),
                            rs.getString("modelo")
                    ));
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar veículo da ordem de serviço", e);
        }
    }

    public Optional<Funcionario> buscarFuncionarioResponsavelCompleto(int idFuncionarioResponsavel) throws DatabaseException {
        String query = "SELECT * FROM funcionario WHERE id = ?";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setInt(1, idFuncionarioResponsavel);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) {
                    return Optional.of(new Funcionario(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            entities.enums.Cargo.fromString(rs.getString("cargo")),
                            rs.getString("telefone"),
                            rs.getString("email"),
                            rs.getDate("data_admissao"),
                            rs.getString("senha_hash")
                    ));
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar funcionário responsável da ordem de serviço", e);
        }
    }

    public List<String> buscarServicosAplicadosDetalhados(int idOrdemServico) throws DatabaseException {
        String query = "SELECT ts.descricao, ts.valor_servico " +
                "FROM tipo_servico ts " +
                "INNER JOIN servico_aplicado sa ON sa.id_tipo_servico = ts.id " +
                "WHERE sa.id_ordem_servico = ? " +
                "ORDER BY ts.descricao";
        List<String> servicos = new ArrayList<>();

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setInt(1, idOrdemServico);

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()) {
                    BigDecimal valor = rs.getBigDecimal("valor_servico");
                    servicos.add(rs.getString("descricao") + " - R$ " + (valor != null ? valor : BigDecimal.ZERO));
                }
            }

            return servicos;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar serviços aplicados da ordem de serviço", e);
        }
    }

    public List<String> buscarPecasAplicadasDetalhadas(int idOrdemServico) throws DatabaseException {
        String query = "SELECT i.nome, i.codigo, i.marca, i.valor_compra " +
                "FROM item i " +
                "INNER JOIN item_servico its ON its.id_item = i.id " +
                "WHERE its.id_ordem_servico = ? " +
                "ORDER BY i.nome";
        List<String> pecas = new ArrayList<>();

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setInt(1, idOrdemServico);

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()) {
                    BigDecimal valor = rs.getBigDecimal("valor_compra");
                    pecas.add(rs.getString("nome") + " (" + rs.getString("marca") + ", cód. " + rs.getString("codigo") + ") - R$ " + (valor != null ? valor : BigDecimal.ZERO));
                }
            }

            return pecas;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar peças aplicadas da ordem de serviço", e);
        }
    }

    public List<OrdemServico> buscarHistoricoVeiculo(int idVeiculo) throws DatabaseException{
        String query = "SELECT * FROM ordem_servico WHERE id_veiculo = ?";
        List<OrdemServico> ordemServicos = new ArrayList<>();

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setInt(1, idVeiculo);

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()) ordemServicos.add(mapearOrdemServico(rs));
            }

            return ordemServicos;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar ordens de serviço registradas no banco de dados", e);
        }
    }

    public Optional<BigDecimal> buscarCustoAtualServico(int idOrdemServico) throws DatabaseException {
        String query = "SELECT " +
                "(SELECT COALESCE(SUM(ts.valor_servico), 0) " +
                "FROM servico_aplicado sa " +
                "INNER JOIN tipo_servico ts ON ts.id = sa.id_tipo_servico " +
                "WHERE sa.id_ordem_servico = ?) + " +
                "(SELECT COALESCE(SUM(i.valor_compra), 0) " +
                "FROM item_servico its " +
                "INNER JOIN item i ON i.id = its.id_item " +
                "WHERE its.id_ordem_servico = ?) AS custo_atual";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setInt(1, idOrdemServico);
            ps.setInt(2, idOrdemServico);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) return Optional.ofNullable(rs.getBigDecimal("custo_atual"));
            }

            return Optional.empty();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar custo do serviço no banco de dados", e);
        }

    }

    public void cancelarOrdemServico(int id) throws DatabaseException {
        String query = "UPDATE ordem_servico SET estado = ? WHERE id = ?;";

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query)){

            ps.setString(1, Estado.CANCELADO.getValorBanco());
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao cancelar ordem de serviço registrada no banco de dados", e);
        }
    }

    public void editarOrdemServico(OrdemServico ordemServico, List<Integer> idsItensPecas, List<Integer> idsTiposServico)
            throws DatabaseException{
        String queryOrdemServico = "UPDATE ordem_servico SET problema = ?, estado = ?, descricao = ?, id_veiculo = ?, id_funcionario_responsavel = ? WHERE id = ?";
        String queryDeleteItemServico = "DELETE FROM item_servico WHERE id_ordem_servico = ?";
        String queryDeleteServicoAplicado = "DELETE FROM servico_aplicado WHERE id_ordem_servico = ?";
        String queryInsertServicoAplicado = "INSERT INTO servico_aplicado (id_ordem_servico, id_tipo_servico) VALUES (?, ?)";
        String queryInsertItemServico = "INSERT INTO item_servico (id_ordem_servico, id_item, id_tipo_servico) VALUES (?, ?, ?)";

        try(Connection con = ConectorBD.conectar()){
            con.setAutoCommit(false);

            try (PreparedStatement psOrdemServico = con.prepareStatement(queryOrdemServico);
                 PreparedStatement psDeleteItemServico = con.prepareStatement(queryDeleteItemServico);
                 PreparedStatement psDeleteServicoAplicado = con.prepareStatement(queryDeleteServicoAplicado);
                 PreparedStatement psInsertServicoAplicado = con.prepareStatement(queryInsertServicoAplicado);
                 PreparedStatement psInsertItemServico = con.prepareStatement(queryInsertItemServico)) {

                if (idsItensPecas != null && !idsItensPecas.isEmpty()
                        && (idsTiposServico == null || idsTiposServico.isEmpty())) {
                    throw new SQLException("Para vincular peças à OS, selecione pelo menos um tipo de serviço.");
                }

                List<Integer> idsItensAntesDaEdicao = buscarIdsItensDaOrdemServico(con, ordemServico.getId());

                psOrdemServico.setString(1, ordemServico.getProblema());
                psOrdemServico.setString(2, ordemServico.getEstado().getValorBanco());
                psOrdemServico.setString(3, ordemServico.getDescricao());
                psOrdemServico.setInt(4, ordemServico.getIdVeiculo());
                psOrdemServico.setInt(5, ordemServico.getIdFuncionarioResponsavel());
                psOrdemServico.setInt(6, ordemServico.getId());
                psOrdemServico.executeUpdate();

                psDeleteItemServico.setInt(1, ordemServico.getId());
                psDeleteItemServico.executeUpdate();

                psDeleteServicoAplicado.setInt(1, ordemServico.getId());
                psDeleteServicoAplicado.executeUpdate();

                if (idsTiposServico != null && !idsTiposServico.isEmpty()) {
                    for(Integer idTipo : idsTiposServico) {
                        psInsertServicoAplicado.setInt(1, ordemServico.getId());
                        psInsertServicoAplicado.setInt(2, idTipo);
                        psInsertServicoAplicado.addBatch();
                    }
                    psInsertServicoAplicado.executeBatch();
                }

                if (idsItensPecas != null && !idsItensPecas.isEmpty()) {
                    int idTipoServicoAssociado = idsTiposServico.get(0);
                    for(Integer idItem : idsItensPecas){
                        psInsertItemServico.setInt(1, ordemServico.getId());
                        psInsertItemServico.setInt(2, idItem);
                        psInsertItemServico.setInt(3, idTipoServicoAssociado);
                        psInsertItemServico.addBatch();
                    }
                    psInsertItemServico.executeBatch();
                }

                ajustarEstoqueItensAposEdicao(con, idsItensAntesDaEdicao, idsItensPecas);

                con.commit();

            } catch (SQLException e) {
                con.rollback();
                throw e;
            }

        } catch (SQLException e){
            e.printStackTrace();
            throw new DatabaseException("Erro ao editar ordem de serviço registrado no banco de dados: " + e.getMessage(), e);
        }

    }


    public List<Estado> listarEstadosDisponiveis() throws DatabaseException {
        String query = "SELECT DISTINCT estado FROM ordem_servico WHERE estado IS NOT NULL AND estado <> '' ORDER BY estado";
        List<Estado> estados = new ArrayList<>();

        try(Connection con = ConectorBD.conectar();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()) {
                String estadoBanco = rs.getString("estado");
                try {
                    Estado estado = Estado.fromBanco(estadoBanco);
                    if (!estados.contains(estado)) {
                        estados.add(estado);
                    }
                } catch (IllegalArgumentException ignored) {
                }
            }

            for (Estado estado : Estado.values()) {
                if (!estados.contains(estado)) {
                    estados.add(estado);
                }
            }

            return estados;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Erro ao buscar status de ordens de serviço no banco de dados", e);
        }
    }


    private List<Integer> buscarIdsItensDaOrdemServico(Connection con, int idOrdemServico) throws SQLException {
        String query = "SELECT id_item FROM item_servico WHERE id_ordem_servico = ?";
        List<Integer> idsItens = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, idOrdemServico);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    idsItens.add(rs.getInt("id_item"));
                }
            }
        }

        return idsItens;
    }

    private void decrementarEstoqueItens(Connection con, List<Integer> idsItens) throws SQLException {
        java.util.Map<Integer, Integer> quantidadesPorItem = contarIds(idsItens);

        for (java.util.Map.Entry<Integer, Integer> entrada : quantidadesPorItem.entrySet()) {
            decrementarEstoqueItem(con, entrada.getKey(), entrada.getValue());
        }
    }

    private void ajustarEstoqueItensAposEdicao(Connection con, List<Integer> idsItensAntes, List<Integer> idsItensDepois) throws SQLException {
        java.util.Map<Integer, Integer> antes = contarIds(idsItensAntes);
        java.util.Map<Integer, Integer> depois = contarIds(idsItensDepois);

        for (java.util.Map.Entry<Integer, Integer> entradaDepois : depois.entrySet()) {
            int idItem = entradaDepois.getKey();
            int quantidadeDepois = entradaDepois.getValue();
            int quantidadeAntes = antes.getOrDefault(idItem, 0);

            if (quantidadeDepois > quantidadeAntes) {
                decrementarEstoqueItem(con, idItem, quantidadeDepois - quantidadeAntes);
            }
        }

        for (java.util.Map.Entry<Integer, Integer> entradaAntes : antes.entrySet()) {
            int idItem = entradaAntes.getKey();
            int quantidadeAntes = entradaAntes.getValue();
            int quantidadeDepois = depois.getOrDefault(idItem, 0);

            if (quantidadeAntes > quantidadeDepois) {
                incrementarEstoqueItem(con, idItem, quantidadeAntes - quantidadeDepois);
            }
        }
    }

    private java.util.Map<Integer, Integer> contarIds(List<Integer> ids) {
        java.util.Map<Integer, Integer> contagem = new java.util.HashMap<>();

        if (ids == null) {
            return contagem;
        }

        for (Integer id : ids) {
            if (id == null || id <= 0) {
                continue;
            }
            contagem.put(id, contagem.getOrDefault(id, 0) + 1);
        }

        return contagem;
    }

    private void decrementarEstoqueItem(Connection con, int idItem, int quantidade) throws SQLException {
        if (quantidade <= 0) {
            return;
        }

        String query = "UPDATE item SET quantidade = quantidade - ? WHERE id = ? AND quantidade >= ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, quantidade);
            ps.setInt(2, idItem);
            ps.setInt(3, quantidade);

            int linhasAfetadas = ps.executeUpdate();
            if (linhasAfetadas == 0) {
                throw new SQLException("Estoque insuficiente para a peça de ID " + idItem + ". Atualize o estoque antes de salvar a OS.");
            }
        }
    }

    private void incrementarEstoqueItem(Connection con, int idItem, int quantidade) throws SQLException {
        if (quantidade <= 0) {
            return;
        }

        String query = "UPDATE item SET quantidade = quantidade + ? WHERE id = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, quantidade);
            ps.setInt(2, idItem);

            int linhasAfetadas = ps.executeUpdate();
            if (linhasAfetadas == 0) {
                throw new SQLException("Peça de ID " + idItem + " não encontrada para devolver ao estoque.");
            }
        }
    }

    private OrdemServico mapearOrdemServico(ResultSet rs) throws SQLException {
        return new OrdemServico(
                rs.getInt("id"),
                rs.getString("problema"),
                Estado.fromBanco(rs.getString("estado")),
                rs.getString("descricao"),
                rs.getDate("data_registro"),
                rs.getInt("id_veiculo"),
                rs.getInt("id_funcionario_responsavel")
        );
    }
}