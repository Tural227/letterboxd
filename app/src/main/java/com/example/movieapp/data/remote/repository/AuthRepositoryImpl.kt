package com.example.movieapp.data.remote.repository

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.movieapp.data.remote.model.FirebaseUser
import com.example.movieapp.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import com.shashank.sony.fancytoastlib.FancyToast
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth,private val firestore: FirebaseFirestore, private val context: Context): AuthRepository{
    override suspend fun register(email: String,name: String, password: String): Boolean {
        val flag = suspendCoroutine {coroutine->
            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                FancyToast.makeText(context,"Register successful!",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show()
                coroutine.resume(true)// success ucun excepction niye olsun
            }.addOnFailureListener{
                FancyToast.makeText(context,"Register Failed: ${it.message}!",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show()
                coroutine.resume(false)
            }
        }
        return flag

    }



    override suspend fun login(email: String, password: String): Boolean {
        val flag: Boolean = suspendCoroutine { coroutine->
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                FancyToast.makeText(context,"Login successful!",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show()
                coroutine.resume(true)
            }.addOnFailureListener {
                FancyToast.makeText(context,"Login unsuccessful!",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show()
                coroutine.resume(false)
            }
        }
        return flag
    }



    override suspend fun recoverAccount(email: String) : Boolean {
        val flag : Boolean = suspendCoroutine { coroutine->
            firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener {
                Intent()
                FancyToast.makeText(context,"Check your email inbox!",FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show()
                coroutine.resume(true)
            }.addOnFailureListener{
                FancyToast.makeText(context,it.message,FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show()
                coroutine.resume(false)
            }
        }
        return flag
    }



}



