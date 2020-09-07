package pl.kgorak.hospitalapp.activities

import android.content.Intent
import androidx.biometric.BiometricPrompt
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import pl.kgorak.hospitalapp.api.RetrofitClient
import pl.kgorak.hospitalapp.data.DataObj
import pl.kgorak.hospitalapp.models.DefaultLoginResponse
import pl.kgorak.hospitalapp.models.RequestLoginBody
import pl.kgorak.hospitalapp.utils.FingerprintManagementUtil
import pl.kgorak.hospitalapp.R
import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {

    private var fingerLoggedFlag: Boolean = false
    private val noFingerprintImage = R.drawable.fingerprint_off
    private val acceptedFingerprintImage = R.drawable.fingerprint_on
    var token: String? = null



    private val callback: BiometricPrompt.AuthenticationCallback =
        object : BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                fingerLoggedFlag = true
                fingerprit_button.setImageResource(acceptedFingerprintImage)
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login_button.setOnClickListener {
            if (!fingerLoggedFlag) {

                RetrofitClient.instance.userLogin(RequestLoginBody(login_text.text.toString(), password_text.text.toString()))
                .enqueue(object : retrofit2.Callback<DefaultLoginResponse> {
                    override fun onFailure(call: Call<DefaultLoginResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<DefaultLoginResponse>, response: Response<DefaultLoginResponse>)
                    {
                        if (response.code()==200){
                            Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_SHORT).show()
                            if (!response.body()?.error!!) {
                                token = response.body()!!.token
                                DataObj.setToken(token.toString())
                                DataObj.setUser(login_text.text.toString())

                                val intent = Intent(applicationContext, HomeActivity::class.java)
                                startActivity(intent)
                            }
                        } else {Toast.makeText(applicationContext, "error: " + response.code(), Toast.LENGTH_SHORT).show() }
                    }
                })
            }
        }

        fingerprit_button.setOnClickListener {
            if (!fingerLoggedFlag) {
                FingerprintManagementUtil(
                    this,
                    Executors.newSingleThreadExecutor(),
                    callback
                ).showBiometricPrompt()
            }
        }

    }

        override fun onStart() {
            super.onStart()

            login_text.text = null
            password_text.text = null
            fingerLoggedFlag = false
            fingerprit_button.setImageResource(noFingerprintImage)
            DataObj.setUser("")
        }
}



