package controllers;

import java.util.List;

import Services.TipoServicoService;
import dao.TipoServicoDAO;
import entities.TipoServico;
import exceptions.ControllerException;
import exceptions.ServiceException;
import java.math.BigDecimal;

public class TipoServicoController {

    private final TipoServicoService tipoServicoService;

    public TipoServicoController() {
        this.tipoServicoService = new TipoServicoService(new TipoServicoDAO());
    }

    public int cadastrar(String descricao, BigDecimal valor) throws ControllerException {
        try {
            TipoServico ts = new TipoServico(0, descricao, valor);
            return tipoServicoService.registrarTipoServico(ts);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public void editar(int id, String descricao, BigDecimal valor) throws ControllerException {
        try {
            TipoServico ts = new TipoServico(id, descricao, valor);
            tipoServicoService.atualizarTipoServico(ts, id);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public void excluir(int id) throws ControllerException {
        try {
            tipoServicoService.deletarTipoServico(id);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public List<TipoServico> listarTodos() throws ControllerException {

        try {
            return tipoServicoService.buscarTodosTiposServicos();

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public List<TipoServico> buscarPorOrdemServico(int idOrdemServico) throws ControllerException {

        try {
            return tipoServicoService.buscarTiposServicoEmOrdemServicoPorId(idOrdemServico);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }
}
