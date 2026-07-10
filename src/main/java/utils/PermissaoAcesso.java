package utils;

import java.awt.Component;
import javax.swing.JOptionPane;
import entities.enums.Cargo;

public final class PermissaoAcesso {

    private PermissaoAcesso() {}

    public static Cargo getCargoUsuarioLogado() {
        if (Sessao.funcionarioLogado == null) {
            return null;
        }
        return Sessao.funcionarioLogado.getCargo();
    }

    public static boolean usuarioLogado() {
        return Sessao.funcionarioLogado != null && Sessao.funcionarioLogado.getCargo() != null;
    }

    public static boolean isGerente() {
        return getCargoUsuarioLogado() == Cargo.GERENTE;
    }

    public static boolean isMecanico() {
        return getCargoUsuarioLogado() == Cargo.MECANICO;
    }

    public static boolean isAtendente() {
        return getCargoUsuarioLogado() == Cargo.ATENDENTE;
    }

    public static boolean podeAcessarOrdemServico() {
        return isGerente() || isMecanico() || isAtendente();
    }

    public static boolean podeAcessarClientes() {
        return isGerente() || isAtendente();
    }

    public static boolean podeAcessarEstoque() {
        return isGerente() || isMecanico() || isAtendente();
    }

    public static boolean podeAcessarEquipe() {
        return isGerente();
    }

    public static boolean podeAcessarFinancas() {
        return isGerente();
    }

    public static boolean podeGerenciarTiposServico() {
        return isGerente();
    }

    public static boolean podeEditarNotaTecnica() {
        return isGerente() || isMecanico();
    }

    public static boolean autorizar(Component parent, boolean permitido, String recurso) {
        if (permitido) {
            return true;
        }

        JOptionPane.showMessageDialog(
                parent,
                "Acesso negado. Seu cargo não possui permissão para acessar: " + recurso + ".",
                "Acesso restrito",
                JOptionPane.WARNING_MESSAGE
        );
        return false;
    }
}