package pl.kgorak.hospitalapp.utils

import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executor

class FingerprintManagementUtil(activity: FragmentActivity, executor: Executor, callBack: BiometricPrompt.AuthenticationCallback) {
    private var biometricPrompt = BiometricPrompt(activity, executor, callBack)

    fun showBiometricPrompt () {
        val prompt = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Fingerprint authentication")
            .setDescription("Verify yourself with your fingerprint")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(prompt)
    }
}