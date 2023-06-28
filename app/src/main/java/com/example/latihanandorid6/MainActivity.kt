package com.example.latihanandorid6

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    //Realtime Database
    private lateinit var dref: DatabaseReference
    //Firestore Database
    private val db = Firebase.firestore
    private val collectionName = "LDR-Sensor"
    private val maxDataCount = 10
    private var currentDataCount = 0

    //Initilized data sensor
    var nilaiProximity: String? = "No Data"
    var nilaiUltrasonic: String? = "No Data"
    var nilaiKecerahan: String? = "No Data"
    var nilaiPIR: String? = "No Data"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Ambil Id dari Interface Android
        val tvDataInfrared = findViewById<TextView>(R.id.tvDataProximity)
        val tvDataUltrasonic = findViewById<TextView>(R.id.tvDataUltrasonic)
        val tvDataPIR = findViewById<TextView>(R.id.tvDataPIR)
        val tvDataKecerahan = findViewById<TextView>(R.id.tvDataKecerahan)

        val btnNextScreen = findViewById<Button>(R.id.btnNextScreen)
        btnNextScreen.setOnClickListener {
            // Ketika tombol ditekan, pindah ke layar baru
            val intent = Intent(this, ChartActivity::class.java)
            startActivity(intent)
        }

        //Read Data Firebase
        dref = FirebaseDatabase.getInstance().reference
        dref.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {

                //Ambil Data Dari RTfirebase
                nilaiProximity = snapshot.child("SensorData/infrared/proximity").value.toString()
                nilaiKecerahan = snapshot.child("SensorData/ldr/kecerahan").value.toString()
                nilaiUltrasonic = snapshot.child("SensorData/ultrasonic/jarak").value.toString()
                nilaiPIR = snapshot.child("SensorData/pir/pirState").value.toString()
                //Function input data to firebase
                tambahkanDataOtomatis()
                //Cek Data Kosong atau Tidak
                if(snapshot.value != null){
                    tvDataPIR.setText("$nilaiPIR").toString()
                    tvDataInfrared.setText("$nilaiProximity").toString()
                    tvDataUltrasonic.setText("$nilaiUltrasonic").toString()
                    tvDataKecerahan.setText("$nilaiKecerahan").toString()
                    tvDataPIR.setText("$nilaiPIR").toString()
                }
            }
            override fun onCancelled(error: DatabaseError) {
              Log.d("FirebaseError", "$error")
            }
        })

    }

    //Function to add data to firestore database
    private fun tambahkanDataOtomatis() {
        if (currentDataCount == maxDataCount) {
            currentDataCount = 0
        }

        val data = hashMapOf(
            "Kecerahan" to nilaiKecerahan
        )

        db.collection(collectionName)
            .document("data_$currentDataCount")
            .set(data)
            .addOnSuccessListener {
                currentDataCount++
                tambahkanDataOtomatis()
            }
            .addOnFailureListener { e ->
                Log.d("AddDataError", "$e")
            }
    }
}