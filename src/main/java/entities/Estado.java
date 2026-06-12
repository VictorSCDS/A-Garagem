package entities;

public enum Estado {
	NAO_PAGO,
	PAGO,
	CONCLUIDO,
	CANCELADO;
	
	public String toString(Estado estado) {
		return String.valueOf(estado).replace("_", " ").toLowerCase();
	}
	
	public Estado fromString(String str) {
		return Estado.valueOf(str.replace(" ", "_").toUpperCase());
	}
}
