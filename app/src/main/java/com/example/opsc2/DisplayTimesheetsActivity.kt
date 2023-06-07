package com.example.opsc2
import TimesheetAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class DisplayTimesheetsActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var timesheetList: MutableList<TimeSheet>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_timesheets)

        recyclerView = findViewById(R.id.timesheetRecyclerView)
        timesheetList = mutableListOf()
        database = FirebaseDatabase.getInstance().getReference("TimeSheet")

        val adapter = TimesheetAdapter(this, timesheetList)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    timesheetList.clear()
                    for (timesheetSnapshot in dataSnapshot.children) {
                        val timesheet = timesheetSnapshot.getValue(TimeSheet::class.java)
                        timesheet?.let {
                            timesheetList.add(it)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database error here, if needed
            }
        })
    }
}
