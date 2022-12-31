package com.rifqiananda.pokemonapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rifqiananda.pokemonapp.R
import com.rifqiananda.pokemonapp.adapter.MyPokemonAdapter
import com.rifqiananda.pokemonapp.data.local.PokemonUser
import com.rifqiananda.pokemonapp.databinding.FragmentMyPokemonBinding
import com.rifqiananda.pokemonapp.ui.view.CaughtPokemonViewModel

class FragmentMyPokemon : Fragment(R.layout.fragment_my_pokemon) {

    private var _binding : FragmentMyPokemonBinding? = null
    private val binding get() = _binding!!

    private lateinit var dataAdapter: MyPokemonAdapter
    private lateinit var viewModel : CaughtPokemonViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMyPokemonBinding.bind(view)

        dataAdapter = MyPokemonAdapter(requireContext())
        dataAdapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(requireActivity())[CaughtPokemonViewModel::class.java]
        viewModel.getPokemon()?.observe(viewLifecycleOwner){
            if (it != null){
                val list = mapList(it)
                if (list.isNotEmpty()){
                    dataAdapter.setData(list)
                    isDataNull(false)
                } else {
                    isDataNull(true)
                }
            }

        }

        binding.apply {
            rvMyPokemon.setHasFixedSize(true)
            rvMyPokemon.layoutManager = LinearLayoutManager(context)
            rvMyPokemon.adapter = dataAdapter
        }

        dataAdapter.setOnItemClick(object : MyPokemonAdapter.OnAdapterListener{
            override fun onDelete(data: PokemonUser) {
                val alertDialog = AlertDialog.Builder(requireContext())
                alertDialog.apply {
                    setTitle("Release Pokemon")
                    setMessage("Are you sure you want to release ${data.name}?")
                    setNegativeButton("Cancel") { dialogInterface, i ->
                        dialogInterface.dismiss()
                    }
                    setPositiveButton("Release") { dialogInterface, i ->
                        dialogInterface.dismiss()
                        viewModel.releasePokemon(data.id)
                    }
                }.show()
            }
        })
    }

    private fun mapList(pokemon: List<PokemonUser>): ArrayList<PokemonUser>{
        val listPokemon = ArrayList<PokemonUser>()
        for (data in pokemon){
            val userMapped = PokemonUser(
                data.id,
                data.name,
                data.image
            )
            listPokemon.add(userMapped)
        }
        return listPokemon
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun isDataNull(state: Boolean){
        if (state) {
            binding.dataNotFound.visibility = View.VISIBLE
        } else {
            binding.dataNotFound.visibility = View.GONE
        }
    }

}