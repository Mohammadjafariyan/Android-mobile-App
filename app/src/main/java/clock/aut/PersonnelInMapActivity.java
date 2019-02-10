package clock.aut;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


import com.example.moham.testandroidapp.R;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.PolygonOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.Line;
import com.mapbox.mapboxsdk.plugins.annotation.LineManager;
import com.mapbox.mapboxsdk.plugins.annotation.LineOptions;
import com.mapbox.mapboxsdk.plugins.annotation.OnLineClickListener;
import com.mapbox.mapboxsdk.plugins.annotation.OnLineLongClickListener;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.style.sources.Source;
import com.mapbox.mapboxsdk.utils.ColorUtils;

import java.util.ArrayList;
import java.util.List;

import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.expressions.Expression.match;
import static com.mapbox.mapboxsdk.style.expressions.Expression.rgb;
import static com.mapbox.mapboxsdk.style.expressions.Expression.stop;

public class PersonnelInMapActivity extends AppCompatActivity {

    private MapView mapView;
    private LineManager lineManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("مکان پرسنل");


        Mapbox.getInstance(getApplicationContext(), "pk.eyJ1IjoibW9oYW1tYWRqYWZhcml5YW43IiwiYSI6ImNqcXRqenkyczBha2k0M281NjQ0amVlNWwifQ.xD69Nt5VabUT8dwmlTOdWQ");
        setContentView(R.layout.activity_personnel_in_map);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final MapboxMap mapboxMap) {

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(48.13863, 11.57603))
                        .title("محمد جعفریان")
                        .snippet("حظور در شرکت"));
                // Map is set up and the style has loaded. Now you can add data or make other map adjustments


                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

                        CameraPosition position = new CameraPosition.Builder()
                                .target(new LatLng(48.13863, 11.57603))
                                .zoom(10)
                                .tilt(20)
                                .build();

                        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 1);

                      /*  lineManager = new LineManager(mapView, mapboxMap, style);
                        lineManager.addClickListener(new OnLineClickListener() {
                            @Override
                            public void onAnnotationClick(Line line) {
                                Toast.makeText(PersonnelInMapActivity.this,
                                        String.format("Line clicked %s", line.getId()),
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        });


                        lineManager.addLongClickListener(new OnLineLongClickListener() {
                            @Override
                            public void onAnnotationLongClick(Line line) {
                                Toast.makeText(PersonnelInMapActivity.this,
                                        String.format("Line long clicked %s", line.getId()),
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        });*/

                     /*   // create a fixed line
                        List<LatLng> latLngs = new ArrayList<>();
                        latLngs.add(new LatLng(48.13863, 11.57603));
                        latLngs.add(new LatLng(48.17863, 11.07603));
                        latLngs.add(new LatLng(48.19863, 11.67603));

                        float s=100f;
                        LineOptions lineOptions = new LineOptions()
                                .withLatLngs(latLngs)
                                .withLineColor("#9b0000")
                                .withLineJoin(Property.LINE_JOIN_ROUND)
                                .withLineOpacity(255f)
                                .withLineWidth(5.0f);
                        lineManager.create(lineOptions);*/


                     /*   List<LatLng> polygonLatLngList = new ArrayList<>();

                        polygonLatLngList.add(new LatLng(48.17863, 11.67603));
                        polygonLatLngList.add(new LatLng(48.27863, 11.77603));
                        polygonLatLngList.add(new LatLng(48.37863, 11.87603));
                        polygonLatLngList.add(new LatLng(48.47863, 11.97603));
                        polygonLatLngList.add(new LatLng(48.57863, 11.59603));
                        polygonLatLngList.add(new LatLng(48.67863, 11.27603));
                        polygonLatLngList.add(new LatLng(48.77863, -122.659091));

                        mapboxMap.addPolygon(new PolygonOptions()
                                .addAll(polygonLatLngList)
                                .fillColor(Color.parseColor("#3bb2d0")));
*/



                    }
                });
            }
        });


    }


    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


}
