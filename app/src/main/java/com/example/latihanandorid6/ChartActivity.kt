package com.example.latihanandorid6

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class ChartActivity : AppCompatActivity() {
    private lateinit var lineGraphView: GraphView

    //Realtime Database
    private lateinit var dref: DatabaseReference

    //Initilized data sensor
    var nilaiUltrasonic0: String? = "No Data"
    var nilaiUltrasonic1: String? = "No Data"
    var nilaiUltrasonic2: String? = "No Data"
    var nilaiUltrasonic3: String? = "No Data"
    var nilaiUltrasonic4: String? = "No Data"

    var nilaiKecerahan0: String? = "No Data"
    var nilaiKecerahan1: String? = "No Data"
    var nilaiKecerahan2: String? = "No Data"
    var nilaiKecerahan3: String? = "No Data"
    var nilaiKecerahan4: String? = "No Data"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)
// on below line we are initializing
        // our variable with their ids.
        lineGraphView = findViewById(R.id.idGraphView)

        //Read Data Firebase
        dref = FirebaseDatabase.getInstance().reference
        dref.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {

                //Ambil Data Dari RTfirebase

                nilaiKecerahan0 = snapshot.child("SensorData/ldr/kecerahan0").value.toString()
                nilaiKecerahan1 = snapshot.child("SensorData/ldr/kecerahan1").value.toString()
                nilaiKecerahan2 = snapshot.child("SensorData/ldr/kecerahan2").value.toString()
                nilaiKecerahan3 = snapshot.child("SensorData/ldr/kecerahan3").value.toString()
                nilaiKecerahan4 = snapshot.child("SensorData/ldr/kecerahan4").value.toString()

                nilaiUltrasonic0 = snapshot.child("SensorData/ultrasonic/jarak0").value.toString()
                nilaiUltrasonic1 = snapshot.child("SensorData/ultrasonic/jarak1").value.toString()
                nilaiUltrasonic2 = snapshot.child("SensorData/ultrasonic/jarak2").value.toString()
                nilaiUltrasonic3 = snapshot.child("SensorData/ultrasonic/jarak3").value.toString()
                nilaiUltrasonic4 = snapshot.child("SensorData/ultrasonic/jarak4").value.toString()

                //Konversi Kecerahan
                val kec1: Double = nilaiKecerahan0!!.toDouble()
                val kec2: Double = nilaiKecerahan1!!.toDouble()
                val kec3: Double = nilaiKecerahan2!!.toDouble()
                val kec4: Double = nilaiKecerahan3!!.toDouble()
                val kec5: Double = nilaiKecerahan4!!.toDouble()

                //Konversi Ultrasonic
                val ultra1: Double = nilaiUltrasonic0!!.toDouble()
                val ultra2: Double = nilaiUltrasonic1!!.toDouble()
                val ultra3: Double = nilaiUltrasonic2!!.toDouble()
                val ultra4: Double = nilaiUltrasonic3!!.toDouble()
                val ultra5: Double = nilaiUltrasonic4!!.toDouble()

                // on below line we are adding data to our graph view.
                val series1: LineGraphSeries<DataPoint> = LineGraphSeries(
                    arrayOf(
                        // on below line we are adding
                        // each point on our x and y axis.
                        DataPoint(0.0, kec1),
                        DataPoint(1.0, kec2),
                        DataPoint(2.0, kec3),
                        DataPoint(3.0, kec4),
                        DataPoint(4.0, kec5)
                    ),
                )

                val series4: LineGraphSeries<DataPoint> = LineGraphSeries(
                    arrayOf(
                        // on below line we are adding
                        // each point on our x and y axis.
                        DataPoint(0.0, ultra1),
                        DataPoint(1.0, ultra2),
                        DataPoint(2.0, ultra3),
                        DataPoint(3.0, ultra4),
                        DataPoint(4.0, ultra5),
                    )
                )

                // on below line adding animation
                lineGraphView.animate()

                lineGraphView.title = "SENSOR DATA"
                // on below line we are setting scrollable
                // for point graph view
                lineGraphView.viewport.isScrollable = true

                // on below line we are setting scalable.
                lineGraphView.viewport.isScalable = true

                // on below line we are setting scalable y
                lineGraphView.viewport.setScalableY(true)

                // on below line we are setting scrollable y
                lineGraphView.viewport.setScrollableY(true)

                series1.isDrawDataPoints

                series1.thickness = 30
                series4.thickness = 30

                series1.isDrawBackground =true
                series4.isDrawBackground =true

                // on below line we are setting color for series.
                series1.color = R.color.black
                series4.color = R.color.white

                // on below line we are adding
                // data series to our graph view.
                lineGraphView.addSeries(series1)
                lineGraphView.addSeries(series4)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("FirebaseError", "$error")
            }
        })

    }
}