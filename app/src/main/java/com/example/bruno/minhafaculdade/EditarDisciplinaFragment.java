package com.example.bruno.minhafaculdade;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import Dao.DbHelper;
import Dao.DisciplinaDao;
import Models.Disciplina;
import Models.Prova;

/**
 * Created by Bruno on 30/01/2015.
 */
public class EditarDisciplinaFragment extends Fragment implements View.OnClickListener {
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.view_editar_disciplina, null);
        final Disciplina disciplina = (Disciplina) getActivity().getIntent().getSerializableExtra("DISCIPLINA");
        disciplina.setProva1((Prova) getActivity().getIntent().getSerializableExtra("PROVA1"));
        disciplina.setProva2((Prova) getActivity().getIntent().getSerializableExtra("PROVA2"));

        etNome = (EditText) view.findViewById(R.id.etNomeEdiDis);
        etProfessor = (EditText) view.findViewById(R.id.etProfessorEdiDis);
        etEmenta = (EditText) view.findViewById(R.id.etEmentaEdiDis);
        etAno = (EditText) view.findViewById(R.id.etAnoEdiDis);
        etPeriodo = (EditText) view.findViewById(R.id.etPeriodoEdiDis);
        etProva1 = (EditText) view.findViewById(R.id.etProva1EdiDis);
        etProva2 = (EditText) view.findViewById(R.id.etProva2EdiDis);
        etDataProva1 = (EditText) view.findViewById(R.id.etProva1DataEdiDis);
        etDataProva2 = (EditText) view.findViewById(R.id.etProva2DataEdiDis);
        btnDataProva1 = (Button) view.findViewById(R.id.btnDataProva1EdiDis);
        btnDataProva2 = (Button) view.findViewById(R.id.btnDataProva2EdiDis);
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        etNome.setText(disciplina.getNome());
        etProfessor.setText(disciplina.getProfessor());
        etEmenta.setText(disciplina.getEmenta());
        etAno.setText(String.valueOf(disciplina.getAno()));
        etPeriodo.setText(String.valueOf(disciplina.getSemestre()));
        if(disciplina.getProva1().isStatus())
            etProva1.setText(String.valueOf(disciplina.getProva1().getNota()));
        else
            etProva1.setText("");
        if(disciplina.getProva2().isStatus())
            etProva2.setText(String.valueOf(disciplina.getProva2().getNota()));
        else
            etProva2.setText("");

        if(disciplina.getProva1().getData() != null)
            etDataProva1.setText(simpleDateFormat.format(disciplina.getProva1().getData()));
        else
            etDataProva1.setText("");
        if(disciplina.getProva2().getData() != null)
            etDataProva2.setText(simpleDateFormat.format(disciplina.getProva2().getData()));
        else
            etDataProva2.setText("");


        btnCadastrarDis = (Button) view.findViewById(R.id.btnSalvarEdiDis);
        btnVoltar = (Button) view.findViewById(R.id.btnVoltarEdiDis);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                fechaTeclado();
                getActivity().onBackPressed();
            }
        });

        btnCadastrarDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String nomeStr = etNome.getText().toString();
                    String anoStr = etAno.getText().toString();
                    String periodoStr = etPeriodo.getText().toString();
                    Disciplina newDisciplina = new Disciplina();
                    newDisciplina = disciplina;
                    newDisciplina.setNome(nomeStr);
                    newDisciplina.setProfessor(etProfessor.getText().toString());
                    newDisciplina.setEmenta(etEmenta.getText().toString());

                    Integer ano = null;
                    if (!anoStr.equals(""))
                        ano = Integer.parseInt(anoStr);
                    newDisciplina.setAno(ano);

                    Integer periodo = null;
                    if (!periodoStr.equals(""))
                        periodo = Integer.parseInt(etPeriodo.getText().toString());
                    newDisciplina.setSemestre(periodo);
                    String prova1Str = etProva1.getText().toString();
                    Prova prova1 = new Prova();
                    prova1.setTipo(1);

                    if (!prova1Str.equals("")) {
                        double prova1Dou = Double.parseDouble(prova1Str);
                        if(prova1Dou<0||prova1Dou>10) {
                            throw new Exception(getResources().getString(R.string.nota_prova1_invalida));
                        }
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
                        prova2.setNota(prova2Dou);
                        prova2.setStatus(true);
                    }

                    String dataProva1 = etDataProva1.getText().toString();
                    if(!dataProva1.equals(getResources().getString(R.string.data)) && !dataProva1.equals(""))
                        try {
                            prova1.setData(simpleDateFormat.parse(dataProva1));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    else
                        prova1.setData(null);
                    String dataProva2 = etDataProva2.getText().toString();
                    if(!dataProva2.equals(getResources().getString(R.string.data)) && !dataProva2.equals(""))
                        try {
                            prova2.setData(simpleDateFormat.parse(dataProva2));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    else
                        prova2.setData(null);

                    prova1.setDisciplina(newDisciplina);
                    prova2.setDisciplina(newDisciplina);
                    newDisciplina.setProva1(prova1);
                    newDisciplina.setProva2(prova2);


                    DisciplinaDao disciplinaDao = new DisciplinaDao(new DbHelper(v.getContext()));
                    if(newDisciplina.getProva1().isStatus() == false||newDisciplina.getProva2().isStatus() == false) {
                        newDisciplina.setMedia(view.getResources().getString(R.string.falta_registrar_nota));
                    }else{
                        newDisciplina.setMedia(disciplinaDao.calculaMedia(newDisciplina));
                    }

                    disciplinaDao.updateDisciplina(newDisciplina, disciplina);
                    Intent intent = new Intent(getActivity(), MainActivity.class);

                    intent.putExtra("DISCIPLINA", newDisciplina);
                    intent.putExtra("PROVA1", newDisciplina.getProva1());
                    intent.putExtra("PROVA2", newDisciplina.getProva2());
                    getActivity().setIntent(intent);
                    Toast.makeText(getActivity(), getResources().getString(R.string.sucesso_editar_disciplina), Toast.LENGTH_SHORT).show();
                    //startActivity(intent);
                    //finish();
                    fechaTeclado();
                    getActivity().onBackPressed();
                }catch (Exception e){
                    Toast.makeText(getActivity(), getResources().getString(R.string.erro_editar_disciplina) + "\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        setData();

        return view;

    }

    private void fechaTeclado(){
        InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(etDataProva1.getWindowToken(), 0);
    }

    private void setData(){


        btnDataProva1.setOnClickListener((View.OnClickListener) this);
        btnDataProva2.setOnClickListener((View.OnClickListener) this);


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
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(etDataProva1.getWindowToken(), 0);
            dpdDataProva1.show();
        } else if(v == btnDataProva2) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(etDataProva1.getWindowToken(), 0);
            dpdDataProva2.show();
        }
    }
}
