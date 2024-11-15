package com.example.botiquin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.botiquin.Medicamento;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "botiquin.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "remedios";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_CANTIDAD = "cantidad";
    public static final String COLUMN_FECHA_VENCIMIENTO = "fecha_vencimiento";
    public static final String COLUMN_MG = "mg";
    public static final String COLUMN_PRESENTACION = "presentacion";
    public static final String COLUMN_DESCRIPCION = "descripcion";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOMBRE + " TEXT NOT NULL, " +
                COLUMN_CANTIDAD + " INTEGER NOT NULL, " +
                COLUMN_FECHA_VENCIMIENTO + " TEXT NOT NULL, " +
                COLUMN_MG + " INTEGER, " +
                COLUMN_PRESENTACION + " TEXT, " +
                COLUMN_DESCRIPCION + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    // Método para agregar un nuevo medicamento
    public long addMedicamento(Medicamento medicamento) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, medicamento.getNombre());
        values.put(COLUMN_CANTIDAD, medicamento.getCantidad());
        values.put(COLUMN_FECHA_VENCIMIENTO, medicamento.getFechaVencimiento());
        values.put(COLUMN_MG, medicamento.getMiligramos());
        values.put(COLUMN_PRESENTACION, medicamento.getPresentacion());
        values.put(COLUMN_DESCRIPCION, medicamento.getDescripcion());

        long id = -1;
        try {
            id = db.insert(TABLE_NAME, null, values);
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error al insertar medicamento", e);
        } finally {
            db.close();
        }
        return id;
    }

    // Método para obtener todos los medicamentos
    public List<Medicamento> getAllMedicamentos() {
        List<Medicamento> medicamentoList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(TABLE_NAME, null, null, null, null, null, COLUMN_NOMBRE + " ASC");
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                    String nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE));
                    int cantidad = cursor.getInt(cursor.getColumnIndex(COLUMN_CANTIDAD));
                    String fechaVencimiento = cursor.getString(cursor.getColumnIndex(COLUMN_FECHA_VENCIMIENTO));
                    int miligramos = cursor.getInt(cursor.getColumnIndex(COLUMN_MG));
                    String presentacion = cursor.getString(cursor.getColumnIndex(COLUMN_PRESENTACION));
                    String descripcion = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPCION));

                    Medicamento medicamento = new Medicamento(id, nombre, cantidad, fechaVencimiento, miligramos, presentacion, descripcion);
                    medicamentoList.add(medicamento);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error al obtener medicamentos", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return medicamentoList;
    }

    // Método para obtener un medicamento por su nombre
    public Medicamento getMedicamentoByName(String nombre) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        Medicamento medicamento = null;

        try {
            cursor = db.query(TABLE_NAME, null, COLUMN_NOMBRE + "=?", new String[]{nombre}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String nombreMedicamento = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE));
                int cantidad = cursor.getInt(cursor.getColumnIndex(COLUMN_CANTIDAD));
                String fechaVencimiento = cursor.getString(cursor.getColumnIndex(COLUMN_FECHA_VENCIMIENTO));
                int miligramos = cursor.getInt(cursor.getColumnIndex(COLUMN_MG));
                String presentacion = cursor.getString(cursor.getColumnIndex(COLUMN_PRESENTACION));
                String descripcion = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPCION));

                medicamento = new Medicamento(id, nombreMedicamento, cantidad, fechaVencimiento, miligramos, presentacion, descripcion);
            }
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error al obtener medicamento por nombre", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return medicamento;
    }

    // Método para actualizar un medicamento
    public int updateMedicamento(Medicamento medicamento) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, medicamento.getNombre());
        values.put(COLUMN_CANTIDAD, medicamento.getCantidad());
        values.put(COLUMN_FECHA_VENCIMIENTO, medicamento.getFechaVencimiento());
        values.put(COLUMN_MG, medicamento.getMiligramos());
        values.put(COLUMN_PRESENTACION, medicamento.getPresentacion());
        values.put(COLUMN_DESCRIPCION, medicamento.getDescripcion());

        int rowsAffected = 0;
        try {
            rowsAffected = db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(medicamento.getId())});
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error al actualizar medicamento", e);
        } finally {
            db.close();
        }
        return rowsAffected;
    }

    // Método para eliminar un medicamento
    public void deleteMedicamento(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error al eliminar medicamento", e);
        } finally {
            db.close();
        }
    }
}
