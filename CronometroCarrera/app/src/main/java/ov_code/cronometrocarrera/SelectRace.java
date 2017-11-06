package ov_code.cronometrocarrera;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class SelectRace extends AppCompatActivity {
    ListView lista;
    TextView StartTe,EndTe,KmTe,TimeTe;
    int position_of_race;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_race);
        lista=(ListView)this.findViewById(R.id.Lista);
        StartTe=(TextView)this.findViewById(R.id.StartChText);
        EndTe=(TextView)this.findViewById(R.id.EndChText);
        KmTe=(TextView)this.findViewById(R.id.kmChText);
        TimeTe=(TextView)this.findViewById(R.id.TimeChText);
        button=(Button)this.findViewById(R.id.EmButton);
        llenarLista();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                position_of_race=position;
                view.setSelected(true);
                chooseRace(Functions.Listacarreras.get(position));
                button.setEnabled(true);
            }
        });
    }

    protected void llenarLista(){
        Cursor cursor=new DataBase(this).selectRaces();
        Functions.llenarCarrera(cursor);
        lista.setAdapter(new Adapter(this, Functions.Listacarreras));
    }

    protected void chooseRace(Carrera carrera){
        StartTe.setText(carrera.getStart());
        EndTe.setText(carrera.getEnd());
        KmTe.setText(Float.toString(Functions.checkDistance(carrera.startLat,carrera.startLon,carrera.endLat,carrera.endLon)));
        TimeTe.setText(Functions.timeFormat(carrera.getTime()));
    }

    public void SelectRace(View view){
        Intent resultIntent = new Intent();
        resultIntent.putExtra("numero_carrera",position_of_race);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
    public void DeleteRace(View view){
        int delete=Functions.Listacarreras.get(position_of_race).getID();
        new DataBase(this).delete(delete);
        llenarLista();
    }

}
