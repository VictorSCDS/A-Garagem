package dao;

import exceptions.DatabaseException;

import java.util.List;
import java.util.Optional;

public interface GenericDAO<T, ID> {

    void criar(T entidade) throws DatabaseException;

    List<T> buscarTodos() throws DatabaseException;

    Optional<T> buscarPorAtributoIdentificador(ID identificador) throws DatabaseException;

    void atualizar(T entidadeAntiga, ID identificador) throws DatabaseException;

    void deletar(ID identificador) throws DatabaseException;

}
