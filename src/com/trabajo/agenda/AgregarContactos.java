package com.trabajo.agenda;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class AgregarContactos extends Activity {

	private EditText txtNombre, txtApellidos;
	private Button btnAgregar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agregar_contactos);
		
		//Obtenci—n de referencias java
		txtNombre= (EditText)this.findViewById(R.id.txtNombre);
		txtApellidos= (EditText)this.findViewById(R.id.txtApellidos);
		btnAgregar = (Button)this.findViewById(R.id.btnAgregar);
		
		btnAgregar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				ContactosSQLiteHelper bdConexion = new ContactosSQLiteHelper(AgregarContactos.this);	
				bdConexion.abrir();
				bdConexion.insertarContacto(txtNombre.getText().toString(), txtApellidos.getText().toString());
				bdConexion.cerrar();
				Intent intent = new Intent(AgregarContactos.this, MainActivity.class);
	            startActivity(intent);
			}
		});
	}

}
