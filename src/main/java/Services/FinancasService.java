package Services;

import dao.FinancasDAO;
import entities.ResumoFinanceiro;
import exceptions.DatabaseException;
import exceptions.ServiceException;

import java.sql.Date;

public class FinancasService {
    private final FinancasDAO financasDAO;

    public FinancasService() {
        this.financasDAO = new FinancasDAO();
    }

    public ResumoFinanceiro obterBalançoPeriodo(Date inicio, Date fim) throws ServiceException {
        if (fim.before(inicio)) {
            throw new ServiceException("A data de término não pode ser anterior à data de início.");
        }
        try {
            return financasDAO.buscarResumoPeriodo(inicio, fim);
        } catch (DatabaseException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}