package com.example.laysantech

import android.R.attr.tag
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.VolleyError
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONException
import org.json.JSONObject


data class News (
    val title: String,
    val author: String,    //description
    //val url: String,
    val imageUrl: String,
)