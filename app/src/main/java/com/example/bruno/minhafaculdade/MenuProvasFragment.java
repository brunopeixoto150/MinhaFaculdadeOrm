package com.example.bruno.minhafaculdade;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.List;

import Adapter.AdapterProva;
import Dao.DbHelper;
import Dao.ProvaDao;
import Models.Prova;

public class MenuProvasFragment extends Fragment {
    private ListView lvProvas;
    private Button btnVotar;
    private Button btnCadastrarProva;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.view_menu_provas, null);

        btnVotar = (Button) view.findViewById(R.id.btnVotarProva);
        btnCadastrarProva = (Button) view.findViewById(R.id.btnCadastrarProva);
        lvProvas = (ListView) view.findViewById(R.id.listProva);
        btnCadastrarProva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), getResources().getString(R.string.em_desenvolvimento), Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(TabelaProvasActivity.this, CadastroProvaActivity.class);
                //startActivity(intent);
            }
        });
        btnVotar.setOnClickListener(new View.OnClickListener() {
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
        ProvaDao provaDao = new ProvaDao(new DbHelper(getActivity()));
        List<Prova> provaList = null;
        try {
            provaList = provaDao.selectTodasAsProvas();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        AdapterProva adapterProva = new AdapterProva(getActivity(), provaList);

        lvProvas.setAdapter(adapterProva);
    }
}
