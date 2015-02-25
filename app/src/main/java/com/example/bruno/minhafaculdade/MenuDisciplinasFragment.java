package com.example.bruno.minhafaculdade;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.text.ParseException;
import java.util.List;

import Adapter.AdapterDisciplina;
import Dao.DbHelper;
import Dao.DisciplinaDao;
import Models.Disciplina;

public class MenuDisciplinasFragment extends Fragment {
    private Button btnCadastrar;
    private ListView lvShowAllDisciplinas;
    private Button btnVoltar;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.view_menu_disciplinas, null);
        lvShowAllDisciplinas = (ListView) view.findViewById(R.id.lvShowAllDisciplinas);
        btnCadastrar = (Button) view.findViewById(R.id.btnCadastroDisciplina);
        btnVoltar = (Button) view.findViewById(R.id.btnVoltarTabelaDis);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft;
                Fragment frag;
                frag = new CadastrarDisciplinaFragment();
                ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, frag);
                ft.addToBackStack(getString(R.string.btn_menu_disciplinas));
                ft.commit();
            }
        });
        lvShowAllDisciplinas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Disciplina disciplina = (Disciplina) adapter.getAdapter().getItem(position);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("DISCIPLINA", disciplina);
                intent.putExtra("PROVA1", disciplina.getProva1());
                intent.putExtra("PROVA2", disciplina.getProva2());
                FragmentTransaction ft;
                Fragment frag = new VisualizarDisciplinaFragment();
                ft = getActivity().getSupportFragmentManager().beginTransaction();
                getActivity().setIntent(intent);
                ft.replace(R.id.container, frag);
                ft.addToBackStack(getString(R.string.btn_menu_disciplinas));
                ft.commit();

                //startActivity(intent);
                //Toast.makeText(TabelaDisciplinasActivity.this, disciplina.getNome(), Toast.LENGTH_SHORT).show();
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
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
        DisciplinaDao disciplinaDao = new DisciplinaDao(new DbHelper(getActivity()));
        List<Disciplina> disciplinaList = null;
        try {
            disciplinaList = disciplinaDao.selectTodasAsDisciplinas();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        AdapterDisciplina adapterDisciplina = new AdapterDisciplina(getActivity(), disciplinaList);

        lvShowAllDisciplinas.setAdapter(adapterDisciplina);



    }
}
