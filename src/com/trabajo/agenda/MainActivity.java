package com.trabajo.agenda;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	private ListView lstContactos;
	private ContactosSQLiteHelper bdConexion;
	private AdaptadorContactos adaptador;
	private Cursor cursor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Obtenci—n de referencias java
		lstContactos= (ListView)this.findViewById(R.id.lstContactos);
		
		//Base de datos
		bdConexion = new ContactosSQLiteHelper(this);
		
		//Asociamos el menœ contextual a la lista
	    registerForContextMenu(lstContactos);
	    
	    //Cuando hagamos clic en algœn elemento de la lista...
	    lstContactos.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				bdConexion.abrir();
				Contacto contacto = bdConexion.getContacto((int)id);
				bdConexion.cerrar();
				
				Intent intent = new Intent(MainActivity.this, DetallesContactoActivity.class);

                //Creamos la informaci—n a pasar entre actividades
                Bundle b = new Bundle();
                b.putString("nombre", contacto.getNombre());
                b.putString("apellidos", contacto.getApellidos());

                //A–adimos la informaci—n al intent
                intent.putExtras(b);

                //Iniciamos la nueva actividad
                startActivity(intent);
			}
		});
	    
	    
	    
	    //Actualizar lista
	    this.actualizarCursor();
		
	}
	
	public void actualizarCursor(){
		bdConexion.abrir();
		
		/*for(int i=1; i<=30; i++){
			bdConexion.insertarContacto("Nombre "+i, "Apellidos "+i);
		}*/

		cursor= bdConexion.obtenerContactos();
		adaptador = new AdaptadorContactos(this, R.layout.listitem_contacto, cursor, new String[]{}, new int[]{},0);
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
                Intent intent = new Intent(MainActivity.this, AgregarContactosActivity.class);
                startActivity(intent);
				break;
			//A–adir m‡s opciones al menœ de la actividad principal si hay
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();

	    //AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
	    
	    menu.setHeaderTitle("Elige una opci—n");
	 
	   inflater.inflate(R.menu.menu_contextual_contacto, menu);
	   
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		 
		    switch (item.getItemId()) {
		        case R.id.menuEditar:
		            //lblMensaje.setText("Etiqueta: Opcion 1 pulsada!");
		            return true;
		        case R.id.menuBorrar:
		            bdConexion.abrir();
		            bdConexion.eliminarContacto((int)info.id);
		            bdConexion.cerrar();
		            this.actualizarCursor();
		            return true;
		        default:
		            return super.onContextItemSelected(item);
		    }
	}
	
	
	


	
	
}
