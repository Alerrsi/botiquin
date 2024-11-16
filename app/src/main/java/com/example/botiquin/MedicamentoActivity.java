package com.example.botiquin;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MedicamentoActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ListView listView;
    private MedicamentoAdapter adapter;
    private List<Medicamento> medicamentoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento);

        dbHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.listViewMedicamentos);

        medicamentoList = dbHelper.getAllMedicamentos();
        adapter = new MedicamentoAdapter(this, medicamentoList);
        listView.setAdapter(adapter);
    }
}
