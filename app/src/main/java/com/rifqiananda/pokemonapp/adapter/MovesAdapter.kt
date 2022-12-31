package com.rifqiananda.pokemonapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rifqiananda.pokemonapp.data.model.Move
import com.rifqiananda.pokemonapp.databinding.AdapterMoveBinding

class MovesAdapter: RecyclerView.Adapter<MovesAdapter.ViewHolder>() {

    private val listData = ArrayList<Move>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Move>) {
        listData.clear()
        listData.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: AdapterMoveBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return MovesAdapter.ViewHolder(
            AdapterMoveBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemonMoves = listData[position]
        holder.binding.apply {
            tvNameMove.text = pokemonMoves.move.name
        }
    }

    override fun getItemCount(): Int = listData.size

}