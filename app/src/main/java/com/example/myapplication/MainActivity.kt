package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.MusicAdapter
import com.example.myapplication.api.ApiClient
import com.example.myapplication.model.MusicProperty
import com.example.myapplication.model.MusicResponse
import com.example.myapplication.viewmodel.MusicViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), MusicAdapter.OnMusicClickListener {
  private val apiClient by lazy { ApiClient.getApiClient() }
  private lateinit var adapter: MusicAdapter
  private lateinit var rvMusic: RecyclerView
  private lateinit var musicViewModel:MusicViewModel



  companion object {
    const val EXTRA_RESULT_ITEM = "extra_result_item"
    const val EXTRA_RESULT_TRANSACTION_NAME = "extra_result_transaction_name"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    rvMusic = findViewById(R.id.rvMusic)
    rvMusic.layoutManager = LinearLayoutManager(this)
    rvMusic.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    musicViewModel = ViewModelProvider(this).get(MusicViewModel::class.java)
    musicViewModel.musicList.observe(this, Observer {
      adapter.setMusicList(it)
    })
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    val searchItem = menu?.findItem(R.id.action_search)
    val searchView = searchItem?.actionView as SearchView
    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String): Boolean {
        adapter.filter.filter(query)
        return false
      }

      override fun onQueryTextChange(newText: String): Boolean {
        apiClient.searchMusic(newText).enqueue(object : Callback<MusicResponse?> {
          override fun onResponse(call: Call<MusicResponse?>, response: Response<MusicResponse?>) {
            musicViewModel.getAllMusics().observe(this@MainActivity, {
              adapter.setMusicList(it)
            })
            adapter = response.body()?.musics?.let {
              MusicAdapter(it as ArrayList<MusicProperty?>,
                  this@MainActivity)
            }!!
            rvMusic?.adapter = adapter

          }

          override fun onFailure(call: Call<MusicResponse?>, t: Throwable) {

          }
        })
        return false
      }
    })
    return true
  }

  override fun onMusicClickListener(results: MusicProperty?, imageView: ImageView) {
    val intent= Intent(this, DetailActivity::class.java)
    intent.putExtra(EXTRA_RESULT_ITEM, results)
    intent.putExtra(EXTRA_RESULT_TRANSACTION_NAME, ViewCompat.getTransitionName(imageView))
    startActivity(intent)
  }
}
