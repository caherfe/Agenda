package com.trabajo.agenda;

import java.io.File;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AgregarContactosActivity extends Activity {

	private static int PICK_IMAGEN = 1;
	
	private EditText txtNombre, txtApellidos, txtTelefono, txtEmail;
	private RadioGroup rdSexo;
	private CheckBox chkDeportes, chkCocina, chkInformatica;
	private Button btnAceptar, btnCancelar;
	private ImageButton btnPerfil;
	private ContactosSQLiteHelper bdConexion;
	private int idActualizar;
	private String imagen="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agregar_contactos);
		
		//Obtención de referencias java
		txtNombre= (EditText)this.findViewById(R.id.txtNombre);
		txtApellidos= (EditText)this.findViewById(R.id.txtApellidos);
		txtTelefono= (EditText)this.findViewById(R.id.txtTelefono);
		txtEmail= (EditText)this.findViewById(R.id.txtEmail);
		rdSexo = (RadioGroup)this.findViewById(R.id.rdGroupSexo);
		chkDeportes = (CheckBox) this.findViewById(R.id.chkDeportes);
		chkCocina = (CheckBox) this.findViewById(R.id.chkCocina);
		chkInformatica = (CheckBox) this.findViewById(R.id.chkInformatica);
		btnAceptar = (Button)this.findViewById(R.id.btnAceptar);
		btnCancelar = (Button)this.findViewById(R.id.btnCancelar);
		btnPerfil = (ImageButton)this.findViewById(R.id.btnPerfil);
			
		//Base de datos
		bdConexion = new ContactosSQLiteHelper(this);
		
		//Si hay información en el intent la recuperamos, obtenemos el contacto...
		if(this.getIntent().hasExtra("id")){
			//Recuperamos la información pasada en el intent, obtenemos el contacto...
			Bundle bundle = this.getIntent().getExtras();
			idActualizar = bundle.getInt("id");
			bdConexion.abrirLectura();
			Contacto contacto = bdConexion.getContacto(idActualizar);
			bdConexion.cerrar();

			//... y mostramos la información
			txtNombre.setText(contacto.getNombre());
			txtApellidos.setText(contacto.getApellidos());
			txtTelefono.setText(contacto.getTelefono());
			txtEmail.setText(contacto.getEmail());
			if(contacto.getSexo()=='H')
				rdSexo.check(R.id.rdHombre);
			else
				rdSexo.check(R.id.rdMujer);
			chkDeportes.setChecked(contacto.isDeportes());
			chkCocina.setChecked(contacto.isCocina());
			chkInformatica.setChecked(contacto.isInformatica());
			imagen = contacto.getImagen();
		}
		//Imagen botón
		Bitmap bitmap =  AdaptadorContactos.getBitmap(imagen);
		if(imagen.equals("") || bitmap==null)
			btnPerfil.setImageResource(R.drawable.ic_add_imagen);
		else
			btnPerfil.setImageBitmap( AdaptadorContactos.getBitmap(imagen));
		
		
		btnAceptar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				//Comprobamos que ningún campo está vacío
				if(txtNombre.getText().toString().equals("") || txtApellidos.getText().toString().equals("") || txtEmail.getText().toString().equals("") || txtTelefono.getText().toString().equals(""))
					crearMensaje("Los campos de texto son obligatorios");
				else{
					char sexo;
					if(rdSexo.getCheckedRadioButtonId()==R.id.rdHombre)
						sexo = 'H';
					else
						sexo = 'M';
					ContactosSQLiteHelper bdConexion = new ContactosSQLiteHelper(AgregarContactosActivity.this);
					Contacto contacto = new Contacto(txtNombre.getText().toString(), txtApellidos.getText().toString(),
							txtTelefono.getText().toString(), txtEmail.getText().toString(), sexo, chkDeportes.isChecked(), 
							chkCocina.isChecked(), chkInformatica.isChecked(), imagen);
					
					bdConexion.abrirEscritura();
					if(idActualizar!=0)
						bdConexion.actualizarContacto(contacto, idActualizar);
					else
						bdConexion.insertarContacto(contacto);
					bdConexion.cerrar();
					Intent intent = new Intent(AgregarContactosActivity.this, MainActivity.class);
		            startActivity(intent);
		            finalizarActividad();
				}
				
			}
		});
		
		btnCancelar.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(AgregarContactosActivity.this, MainActivity.class);
	            startActivity(intent);
	            finalizarActividad();
			}
		});
		
		btnPerfil.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				//Lanza actividad que muestra una lista de objetos a seleccionar para que el usuario elija uno de ellos
				Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
				//Filtrado para las imágenes
				intent.setType("image/*");
				//Usamos esta porque tiene callback para indicar qué ha pasado en la actividad invocada (intent y requestCode)
				startActivityForResult(intent, PICK_IMAGEN);
				
			}
			
		});
	}
	
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		try {
			if (requestCode == PICK_IMAGEN) {
				if (resultCode == Activity.RESULT_OK) {
					Uri selectedImage = data.getData();
					imagen = getRuta(selectedImage);
					Bitmap bitmap = AdaptadorContactos.getBitmap(imagen);

					if (bitmap.getHeight() != 120 || bitmap.getWidth() != 120) {
						bitmap = Bitmap.createScaledBitmap(
								bitmap,
								(bitmap.getHeight() != 120) ? 120 : bitmap
										.getHeight(),
								(bitmap.getWidth() != 120) ? 120 : bitmap
										.getWidth(), true);
						
					} 
					
					btnPerfil.setImageBitmap(bitmap);
				}
			}
		} catch (Exception e) {
		}

	}

	private String getRuta(Uri uri) {
		String[] projection = { android.provider.MediaStore.Images.Media.DATA };
		Cursor cursor = this.getContentResolver().query(uri, projection, null, null, null);
		int columnIndex = cursor.getColumnIndex(projection[0]);
		cursor.moveToFirst();
		String ruta = cursor.getString(columnIndex);
	    cursor.close();
	    return ruta;
	}


	public void crearMensaje(String mensaje){
		Toast.makeText(this.getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
	}

	public void finalizarActividad(){
		this.finish();
	}

}
