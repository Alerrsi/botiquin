package com.example.botiquin;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddItemActivity extends AppCompatActivity {
    private EditText etNombre, etCantidad, etFechaVencimiento, etMg, etDescripcion;
    private Spinner spPresentacion;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        dbHelper = new DatabaseHelper(this);

        etNombre = findViewById(R.id.etNombre);
        etCantidad = findViewById(R.id.etCantidad);
        etFechaVencimiento = findViewById(R.id.etFechaVencimiento);
        etMg = findViewById(R.id.etMg);
        etDescripcion = findViewById(R.id.etDescripcion);
        spPresentacion = findViewById(R.id.spPresentacion);
        Button btnGuardar = findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(v -> guardarRemedio());
    }

    private void guardarRemedio() {
        String nombre = etNombre.getText().toString().trim();
        String cantidadStr = etCantidad.getText().toString().trim();
        String fechaVencimiento = etFechaVencimiento.getText().toString().trim();
        String mgStr = etMg.getText().toString().trim();
        String presentacion = spPresentacion.getSelectedItem().toString();
        String descripcion = etDescripcion.getText().toString().trim();

        if (nombre.isEmpty() || cantidadStr.isEmpty() || fechaVencimiento.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        int cantidad = Integer.parseInt(cantidadStr);
        Integer mg = mgStr.isEmpty() ? null : Integer.parseInt(mgStr);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NOMBRE, nombre);
        values.put(DatabaseHelper.COLUMN_CANTIDAD, cantidad);
        values.put(DatabaseHelper.COLUMN_FECHA_VENCIMIENTO, fechaVencimiento);
        values.put(DatabaseHelper.COLUMN_MG, mg);
        values.put(DatabaseHelper.COLUMN_PRESENTACION, presentacion);
        values.put(DatabaseHelper.COLUMN_DESCRIPCION, descripcion);

        long result = db.insert(DatabaseHelper.TABLE_NAME, null, values);
        if (result != -1) {
            Toast.makeText(this, "Producto grabado", Toast.LENGTH_SHORT).show();
            limpiarCampos();
        } else {
            Toast.makeText(this, "Error al guardar el producto", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarCampos() {
        etNombre.setText("");
        etCantidad.setText("");
        etFechaVencimiento.setText("");
        etMg.setText("");
        etDescripcion.setText("");
        spPresentacion.setSelection(0);
    }
}
