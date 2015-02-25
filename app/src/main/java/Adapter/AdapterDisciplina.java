package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.bruno.minhafaculdade.R;

import java.util.List;

import Models.Disciplina;

/**
 * Created by bruno on 26/01/15.
 */
public class AdapterDisciplina extends BaseAdapter{
    private Context ctx;
    private List<Disciplina> disciplinaList;

    public AdapterDisciplina(Context ctx, List<Disciplina> disciplinaList) {
        this.ctx = ctx;
        this.disciplinaList = disciplinaList;
    }

    @Override
    public int getCount() {
        return disciplinaList.size();
    }

    @Override
    public Object getItem(int position) {
        return disciplinaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return disciplinaList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Disciplina disciplina = disciplinaList.get(position);
        View view = LayoutInflater.from(ctx).inflate(R.layout.layout_list_disciplina, null);
        TextView tvNomeDis = (TextView) view.findViewById(R.id.tvNomeDis);
        tvNomeDis.setText(disciplina.getNomeTabela());
        TextView tvStatusDis = (TextView) view.findViewById(R.id.tvStatusDis);
        if(disciplina.getProva1().isStatus() && disciplina.getProva2().isStatus())
            tvStatusDis.setText(ctx.getResources().getString(R.string.media_) + disciplina.getMedia());
        else
            tvStatusDis.setText(disciplina.getMedia());
        return view;
    }
}
