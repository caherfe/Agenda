package com.trabajo.agenda;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
		
//		/*Descomentar este c—digo para rellenar la base de datos inicialmente*/
//		bdConexion.abrirEscritura();
//		Contacto contacto;
//		for(int i=1; i<=10; i++){
//			contacto = new Contacto("Nombre "+i, "Apellidos "+i, "Telefono"+i, "nombre"+i+"@email.com",
//					'H', true, true, true);
//			bdConexion.insertarContacto(contacto);
//		}	
//		bdConexion.cerrar();
		
		//Asociamos el menœ contextual a la lista
	    registerForContextMenu(lstContactos);
	    
	    //Cuando hagamos clic en algœn elemento de la lista...
	    lstContactos.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {				
				iniciarActividad(DetallesContactoActivity.class, (int)id);
			}
		});
	    
	    //Actualizar lista
	    this.actualizarCursor();
		
	}
	
	public void actualizarCursor(){
		bdConexion.abrirLectura();
		cursor= bdConexion.obtenerContactos();
		adaptador = new AdaptadorContactos(this, R.layout.listitem_contacto, cursor, new String[]{}, new int[]{},0);
		lstContactos.setAdapter(adaptador);
		bdConexion.cerrar();		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
	    menu.setHeaderTitle("Elige una opci—n");
	    inflater.inflate(R.menu.menu_contextual_contacto, menu);
	   
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		 
		    switch (item.getItemId()) {
		    	case R.id.menuDetalles:
		    		iniciarActividad(DetallesContactoActivity.class, (int)info.id);
		    	return true;
		        case R.id.menuEditar:
		        	iniciarActividad(AgregarContactosActivity.class, (int)info.id);
		            return true;
		        case R.id.menuBorrar:
		        	eliminarContacto((int)info.id);
		            return true;
		        default:
		            return super.onContextItemSelected(item);
		    }
	}
	
	private void eliminarContacto(int id) {
		final int idContacto = id;
		AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
 
        builder.setMessage("ÀConfirma que desea eliminar el contacto?")
        .setTitle("ÁLa acci—n no podr‡ deshacerse!")
        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener()  {
               public void onClick(DialogInterface dialog, int id) {
	            	   	bdConexion.abrirEscritura();
	   	            	bdConexion.eliminarContacto(idContacto);
	   	            	bdConexion.cerrar();
	   	            	actualizarCursor();
	                    dialog.cancel();
                   }
               })
        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                   }
               });
 
        builder.show();
	            
		
	}

	private void iniciarActividad(Class clase, int id) {
    	Intent intent = new Intent(MainActivity.this, clase);
		Bundle b = new Bundle();
		b.putInt("id", id);
		intent.putExtras(b);
		startActivity(intent);
		
	}
	
	
}
