package pl.kgorak.hospitalapp.data

import pl.kgorak.hospitalapp.models.patient.Patient

object PatientObj {
    private lateinit var patient :Patient

    public fun setPatient (pat: Patient){
        patient = pat
    }
    public fun getPatient (): Patient {
        return patient
    }
}