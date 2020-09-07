package pl.kgorak.hospitalapp.models

import pl.kgorak.hospitalapp.models.patient.Patient

data class DefaultGetPatientResponse (val error: Boolean, val message: String, val patientData: Patient)