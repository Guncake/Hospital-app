package pl.kgorak.hospitalapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_patient_show.*
import pl.kgorak.hospitalapp.R
import pl.kgorak.hospitalapp.data.PatientObj

class PatientShowActivity : AppCompatActivity() {

    private val patient = PatientObj.getPatient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_show)

        patient_name.text = patient.name
        patient_pesel.text = patient.pesel

        patient_disease_type.text = patient.disease[0].type
        patient_disease_icd.text = patient.disease[0].icd10
        
        patient_disease_description.text = patient.disease[0].description
        patient_medication_name.text = patient.medication[0].name
        patient_medication_dose.text = patient.medication[0].dose
    }
}
