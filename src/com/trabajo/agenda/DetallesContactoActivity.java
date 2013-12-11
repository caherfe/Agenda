package com.trabajo.agenda;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

public class DetallesContactoActivity extends Activity {
	private TextView txtNombre, txtApellidos, txtTelefono, txtEmail, txtSexo, txtAficiones;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalles_contacto);
		
		//Referencias java
		txtNombre = (TextView) this.findViewById(R.id.txtNombre);
		txtApellidos = (TextView) this.findViewById(R.id.txtApellidos);
		txtTelefono = (TextView) this.findViewById(R.id.txtTelefono);
		txtEmail = (TextView) this.findViewById(R.id.txtEmail);
		txtSexo = (TextView) this.findViewById(R.id.txtSexo);
		txtAficiones = (TextView) this.findViewById(R.id.txtAficiones);
		
		//Recuperamos la informaci—n pasada en el intent y la mostramos
        Bundle bundle = this.getIntent().getExtras();
		txtNombre.setText(bundle.getString("nombre"));
		txtApellidos.setText(bundle.getString("apellidos"));
		txtTelefono.setText(bundle.getString("telefono"));
		txtEmail.setText(bundle.getString("email"));
		txtSexo.setText(String.valueOf(bundle.getChar("sexo")));
		if(bundle.getBoolean("deportes"))
			txtAficiones.append("Deportes ");
		if(bundle.getBoolean("cocina"))
			txtAficiones.append("Cocina ");
		if(bundle.getBoolean("informatica"))
			txtAficiones.append("Inform‡tica ");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalles_contacto, menu);
		return true;
	}

}
