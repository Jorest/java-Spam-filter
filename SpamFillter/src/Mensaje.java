
public class Mensaje {

	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	int tipo;
	String mensaje;
	
	public Mensaje(int t, String m){
		tipo=t;
		mensaje=m;
	}
	
}

