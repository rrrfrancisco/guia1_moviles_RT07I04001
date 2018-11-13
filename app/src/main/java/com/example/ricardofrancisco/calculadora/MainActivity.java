package com.example.ricardofrancisco.calculadora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //CREACION DE VARIABLES
    private TextView lblentrada;
    private TextView lblresultado;
    private ArrayList<Button> lstNumeros;
    private Button btnAC,btnDEL,btnigual;
    private ArrayList<Button> lstOperaciones;
    private boolean opPulsada = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblentrada      = (TextView) findViewById(R.id.lblentrada);
        lblresultado    = (TextView) findViewById(R.id.lblresultado);
        btnAC           = (Button)   findViewById(R.id.btnAC);
        btnDEL         = (Button)   findViewById(R.id.btnDEL);
        btnigual        = (Button)   findViewById(R.id.btnigual);

        lstNumeros = new ArrayList<>();
        lstNumeros.add( (Button) findViewById(R.id.btnnum9));
        lstNumeros.add( (Button) findViewById(R.id.btnnum8));
        lstNumeros.add( (Button) findViewById(R.id.btnnum7));
        lstNumeros.add( (Button) findViewById(R.id.btnnum6));
        lstNumeros.add( (Button) findViewById(R.id.btnnum5));
        lstNumeros.add( (Button) findViewById(R.id.btnnum4));
        lstNumeros.add( (Button) findViewById(R.id.btnnum3));
        lstNumeros.add( (Button) findViewById(R.id.btnnum2));
        lstNumeros.add( (Button) findViewById(R.id.btnnum1));
        lstNumeros.add( (Button) findViewById(R.id.btnnum0));

        lstOperaciones = new ArrayList<>();
        lstOperaciones.add( (Button) findViewById(R.id.btnsuma) );
        lstOperaciones.add( (Button) findViewById(R.id.btnresta) );
        lstOperaciones.add( (Button) findViewById(R.id.btnproducto) );
        lstOperaciones.add( (Button) findViewById(R.id.btndiv) );

        //eventos onClic
        btnAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //limpia las cajas y setea la opPulsada en true para evitar poner la op matematica al inicio
                lblentrada.setText("");
                lblresultado.setText("");
                opPulsada=true;
            }
        });
        btnDEL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarUltimo();
            }
        });
        btnigual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //evalua la expresion dada en la entrada y lo muestra en txtResultado
                lblresultado.setText(Parser.evaluar(lblentrada.getText().toString()));
            }
        });
        //llamo las funciones que inicializan los botones de numeros y op mat
        initNumeros();
        initOperaciones();
    }

    private void initNumeros(){
        //recorre todos los botones en la lista y les agrega eventos onClick
        for (final Button btn:lstNumeros){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //cada vez que pulse un numero lo concatena al texto
                    lblentrada.setText(lblentrada.getText().toString() + btn.getText().toString());
                    opPulsada=false;
                }
            });
        }
    }
    private void initOperaciones(){
        //recorre todos los botones en la lista y les agrega eventos onClick
        for (final Button btn:lstOperaciones){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!opPulsada){// si no hay operacion matematica pulsada con anterioridad
                        //agrega la operacion al texto
                        lblentrada.setText(lblentrada.getText().toString() + btn.getText().toString());
                        opPulsada=true;// y entonces la operacion matematica ya esta pulsada
                    }
                }
            });
        }
    }
    private void eliminarUltimo(){
        //elimina el ultimo caracter en el TextView
        String str = lblentrada.getText().toString();//obtento el texto del TextView
        if (str != null && str.length() > 0 ) {// verifico que no sea nulo y que tenga mas de 1 caracter
            str = str.substring(0, str.length() - 1); // saco una subcadena del texto total - 1 (esto elimina el ultimo)
            if(str.length()>0)//si la longitud ya cortada es mayor a cero
                opPulsada = esOperacion((str.substring(str.length()-1,str.length())));//evaluo si es operacion
            else//si es menor a cero, es decir esta vacio
                opPulsada = true;//guardo como pulsado para evitar poner op matematicas al inicio
        }
        lblentrada.setText(str);
    }

    private boolean esOperacion(String txt){//evalua si es operacion matematica
        for (final Button btn:lstOperaciones){//revizo en la lista de botones
            if(btn.getText().equals(txt)){//comparo si el texto que envio es igual al texto de los botones '+' == '+' -> true
                return true;
            }
        }
        return false;//si no hay ningun texto que coincida entonces no es op matematica
    }

}




