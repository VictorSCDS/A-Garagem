package Services;

import dao.OrdemServicoDAO;
import entities.OrdemServico;
import entities.enums.Estado;
import exceptions.DatabaseException;
import exceptions.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class OrdemServicoService {
    private OrdemServicoDAO ordemServicoDao;
    private final String MENSAGEM_PADRAO_EXCECAO = "Erro ao cadastrar ordem de serviço";

    public OrdemServicoService(OrdemServicoDAO ordemServicoDao){
        this.ordemServicoDao = ordemServicoDao;
    }

    public void cadastrarOrdemServico(OrdemServico ordemServico, int idVeiculo, int idFuncionario) throws ServiceException {
        validarCampos(ordemServico);
        try{
            ordemServicoDao.cadastrarOrdemServico(ordemServico, idVeiculo, idFuncionario);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException(MENSAGEM_PADRAO_EXCECAO);
        }
    }

    public List<OrdemServico> buscarTodasOdensServico() throws ServiceException {
        try{
            return ordemServicoDao.buscarTodos();
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException(MENSAGEM_PADRAO_EXCECAO);
        }
    }

    public Optional<OrdemServico> buscarOrdemServicoPorId(int id) throws ServiceException {
        validarId(id);
        try{
            return ordemServicoDao.buscarPorAtributoIdentificador(id);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException(MENSAGEM_PADRAO_EXCECAO);
        }
    }

    public void deletarOrdemServico(int id) throws ServiceException {
        validarId(id);
        try{
            ordemServicoDao.deletar(id);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException(MENSAGEM_PADRAO_EXCECAO);
        }
    }

    public List<OrdemServico> buscarHistoricoVelico(int idVeiculo) throws ServiceException {
        validarId(idVeiculo);
        try{
            return ordemServicoDao.buscarHistoricoVeiculo(idVeiculo);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException(MENSAGEM_PADRAO_EXCECAO);
        }
    }

    public Optional<BigDecimal> buscarCustoAtualServico(int idOrdemServico) throws ServiceException {
        validarId(idOrdemServico);
        try{
            return ordemServicoDao.buscarCustoAtualServico(idOrdemServico);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException(MENSAGEM_PADRAO_EXCECAO);
        }
    }

    public void cancelarOrdemServico(int id) throws ServiceException {
        validarId(id);
        try{
            ordemServicoDao.cancelarOrdemServico(id);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException(MENSAGEM_PADRAO_EXCECAO);
        }
    }

    public void editarOrdemServico(OrdemServico ordemServico, List<Integer> idsItensPecas, List<Integer> idsTiposServico) throws ServiceException {
        validarCampos(ordemServico);
        validarId(idsTiposServico.get(0));
        validarId(idsItensPecas.get(0));

        try{
            ordemServicoDao.editarOrdemServico(ordemServico, idsItensPecas, idsTiposServico);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException(MENSAGEM_PADRAO_EXCECAO);
        }
    }

    private void validarId(int id) {
        if(id == 0) throw new ServiceException("Ordens de serviço não podem tem ID = 0");
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

        if(ordemServico.getEstado() == null || Estado.estadoToString(ordemServico.getEstado()).isEmpty()){
            throw new ServiceException("Campo de estado vazio");
        }

        if(ordemServico.getDescricao() == null || ordemServico.getDescricao().isEmpty()){
            throw new ServiceException("Campo de descrição vazio");
        }

        validarData(String.valueOf(ordemServico.getDataRegistro()));
    }
}
