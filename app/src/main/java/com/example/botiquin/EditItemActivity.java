package com.example.botiquin;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditItemActivity extends AppCompatActivity {

    private EditText etNombre, etCantidad, etFechaVencimiento, etMg, etPresentacion, etDescripcion;
    private Button btnGuardar, btnEliminar;
    private DatabaseHelper dbHelper;
    private int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        // Inicializar vistas
        etNombre = findViewById(R.id.etNombre);
        etCantidad = findViewById(R.id.etCantidad);
        etFechaVencimiento = findViewById(R.id.etFechaVencimiento);
        etMg = findViewById(R.id.etMg);
        etPresentacion = findViewById(R.id.etPresentacion);
        etDescripcion = findViewById(R.id.etDescripcion);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnEliminar = findViewById(R.id.btnEliminar);

        dbHelper = new DatabaseHelper(this);

        // Obtener el ID del remedio desde el Intent
        itemId = getIntent().getIntExtra("ITEM_ID", -1);
        if (itemId != -1) {
            cargarDatosItem();
        }

        // Configurar evento de clic para guardar los cambios
        btnGuardar.setOnClickListener(v -> actualizarRemedio());

        // Configurar evento de clic para eliminar el remedio
        btnEliminar.setOnClickListener(v -> eliminarRemedio());
    }

    private void cargarDatosItem() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_NAME,
                null,
                DatabaseHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(itemId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            // Cargar los datos del remedio en los campos correspondientes
            etNombre.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NOMBRE)));
            etCantidad.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CANTIDAD)));
            etFechaVencimiento.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FECHA_VENCIMIENTO)));
            etMg.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_MG)));
            etPresentacion.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRESENTACION)));
            etDescripcion.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPCION)));
        }
        cursor.close();
    }

    private void actualizarRemedio() {
        String nombre = etNombre.getText().toString();
        String cantidad = etCantidad.getText().toString();
        String fechaVencimiento = etFechaVencimiento.getText().toString();
        String mg = etMg.getText().toString();
        String presentacion = etPresentacion.getText().toString();
        String descripcion = etDescripcion.getText().toString();

        if (nombre.isEmpty() || cantidad.isEmpty() || fechaVencimiento.isEmpty()) {
            Toast.makeText(this, "Los campos Nombre, Cantidad y Fecha son obligatorios.", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("UPDATE " + DatabaseHelper.TABLE_NAME + " SET " +
                        DatabaseHelper.COLUMN_NOMBRE + " = ?, " +
                        DatabaseHelper.COLUMN_CANTIDAD + " = ?, " +
                        DatabaseHelper.COLUMN_FECHA_VENCIMIENTO + " = ?, " +
                        DatabaseHelper.COLUMN_MG + " = ?, " +
                        DatabaseHelper.COLUMN_PRESENTACION + " = ?, " +
                        DatabaseHelper.COLUMN_DESCRIPCION + " = ? " +
                        "WHERE " + DatabaseHelper.COLUMN_ID + " = ?",
                new Object[]{nombre, cantidad, fechaVencimiento, mg, presentacion, descripcion, itemId});

        Toast.makeText(this, "Producto actualizado", Toast.LENGTH_SHORT).show();
        finish();  // Volver a la pantalla anterior
    }

    private void eliminarRemedio() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COLUMN_ID + " = ?", new Object[]{itemId});

        Toast.makeText(this, "Producto eliminado", Toast.LENGTH_SHORT).show();
        finish();  // Volver a la pantalla anterior
    }
}
