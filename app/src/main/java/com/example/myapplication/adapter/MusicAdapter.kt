package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.MusicProperty
import com.example.myapplication.model.MusicResponse
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList


class MusicAdapter(var musicList: ArrayList<MusicProperty?>, var onMusicClickListener: OnMusicClickListener):
    RecyclerView.Adapter<MusicAdapter.ViewHolder>(), Filterable {
    var musicFilterList=ArrayList<MusicProperty>(musicList)
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val trackName: TextView =itemView.findViewById<TextView>(R.id.tvTrackName)
        var artistName: TextView =itemView.findViewById<TextView>(R.id.tvArtistName)
        val music: ImageView =itemView.findViewById<ImageView>(R.id.ivMusic)
        var wrapperType: TextView =itemView.findViewById<TextView>(R.id.tvWrapperType)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_music,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.artistName.text="${musicList[position]?.artistName}"
        holder.trackName.text="${musicList[position]?.trackName}"
        holder.wrapperType.text="${musicList[position]?.wrapperType}"
        Picasso.get().load(musicList[position]?.artworkUrl100).into(holder.music)
        ViewCompat.setTransitionName(holder.music,"${musicList[position]?.artistName}${musicList[position]?.trackName}" +
                "${musicList[position]?.releaseDate}${musicList[position]?.country}")
        holder.itemView.setOnClickListener {
            onMusicClickListener.onMusicClickListener(musicList[position],holder.music)
        }

    }
    override fun getItemCount(): Int =musicList.size
    override fun getFilter(): Filter =object: Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val filteredList = ArrayList<MusicProperty>()
            if (p0 == null || p0.isEmpty()) {
                filteredList.addAll(musicFilterList)
            } else {
                val filteredPattern = p0.toString().toLowerCase(Locale.ROOT).trim()
                for (result: MusicProperty in filteredList) {
                    val wrapperType = "${result.wrapperType}"
                    if ( wrapperType.toLowerCase(Locale.ROOT).contains(filteredPattern)){
                        filteredList.add(result)
                    }
                }

            }
            return FilterResults().apply { values=filteredList }
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            musicList.clear()
            musicList.addAll(p1?.values as ArrayList<MusicProperty>)
            notifyDataSetChanged()
        }

    }
    interface OnMusicClickListener{
        fun onMusicClickListener(results: MusicProperty?,imageView: ImageView)
    }
    var musics = mutableListOf<MusicProperty>()
    @JvmName("setMusicList1")
    fun setMusicList(musics: ArrayList<MusicProperty>) {
        this.musics = musics.toMutableList()
        notifyDataSetChanged()
    }

}