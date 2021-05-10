package com.example.soutenance.Service

import android.util.Log
import com.example.soutenance.Util.FirestoreUtil
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.notification != null) {
            //TODO: Show notification if we're not online
            Log.d("FCM", remoteMessage.data.toString())
        }
    }
         fun onTokenRefresh() {
            val newRegistrationToken = FirebaseMessaging.getInstance().token

            if (FirebaseAuth.getInstance().currentUser != null)
                addTokenToFirestore(newRegistrationToken)

    }

        companion object {
            fun addTokenToFirestore(newRegistrationToken: Task<String>) {
                if (newRegistrationToken == null) throw NullPointerException("FCM token is null.")

                FirestoreUtil.getFCMRegistrationTokens { tokens ->
                    if (tokens.contains(newRegistrationToken))
                        return@getFCMRegistrationTokens

                    val add = tokens.add(newRegistrationToken.toString())
                    FirestoreUtil.setFCMRegistrationTokens(tokens)
                }
            }
        }
    }
