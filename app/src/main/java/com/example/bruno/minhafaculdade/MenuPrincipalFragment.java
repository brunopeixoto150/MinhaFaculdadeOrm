package com.example.bruno.minhafaculdade;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.util.List;

import Adapter.AdapterLembretes;
import Adapter.AdapterProva;
import Dao.ConfiguracoesDao;
import Dao.DbHelper;
import Dao.ProvaDao;
import Models.Configuracoes;
import Models.Prova;

/**
 * Created by Bruno on 30/01/2015.
 */
public class MenuPrincipalFragment extends Fragment {
    private TextView tvBemVindo;
    private Button btnSair;
    private ListView lvLembretes;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_menu_principal, null);

        tvBemVindo = (TextView) view.findViewById(R.id.tvBemVindo);
        btnSair = (Button) view.findViewById(R.id.btnSair);
        lvLembretes = (ListView) view.findViewById(R.id.lvProximasProvas);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        ConfiguracoesDao configuracoesDao = new ConfiguracoesDao(new DbHelper(getActivity()));
        Configuracoes configuracoes = configuracoesDao.selectConfiguracoes();
        if(configuracoes != null)
            tvBemVindo.setText(getResources().getString(R.string.bem_vindo)+" "+configuracoes.getNome());

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        ProvaDao provaDao = new ProvaDao(new DbHelper(getActivity()));
        List<Prova> provaList = null;
        try {
            provaList = provaDao.selectTodasAsProvasComDatas();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        AdapterLembretes adapterLembretes = new AdapterLembretes(getActivity(), provaList);

        lvLembretes.setAdapter(adapterLembretes);

    }
}
