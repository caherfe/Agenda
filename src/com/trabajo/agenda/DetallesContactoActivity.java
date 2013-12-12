package com.trabajo.agenda;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DetallesContactoActivity extends Activity {
	private TextView txtNombre, txtApellidos, txtTelefono, txtEmail, txtAficiones;
	private ImageView ivSexo;
	private ContactosSQLiteHelper bdConexion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalles_contacto);
		
		//Referencias java
		txtNombre = (TextView) this.findViewById(R.id.txtNombre);
		txtApellidos = (TextView) this.findViewById(R.id.txtApellidos);
		txtTelefono = (TextView) this.findViewById(R.id.txtTelefono);
		txtEmail = (TextView) this.findViewById(R.id.txtEmail);
		txtAficiones = (TextView) this.findViewById(R.id.txtAficiones);
		ivSexo = (ImageView) this.findViewById(R.id.ivSexo);
		
		//Base de datos
		bdConexion = new ContactosSQLiteHelper(this);
		
		//Recuperamos la informaci—n pasada en el intent, obtenemos el contacto...
		Bundle bundle = this.getIntent().getExtras();
		bdConexion.abrirLectura();
		Contacto contacto = bdConexion.getContacto(bundle.getInt("id"));
		bdConexion.cerrar();

		//... y mostramos la informaci—n
		txtNombre.setText(contacto.getNombre());
		txtApellidos.setText(contacto.getApellidos());
		txtTelefono.setText(contacto.getTelefono());
		txtEmail.setText(contacto.getEmail());
		if(contacto.getSexo()=='H')
			ivSexo.setImageResource(R.drawable.ic_hombre);
		else
			ivSexo.setImageResource(R.drawable.ic_mujer);			
		if(contacto.isDeportes())
			txtAficiones.append("Deportes\n");
		if(contacto.isCocina())
			txtAficiones.append("Cocina\n");
		if(contacto.isInformatica())
			txtAficiones.append("Inform‡tica\n");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.detalles_contacto, menu);
		return true;
	}
	
	public void finalizarActividad(){
		this.finish();
	}

}
