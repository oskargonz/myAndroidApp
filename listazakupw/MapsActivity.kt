package com.example.listazakupw


import android.Manifest
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.HashMap

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val geoClient = LocationServices.getGeofencingClient(this)

        //DB creation
        val database = FirebaseDatabase.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        val myRef3 = database.getReference().child("places").child(user!!.uid)
        //val product_ref = myRef2.child("products")
        val list = arrayListOf<Place>()

        //Zezwolenia
        val perms = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )
        if(ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED
        )
        {
            requestPermissions(perms,0)
        }

        bt_map.setOnClickListener {
            //zapisanie lokalizacji
            var location = Location("gps")
            LocationServices.getFusedLocationProviderClient(this).lastLocation
                .addOnSuccessListener {
                    location = it
                    Log.i("location", "Location: ${it.latitude}, ${it.longitude}")
                    //Dodawanie markera
                   val latLng = LatLng(location.latitude, location.longitude)
                    val marker = MarkerOptions()
                            .position(latLng)
                            .title(et_map.text.toString())
                    mMap.addMarker(marker)
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))

                    //Geofence
                    val geo = Geofence.Builder().setRequestId("Geo${et_map.text.toString()}")
                            .setCircularRegion(
                                    latLng.latitude,
                                    latLng.longitude,
                                    100F
                            )
                            .setExpirationDuration(Geofence.NEVER_EXPIRE)
                            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
                            .build()
                    val geoRequest = GeofencingRequest.Builder()
                            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                            .addGeofence(geo)
                            .build()

                    val geoPendingIntent = PendingIntent.getBroadcast(
                            this,
                            0,
                            Intent(this, GeofenceReceiver::class.java),
                            PendingIntent.FLAG_UPDATE_CURRENT
                    )
                    //Broadcast receiver
                    val explicitBroadcastRemote = Intent()
                    explicitBroadcastRemote.component = ComponentName("com.example.listazakupw",  "com.example.listazakupw.GeofenceReceiver")
                    sendBroadcast(explicitBroadcastRemote, "com.example.listazakupw.MY_PERMISSION")

                    geoClient.addGeofences(geoRequest, geoPendingIntent).addOnSuccessListener {
                        Log.i("MyGeo", "Dodano geofence")
                    }.addOnFailureListener{
                        Log.i("MyGeo", "Nie doodano geofence ${it.printStackTrace()}")
                    }

                    //Push do bazy danych
                    val place = Place(
                            et_map.text.toString(),
                            et_map_description.text.toString(),
                            "100m",
                            "Brak promocji",
                            location.latitude,
                            location.longitude
                    )
                    CoroutineScope(Dispatchers.IO).launch{
                        myRef3.push().setValue(place)
                    }
                    Toast.makeText(this, "ADD ITEM", Toast.LENGTH_SHORT).show()




                }

            }
        bt_lista_sklepow.setOnClickListener{
            val intentPlacesList = Intent(this, PlacesListActivity::class.java)
            startActivity(intentPlacesList)
        }
        myRef3.orderByChild("name").addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.value == null){
                    return
                }
                val response = snapshot.value as HashMap<String, HashMap<String, Objects>>
                response.forEach{
                    var title = it.value["name"] as String
                    var latitude = it.value["latitude"] as Double
                    var longitude = it.value["longitude"] as Double

                    val marker = MarkerOptions()
                            .position(LatLng(latitude, longitude))
                            .title(title)
                    mMap.addMarker(marker)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MapsActivity, error.message, Toast.LENGTH_LONG).show()
            }

        })
        

/*        myRef3.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val place = snapshot.getValue(Place::class.java)
                var placeName = place!!.name
                var radius = place!!.radius.toDouble()
                var lat = place!!.latitude
                var long = place!!.longtitude
                val latLng = LatLng(lat, long)


                val marker = MarkerOptions()
                        .position(latLng)
                        .title(placeName)

                val circle = CircleOptions()
                        .center(latLng)
                        .radius(radius)

                mMap.addMarker(marker)
                mMap.addCircle(circle)
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

        }

        )*/

        
        
        
        
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onResume() {
        super.onResume()

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.isMyLocationEnabled = true


        /*val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/
    }
}