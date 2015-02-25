package Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bruno.minhafaculdade.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import Dao.ConfiguracoesDao;
import Dao.DbHelper;
import Dao.DisciplinaDao;
import Models.Prova;

/**
 * Created by bruno on 02/02/15.
 */
public class AdapterLembretes extends BaseAdapter {

    private Context ctx;
    private List<Prova> provaList;

    public AdapterLembretes(Context ctx, List<Prova> provaList) {
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
        View view = LayoutInflater.from(ctx).inflate(R.layout.layout_list_lembretes, null);
        TextView tvNomeDis = (TextView) view.findViewById(R.id.tvNomeDisLembrete);
        if(prova.getDisciplina().getNome()!= null)
            tvNomeDis.setText(String.valueOf(prova.getDisciplina().getNomeTabela()));
        TextView tvData = (TextView) view.findViewById(R.id.tvDataProvaLembrete);
        SimpleDateFormat format= new SimpleDateFormat(ctx.getResources().getString(R.string.tipo_data));
        tvData.setText(format.format(prova.getData()));
        DisciplinaDao disciplinaDao = new DisciplinaDao(new DbHelper(ctx));
        try {
            Log.w("Disciplina", prova.getDisciplina().getNome());
            prova.setDisciplina(disciplinaDao.selectDisciplina(prova.getDisciplina().getNome(), prova.getDisciplina().getAno(), prova.getDisciplina().getSemestre()));
            TextView tvProfessor = (TextView) view.findViewById(R.id.tvProfessorLembrete);
            tvProfessor.setText(prova.getDisciplina().getProfessorTabela());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return view;
    }
}
