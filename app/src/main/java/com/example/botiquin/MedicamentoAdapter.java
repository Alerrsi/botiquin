package com.example.botiquin;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.botiquin.R;
import com.example.botiquin.Medicamento;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Calendar;


public class MedicamentoAdapter extends RecyclerView.Adapter<MedicamentoAdapter.MedicamentoViewHolder> {

    private List<Medicamento> medicamentoList;
    private Context context;
    private List<Medicamento> medicamentos;
    private DatabaseHelper dbHelper;

    public MedicamentoAdapter(List<Medicamento> medicamentoList) {
        super(context, 0, medicamentoList);
        this.medicamentoList = medicamentoList;
        this.context = context;
        this.medicamentos = medicamentos;
        this.dbHelper = new DatabaseHelper(context);
    }

    @Override
    public MedicamentoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medicamento, parent, false);
        return new MedicamentoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MedicamentoViewHolder holder, int position) {
        Medicamento medicamento = medicamentoList.get(position);
        holder.nombreTextView.setText(medicamento.getNombre());
        holder.cantidadTextView.setText("Cantidad: " + medicamento.getCantidad());
        holder.fechaVencimientoTextView.setText("Vence: " + medicamento.getFechaVencimiento());


        if (isExpiringSoon(medicamento.getFechaVencimiento())) {
            holder.nombreTextView.setTextColor(Color.RED);
            holder.fechaVencimientoTextView.setTextColor(Color.RED);
        } else {
            holder.nombreTextView.setTextColor(Color.BLACK);
            holder.fechaVencimientoTextView.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return medicamentoList.size();
    }

    private boolean isExpiringSoon(String fechaVencimiento) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Calendar today = Calendar.getInstance();
            Calendar expiration = Calendar.getInstance();
            expiration.setTime(sdf.parse(fechaVencimiento));


            today.add(Calendar.MONTH, 3);

            return expiration.before(today);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static class MedicamentoViewHolder extends RecyclerView.ViewHolder {
        TextView nombreTextView;
        TextView cantidadTextView;
        TextView fechaVencimientoTextView;

        public MedicamentoViewHolder(View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.nombreTextView);
            cantidadTextView = itemView.findViewById(R.id.cantidadTextView);
            fechaVencimientoTextView = itemView.findViewById(R.id.fechaVencimientoTextView);
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_medicamento, parent, false);
        }

        Medicamento medicamento = getItem(position);
        TextView nombreTextView = convertView.findViewById(R.id.nombre);
        TextView cantidadTextView = convertView.findViewById(R.id.cantidad);
        Button eliminarButton = convertView.findViewById(R.id.btnEliminar);
        Button actualizarButton = convertView.findViewById(R.id.btnActualizar);

        nombreTextView.setText(medicamento.getNombre());
        cantidadTextView.setText(String.valueOf(medicamento.getCantidad()));

        eliminarButton.setOnClickListener(v -> {
            dbHelper.deleteMedicamento(medicamento.getId());
            medicamentos.remove(position);
            notifyDataSetChanged();
        });

        actualizarButton.setOnClickListener(v -> {

            medicamento.setCantidad(medicamento.getCantidad() + 1);
            dbHelper.updateMedicamento(medicamento);
            notifyDataSetChanged();
        });

        return convertView;
    }
}


