package com.trabajo.agenda;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.support.v4.widget.SimpleCursorAdapter;

public class MainActivity extends Activity {

	private ListView lstContactos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Obtenci—n de referencias java
		lstContactos= (ListView)this.findViewById(R.id.lstContactos);
		
		//Base de datos
		ContactosSQLiteHelper bdConexion = new ContactosSQLiteHelper(this);
		bdConexion.abrir();
		
		/*for(int i=1; i<=10; i++){
			bdConexion.insertarContacto("Nombre "+i, "Apellidos "+i);
		}*/
		
		Cursor cursor = null;
		cursor= bdConexion.obtenerContactos();
		AdaptadorContactos adaptador = new AdaptadorContactos(this, R.layout.listitem_contacto, cursor, new String[]{ContactosSQLiteHelper.ID_NOMBRE}, new int[]{android.R.id.text1},0);
		//SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[]{ContactosSQLiteHelper.ID_NOMBRE}, new int[]{android.R.id.text1},0);
	    lstContactos.setAdapter(adaptador);
		
	    bdConexion.cerrar();
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main,menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
			case R.id.menuContactoNuevo:
                Intent intent = new Intent(MainActivity.this, AgregarContactos.class);
                startActivity(intent);
				break;
			//A–adir m‡s opciones al menœ de la actividad principal si hay
		}
		return super.onOptionsItemSelected(item);
	}
}
