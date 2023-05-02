package com.leo_escobar.pruebafinal.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.leo_escobar.pruebafinal.R;
import com.leo_escobar.pruebafinal.data.Cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ViewHolderDatos> implements Filterable {

    private final int tamañoOriginal;
    ArrayList<Cliente> clientes;
    Context context;
    private AlertDialog alertDialog;
    private ArrayList<Cliente> clientesOriginales;
    private ArrayList<Cliente> clientesFiltrados;

    public ClienteAdapter(ArrayList<Cliente> clientes, Context context) {
        Log.i("ClienteAdapter", "estoy en el constructor");
        this.clientes = clientes;
        this.context = context;
        this.clientesOriginales = new ArrayList<>(clientes);
        this.clientesFiltrados = new ArrayList<>(clientes);
        this.tamañoOriginal = clientes.size();

    }


    private OnItemClickListener onItemClickListener;


    public interface OnItemClickListener{
        void onItemClick(Cliente cliente);
    }
    public interface OnDeleteClickListener{
        void onDeleteClick(Cliente cliente, int position);
    }
    private OnDeleteClickListener onDeleteClickListener;
    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        onDeleteClickListener = listener;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public void onItemClick(int position){
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(clientes.get(position));
        }
    }


    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //log indicando que funciona el onCreateViewHolder
        Log.i("ClienteAdapter", "estoy en el onCreateViewHolder");
        //usar el DataBinding para inflar el layout
        View view = LayoutInflater.from(context).inflate(R.layout.pf_list_item, parent, false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        //log indicando que funciona el onBindViewHolder
        Log.i("ClienteAdapter", "estoy en el onBindViewHolder");
        Cliente cliente = clientesFiltrados.get(position);

        holder.textViewNombres.setText(cliente.getNombres());
        holder.textViewTelefono.setText(cliente.getTelefono());
        holder.textViewCorreo.setText(cliente.getCorreo());
        holder.textViewDireccion.setText(cliente.getEstado() + ", " + cliente.getMunicipio() + ", " + cliente.getCalle() + ", " + cliente.getColonia());


    holder.textViewNombres.setText(cliente.getNombres());
    holder.textViewTelefono.setText(cliente.getTelefono());
    holder.textViewCorreo.setText(cliente.getCorreo());
    holder.textViewDireccion.setText(cliente.getEstado() + ", " + cliente.getMunicipio() + ", " + cliente.getCalle() + ", " + cliente.getColonia());

    }

    @Override
    public int getItemCount() {

        //log de tamaño de la lista
        Log.i("ClienteAdapter", "Tamaño de la lista desde getItemCount : " + clientes.size());

        //log de tamaño de clientesFiltrados
        Log.i("ClienteAdapter", "Tamaño de clientesFiltrados desde getItemCount : " + clientesFiltrados.size());
        //log de tamaño de clientesOriginales
        Log.i("ClienteAdapter", "Tamaño de clientesOriginales desde getItemCount : " + clientesOriginales.size());


        //toast de prueba
        Toast.makeText(context, "Tamaño de la lista desde getItemCount : " + clientes.size(), Toast.LENGTH_SHORT).show();
        return clientesFiltrados.isEmpty() ? tamañoOriginal : clientesFiltrados.size();


    }







    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString().toLowerCase().trim();
                ArrayList<Cliente> filteredList = new ArrayList<>();

                if (query.isEmpty()) {

                    //log indicando que el query está vacío
                    Log.i("ClienteAdapter", "Query vacío");
                    //indicar el tamaño de clientesOriginales
                    Log.i("ClienteAdapter", "Tamaño de clientesOriginales: " + clientesOriginales.size());
                    //indicar el tamaño de clientes
                    Log.i("ClienteAdapter", "Tamaño de clientes: " + clientes.size());
                    //llenar la lista con todos los clientes
                    filteredList.addAll(clientes);



                } else {
                    for (Cliente cliente : clientes) {
                        if (cliente.getNombres().toLowerCase().contains(query) || cliente.getApellidos().toLowerCase().contains(query) || cliente.getCorreo().toLowerCase().contains(query)) {
                            filteredList.add(cliente);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                filterResults.count = filteredList.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                ArrayList<Cliente> filteredList = (ArrayList<Cliente>) results.values;
                clientesFiltrados.clear();
                clientesFiltrados.addAll(filteredList);
                notifyDataSetChanged();

                if (filteredList.size() == 0) {
                    Toast.makeText(context, "No se encontraron resultados", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "Se encontraron " + filteredList.size() + " resultados", Toast.LENGTH_SHORT).show();
                }



            }

        };
    }





    public class ViewHolderDatos extends RecyclerView.ViewHolder{

        public TextView textViewNombres;
        public TextView textViewTelefono;
        public TextView textViewCorreo;
        public TextView textViewDireccion;

        public Button eliminarButton;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            textViewNombres = itemView.findViewById(R.id.nombres);
            textViewTelefono = itemView.findViewById(R.id.telefono);
            textViewCorreo = itemView.findViewById(R.id.correo);
            textViewDireccion = itemView.findViewById(R.id.direccion);
            eliminarButton = itemView.findViewById(R.id.eliminarButton);
            eliminarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Eliminar");
                    builder.setMessage("¿Estás seguro de que deseas eliminar a este usuario?");

                    builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(onDeleteClickListener != null)
                                onDeleteClickListener.onDeleteClick(clientes.get(position), position);
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Acción a realizar si el usuario hace clic en No
                        }
                    });

                    AlertDialog alertDialog = builder.create();

                    // Personalizar los colores de los botones
                    alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialog) {
                            Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                            positiveButton.setTextColor(context.getColor(android.R.color.white));
                            //poner el fondo tipo boton redondo color azul
                            positiveButton.setBackgroundColor(context.getColor(R.color.blue));


                            Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                            negativeButton.setTextColor(context.getColor(android.R.color.white));
                            negativeButton.setBackgroundColor(context.getColor(R.color.red));
                        }
                    });


                    alertDialog.show();

                }
            });



            //agregar el listener al item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    onItemClick(position);
                }
            });
        }


    }

    public void setItems(List<Cliente> items){
        //agregar los items a la lista
        clientes.clear();
        clientes.addAll(items);
        //log de nombres de los clientes
        for (Cliente cliente : clientes){
            Log.d("Cliente", cliente.getNombres());
        }
        notifyDataSetChanged();
    }






}