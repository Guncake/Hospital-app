package pl.kgorak.hospitalapp.api

import pl.kgorak.hospitalapp.data.DataObj
import pl.kgorak.hospitalapp.models.DefaultGetPatientResponse
import pl.kgorak.hospitalapp.models.DefaultLoginResponse
import pl.kgorak.hospitalapp.models.RequestGetPatientBody
import pl.kgorak.hospitalapp.models.RequestLoginBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface ApiInterface {

    @POST("/user/login")
    fun userLogin(
        @Body requestBody: RequestLoginBody
    ): Call<DefaultLoginResponse>

    @POST("/patient/getpatient")
    fun getPatient(
        @Header ("auth-token") token: String,
        @Body requestBody: RequestGetPatientBody
    ): Call<DefaultGetPatientResponse>

}