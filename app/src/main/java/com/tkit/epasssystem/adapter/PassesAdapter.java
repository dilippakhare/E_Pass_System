package com.tkit.epasssystem.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.tkit.epasssystem.R;
import com.tkit.epasssystem.model.ModelPassesRequest;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PassesAdapter extends ArrayAdapter
{
    Context context;
    ArrayList<ModelPassesRequest> list;
    public PassesAdapter(@NonNull Context context, int resource, ArrayList<ModelPassesRequest> list) {
        super(context, resource, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {




        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_passrequest, null);



        //------------------ Data Binding Code----------------------------
        ModelPassesRequest model=list.get(position);

        TextView txtPrice=convertView.findViewById(R.id.txtPrice),
                txtDate=convertView.findViewById(R.id.txtDate),title=convertView.findViewById(R.id.title);
        txtPrice.setText("Price :"+model.getPrice()+" Rs.");
        txtDate.setText(""+model.getFromDate().split("T")[0]);
        title.setText("Daily Pass");

        Button btnStartExam=convertView.findViewById(R.id.btnViewQR);
        btnStartExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //-----------------------------------------------------------------


        return convertView;
    }

}
