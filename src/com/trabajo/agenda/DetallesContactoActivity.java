package com.trabajo.agenda;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class DetallesContactoActivity extends Activity {
	private TextView txtNombre;
	private TextView txtApellidos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalles_contacto);
		
		//Referencias java
		txtNombre = (TextView) this.findViewById(R.id.txtNombre);
		txtApellidos = (TextView) this.findViewById(R.id.txtApellidos);
		
		//Recuperamos la informaci—n pasada en el intent y la mostramos
        Bundle bundle = this.getIntent().getExtras();
		txtNombre.setText(bundle.getString("nombre"));
		txtApellidos.setText(bundle.getString("apellidos"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalles_contacto, menu);
		return true;
	}

}
