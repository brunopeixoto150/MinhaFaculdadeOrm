package com.example.bruno.minhafaculdade;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import Dao.DbHelper;
import Dao.DisciplinaDao;
import Dao.ProvaDao;
import Models.Disciplina;
import Models.Prova;

/**
 * Created by Bruno on 30/01/2015.
 */
public class VisualizarDisciplinaFragment extends Fragment {
    private TextView tvNomeDis;
    private TextView tvProfessorDis;
    private TextView tvEmentaDis;
    private TextView tvAnoDis;
    private TextView tvPeriodoDis;
    private TextView tvProva1Dis;
    private TextView tvProva2Dis;
    private TextView tvDataProva1Dis;
    private TextView tvDataProva2Dis;
    private TextView tvMediaDis;
    private Button btnEditarDisVis;
    private Button btnExcluirDisVis;
    private Button btnVoltarDisVis;

    private SimpleDateFormat format;
    private Disciplina disciplina;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.view_visualizar_disciplina, null);
        disciplina = (Disciplina) getActivity().getIntent().getSerializableExtra("DISCIPLINA");
        disciplina.setProva1((Prova) getActivity().getIntent().getSerializableExtra("PROVA1"));
        disciplina.setProva2((Prova) getActivity().getIntent().getSerializableExtra("PROVA2"));

        tvNomeDis = (TextView) view.findViewById(R.id.tvNomeDisVis);
        tvProfessorDis = (TextView) view.findViewById(R.id.tvProfessorDisVis);
        tvEmentaDis = (TextView) view.findViewById(R.id.tvEmentaDisVis);
        tvAnoDis  = (TextView) view.findViewById(R.id.tvAnoDisVis);
        tvPeriodoDis = (TextView) view.findViewById(R.id.tvPeriodoDisVis);
        tvProva1Dis = (TextView) view.findViewById(R.id.tvProva1DisVis);
        tvProva2Dis = (TextView) view.findViewById(R.id.tvProva2DisVis);
        tvDataProva1Dis = (TextView) view.findViewById(R.id.tvDataProva1DisVis);
        tvDataProva2Dis = (TextView) view.findViewById(R.id.tvDataProva2DisVis);
        tvMediaDis = (TextView) view.findViewById(R.id.tvMediaDisVis);
        btnEditarDisVis = (Button) view.findViewById(R.id.btnEditarDisVis);
        btnExcluirDisVis = (Button) view.findViewById(R.id.btnExcluirDisVis);
        btnVoltarDisVis = (Button) view.findViewById(R.id.btnVoltarDisVis);

        format = new SimpleDateFormat(getResources().getString(R.string.tipo_data));

        tvNomeDis.setText(disciplina.getNome());
        tvProfessorDis.setText(disciplina.getProfessor());
        tvEmentaDis.setText(disciplina.getEmenta());
        tvAnoDis.setText(String.valueOf(disciplina.getAno()));
        tvPeriodoDis.setText(String.valueOf(disciplina.getSemestre()));
        if(disciplina.getProva1().isStatus())
            tvProva1Dis.setText(String.valueOf(disciplina.getProva1().getNota()));
        else
            tvProva1Dis.setText(String.valueOf(getResources().getString(R.string.falta_registrar_nota)));
        if(disciplina.getProva2().isStatus())
            tvProva2Dis.setText(String.valueOf(disciplina.getProva2().getNota()));
        else
            tvProva2Dis.setText(String.valueOf(getResources().getString(R.string.falta_registrar_nota)));
        if(disciplina.getProva1().getData() != null)
            tvDataProva1Dis.setText(format.format(disciplina.getProva1().getData()));
        else
            tvDataProva1Dis.setText("");
        if(disciplina.getProva2().getData() != null)
            tvDataProva2Dis.setText(format.format(disciplina.getProva2().getData()));
        else
            tvDataProva2Dis.setText("");
        tvMediaDis.setText(disciplina.getMedia());



        btnExcluirDisVis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DbHelper dbHelper = new DbHelper(v.getContext());
                    DisciplinaDao disciplinaDao = new DisciplinaDao(dbHelper);
                    disciplinaDao.deleteDisciplina(disciplina.getNome(), disciplina.getAno(), disciplina.getSemestre());
                    ProvaDao provaDao = new ProvaDao(dbHelper);
                    provaDao.deleteProva(disciplina.getNome(), disciplina.getAno(), disciplina.getSemestre());
                    Toast.makeText(getActivity(), getResources().getString(R.string.sucesso_excluir_disciplina), Toast.LENGTH_SHORT).show();
                    //finish();
                    getActivity().onBackPressed();
                }catch (Exception e){
                    Toast.makeText(getActivity(), getResources().getString(R.string.erro_excluir_disciplina) + "\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnEditarDisVis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("DISCIPLINA", disciplina);
                intent.putExtra("PROVA1", disciplina.getProva1());
                intent.putExtra("PROVA2", disciplina.getProva2());
                //startActivity(intent);
                //finish();

                FragmentTransaction ft;
                Fragment frag = new EditarDisciplinaFragment();
                ft = getActivity().getSupportFragmentManager().beginTransaction();
                getActivity().setIntent(intent);
                ft.replace(R.id.container, frag);
                ft.addToBackStack(getString(R.string.visualizacao_disciplina));
                ft.commit();

            }
        });

        btnVoltarDisVis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        disciplina.setProva1((Prova) getActivity().getIntent().getSerializableExtra("PROVA1"));
        disciplina = (Disciplina) getActivity().getIntent().getSerializableExtra("DISCIPLINA");

        disciplina.setProva2((Prova) getActivity().getIntent().getSerializableExtra("PROVA2"));
        tvNomeDis.setText(disciplina.getNome());
        tvProfessorDis.setText(disciplina.getProfessor());
        tvEmentaDis.setText(disciplina.getEmenta());
        tvAnoDis.setText(String.valueOf(disciplina.getAno()));
        tvPeriodoDis.setText(String.valueOf(disciplina.getSemestre()));
        if(disciplina.getProva1().isStatus())
            tvProva1Dis.setText(String.valueOf(disciplina.getProva1().getNota()));
        else
            tvProva1Dis.setText(String.valueOf(getResources().getString(R.string.falta_registrar_nota)));
        if(disciplina.getProva2().isStatus())
            tvProva2Dis.setText(String.valueOf(disciplina.getProva2().getNota()));
        else
            tvProva2Dis.setText(String.valueOf(getResources().getString(R.string.falta_registrar_nota)));
        if(disciplina.getProva1().getData() != null)
            tvDataProva1Dis.setText(format.format(disciplina.getProva1().getData()));
        else
            tvDataProva1Dis.setText("");
        if(disciplina.getProva2().getData() != null)
            tvDataProva2Dis.setText(format.format(disciplina.getProva2().getData()));
        else
            tvDataProva2Dis.setText("");
        tvMediaDis.setText(disciplina.getMedia());
    }

}
