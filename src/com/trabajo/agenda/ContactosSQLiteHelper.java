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

	//Código necesario para crear nuestra base de datos 
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
				+ ID_INFORMATICA + " INTEGER NOT NULL);"; //1 checked 0 unchecked
		db.execSQL(sqlCreate);
		
	}

	//Código necesario para actualizar la estructura de nuestra base de datos
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//NOTA: Por simplicidad del ejemplo aquí utilizamos directamente la opción de
        //      eliminar la tabla anterior y crearla de nuevo vacía con el nuevo formato.
        //      Sin embargo lo normal será que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este método debería ser más elaborado.
 
        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_NOMBRE);
        //Se crea la nueva versión de la tabla
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
	
	public void insertarContacto(String nombre, String apellidos, String telefono, String email, char sexo, boolean deportes, boolean cocina, boolean informatica){
		//Convertimos a enteros los booleanos
		int dep = (deportes)? 1 : 0;
		int coc = (cocina)? 1 : 0;
		int inf = (informatica)? 1 : 0;
		
		String sql = "INSERT INTO " + TABLA_NOMBRE +  "(" + ID_NOMBRE + ", " + ID_APELLIDOS + ", " 
				+ ID_TELEFONO + ", " + ID_EMAIL + ", " + ID_SEXO + ", " + ID_DEPORTES + ", " 
				+ ID_COCINA + ", " + ID_INFORMATICA + ") VALUES ('" + nombre + "', '" + apellidos + "', '" 
				+ telefono + "', '" + email + "', '" + sexo + "', '" + dep + "', '" 
				+ coc + "', '" + inf +  "')";
		baseDatos.execSQL(sql);	
	}
	
	public Cursor obtenerContactos(){
		String sql = "SELECT * FROM " + TABLA_NOMBRE + ";";
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
				cursor.getString(4), cursor.getString(5).charAt(0), dep, coc, inf);
		
		return contacto;
	}
		
	
	/*public Cursor buscarContactoPorId(int num){
		
	}*/
	
	

}
