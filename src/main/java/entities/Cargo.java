package entities;

public enum Cargo {
	GERENTE,
	ATENDENTE,
	MECANICO;
	
	public String toString(Cargo cargo) {
		return String.valueOf(cargo).replace("_", " ").toLowerCase();
	}
	
	public Cargo fromString(String str) {
		return Cargo.valueOf(str.replace(" ", "_").toUpperCase());
	}
}
