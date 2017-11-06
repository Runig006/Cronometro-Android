package ov_code.cronometrocarrera;

import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Text;
/*TODO
    Meter una manera de ponerle nombres concretos a los puntos, es decir, que en vez de usar el Geocoder los ponga el usuario...que quieres que te diga, falla el Geocoder, ya lo mejoraremos
 */
public class newRace extends AppCompatActivity {
    LatLng Start=null;
    LatLng End=null;
    TextView InicioText;
    TextView MetaText;
    TextView kmText;
    EditText nameText, StartName, EndName;
    Button boton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_race);
        InicioText=(TextView)this.findViewById(R.id.StartName);
        MetaText=(TextView)this.findViewById(R.id.EndName);
        kmText=(TextView)this.findViewById(R.id.kmText);
        nameText=(EditText)this.findViewById(R.id.RaceName);
        StartName=(EditText)this.findViewById(R.id.StartName);
        EndName=(EditText)this.findViewById(R.id.EndName);
        boton=(Button)this.findViewById(R.id.button3);
    }
    public void map(View view) {
        switch (view.getId()) {
            case R.id.InicioSelect:
                startActivityForResult(new Intent(this, MapsActivity.class), 0);
                break;
            case R.id.MetaSelect:
                startActivityForResult(new Intent(this, MapsActivity.class), 1);
                break;

        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                Start = new LatLng(data.getExtras().getDouble("Lat"),data.getExtras().getDouble("Log"));
            }
        }
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                End = new LatLng(data.getExtras().getDouble("Lat"),data.getExtras().getDouble("Log"));
            }
        }
        if (Start!=null && End!=null){
            float temp=Functions.checkDistance(Start.latitude,Start.longitude,End.latitude,End.longitude);
            String.format("%.2f", temp);
            kmText.setText(temp+"km");
            boton.setEnabled(true);
        }

    }
    public void add(View view){
        Double lon1 = Start.longitude;
        Double lat1 = Start.latitude;
        Double lon2 = End.longitude;
        Double lat2 = End.latitude;

        String name = nameText.getText().toString();
        String startName = StartName.getText().toString();
        String endName = EndName.getText().toString();
        if (startName.isEmpty()||endName.isEmpty()){
            Toast toast = Toast.makeText(this, "Nombres de inicio y final no seleccionados", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        new DataBase(this).insert(name,lat1, lon1,startName, lat2, lon2, endName, 0.0f);
        finish();
    }

}

