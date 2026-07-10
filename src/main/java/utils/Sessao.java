package utils;

import entities.Funcionario;
import entities.enums.Cargo;

public class Sessao {
    public static String email;
    public static String codigo;
    public static Funcionario funcionarioLogado;
    public static boolean codigoSenhaValidado;

    public static void limparDadosCadastroSenha() {
        email = null;
        codigo = null;
        codigoSenhaValidado = false;
    }

    public static void encerrarSessao() {
        email = null;
        codigo = null;
        codigoSenhaValidado = false;
        funcionarioLogado = null;
    }

    public static boolean usuarioPodeEditarNotaTecnica() {
        if (funcionarioLogado == null || funcionarioLogado.getCargo() == null) {
            return false;
        }

        Cargo cargo = funcionarioLogado.getCargo();
        return cargo == Cargo.GERENTE || cargo == Cargo.MECANICO;
    }
}