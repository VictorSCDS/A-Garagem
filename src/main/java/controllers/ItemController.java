package controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import Services.ItemService;
import dao.ItemDAO;
import entities.Item;
import exceptions.ServiceException;

public class ItemController {

    private final ItemService itemService;

    public ItemController() {
        this.itemService = new ItemService(new ItemDAO());
    }

    public void cadastrar(String nome, String codigo, String marca, int quantidade, BigDecimal valorCompra, BigDecimal valorVenda)  throws ServiceException {
        Item item = new Item(0, nome, codigo, marca, quantidade, valorCompra, valorVenda);
        itemService.criarItem(item);
    }

    public void editar(String nome,
                        String codigoNovo,
                        String marca,
                        int quantidade,
                        BigDecimal valorCompra,
                        BigDecimal valorVenda,
                        String codigoAntigo)
            throws ServiceException {

        Item item = new Item(0, nome, codigoNovo, marca, quantidade, valorCompra, valorVenda);
        itemService.atualizarItem(item, codigoAntigo);
    }

    public void excluir(String codigo) throws ServiceException {
        itemService.deletarItemPorCodigo(codigo);
    }

    public Optional<Item> buscarPorCodigo(String codigo) throws ServiceException {
        return itemService.buscarItemPorCodigo(codigo);
    }

    public List<Item> buscarPorNome(String nome) throws ServiceException {
        return itemService.buscarItensPorNome(nome);
    }

    public List<Item> listarTodos() throws ServiceException {
        return itemService.buscarTodosItens();
    }

    public List<Item> buscarPorOrdemServico(int idOrdemServico) throws ServiceException {
        return itemService.buscarItensPorOrdemServico(idOrdemServico);
    }

    public void diminuirQuantidade(String codigoItem, int quantidadeUsada) throws ServiceException {
        itemService.diminuirQuantidade(codigoItem, quantidadeUsada);
    }

    public boolean codigoExiste(String codigo) throws ServiceException {
        return itemService.buscarItemPorCodigo(codigo).isPresent();
    }
}