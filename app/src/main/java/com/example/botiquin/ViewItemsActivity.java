package com.example.botiquin;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.botiquin.DatabaseHelper;
import com.example.botiquin.Medicamento;
import com.example.botiquin.MedicamentoAdapter;
import java.util.ArrayList;
import java.util.List;

public class ViewItemsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MedicamentoAdapter medicamentoAdapter;
    private List<Medicamento> medicamentoList = new ArrayList<>();
    private DatabaseHelper databaseHelper;
    private EditText editTextSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items);

        recyclerView = findViewById(R.id.recyclerViewItems);
        editTextSearch = findViewById(R.id.editTextSearch);
        databaseHelper = new DatabaseHelper(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        medicamentoAdapter = new MedicamentoAdapter(medicamentoList);
        recyclerView.setAdapter(medicamentoAdapter);

        // Cargar la lista de remedios desde la base de datos
        loadMedicamentos("");

        // Listener para la búsqueda
        editTextSearch.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                loadMedicamentos(charSequence.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable editable) {}
        });
    }

    private void loadMedicamentos(String query) {
        // Limpiar la lista actual
        medicamentoList.clear();

        // Obtener todos los medicamentos desde la base de datos
        List<Medicamento> allMedicamentos = databaseHelper.getAllMedicamentos();

        // Filtrar por nombre si es necesario
        for (Medicamento medicamento : allMedicamentos) {
            if (TextUtils.isEmpty(query) || medicamento.getNombre().toLowerCase().contains(query.toLowerCase())) {
                medicamentoList.add(medicamento);
            }
        }

        // Ordenar alfabéticamente
        medicamentoList.sort((m1, m2) -> m1.getNombre().compareTo(m2.getNombre()));

        // Actualizar el adaptador
        medicamentoAdapter.notifyDataSetChanged();
    }
}
