package com.trabajo.agenda;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
			convertView.setTag(contenedor);
		}
		else{
			contenedor = (ViewHolder) convertView.getTag();
		}
		c.moveToPosition(position);
		contenedor.tvNombre.setText(c.getString(1) + " ");
		contenedor.tvApellidos.setText(c.getString(2));
		return convertView;
	}

}

class ViewHolder {
    TextView tvNombre;
    TextView tvApellidos;
}