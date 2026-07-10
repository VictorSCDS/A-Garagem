package Services;

import dao.OrdemServicoDAO;
import entities.OrdemServico;
import entities.Cliente;
import entities.Funcionario;
import entities.Veiculo;
import entities.enums.Estado;
import exceptions.DatabaseException;
import exceptions.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class OrdemServicoService {
    private OrdemServicoDAO ordemServicoDao;

    public OrdemServicoService(OrdemServicoDAO ordemServicoDao){
        this.ordemServicoDao = ordemServicoDao;
    }

    public void cadastrarOrdemServico(OrdemServico ordemServico, int idVeiculo, int idFuncionario, List<Integer> idsItensPecas, List<Integer> idsTiposServico) throws ServiceException {
        validarCampos(ordemServico);
        try {
            ordemServicoDao.cadastrarOrdemServico(ordemServico, idVeiculo, idFuncionario, idsItensPecas, idsTiposServico);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao cadastrar ordem de serviço completa: " + e.getMessage(), e);
        }
    }

    public List<OrdemServico> buscarTodasOdensServico() throws ServiceException {
        try{
            return ordemServicoDao.buscarTodos();
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao buscar todas ordens de serviço");
        }
    }

    public Optional<OrdemServico> buscarOrdemServicoPorId(int id) throws ServiceException {
        validarId(id);
        try{
            return ordemServicoDao.buscarPorAtributoIdentificador(id);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao buscar ordem de serviço de id " + id);
        }
    }

    public void deletarOrdemServico(int id) throws ServiceException {
        validarId(id);
        try{
            ordemServicoDao.deletar(id);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao deletar ordem de serviço de id " + id);
        }
    }


    public List<OrdemServico> buscarPorCpfCliente(String cpf) throws ServiceException {
        validarTextoBusca(cpf, "CPF do cliente");
        try{
            return ordemServicoDao.buscarPorCpfCliente(cpf.trim());
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao buscar ordens de serviço por CPF do cliente");
        }
    }

    public List<OrdemServico> buscarPorPlacaVeiculo(String placa) throws ServiceException {
        validarTextoBusca(placa, "placa do veículo");
        try{
            return ordemServicoDao.buscarPorPlacaVeiculo(placa.trim());
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao buscar ordens de serviço por placa do veículo");
        }
    }

    public List<OrdemServico> buscarPorNomeCliente(String nomeCliente) throws ServiceException {
        validarTextoBusca(nomeCliente, "nome do cliente");
        try{
            return ordemServicoDao.buscarPorNomeCliente(nomeCliente.trim());
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao buscar ordens de serviço por nome do cliente");
        }
    }


    public String buscarNomeFuncionarioResponsavel(int idFuncionarioResponsavel) throws ServiceException {
        if(idFuncionarioResponsavel <= 0) {
            return "Não informado";
        }

        try {
            return ordemServicoDao.buscarNomeFuncionarioResponsavel(idFuncionarioResponsavel)
                    .orElse("Não encontrado");
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao buscar nome do funcionário responsável");
        }
    }

    public String buscarPlacaVeiculo(int idVeiculo) throws ServiceException {
        if(idVeiculo <= 0) {
            return "Não informada";
        }

        try {
            return ordemServicoDao.buscarPlacaVeiculo(idVeiculo)
                    .orElse("Não encontrada");
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao buscar placa do veículo");
        }
    }

    public Optional<Cliente> buscarClientePorOrdemServico(int idOrdemServico) throws ServiceException {
        validarId(idOrdemServico);
        try {
            return ordemServicoDao.buscarClientePorOrdemServico(idOrdemServico);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao buscar cliente da ordem de serviço");
        }
    }

    public Optional<Veiculo> buscarVeiculoPorId(int idVeiculo) throws ServiceException {
        validarId(idVeiculo);
        try {
            return ordemServicoDao.buscarVeiculoPorId(idVeiculo);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao buscar veículo da ordem de serviço");
        }
    }

    public Optional<Funcionario> buscarFuncionarioResponsavelCompleto(int idFuncionarioResponsavel) throws ServiceException {
        validarId(idFuncionarioResponsavel);
        try {
            return ordemServicoDao.buscarFuncionarioResponsavelCompleto(idFuncionarioResponsavel);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao buscar funcionário responsável da ordem de serviço");
        }
    }

    public List<String> buscarServicosAplicadosDetalhados(int idOrdemServico) throws ServiceException {
        validarId(idOrdemServico);
        try {
            return ordemServicoDao.buscarServicosAplicadosDetalhados(idOrdemServico);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao buscar serviços aplicados da ordem de serviço");
        }
    }

    public List<String> buscarPecasAplicadasDetalhadas(int idOrdemServico) throws ServiceException {
        validarId(idOrdemServico);
        try {
            return ordemServicoDao.buscarPecasAplicadasDetalhadas(idOrdemServico);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao buscar peças aplicadas da ordem de serviço");
        }
    }

    public List<OrdemServico> buscarHistoricoVelico(int idVeiculo) throws ServiceException {
        validarId(idVeiculo);
        try{
            return ordemServicoDao.buscarHistoricoVeiculo(idVeiculo);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao buscar histórico de veículo");
        }
    }

    public Optional<BigDecimal> buscarCustoAtualServico(int idOrdemServico) throws ServiceException {
        validarId(idOrdemServico);
        try{
            return ordemServicoDao.buscarCustoAtualServico(idOrdemServico);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao buscar o atual custo do serviço");
        }
    }


    public List<Estado> listarEstadosDisponiveis() throws ServiceException {
        try {
            return ordemServicoDao.listarEstadosDisponiveis();
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao buscar status de ordens de serviço");
        }
    }

    public void cancelarOrdemServico(int id) throws ServiceException {
        validarId(id);
        try{
            ordemServicoDao.cancelarOrdemServico(id);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao cancelar ordem de serviço: " + e.getMessage(), e);
        }
    }

    public void editarOrdemServico(OrdemServico ordemServico, List<Integer> idsItensPecas, List<Integer> idsTiposServico) throws ServiceException {
        validarCampos(ordemServico);

        if (idsItensPecas == null) {
            idsItensPecas = java.util.Collections.emptyList();
        }

        if (idsTiposServico == null) {
            idsTiposServico = java.util.Collections.emptyList();
        }

        try{
            ordemServicoDao.editarOrdemServico(ordemServico, idsItensPecas, idsTiposServico);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao editar ordem de serviço: " + e.getMessage(), e);
        }
    }

    private void validarId(int id) {
        if(id == 0) throw new ServiceException("Ordens de serviço não podem tem ID = 0");
    }

    private void validarTextoBusca(String texto, String campo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new ServiceException("Campo de " + campo + " vazio");
        }
    }

    private void validarData(String data){
        if (data == null || data.isEmpty()){
            throw new ServiceException("Campo de data vazio");
        }
    }

    private void validarCampos(OrdemServico ordemServico){
        if(ordemServico.getProblema() == null || ordemServico.getProblema().isEmpty()){
            throw new ServiceException("Campo de problema vazio");
        }

        if(ordemServico.getEstado() == null || ordemServico.getEstado().getValorBanco().isEmpty()){
            throw new ServiceException("Campo de estado vazio");
        }

        if(ordemServico.getDescricao() == null || ordemServico.getDescricao().isEmpty()){
            throw new ServiceException("Campo de descrição vazio");
        }

        validarData(String.valueOf(ordemServico.getDataRegistro()));
    }
}