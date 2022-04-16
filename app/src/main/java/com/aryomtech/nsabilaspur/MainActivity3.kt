package com.aryomtech.nsabilaspur

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main3.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val TOPIC="/topics/myTopic"
class MainActivity3 : AppCompatActivity() {

    val TAG="MainActivity"

    var auth: FirebaseAuth? = null
    var checkuser: FirebaseUser? = null

    var member: Member? = null
    var receive_name: String? = null
    var fetchno: String? = null
    var pushKey: String? = null
    var TYPE:String?=null
    var fetchname:kotlin.String? = null
    var id:kotlin.String? = null
    var getIdentity: String? = null
    var keypush: String? = null
    var mx: String? = null
    var individualsKey: String? = null
    var time: String? = null
    var DeviceToken: String? = null
    var task: String? = null
    var category: String? = null
    var eventdonationstorer: Int? = 0
    var checkloc: String? = null
    var createdatanode: String? = null
    var show:String?="true"
    var eventpreference:String?=null
    var database: FirebaseDatabase? = null
    var ref: DatabaseReference? = null
    private val PICK_IMAGE:Int = 2
    var value:Int = 0
    var valuesforbadges:Int = 0
    var levelincrease:String?=null
    var pushkey: String? = null
    var badgeopen: String? = null
    var imageUri: Uri? = null
    private val storage: FirebaseStorage? = null
    private val storageReference: StorageReference? = null

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
///////////////////////////////------------------------LEVEL-------------------------------------////////////////////////////////////////////////////////////////////////////////////////////////////////////
        auth = FirebaseAuth.getInstance()
        checkuser = auth!!.currentUser


        val window: Window = this@MainActivity3.getWindow()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this@MainActivity3, R.color.statusbar)
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = ContextCompat.getColor(this@MainActivity3, R.color.statusbar)
        }

        val f1 = intent
        getIdentity = f1.getStringExtra("sendingIdentity")

        fetchno = f1.getStringExtra("key1")
        fetchname = f1.getStringExtra("key2")
        id = f1.getStringExtra("key3")
        category = f1.getStringExtra("category")
        checkloc = f1.getStringExtra("checkloc")
        pushKey = f1.getStringExtra("sendingpushkey")
        mx = f1.getStringExtra("sendingmax")
        task = f1.getStringExtra("sendingtask")
        individualsKey = f1.getStringExtra("sendingindividualkey")
        time = f1.getStringExtra("sendingtime")
        DeviceToken = f1.getStringExtra("sendingdevicetoken")
        TYPE=f1.getStringExtra("sendingthetypeofTYPE")


        ref = FirebaseDatabase.getInstance().reference.child("DonorsData")
       // pushKey = ref!!.push().key
        if(TYPE.equals("DONATION")) {

            getSharedPreferences("showlevelanimationuserhasdonated", MODE_PRIVATE).edit()
                    .putString("userhasdonated", "true").apply()

            getSharedPreferences("contolling_Animation_on_recreate", Context.MODE_PRIVATE).edit()
                    .putString("Animation_on_create", "true").apply()


            createdatanode = getSharedPreferences("checkingdatecurrent", MODE_PRIVATE)
                    .getString("currentdate", "Null")

            eventpreference = getSharedPreferences("eventdonation", MODE_PRIVATE)
                    .getString("eventgive", "0")

            if (createdatanode.equals("true")) {

                eventdonationstorer = eventpreference!!.toInt() + 1;
                getSharedPreferences("eventdonation", MODE_PRIVATE).edit()
                        .putString("eventgive", eventdonationstorer.toString()).apply()

                ref!!.child(checkuser?.uid!!).child("EventDonation").child(pushKey!!).setValue(pushKey)
            }
        }
        if(TYPE.equals("HELP")){
            findViewById<TextView>(R.id.textView3).setText("REQUESTED HELP SUCCESSFULLY!!!")
            findViewById<TextView>(R.id.textView52).setVisibility(View.GONE)
            findViewById<TextView>(R.id.textView49).setVisibility(View.GONE)
        }
        member = Member()

        locate.setText(checkloc)
        namenaam.setText(fetchname)
        num.setText(fetchno)
       /* FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
        val title=TYPE
        val message= category + " ( " + checkloc!!.trim() + " ). " + "Click To Respond"
        if (title != null) {
            if(title.isNotEmpty() && message.isNotEmpty()){
                PushNotification(
                        NotificationData(title, message),
                        TOPIC
                ).also {
                    sendNotification(it)
                }


            }
        }*/
        homejaa.setOnClickListener{

            val foodkt=Intent(this, MainActivity2::class.java)
            foodkt.putExtra("key1", fetchno)
            foodkt.putExtra("key2", fetchname)
            foodkt.putExtra("key3", id)
            foodkt.putExtra("animatingXP", show)//
            foodkt.putExtra("animatinglevel", levelincrease)
            foodkt.putExtra("animatingbadge", valuesforbadges.toString())
            foodkt.putExtra("sendingIdentity", getIdentity)
            foodkt.putExtra("sendingTYPE", TYPE)//
            startActivity(foodkt)

            finish()

        }


    }


    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance.api.postNotification(notification)
            if(response.isSuccessful) {
                Log.d(TAG, "Response: ${Gson().toJson(response)}")
            } else {
                Log.e(TAG, response.errorBody().toString())
            }
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
    }
    override fun onBackPressed() {

            val gotohome = Intent(this, MainActivity2::class.java)
            startActivity(gotohome)
            super.onBackPressed()
    }
}