package controllers;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import Services.OrdemServicoService;
import dao.OrdemServicoDAO;
import entities.OrdemServico;
import entities.Cliente;
import entities.Funcionario;
import entities.Veiculo;
import entities.enums.Estado;
import exceptions.ControllerException;
import exceptions.ServiceException;

public class OrdemServicoController {

    private final OrdemServicoService ordemServicoService;

    public OrdemServicoController() {
        this.ordemServicoService = new OrdemServicoService(new OrdemServicoDAO());
    }

public void cadastrar(String problema, Estado estado, String descricao, Date dataRegistro, int idVeiculo, int idFuncionario, List<Integer> idsItensPecas, List<Integer> idsTiposServico) throws ControllerException {
        
        OrdemServico ordemServico = new OrdemServico(0, problema, estado, descricao, dataRegistro, idVeiculo, idFuncionario);

        try {
            ordemServicoService.cadastrarOrdemServico(ordemServico, idVeiculo, idFuncionario, idsItensPecas, idsTiposServico);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public List<OrdemServico> listarTodos() throws ControllerException {

        try {
            return ordemServicoService.buscarTodasOdensServico();

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public Optional<OrdemServico> buscarPorId(int id) throws ControllerException {

        try {
            return ordemServicoService.buscarOrdemServicoPorId(id);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public void excluir(int id) throws ControllerException {

        try {
            ordemServicoService.deletarOrdemServico(id);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }


    public List<OrdemServico> buscarPorCpfCliente(String cpf) throws ControllerException {

        try {
            return ordemServicoService.buscarPorCpfCliente(cpf);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public List<OrdemServico> buscarPorPlacaVeiculo(String placa) throws ControllerException {

        try {
            return ordemServicoService.buscarPorPlacaVeiculo(placa);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public List<OrdemServico> buscarPorNomeCliente(String nomeCliente) throws ControllerException {

        try {
            return ordemServicoService.buscarPorNomeCliente(nomeCliente);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public String buscarNomeFuncionarioResponsavel(int idFuncionarioResponsavel) throws ControllerException {
        try {
            return ordemServicoService.buscarNomeFuncionarioResponsavel(idFuncionarioResponsavel);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public String buscarPlacaVeiculo(int idVeiculo) throws ControllerException {
        try {
            return ordemServicoService.buscarPlacaVeiculo(idVeiculo);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public Optional<Cliente> buscarClientePorOrdemServico(int idOrdemServico) throws ControllerException {
        try {
            return ordemServicoService.buscarClientePorOrdemServico(idOrdemServico);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public Optional<Veiculo> buscarVeiculoPorId(int idVeiculo) throws ControllerException {
        try {
            return ordemServicoService.buscarVeiculoPorId(idVeiculo);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public Optional<Funcionario> buscarFuncionarioResponsavelCompleto(int idFuncionarioResponsavel) throws ControllerException {
        try {
            return ordemServicoService.buscarFuncionarioResponsavelCompleto(idFuncionarioResponsavel);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public List<String> buscarServicosAplicadosDetalhados(int idOrdemServico) throws ControllerException {
        try {
            return ordemServicoService.buscarServicosAplicadosDetalhados(idOrdemServico);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public List<String> buscarPecasAplicadasDetalhadas(int idOrdemServico) throws ControllerException {
        try {
            return ordemServicoService.buscarPecasAplicadasDetalhadas(idOrdemServico);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public List<OrdemServico> buscarHistoricoVeiculo(int idVeiculo) throws ControllerException {

        try {
            return ordemServicoService.buscarHistoricoVelico(idVeiculo);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public Optional<BigDecimal> buscarCustoAtualServico(int idOrdemServico) throws ControllerException {

        try {
            return ordemServicoService.buscarCustoAtualServico(idOrdemServico);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }


    public List<Estado> listarEstadosDisponiveis() throws ControllerException {
        try {
            return ordemServicoService.listarEstadosDisponiveis();
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public void cancelar(int id) throws ControllerException {

        try {
            ordemServicoService.cancelarOrdemServico(id);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public void editar(int id,
                       String problema,
                       Estado estado,
                       String descricao,
                       Date dataRegistro,
                       int idVeiculo,
                       int idFuncionario,
                       List<Integer> idsItensPecas,
                       List<Integer> idsTiposServico)
            throws ControllerException {

        OrdemServico ordemServico = new OrdemServico(id, problema, estado, descricao, dataRegistro, idVeiculo, idFuncionario);

        try {
            ordemServicoService.editarOrdemServico(ordemServico, idsItensPecas, idsTiposServico);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }
}