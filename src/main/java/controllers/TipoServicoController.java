package controllers;

import java.util.List;

import Services.TipoServicoService;
import dao.TipoServicoDAO;
import entities.TipoServico;
import exceptions.ControllerException;
import exceptions.ServiceException;

public class TipoServicoController {

    private final TipoServicoService tipoServicoService;

    public TipoServicoController() {
        this.tipoServicoService = new TipoServicoService(new TipoServicoDAO());
    }

    public int registrarTipoServico(String descricao) throws ControllerException {

        try {
            return tipoServicoService.registrarTipoServico(descricao);

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