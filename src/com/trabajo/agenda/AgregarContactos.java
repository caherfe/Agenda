package com.trabajo.agenda;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AgregarContactos extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agregar_contactos);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// el item es el del boton para confirmar y agregar
		getMenuInflater().inflate(R.menu.agregar_contactos, menu);
		return true;
	}

}
