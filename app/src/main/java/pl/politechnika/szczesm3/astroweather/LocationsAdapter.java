package pl.politechnika.szczesm3.astroweather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import pl.politechnika.szczesm3.astroweather.config.AppConfig;
import pl.politechnika.szczesm3.astroweather.entity.Location;

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.MyViewHolder> {

    private Context context;
    private List<Location> locations;

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView name, lat, lon, unit;

        MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            lat = view.findViewById(R.id.lat);
            lon = view.findViewById(R.id.lon);
            unit = view.findViewById(R.id.unit);
        }
    }

    LocationsAdapter(List<Location> locations, Context context) {
        this.locations = locations;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_row, parent, false);
        return new MyViewHolder(itemView);
    }

    public void removeItem(int position) {
        locations.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Location location = locations.get(position);
        holder.name.setText(location.getName());
        holder.lat.setText(String.valueOf(location.getLatitude()));
        holder.lon.setText(String.valueOf(location.getLongitude()));
        holder.unit.setText("f".equals(location.getUnit()) ? "Imperial" : "Metric");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfig.getInstance().setWoeid(location.getWoeid());
                AppConfig.getInstance().setUnits(location.getUnit());
                AppConfig.getInstance().setLatitude(location.getLatitude());
                AppConfig.getInstance().setLongitude(location.getLongitude());
                Log.d("APPCONFIG: ", AppConfig.getInstance().toString());
                Toast.makeText(context, location.getName() + " selected.", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public void setItems(@NonNull final List items) {
        this.locations.clear();
        this.locations.addAll(items);
    }
}