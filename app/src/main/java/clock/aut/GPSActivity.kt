package clock.aut

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import base.BaseActivity
import com.bulutsoft.attendance.R
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import service.base.ClockType
import service.base.ClockTypeFactory
import service.base.IClockType

class GPSActivity : BaseActivity() {
    private val clockTypeFactory = ClockTypeFactory(this@GPSActivity, this)
    private lateinit var mapView: MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  Mapbox.getInstance(this, "pk.eyJ1IjoibW9oYW1tYWRqYWZhcml5YW43IiwiYSI6ImNqcXRqenkyczBha2k0M281NjQ0amVlNWwifQ.xD69Nt5VabUT8dwmlTOdWQ");
        setContentView(R.layout.activity_gps)
        title = "مکان یابی"
        clockInAttempt()
        initMap(savedInstanceState)
    }

    private fun initMap(savedInstanceState: Bundle?) {

        mapView = MapView(this)
        mapView.mapboxMap.setCamera(
            CameraOptions.Builder()
                .center(Point.fromLngLat(-98.0, 39.5))
                .pitch(0.0)
                .zoom(2.0)
                .bearing(0.0)
                .build()
        )
// Add the map view to the activity (you can also add it to other views as a child)
        setContentView(mapView)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PackageManager.PERMISSION_GRANTED) {
            for (i in permissions.indices) {
                val permission = permissions[i]
                val grantResult = grantResults[i]
                if (permission == Manifest.permission.ACCESS_FINE_LOCATION || permission == Manifest.permission.ACCESS_COARSE_LOCATION) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    } else {
                    }
                }
            }
        }
    }

    private fun clockInAttempt() {
        var clock: IClockType? = null
        var location: Location? = null
        try {
            Toast.makeText(applicationContext, "در حال خواندن GPS", Toast.LENGTH_LONG).show()
            clock = clockTypeFactory.GetClockType(ClockType.GPS)
            location = clock.clockInAttempt() as Location
            if (!clock.isSuccess) {
                Toast.makeText(applicationContext, clock.message, Toast.LENGTH_LONG)
                val intent = Intent()
                intent.putExtra("error", clock.message)
                setResult(RESULT_CANCELED, intent)
                finish()
                return
            }
            setResult(RESULT_OK)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG)
            val intent = Intent()
            intent.putExtra("error", e.message)
            setResult(RESULT_CANCELED, intent)
            finish()
            return
        }
        Toast.makeText(applicationContext, "با موفقیت دیتکت شد", Toast.LENGTH_LONG).show()
        SingleTon.getInstance().location = location
        finish()

        // showAlert("پیغام", "با موفقیت دیتکت شد");
    }

    val isNetworkAvailable: Boolean
        get() {
            val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
}
