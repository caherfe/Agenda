package com.trabajo.agenda;

public class Contacto {
	private String nombre;
	private String apellidos;
	private String telefono;
	private String email;
	private char sexo;
	private boolean deportes;
	private boolean cocina;
	private boolean informatica;
	
	Contacto(String nombre, String apellidos, String telefono,
			String email, char sexo, boolean deportes, boolean cocina,
			boolean informatica) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.email = email;
		this.sexo = sexo;
		this.deportes = deportes;
		this.cocina = cocina;
		this.informatica = informatica;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public boolean isDeportes() {
		return deportes;
	}

	public void setDeportes(boolean deportes) {
		this.deportes = deportes;
	}

	public boolean isCocina() {
		return cocina;
	}

	public void setCocina(boolean cocina) {
		this.cocina = cocina;
	}

	public boolean isInformatica() {
		return informatica;
	}

	public void setInformatica(boolean informatica) {
		this.informatica = informatica;
	}
}
