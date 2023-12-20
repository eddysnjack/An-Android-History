package com.leylapps.anandroidhistory.ui.mainpage

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.widget.SearchView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.leylapps.anandroidhistory.BuildConfig
import com.leylapps.anandroidhistory.R
import com.leylapps.anandroidhistory.data.IntentServiceAPICaller
import com.leylapps.anandroidhistory.data.models.ApiRequestCapsule
import com.leylapps.anandroidhistory.data.models.ApiResponseCapsule
import com.leylapps.anandroidhistory.data.models.Headers
import com.leylapps.anandroidhistory.data.models.TmdbApiLinks
import com.leylapps.anandroidhistory.domain.ParcelablePair


class MovieListAC : AppCompatActivity() {
    private lateinit var recMovieList: RecyclerView;
    private lateinit var searchViewMovie: SearchView;
    private lateinit var broadcastReceiver: BroadcastReceiver
    private lateinit var btnTest: Button

    companion object {
        val API_SERVICE_DATA_KEY: String = this::class.java.name; // API_REQUEST_KEY=com.leylapps.anandroidhistory.ui.mainpage.MovieListAC$Companion
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recMovieList = findViewById(R.id.recListMovies);
        searchViewMovie = findViewById(R.id.svMovie)
        btnTest = findViewById(R.id.btnTest)
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                onBroadcastReceived(context, intent)
            }
        }

        btnTest.setOnClickListener {
            val intent = Intent(this@MovieListAC, IntentServiceAPICaller::class.java)
            intent.putExtra(IntentServiceAPICaller.API_SERVICE_REQUEST_KEY_FOR_PARCEL,
                ApiRequestCapsule().apply {
                    url = TmdbApiLinks.Trending.all(TmdbApiLinks.Trending.Params.TimeWindow.day)
                    httpMethod = IntentServiceAPICaller.HttpMethodTypes.GET
                    requestType = IntentServiceAPICaller.RequestTypes.RAW_TEXT_OR_JSON
                    headers = arrayListOf<ParcelablePair<String, String>>(
                        ParcelablePair(
                            Headers.Authorization.key,
                            Headers.Authorization.valueBearer.format(BuildConfig.THEMOVIEDB_API_TOKEN)
                        )
                    )
                    broadcastResultKey = API_SERVICE_DATA_KEY
                }
            )
            startService(intent)
        }
    }


    override fun onResume() {
        super.onResume()
        LocalBroadcastManager
            .getInstance(this)
            .registerReceiver(
                broadcastReceiver,
                IntentFilter(IntentServiceAPICaller.API_SERVICE_BROADCAST_RESULT_KEY)
            )
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
    }

    fun onBroadcastReceived(context: Context?, intent: Intent?) {
        if (intent?.action == IntentServiceAPICaller.API_SERVICE_BROADCAST_RESULT_KEY) {
            val response: ApiResponseCapsule? = intent.getParcelableExtra<ApiResponseCapsule>(API_SERVICE_DATA_KEY)
            if (response != null) {
                if (response.isSuccess) {
                    Log.d("EDDY", "onBroadcastReceived: response.responseBody=${response.responseBody}")
                }
            }
        }
    }
}