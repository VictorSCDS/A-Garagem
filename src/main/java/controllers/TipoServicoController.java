package controllers;

import java.util.List;

import Services.TipoServicoService;
import dao.TipoServicoDAO;
import entities.TipoServico;
import exceptions.ServiceException;

public class TipoServicoController {

    private final TipoServicoService tipoServicoService;

    public TipoServicoController() {
        this.tipoServicoService = new TipoServicoService(new TipoServicoDAO());
    }

    public int registrarTipoServico(String descricao) throws ServiceException {
        return tipoServicoService.registrarTipoServico(descricao);
    }

    public List<TipoServico> listarTodos() throws ServiceException {
        return tipoServicoService.buscarTodosTiposServicos();
    }

    public List<TipoServico> buscarPorOrdemServico(int idOrdemServico) throws ServiceException {
        return tipoServicoService.buscarTiposServicoEmOrdemServicoPorId(idOrdemServico);
    }
}