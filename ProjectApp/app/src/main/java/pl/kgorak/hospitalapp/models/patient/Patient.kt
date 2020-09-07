package pl.kgorak.hospitalapp.models.patient

import android.location.Location
import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

data class Patient (
    val id: Int,
    val name: String,
    @SerializedName("PESEL") val pesel: String,
    val date: String,
    val disease: Array<Disease>,
    @SerializedName("location")val patientLocation: PatientLocation,
    val medication: Array<Medication>
)
