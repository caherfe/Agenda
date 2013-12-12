package com.trabajo.agenda;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class AgregarContactosActivity extends Activity {

	private EditText txtNombre, txtApellidos, txtTelefono, txtEmail;
	private RadioGroup rdSexo;
	private CheckBox chkDeportes, chkCocina, chkInformatica;
	private Button btnAceptar, btnCancelar;
	private ContactosSQLiteHelper bdConexion;
	private int idActualizar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agregar_contactos);
		
		//Obtenci—n de referencias java
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
			
		//Base de datos
		bdConexion = new ContactosSQLiteHelper(this);
		
		//Si hay informaci—n en el intent la recuperamos, obtenemos el contacto...
		if(this.getIntent().hasExtra("id")){
			//Recuperamos la informaci—n pasada en el intent, obtenemos el contacto...
			Bundle bundle = this.getIntent().getExtras();
			idActualizar = bundle.getInt("id");
			bdConexion.abrirLectura();
			Contacto contacto = bdConexion.getContacto(idActualizar);
			bdConexion.cerrar();

			//... y mostramos la informaci—n
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
		}
		
		
		btnAceptar.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				char sexo;
				if(rdSexo.getCheckedRadioButtonId()==R.id.rdHombre)
					sexo = 'H';
				else
					sexo = 'M';
				ContactosSQLiteHelper bdConexion = new ContactosSQLiteHelper(AgregarContactosActivity.this);
				Contacto contacto = new Contacto(txtNombre.getText().toString(), txtApellidos.getText().toString(),
						txtTelefono.getText().toString(), txtEmail.getText().toString(), sexo, chkDeportes.isChecked(), 
						chkCocina.isChecked(), chkInformatica.isChecked());
				
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
		});
		
		btnCancelar.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(AgregarContactosActivity.this, MainActivity.class);
	            startActivity(intent);
	            finalizarActividad();
			}
		});
	}
	
	public void finalizarActividad(){
		this.finish();
	}

}
