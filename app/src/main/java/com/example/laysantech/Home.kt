package com.example.laysantech

/*import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)



    }
}*/

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.google.firebase.auth.FirebaseAuth
import com.smarteist.autoimageslider.SliderView


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;

import androidx.drawerlayout.widget.DrawerLayout;

import android.view.MenuItem;


import com.example.laysantech.MySingleton.Companion as MySingleton


import android.R.string.no
import org.json.JSONObject

import org.json.JSONArray







class Home : AppCompatActivity() , NewsItemClicked {

    lateinit var auth: FirebaseAuth
    private  lateinit var mAdapter: NewsListAdapter

     private  lateinit var recyclerView : RecyclerView




    var drawerLayout: DrawerLayout? = null
    var actionBarDrawerToggle: ActionBarDrawerToggle? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        recyclerView = findViewById(R.id.recyclerView)


          

        

        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser

       recyclerView.layoutManager = LinearLayoutManager(this)


        drawerLayout = findViewById(R.id.my_drawer_layout)
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout?.addDrawerListener(actionBarDrawerToggle!!)
        actionBarDrawerToggle!!.syncState()


        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mAdapter = NewsListAdapter(this)

       recyclerView.adapter = mAdapter

//        Reference
        val logout = findViewById<Button>(R.id.idLogout)

        if (currentUser == null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }



        logout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


        val imageSlider = findViewById<SliderView>(R.id.imageSlider)
        val imageList: ArrayList<String> = ArrayList()
        //imageList.add("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg")
        // imageList.add("https://images.ctfassets.net/hrltx12pl8hq/4plHDVeTkWuFMihxQnzBSb/aea2f06d675c3d710d095306e377382f/shutterstock_554314555_copy.jpg")
        // imageList.add("https://media.istockphoto.com/photos/child-hands-formig-heart-shape-picture-id951945718?k=6&m=951945718&s=612x612&w=0&h=ih-N7RytxrTfhDyvyTQCA5q5xKoJToKSYgdsJ_mHrv0=")
        //setImageInSlider(imageList, imageSlider)


        //     JSON SPLITTING


        // FOR IMAGE SLIDER

        val url = "https://omst5afyma.execute-api.ap-south-1.amazonaws.com/production/app/home"


        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET,
            url,
            null,
            Response.Listener {
                val imagesliderarray = it.getJSONArray("slider")
                val productlistarray = it.getJSONArray("featured")
                //val imagelistarray = productlistarray.getJSONArray("file")
                // val newsArray = ArrayList<News>()

                for (i in 0 until imagesliderarray.length()) {
                    val newsJsonObject = imagesliderarray.getJSONObject(i)
                    val imagurl = newsJsonObject.getString("file")
                    Log.i("IMAGE URL =", imagurl)
                    imageList.add(imagurl)

                }



                //changes

                val newsArray = ArrayList<News>()
                for (i in 0 until productlistarray.length()) {
                    val   productJsonObject = productlistarray.getJSONObject(i)
                    //val  productImageJsonObject = productJsonObject.getJSONArray("file")



                 //   Log.i("URL  =",  productImageJsonObject.getString().toString())


                    var description =   productJsonObject.getString("name")
                    var name =  productJsonObject.getString("desc")
                    name = ""

                    Log.i("DESC =",  productJsonObject.getString("desc").toString())
                    Log.i("NAME =",  productJsonObject.getString("name").toString())
                  //  lateinit var  fileurl  : String

                    val files: JSONArray = productJsonObject.getJSONArray("file")
                    if (files != null && files.length() > 0) {
                        for (j in 0 until files.length()) {
                            val Jsonfilename = files.getJSONObject(j)
                            val fileurl = Jsonfilename.getString("location")
                            val news = News(

                                description,name,fileurl
                                //  productJsonObject.getString("desc"),
                                //   productJsonObject.getString("name"),
                                // productJsonObject.getString("location")




                            )
                            newsArray.add(news)
                            Log.i("DESC =",  productJsonObject.getString("desc").toString())
                            Log.i("NAME =",  productJsonObject.getString("name").toString())
                            Log.e("Files", "" + fileurl)
                        }

                    }



                    //val news = News(

                       // description,name,fileurl
                       //  productJsonObject.getString("desc"),
                     //   productJsonObject.getString("name"),
                        // productJsonObject.getString("location")




                   // )
                   // newsArray.add(news)
                }







              //  mAdapter.updateNews(newsArray)
                   // changes

                mAdapter.updateNews(newsArray)
                setImageInSlider(imageList, imageSlider)


            },
            Response.ErrorListener {


            }


        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)




       // fetchdata()


    }



   /* private fun fetchdata() {

        val url = "https://omst5afyma.execute-api.ap-south-1.amazonaws.com/production/app/home"



        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET,
            url,
            null,
            Response.Listener {
                val newsJsonArraypro = it.getJSONArray("category")
                val newsArray = ArrayList<News>()
                for (i in 0 until newsJsonArraypro.length()) {
                    val newsJsonObject = newsJsonArraypro.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("description"),
                        newsJsonObject.getString("name"),
                       // newsJsonObject.getString("file"),
                        newsJsonObject.getString("file")
                    )
                    newsArray.add(news)
                }


                mAdapter.updateNews(newsArray)


            },
            Response.ErrorListener {


            }


        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }*/


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle!!.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }




    private fun setImageInSlider(images: ArrayList<String>, imageSlider: SliderView) {
        val adapter = MySliderImageAdapter()
        adapter.renewItems(images)
        imageSlider.setSliderAdapter(adapter)
        imageSlider.isAutoCycle = true
        imageSlider.startAutoCycle()
    }

    override fun onItemClicked(item: News) {
        Toast.makeText(this,"SERVICE NOT READY",Toast.LENGTH_SHORT).show()
    }


}