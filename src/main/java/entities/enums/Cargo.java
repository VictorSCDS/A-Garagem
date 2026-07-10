package entities.enums;

public enum Cargo {
    GERENTE,
    ATENDENTE,
    MECANICO;

    public static Cargo fromString(String str) {
        return Cargo.valueOf(str.toUpperCase());
    }

    public static String cargoToString(Cargo cargo) {
        return String.valueOf(cargo).replace("_", " ");
    }
}
