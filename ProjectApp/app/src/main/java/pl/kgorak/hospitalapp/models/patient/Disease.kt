package pl.kgorak.hospitalapp.models.patient

import android.hardware.SensorAdditionalInfo
import com.google.gson.annotations.SerializedName

data class Disease (
    val type_n: Int,
    val type: String,
    @SerializedName("ICD-10") val icd10: String,
    val description: String,
    @SerializedName("additionalInfo")val addInfo: String
)