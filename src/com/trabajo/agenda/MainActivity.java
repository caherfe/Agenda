package com.trabajo.agenda;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ListView lstContactos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Obtenci�n de referencias java
		lstContactos= (ListView)this.findViewById(R.id.lstContactos);
		
		//Base de datos
		ContactosSQLiteHelper bdConexion = new ContactosSQLiteHelper(this);
		bdConexion.abrir();
		
		/*for(int i=1; i<=10; i++){
			bdConexion.insertarContacto("Nombre "+i, "Apellidos "+i);
		}*/
		
		Cursor cursor = null;
		cursor= bdConexion.obtenerContactos();
		SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[]{ContactosSQLiteHelper.ID_NOMBRE}, new int[]{android.R.id.text1},0);
	    lstContactos.setAdapter(adaptador);
		
	    bdConexion.cerrar();
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main,menu);
		return true;
	}

}
