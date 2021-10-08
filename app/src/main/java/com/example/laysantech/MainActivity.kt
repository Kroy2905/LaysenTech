package com.example.laysantech

/*import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}*/


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser

import com.google.firebase.auth.AuthResult

import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnCompleteListener






class MainActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var storedVerificationId:String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
     lateinit var Login : Button
      lateinit var otpGiven : EditText
      lateinit var  mobileNumber : EditText
      lateinit var signupButton: Button

    var Isotp = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mobileNumber=findViewById<EditText>(R.id.PhoneET)
        Login=findViewById<Button>(R.id.getOTPButton)
        otpGiven=findViewById<EditText>(R.id.otpET)
        signupButton = findViewById<Button>(R.id.signupbutton)


        auth=FirebaseAuth.getInstance()

//        Reference
       // val Login=findViewById<Button>(R.id.getOTPButton)


        var currentUser = auth.currentUser
        if(currentUser != null) {
            startActivity(Intent(applicationContext, Home::class.java))
            finish()
        }

        Login.setOnClickListener{

            var otp=otpGiven.text.toString().trim()
            var number=mobileNumber.text.toString().trim()






              if(otp.isEmpty() && !number.isEmpty() && !otpGiven.isEnabled)
                  login()
            if(!otp.isEmpty() && !number.isEmpty()){
                val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId.toString(), otp)
                signInWithPhoneAuthCredential(credential)
            }else if(!number.isEmpty() && otp.isEmpty()){
                Toast.makeText(this,"OTP has been Sent",Toast.LENGTH_SHORT).show()
            }
           //if(Isotp)

           // login()
           /* else
            {
                var intent = Intent(applicationContext,Home::class.java)
                //intent.putExtra("storedVerificationId",storedVerificationId)
                startActivity(intent)
            }*/

           // verify()
        }


        signupButton.setOnClickListener{


            val intent = Intent(this, signupActivity::class.java)
            startActivity(intent)
            /*startActivity(Intent(this, signupActivity::class.java))
            finish()*/


        }


        // Callback function for Phone Auth
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                startActivity(Intent(applicationContext, Home::class.java))
                finish()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(applicationContext, "Failed", Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

                Log.d("TAG","onCodeSent:$verificationId")
                storedVerificationId = verificationId
                resendToken = token
                 //verify(storedVerificationId)
                otpGiven.isEnabled = true

                Login.setText("LOGIN")

                //verify(storedVerificationId)






               /* var intent = Intent(applicationContext,Verify::class.java)
                intent.putExtra("storedVerificationId",storedVerificationId)
                startActivity(intent)*/
            }
        }

    }

    private fun login() {
        //val mobileNumber=findViewById<EditText>(R.id.PhoneET)
        var number=mobileNumber.text.toString().trim()

        if(!number.isEmpty()){
            number="+91"+number
            sendVerificationcode (number)
        }else{
            Toast.makeText(this,"Enter mobile number",Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendVerificationcode(number: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }


   /* private fun verify(storedVerificationId:String){

        var otp=otpGiven.text.toString().trim()
        if(!otp.isEmpty()){
            val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                storedVerificationId.toString(), otp)
            signInWithPhoneAuthCredential(credential)
        }else{
            Toast.makeText(this,"Enter OTP",Toast.LENGTH_SHORT).show()
        }
    }*/

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {

        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    startActivity(Intent(applicationContext, Home::class.java))
                   finish()
// ...
                } else {
// Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
// The verification code entered was invalid
                        Toast.makeText(this,"Invalid OTP",Toast.LENGTH_SHORT).show()
                    }
                }
            }

    }


        /*public fun signup (view :  View){
            startActivity(Intent(applicationContext, signupActivity::class.java))
            finish()


    }*/




}



