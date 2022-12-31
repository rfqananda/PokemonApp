package com.rifqiananda.pokemonapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rifqiananda.pokemonapp.data.local.PokemonUser
import com.rifqiananda.pokemonapp.databinding.AdapterMyPokemonBinding
import com.rifqiananda.pokemonapp.databinding.AdapterPokemonBinding

class MyPokemonAdapter(private val context: Context): RecyclerView.Adapter<MyPokemonAdapter.ViewHolder>() {

    private val listData = ArrayList<PokemonUser>()

    private var onItemClick: MyPokemonAdapter.OnAdapterListener? = null

    fun setOnItemClick(onItemClick: MyPokemonAdapter.OnAdapterListener){
        this.onItemClick = onItemClick
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<PokemonUser>) {
        listData.clear()
        listData.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: AdapterMyPokemonBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AdapterMyPokemonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemonData = listData[position]
        holder.binding.apply {
            Glide.with(context)
                .load(pokemonData.image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .circleCrop()
                .into(ivPokemon)

            tvName.text = pokemonData.name

            btnDelete.setOnClickListener {
                onItemClick?.onDelete(pokemonData)
            }
        }
    }

    override fun getItemCount(): Int = listData.size

    interface OnAdapterListener {
        fun onDelete(data: PokemonUser)
    }
}