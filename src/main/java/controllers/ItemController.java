package controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import Services.ItemService;
import dao.ItemDAO;
import entities.Item;
import exceptions.ControllerException;
import exceptions.ServiceException;

public class ItemController {

    private final ItemService itemService;

    public ItemController() {
        this.itemService = new ItemService(new ItemDAO());
    }

    public void cadastrar(String nome,
                           String codigo,
                           String marca,
                           int quantidade,
                           BigDecimal valorCompra,
                           BigDecimal valorVenda)
            throws ControllerException {

        Item item = new Item(0, nome, codigo, marca, quantidade, valorCompra, valorVenda);

        try {
            itemService.criarItem(item);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public void editar(String nome,
                       String codigoNovo,
                       String marca,
                       int quantidade,
                       BigDecimal valorCompra,
                       BigDecimal valorVenda,
                       String codigoAntigo)
            throws ControllerException {

        Item item = new Item(0, nome, codigoNovo, marca, quantidade, valorCompra, valorVenda);

        try {
            itemService.atualizarItem(item, codigoAntigo);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public void excluir(String codigo) throws ControllerException {

        try {
            itemService.deletarItemPorCodigo(codigo);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public Optional<Item> buscarPorCodigo(String codigo) throws ControllerException {

        try {
            return itemService.buscarItemPorCodigo(codigo);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public List<Item> buscarPorNome(String nome) throws ControllerException {

        try {
            return itemService.buscarItensPorNome(nome);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public List<Item> buscarPorMarca(String marca) throws ControllerException {

        try {
            return itemService.buscarItensPorMarca(marca);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public List<Item> listarTodos() throws ControllerException {

        try {
            return itemService.buscarTodosItens();

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public List<Item> buscarPorOrdemServico(int idOrdemServico) throws ControllerException {

        try {
            return itemService.buscarItensPorOrdemServico(idOrdemServico);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public void diminuirQuantidade(String codigoItem, int quantidadeUsada) throws ControllerException {

        try {
            itemService.diminuirQuantidade(codigoItem, quantidadeUsada);

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }

    public boolean codigoExiste(String codigo) throws ControllerException {

        try {
            return itemService.buscarItemPorCodigo(codigo).isPresent();

        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }
}