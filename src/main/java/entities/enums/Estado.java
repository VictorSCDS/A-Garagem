package entities.enums;

public enum Estado {
    NAO_PAGO("nao_pago", "Não pago"),
    PAGO("pago", "Pago"),
    CONCLUIDO("concluido", "Concluído"),
    CANCELADO("cancelado", "Cancelado");

    private final String valorBanco;
    private final String descricaoTela;

    Estado(String valorBanco, String descricaoTela) {
        this.valorBanco = valorBanco;
        this.descricaoTela = descricaoTela;
    }

    public String getValorBanco() {
        return valorBanco;
    }

    public String getDescricaoTela() {
        return descricaoTela;
    }

    public static Estado fromBanco(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return NAO_PAGO;
        }

        String normalizado = normalizar(valor);

        for (Estado estado : Estado.values()) {
            if (normalizado.equals(normalizar(estado.valorBanco))
                    || normalizado.equals(normalizar(estado.name()))
                    || normalizado.equals(normalizar(estado.descricaoTela))) {
                return estado;
            }
        }

        throw new IllegalArgumentException("Estado inválido no banco: " + valor);
    }

    public static Estado fromTela(String valor) {
        return fromBanco(valor);
    }

    public static Estado fromString(String valor) {
        return fromBanco(valor);
    }
    public static String estadoToString(Estado estado) {
        return estado == null ? "" : estado.getValorBanco();
    }

    public static String estadoToStringTela(Estado estado) {
        return estado == null ? "" : estado.getDescricaoTela();
    }

    private static String normalizar(String texto) {
        if (texto == null) {
            return "";
        }

        return java.text.Normalizer
                .normalize(texto.trim(), java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase()
                .replace('-', '_')
                .replace(' ', '_');
    }

    @Override
    public String toString() {
        return descricaoTela;
    }
}