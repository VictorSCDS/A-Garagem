package Services;

import dao.ItemDAO;
import entities.Item;
import exceptions.DatabaseException;
import exceptions.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ItemService {
    private ItemDAO itemDao;

    public ItemService(ItemDAO itemDao){
        this.itemDao = itemDao;
    }

    public void criarItem(Item item) throws ServiceException {
        validarCampos(item);

        try{


            itemDao.criar(item);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao cadastrar item");
        }
    }

    public List<Item> buscarTodosItens() throws ServiceException {
        try{
            return itemDao.buscarTodos();
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao buscar todos os itens");
        }
    }

    public Optional<Item> buscarItemPorCodigo(String codigo) {
        validarCodigo(codigo);

        try{
            return itemDao.buscarPorAtributoIdentificador(codigo);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao buscar item de código " + codigo);
        }
    }

    public void atualizarItem(Item item, String codigo) throws ServiceException {
        validarCampos(item);
        try{
            itemDao.atualizar(item, codigo);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao atualizar item de código " + codigo);
        }
    }

    public void deletarItemPorCodigo(String codigo) throws ServiceException {
        validarCodigo(codigo);
        try{
            itemDao.deletar(codigo);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao deletar item de código " + codigo);
        }
    }

    public List<Item> buscarItensPorNome(String nome) throws ServiceException {
        if(nome == null || nome.isEmpty()) throw new ServiceException("Nome vazio");

        try{
            return itemDao.buscarItensPorNome(nome);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao buscar itens com o nome " + nome);
        }
    }

    public List<Item> buscarItensPorOrdemServico(int idOrdemServico) throws ServiceException {
        if(idOrdemServico == 0) throw new ServiceException("Ordem de serviço inexistente");

        try{
            return itemDao.buscarPecasPorOrdemServico(idOrdemServico);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao buscar itens na ordem de serviço de ID " + idOrdemServico);
        }
    }

    public void diminuirQuantidade(String codigoItem, int quantidadeUsada) throws ServiceException {
        validarCodigo(codigoItem);

        try{
            itemDao.diminuirQuantidade(codigoItem, quantidadeUsada);
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new ServiceException("Erro ao diminuir quantidade do item de código" + codigoItem);
        }
    }

    private void validarCodigo(String codigo) {
        if(codigo == null || codigo.isEmpty()){
            throw new ServiceException("Código vazio");
        }
    }

    private void validarCampos(Item item) {
        if(item.getValorVenda() == null || item.getValorVenda().compareTo(BigDecimal.ZERO) == 0) {
            throw new ServiceException("Valor de venda vazio");
        }

        if(item.getValorCompra() == null || item.getValorCompra().compareTo(BigDecimal.ZERO) == 0){
            throw new ServiceException("Valor de compra vazio");
        }

        if(item.getNome() == null || item.getNome().isEmpty()){
            throw new ServiceException("Campo de nome vazio");
        }

        if(item.getMarca() == null || item.getMarca().isEmpty()){
            throw new ServiceException("Campos de marca vazio");
        }

        validarCodigo(item.getCodigo());

    }

}
