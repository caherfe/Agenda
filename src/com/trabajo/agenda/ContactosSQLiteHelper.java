package com.trabajo.agenda;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactosSQLiteHelper extends SQLiteOpenHelper{
	
	public static final String ID_FILA = "_id";
	public static final String ID_NOMBRE = "nombre";
	public static final String ID_APELLIDOS = "apellidos";
	public static final String ID_EMAIL = "email";
	public static final String ID_TELEFONO = "telefono";
	public static final String ID_SEXO = "sexo";
	public static final String ID_DEPORTES = "deportes";
	public static final String ID_COCINA = "cocina";
	public static final String ID_INFORMATICA = "informatica";
	public static final String ID_IMAGEN = "imagen";
	
	private static final String TABLA_NOMBRE = "contactos";
	private static final String BASEDATOS_NOMBRE = "bdContactos.db";
	private static final int BASEDATOS_VERSION = 1;
	private SQLiteDatabase baseDatos;
	
	public ContactosSQLiteHelper(Context context){
		this(context, BASEDATOS_NOMBRE, null, BASEDATOS_VERSION);
	}
	
	public ContactosSQLiteHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	//C—digo necesario para crear nuestra base de datos 
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sqlCreate = "CREATE TABLE " + TABLA_NOMBRE +"("
				+ ID_FILA + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ ID_NOMBRE + " TEXT NOT NULL, "
				+ ID_APELLIDOS + " TEXT NOT NULL, "
				+ ID_TELEFONO + " TEXT NOT NULL, "
				+ ID_EMAIL + " TEXT NOT NULL, "
				+ ID_SEXO + " TEXT NOT NULL, " //H o M
				+ ID_DEPORTES + " INTEGER NOT NULL, " //1 checked 0 unchecked
				+ ID_COCINA + " INTEGER NOT NULL, " //1 checked 0 unchecked
				+ ID_INFORMATICA + " INTEGER NOT NULL, " //1 checked 0 unchecked
				+ ID_IMAGEN + " TEXT);"; 
		db.execSQL(sqlCreate);
	}

	//C—digo necesario para actualizar la estructura de nuestra base de datos
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//NOTA: Por simplicidad del ejemplo aqu’ utilizamos directamente la opci—n de
        //      eliminar la tabla anterior y crearla de nuevo vac’a con el nuevo formato.
        //      Sin embargo lo normal ser‡ que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este mŽtodo deber’a ser m‡s elaborado.
 
        //Se elimina la versi—n anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_NOMBRE);
        //Se crea la nueva versi—n de la tabla
        onCreate(db);
	}
	
	public void abrirEscritura(){
		baseDatos = getWritableDatabase();		
	}
	
	public void abrirLectura(){
		baseDatos = getReadableDatabase();
	}
	
	public void cerrar(){
		close();
	}
	
	public void insertarContacto(Contacto contacto){
		//Convertimos a enteros los booleanos
		int dep = (contacto.isDeportes())? 1 : 0;
		int coc = (contacto.isCocina())? 1 : 0;
		int inf = (contacto.isInformatica())? 1 : 0;
		
		String sql = "INSERT INTO " + TABLA_NOMBRE +  "(" + ID_NOMBRE + ", " + ID_APELLIDOS + ", " 
				+ ID_TELEFONO + ", " + ID_EMAIL + ", " + ID_SEXO + ", " + ID_DEPORTES + ", " 
				+ ID_COCINA + ", " + ID_INFORMATICA + ", " + ID_IMAGEN + ") VALUES ('" + contacto.getNombre() + "', '" 
				+ contacto.getApellidos() + "', '" + contacto.getTelefono() + "', '" + contacto.getEmail() + "', '" 
				+ contacto.getSexo() + "', '" + dep + "', '" + coc + "', '" + inf +  "', '" + contacto.getImagen() +"')";
		baseDatos.execSQL(sql);	
	}
	
	public Cursor obtenerContactos(){
		String sql = "SELECT * FROM " + TABLA_NOMBRE + " ORDER BY " + ID_NOMBRE + ";";
		return baseDatos.rawQuery(sql, null);
		
	}
	
	public void eliminarContacto(int id){
		String sql = "DELETE FROM " + TABLA_NOMBRE + " WHERE " + ID_FILA + " = '" + id + "'";
		baseDatos.execSQL(sql);
		
	}

	public Contacto getContacto(int id){
		String sql = "SELECT * FROM " + TABLA_NOMBRE + " WHERE " + ID_FILA + " = '" + id + "'";
		Cursor cursor = baseDatos.rawQuery(sql, null);
		cursor.moveToFirst();
		Boolean dep = (cursor.getInt(6) == 1)? true : false;
		Boolean coc = (cursor.getInt(7) == 1)? true : false;
		Boolean inf = (cursor.getInt(8) == 1)? true : false;
		Contacto contacto = new Contacto(cursor.getString(1), cursor.getString(2), cursor.getString(3),
				cursor.getString(4), cursor.getString(5).charAt(0), dep, coc, inf, cursor.getString(9));
		cursor.close();
		return contacto;
	}
	
	public String getTelefono(int id){
		String sql = "SELECT " + ID_TELEFONO + " FROM " + TABLA_NOMBRE + " WHERE " + ID_FILA + " = '" + id + "'";
		Cursor cursor = baseDatos.rawQuery(sql, null);
		cursor.moveToFirst();
		String telefono = cursor.getString(cursor.getColumnIndex(ID_TELEFONO));
		cursor.close();
		return telefono;
	}
	
	public void actualizarContacto(Contacto contacto, int id){
		//Convertimos a enteros los booleanos -> Fusionar con el insertar
		int dep = (contacto.isDeportes())? 1 : 0;
		int coc = (contacto.isCocina())? 1 : 0;
		int inf = (contacto.isInformatica())? 1 : 0;
		
		String sql = "UPDATE " + TABLA_NOMBRE +  " SET " + ID_NOMBRE + " = '" + contacto.getNombre() + "', " + ID_APELLIDOS + " = '" + contacto.getApellidos() + "', " 
				+ ID_TELEFONO + " = '" + contacto.getTelefono() + "', " + ID_EMAIL + " = '" + contacto.getEmail() + "', " + ID_SEXO + " = '" + contacto.getSexo() + "', " 
				+ ID_DEPORTES + " = " + dep + ", " + ID_COCINA + " = " + coc + ", " + ID_INFORMATICA + "=" + inf+ ", " + ID_IMAGEN + " = '" + contacto.getImagen() + "'" + " WHERE " + ID_FILA + " = " + id + ";";
		baseDatos.execSQL(sql);	
	}
		
	
	public void autodestruccion(){
		String sql = "DELETE FROM " + TABLA_NOMBRE;
		baseDatos.execSQL(sql);
	}
	
	

}
