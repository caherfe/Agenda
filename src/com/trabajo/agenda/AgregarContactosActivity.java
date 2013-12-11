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
	private Button btnOk;
	
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
		btnOk = (Button)this.findViewById(R.id.btnOk);
		
		btnOk.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				char sexo;
				if(rdSexo.getCheckedRadioButtonId()==R.id.rdHombre)
					sexo = 'H';
				else
					sexo = 'M';
				ContactosSQLiteHelper bdConexion = new ContactosSQLiteHelper(AgregarContactosActivity.this);	
				bdConexion.abrirEscritura();
				bdConexion.insertarContacto(txtNombre.getText().toString(), txtApellidos.getText().toString(),
						txtTelefono.getText().toString(), txtEmail.getText().toString(), sexo, chkDeportes.isChecked(), 
						chkCocina.isChecked(), chkInformatica.isChecked());
				bdConexion.cerrar();
				Intent intent = new Intent(AgregarContactosActivity.this, MainActivity.class);
	            startActivity(intent);
			}
		});
	}

}
