package com.example.opsc2
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.opsc2.DisplayTimesheetsActivity
import com.example.opsc2.TimeSheet
import com.example.opsc2.databinding.ActivityTimesheetBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class TimesheetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTimesheetBinding
    private lateinit var database: DatabaseReference
    private lateinit var storage: FirebaseStorage
    private lateinit var attachmentRef: StorageReference
    private var attachmentUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimesheetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().getReference("TimeSheet")
        storage = FirebaseStorage.getInstance()
        attachmentRef = storage.reference.child("attachments")

        binding.button4.setOnClickListener {
            val timesheetname = binding.editTextTextPersonName7.text.toString()
            val selectedRadioButtonId = binding.radioGroup.checkedRadioButtonId
            val selectedRadioButton: RadioButton = findViewById(selectedRadioButtonId)
            val categoryTS = selectedRadioButton.text.toString()
            val calendarView = binding.calendarView2
            val selectedDateMillis = calendarView.date
            val selectedDate = Date(selectedDateMillis)
            val StartTime = binding.editTextTime2.text.toString()
            val EndTime = binding.editTextTime3.text.toString()
            val description = binding.editTextTextPersonName6.text.toString()
            val mingoalss = binding.editTextTextPersonName4.text.toString()
            val maxgoalss = binding.editTextTextPersonName6.text.toString()

            if (selectedRadioButton.text == "Add Attachment") {
                if (attachmentUri != null) {
                    uploadAttachment(timesheetname, categoryTS, selectedDate, StartTime, EndTime, description, mingoalss, maxgoalss)
                } else {
                    Toast.makeText(this, "Please select an attachment.", Toast.LENGTH_SHORT).show()
                }
            } else {
                val timesheet = TimeSheet(timesheetname, categoryTS, selectedDate, StartTime, EndTime, description, mingoalss, maxgoalss)
                database.child(timesheetname).setValue(timesheet).addOnSuccessListener {
                    Toast.makeText(this, "Timesheet created", Toast.LENGTH_SHORT).show()
                }
            }
            val intent = Intent(this, DisplayTimesheetsActivity::class.java)
            startActivity(intent)

        }

        binding.radioButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                openImagePicker()
            }
        }


    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICKER_REQUEST)
    }

    private fun uploadAttachment(
        timesheetname: String,
        categoryTS: String,
        selectedDate: Date,
        startTime: String,
        endTime: String,
        description: String,
        minGoals: String,
        maxGoals: String
    ) {
        val attachmentName = UUID.randomUUID().toString()
        val attachmentRef = attachmentRef.child(attachmentName)
        val uploadTask = attachmentRef.putFile(attachmentUri!!)

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            attachmentRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                val timesheet = TimeSheet(
                    timesheetname,
                    categoryTS,
                    selectedDate,
                    startTime,
                    endTime,
                    description,
                    minGoals,
                    maxGoals
                )
                database.child(timesheetname).setValue(timesheet).addOnSuccessListener {
                    Toast.makeText(this, "Timesheet created", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Failed to upload attachment.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val IMAGE_PICKER_REQUEST = 1
    }
}
