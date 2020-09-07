package pl.kgorak.hospitalapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*
import pl.kgorak.hospitalapp.api.RetrofitClient
import pl.kgorak.hospitalapp.data.DataObj
import pl.kgorak.hospitalapp.R
import pl.kgorak.hospitalapp.data.PatientObj
import pl.kgorak.hospitalapp.models.DefaultGetPatientResponse
import pl.kgorak.hospitalapp.models.RequestGetPatientBody
import retrofit2.Call
import retrofit2.Response

class HomeActivity : AppCompatActivity() {


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        welcome_text.text = getString(R.string.logged_as) + " " + DataObj.getUser()
        qr_button.setOnClickListener{
            val intent = Intent(this, ScanqrActivity::class.java)

            startActivity(intent)

        }
        search_patient_button.setOnClickListener{

            RetrofitClient.instance.getPatient(DataObj.getToken(), RequestGetPatientBody(patient_text.text.toString()))
                .enqueue(object : retrofit2.Callback<DefaultGetPatientResponse> {
                    override fun onFailure(call: Call<DefaultGetPatientResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<DefaultGetPatientResponse>, response: Response<DefaultGetPatientResponse>)
                    {
                        if (response.code()==200){
                            Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_SHORT).show()
                            if(!response.body()?.error!!){
                                PatientObj.setPatient(response.body()!!.patientData)
                                val intent = Intent(applicationContext, PatientShowActivity::class.java)
                                startActivity(intent)
                            }
                        } else{Toast.makeText(applicationContext, "error: " + response.code(), Toast.LENGTH_SHORT).show()}

                    }
                })
        }
    }

    override fun onStart() {
        super.onStart()
        patient_text.setText(DataObj.getQRcode())
    }

    override fun onStop() {
        super.onStop()
        DataObj.setQRcode("")
    }
}
