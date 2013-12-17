package com.trabajo.agenda;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.widget.SimpleCursorAdapter;


public class AdaptadorContactos extends SimpleCursorAdapter{
	
	private Cursor c;
	private Activity context;
	
	public AdaptadorContactos(Activity context, int layout, Cursor c,
			String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
		this.c=c;
		this.context=context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder contenedor;
		
		if(convertView == null){
			LayoutInflater inflater = context.getLayoutInflater();
			convertView = inflater.inflate(R.layout.listitem_contacto, null);
			contenedor = new ViewHolder();
			contenedor.tvNombre = (TextView)convertView.findViewById(R.id.tvNombre);
			contenedor.tvApellidos = (TextView)convertView.findViewById(R.id.tvApellidos);
			contenedor.ivImagen = (ImageView)convertView.findViewById(R.id.ivImagen);
			convertView.setTag(contenedor);
		}
		else{
			contenedor = (ViewHolder) convertView.getTag();
		}
		c.moveToPosition(position);
		contenedor.tvNombre.setText(c.getString(1));
		contenedor.tvApellidos.setText(c.getString(2));
		String ruta = c.getString(c.getColumnIndex(ContactosSQLiteHelper.ID_IMAGEN));
		Bitmap bitmap = getBitmap(ruta);
		if(ruta.equals("") || bitmap==null)
			contenedor.ivImagen.setImageResource(R.drawable.ic_profile);
		else
			contenedor.ivImagen.setImageBitmap(bitmap);
			
		return convertView;
	}
	
	public static Bitmap getBitmap(String imagen) {
		File imagenArchivo = new File(imagen);
		Bitmap bitmap = null;

		if (imagenArchivo.exists()) {
			bitmap = BitmapFactory.decodeFile(imagenArchivo.getAbsolutePath());
			return bitmap;
		}
		return null;		
	}

}

class ViewHolder {
    TextView tvNombre;
    TextView tvApellidos;
    ImageView ivImagen;
}