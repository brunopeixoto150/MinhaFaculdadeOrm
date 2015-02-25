package com.example.bruno.minhafaculdade;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLClientInfoException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import Dao.DbHelper;
import Dao.DisciplinaDao;
import Dao.ProvaDao;
import Models.Disciplina;
import Models.Prova;

/**
 * Created by bruno on 30/01/15.
 */
public class CadastrarDisciplinaFragment extends Fragment implements View.OnClickListener {

    private EditText etNome;
    private EditText etProfessor;
    private EditText etEmenta;
    private EditText etAno;
    private EditText etPeriodo;
    private EditText etProva1;
    private EditText etProva2;
    private EditText etDataProva1;
    private EditText etDataProva2;
    private DatePickerDialog dpdDataProva1;
    private DatePickerDialog dpdDataProva2;
    private Button btnCadastrarDis;
    private Button btnVoltar;
    private Button btnDataProva2;
    private Button btnDataProva1;
    private SimpleDateFormat simpleDateFormat;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.view_cadastro_disciplina, null);
        etNome = (EditText) view.findViewById(R.id.etNomeDis);
        etProfessor = (EditText) view.findViewById(R.id.etProfessorDis);
        etEmenta = (EditText) view.findViewById(R.id.etEmentaDis);
        etAno = (EditText) view.findViewById(R.id.etAnoDis);
        etPeriodo = (EditText) view.findViewById(R.id.etPeriodoDis);
        etProva1 = (EditText) view.findViewById(R.id.etProva1Dis);
        etProva2 = (EditText) view.findViewById(R.id.etProva2Dis);
        etDataProva1 = (EditText) view.findViewById(R.id.etDataProva1Dis);
        etDataProva2 = (EditText) view.findViewById(R.id.etDataProva2Dis);
        btnDataProva1 = (Button) view.findViewById(R.id.btnDataProva1Dis);
        btnDataProva2 = (Button) view.findViewById(R.id.btnDataProva2Dis);
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);



        btnCadastrarDis = (Button) view.findViewById(R.id.btnCadastrarDis);
        btnVoltar = (Button) view.findViewById(R.id.btnVoltarCadastroDis);

        btnCadastrarDis.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    String nomeStr = etNome.getText().toString();
                    String anoStr = etAno.getText().toString();
                    String periodoStr = etPeriodo.getText().toString();
                    if(nomeStr.equals("") || anoStr.equals("") || periodoStr.equals("")) {
                        throw new Exception(getResources().getString(R.string.campo_nome_vazio));
                    }
                    //throw new Exception(getResources().getString(R.string.campo_nome_vazio));
                    Integer ano = Integer.parseInt(anoStr);
                    Integer periodo = Integer.parseInt(periodoStr);
                    DisciplinaDao disciplinaDao = new DisciplinaDao(new DbHelper(v.getContext()));
                    if(disciplinaDao.selectDisciplina(nomeStr, ano, periodo)!=null) {
                        throw new Exception(getResources().getString(R.string.disciplina_ja_existe));
                    }
                    //throw new Exception(getResources().getString(R.string.disciplina_ja_existe));

                    Disciplina disciplina = new Disciplina();

                    disciplina.setNome(nomeStr);
                    disciplina.setProfessor(etProfessor.getText().toString());
                    disciplina.setEmenta(etEmenta.getText().toString());
                    disciplina.setAno(ano);
                    disciplina.setSemestre(periodo);
                    String prova1Str = etProva1.getText().toString();
                    Prova prova1 = new Prova();
                    prova1.setTipo(1);

                    if (!prova1Str.equals("")) {
                        double prova1Dou = Double.parseDouble(prova1Str);
                        if(prova1Dou<0||prova1Dou>10) {
                            throw new Exception(getResources().getString(R.string.nota_prova1_invalida));
                        }
                        //throw new Exception(getResources().getString(R.string.nota_prova1_invalida));
                        prova1.setNota(prova1Dou);
                        prova1.setStatus(true);
                    }


                    String prova2Str = etProva2.getText().toString();
                    Prova prova2 = new Prova();
                    prova2.setTipo(2);

                    if (!prova2Str.equals("")) {
                        double prova2Dou = Double.parseDouble(prova2Str);
                        if(prova2Dou<0||prova2Dou>10) {
                            throw new Exception(getResources().getString(R.string.nota_prova2_invalida));
                        }
                        //throw new Exception(getResources().getString(R.string.nota_prova2_invalida));
                        prova2.setNota(prova2Dou);
                        prova2.setStatus(true);
                    }


                    String dataProva1 = etDataProva1.getText().toString();
                    if(!dataProva1.equals(getResources().getString(R.string.data)) && !dataProva1.equals("")){
                        prova1.setData(simpleDateFormat.parse(dataProva1));
                    }else
                        prova1.setData(null);
                    String dataProva2 = etDataProva2.getText().toString();
                    if(!dataProva2.equals(getResources().getString(R.string.data))&& !dataProva2.equals("")){
                        prova2.setData(simpleDateFormat.parse(dataProva2));
                    }else
                        prova2.setData(null);
                    disciplina.setProva1(prova1);
                    disciplina.setProva2(prova2);
                    if(disciplina.getProva1().isStatus() == false||disciplina.getProva2().isStatus() == false) {
                        disciplina.setMedia(view.getResources().getString(R.string.falta_registrar_nota));
                    }else{
                        disciplina.setMedia(disciplinaDao.calculaMedia(disciplina));
                    }


                    disciplinaDao.insertDisciplina(disciplina);
                    prova1.setDisciplina(disciplina);
                    prova2.setDisciplina(disciplina);
                    try {
                        ProvaDao provaDao = new ProvaDao(new DbHelper(v.getContext()));
                        provaDao.insertProva(prova1);
                        provaDao.insertProva(prova2);
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.erro_cadastro_prova), Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getActivity(), getResources().getString(R.string.sucesso_cadastro_disciplina), Toast.LENGTH_SHORT).show();
                    //finish();
                    fechaTeclado();
                    getActivity().onBackPressed();



                } catch (SQLClientInfoException e){
                    Toast.makeText(getActivity(), getResources().getString(R.string.erro_cadastro_disciplina) + "\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Toast.makeText(getActivity(), getResources().getString(R.string.erro_cadastro_disciplina) + "\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                } finally {


                }


            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                getActivity().onBackPressed();
            }
        });

        setData(view);
        return(view);
    }

    private void setData(View view){


        btnDataProva1.setOnClickListener(this);
        btnDataProva2.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();

        dpdDataProva1 = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {


            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etDataProva1.setText(simpleDateFormat.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        dpdDataProva2 = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etDataProva2.setText(simpleDateFormat.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onClick(View v) {
        if(v == btnDataProva1) {
            fechaTeclado();
            dpdDataProva1.show();
        } else if(v == btnDataProva2) {
            fechaTeclado();
            dpdDataProva2.show();
        }
    }

    private void fechaTeclado(){
        InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(etDataProva1.getWindowToken(), 0);
    }


}
