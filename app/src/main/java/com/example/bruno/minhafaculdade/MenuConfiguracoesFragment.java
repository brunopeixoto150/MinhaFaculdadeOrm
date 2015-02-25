package com.example.bruno.minhafaculdade;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import Dao.ConfiguracoesDao;
import Dao.DbHelper;
import Models.Configuracoes;

public class MenuConfiguracoesFragment extends Fragment {
    private Button btnSalvar;
    private Button btnVoltar;
    private EditText etNomeUsuario;
    private EditText etCurso;
    private RadioGroup rgFaculdade;
    private ConfiguracoesDao configuracoesDao;
    private Configuracoes configuracoes;

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.view_configuracoes, null);

        configuracoesDao = new ConfiguracoesDao(new DbHelper(getActivity()));
        configuracoes = configuracoesDao.selectConfiguracoes();

        etNomeUsuario = (EditText) view.findViewById(R.id.etNomeUsuario);
        etCurso = (EditText) view.findViewById(R.id.etCurso);
        rgFaculdade = (RadioGroup) view.findViewById(R.id.rgFaculdade);
        if(configuracoes == null)
            configuracoes = new Configuracoes();
        else {
            etNomeUsuario.setText(configuracoes.getNome());
            etCurso.setText(configuracoes.getCurso());
            if (configuracoes.getFaculdade().equals(getResources().getString(R.string.estacio)))
                rgFaculdade.check(R.id.rbEstacio);
            else
                rgFaculdade.check(R.id.rbUnicap);
        }

        btnSalvar = (Button) view.findViewById(R.id.btnSalvarConfiguracoes);
        btnVoltar = (Button) view.findViewById(R.id.btnVoltarconfiguracoes);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fechaTeclado();
                getActivity().onBackPressed();
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    configuracoes.setNome(etNomeUsuario.getText().toString());
                    configuracoes.setCurso(etCurso.getText().toString());
                    int op = rgFaculdade.getCheckedRadioButtonId();
                    if (op == R.id.rbEstacio)
                        configuracoes.setFaculdade(getResources().getString(R.string.estacio));
                    else
                        configuracoes.setFaculdade(getResources().getString(R.string.unicap));
                    if (configuracoesDao.selectConfiguracoes() == null)
                        configuracoesDao.insertConfiguracoes(configuracoes);
                    else {
                        configuracoesDao.updateConfiguracoes(configuracoes);
                        Toast.makeText(getActivity(), getResources().getString(R.string.sucesso_salvar_configuracoes), Toast.LENGTH_SHORT).show();
                    }

                    fechaTeclado();
                    getActivity().onBackPressed();
                }catch (Exception e){
                    Toast.makeText(getActivity(), getResources().getString(R.string.erro_salvar_configuracoes), Toast.LENGTH_SHORT).show();

                }

            }
        });

        return(view);


	}

    private void fechaTeclado(){
        InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(etNomeUsuario.getWindowToken(), 0);
    }
}
