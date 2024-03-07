package co.edu.unipiloto.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //determinar si el cronometro esta en ejecución
    private boolean running;
    //contador con los segundos cuando se ejecuta
    private int segundos;
    private ArrayList<String> laps = new ArrayList<>();
    private ArrayAdapter<String> array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //invocar un hilo de cronometrrar
        runTimer();
        array = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,laps);
        ListView listView = findViewById(R.id.vueltas);
        listView.setAdapter(array);

    }

    private void runTimer() {
        //relacion un objeto textview con el elemento grafico
        TextView timeview=(TextView) findViewById(R.id.time_view);
        //Declarar un handler para manejar el teimpo en un hilo de ejecución
        Handler handler=new Handler();
        //Invocar el metodo post e instaciar un objeto runnable
        handler.post(new Runnable() {
            @Override
            public void run() {
                int horas= segundos/3600;
                int minutos=(segundos%3600)/60;
                int secs = segundos%60;
                //Formato de salida
                String tiempo=String.format(Locale.getDefault(),"%d:%02d:%02d",horas,minutos,secs);
                timeview.setText(tiempo);
                if(running)
                    segundos++;
                handler.postDelayed(this,1000);
            }
        });
    }

    public void onClickStart(View view) {
        running=true;
    }
    public void onClickPause(View view) {
        running=false;

    }

    public void OnClickReset(View view) {
        running=false;
        segundos=0;
        laps.clear();
        array.notifyDataSetChanged();
    }

    public void OnClickLap(View view){
        if(running && laps.size() < 5){
            int horas = segundos/3600;
            int minutos =(segundos%3600)/60;
            int secs =segundos%60;
            String tiempo =String.format(Locale.getDefault(),"%d%02d:%02d",horas,minutos,secs);
            laps.add(tiempo);
            array.notifyDataSetChanged();
        }
    }


}