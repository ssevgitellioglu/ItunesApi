package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Callback
import com.example.myapplication.model.MusicProperty
import com.squareup.picasso.Picasso
import java.lang.Exception

class DetailActivity : AppCompatActivity() {
    private lateinit var detailArtist: ImageView
    private lateinit var detailTrackName: TextView
    private lateinit var detailArtistName: TextView
    private lateinit var detailReleasedDate: TextView
    private lateinit var detailCountry: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportStartPostponedEnterTransition()
        detailArtist=findViewById(R.id.ivDetailArtist)
        detailArtistName=findViewById(R.id.tvDetailArtistName)
        detailReleasedDate=findViewById(R.id.tvDetailDate)
        detailTrackName=findViewById(R.id.tvDetailTrackName)
        detailCountry=findViewById(R.id.tvDetailCountry)

        val results: MusicProperty =intent.extras?.getSerializable(MainActivity.EXTRA_RESULT_ITEM) as MusicProperty
        detailCountry.text="${results.country}"
        detailTrackName.text="${results.trackName}"
        detailArtistName.text="${results.artistName}"
        detailReleasedDate.text="${results.releaseDate}"
        val imageTransaction= intent.extras!!.getString(MainActivity.EXTRA_RESULT_TRANSACTION_NAME)
        detailArtist.transitionName=imageTransaction
        Picasso.get().load(results.artworkUrl100).into(detailArtist,object: Callback {
            override fun onSuccess() {
                supportStartPostponedEnterTransition()
            }

            override fun onError(e: Exception?) {
                supportStartPostponedEnterTransition()
            }

        })

    }
}