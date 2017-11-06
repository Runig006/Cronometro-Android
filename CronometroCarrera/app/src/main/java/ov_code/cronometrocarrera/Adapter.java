package ov_code.cronometrocarrera;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dawmi on 26/10/2017.
 */

public class Adapter extends BaseAdapter {
    Context context;
    ArrayList<Carrera> data;
    private static LayoutInflater inflater = null;

    public Adapter(Context context, ArrayList<Carrera> data){
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.itemlayout, null);
        TextView text = (TextView) vi.findViewById(R.id.Name);
        text.setText(data.get(position).getName());
        return vi;
    }
}
