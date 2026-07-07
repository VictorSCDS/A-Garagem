package controllers;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import Services.OrdemServicoService;
import dao.OrdemServicoDAO;
import entities.OrdemServico;
import entities.enums.Estado;
import exceptions.ControllerException;
import exceptions.ServiceException;

public class OrdemServicoController {

    private final OrdemServicoService ordemServicoService;

    public OrdemServicoController() {
        this.ordemServicoService = new OrdemServicoService(new OrdemServicoDAO());
    }

    public void cadastrar(String problema, Estado estado, String descricao, Date dataRegistro, int idVeiculo, int idFuncionario) throws ControllerException {
        OrdemServico ordemServico = new OrdemServico(0, problema, estado, descricao, dataRegistro, idVeiculo, idFuncionario);

        try {
            ordemServicoService.cadastrarOrdemServico(ordemServico, idVeiculo, idFuncionario);

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