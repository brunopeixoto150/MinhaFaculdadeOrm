package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.bruno.minhafaculdade.R;

import java.util.List;

import Models.Prova;

/**
 * Created by Bruno on 27/01/2015.
 */
public class AdapterProva extends BaseAdapter{
    private Context ctx;
    private List<Prova> provaList;

    public AdapterProva(Context ctx, List<Prova> provaList) {
        this.ctx = ctx;
        this.provaList = provaList;
    }

    @Override
    public int getCount() {
        return provaList.size();
    }

    @Override
    public Object getItem(int position) {
        return provaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return provaList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Prova prova = provaList.get(position);
        View view = LayoutInflater.from(ctx).inflate(R.layout.layout_list_prova, null);
        TextView tvTipoProva = (TextView) view.findViewById(R.id.tvTipoProva);
        tvTipoProva.setText(String.valueOf(prova.getTipo()));
        TextView tvIdDisProva = (TextView) view.findViewById(R.id.tvIdDisProva);


        tvIdDisProva.setText(String.valueOf(prova.getNota()));
        return view;
    }
}
