package controllers;

import Services.FinancasService;
import entities.ResumoFinanceiro;
import exceptions.ControllerException;
import exceptions.ServiceException;

import java.sql.Date;

public class FinancasController {
    private final FinancasService financasService;

    public FinancasController() {
        this.financasService = new FinancasService();
    }

    public ResumoFinanceiro consultarResumo(Date inicio, Date fim) throws ControllerException {
        try {
            return financasService.obterBalançoPeriodo(inicio, fim);
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }
}