package com.rifqiananda.pokemonapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rifqiananda.pokemonapp.databinding.AdapterPokemonBinding

class PokemonAdapter(private val context: Context) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    private val listData = ArrayList<com.rifqiananda.pokemonapp.data.model.Result>()

    private var onItemClick: OnAdapterListener? = null

    fun setOnItemClick(onItemClick: OnAdapterListener){
        this.onItemClick = onItemClick
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<com.rifqiananda.pokemonapp.data.model.Result>) {
        listData.clear()
        listData.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: AdapterPokemonBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AdapterPokemonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemonData = listData[position]
        holder.binding.apply {
            val number = if (pokemonData.url.endsWith("/")){
                pokemonData.url.dropLast(1).takeLastWhile { it.isDigit()}
            }else{
                pokemonData.url.takeLastWhile { it.isDigit()}
            }

            val url ="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"

            Glide.with(context)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .circleCrop()
                .into(ivPokemon)

            tvNamePokemon.text = pokemonData.name

            btnDetail.setOnClickListener {
                onItemClick?.onClick(pokemonData, pokemonData.name, url)

            }
        }
    }

    override fun getItemCount(): Int = listData.size

    interface OnAdapterListener {
        fun onClick(data: com.rifqiananda.pokemonapp.data.model.Result, name: String, url:String)
    }
}

