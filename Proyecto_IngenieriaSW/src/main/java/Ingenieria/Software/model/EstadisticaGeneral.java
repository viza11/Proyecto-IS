package Ingenieria.Software.model;

public class EstadisticaGeneral {
	private String totalUsuariosN;
	private int cantidadesTotal;
	
	
	
	
	public EstadisticaGeneral() {
		super();
	}




	public EstadisticaGeneral(String totalUsuariosN, int cantidadesTotal) {
		super();
		this.totalUsuariosN = totalUsuariosN;
		this.cantidadesTotal = cantidadesTotal;
	}




	public String getTotalUsuariosN() {
		return totalUsuariosN;
	}




	public void setTotalUsuariosN(String totalUsuariosN) {
		this.totalUsuariosN = totalUsuariosN;
	}




	public int getCantidadesTotal() {
		return cantidadesTotal;
	}




	public void setCantidadesTotal(int cantidadesTotal) {
		this.cantidadesTotal = cantidadesTotal;
	}

	
	
	
}
