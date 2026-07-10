package Services;

import dao.TipoServicoDAO;
import entities.TipoServico;
import exceptions.DatabaseException;
import exceptions.ServiceException;

import java.math.BigDecimal;
import java.util.List;

public class TipoServicoService {
    private TipoServicoDAO tipoServicoDao;

    public TipoServicoService(TipoServicoDAO tipoServicoDao){
        this.tipoServicoDao = tipoServicoDao;
    }

    public List<TipoServico> buscarTodosTiposServicos() throws ServiceException {
        try{
            return tipoServicoDao.buscarTodos();
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao buscar todos os tipos de serviço");
        }
    }

    public int registrarTipoServico(TipoServico tipoServico) throws ServiceException {
        validarCampos(tipoServico);
        try{
            return tipoServicoDao.registrarTipoServico(tipoServico);
        } catch (DatabaseException e) {
            throw new ServiceException("Erro ao registrar o tipo de serviço", e);
        }
    }

    public void atualizarTipoServico(TipoServico tipoServico, int id) throws ServiceException {
        validarCampos(tipoServico);
        try {
            tipoServicoDao.atualizar(tipoServico, id);
        } catch (DatabaseException e) {
            throw new ServiceException("Erro ao atualizar serviço", e);
        }
    }

    public void deletarTipoServico(int id) throws ServiceException {
        try {
            tipoServicoDao.deletar(id);
        } catch (DatabaseException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<TipoServico> buscarTiposServicoEmOrdemServicoPorId(int id) throws ServiceException {
        try{
            return tipoServicoDao.buscarTiposServicoEmOrdemServicoPorId(id);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao buscar todos os tipos de serviço na ordem de serviço de id " + id);
        }
    }

    private void validarCampos(TipoServico tipoServico){
        if(tipoServico.getValorServico() == null || tipoServico.getValorServico().compareTo(BigDecimal.ZERO) == 0){
            throw new ServiceException("Tipo de Serviço de valor 0");
        }

        if(tipoServico.getDescricao() == null || tipoServico.getDescricao().isEmpty()){
            throw new ServiceException("Tipo de Serviço vazio");
        }
    }
}
