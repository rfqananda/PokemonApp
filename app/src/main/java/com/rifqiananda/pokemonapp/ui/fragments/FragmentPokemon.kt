package com.rifqiananda.pokemonapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rifqiananda.pokemonapp.R
import com.rifqiananda.pokemonapp.adapter.PokemonAdapter
import com.rifqiananda.pokemonapp.data.model.Result
import com.rifqiananda.pokemonapp.databinding.FragmentPokemonBinding
import com.rifqiananda.pokemonapp.ui.view.PokemonViewModel
import com.rifqiananda.pokemonapp.util.Communicator

class FragmentPokemon: Fragment(R.layout.fragment_pokemon) {

    private var _binding : FragmentPokemonBinding? = null
    private val binding get() = _binding!!

    private lateinit var dataAdapter: PokemonAdapter
    private lateinit var viewModel : PokemonViewModel

    private lateinit var comm: Communicator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPokemonBinding.bind(view)

        val toolBar = requireActivity().findViewById<View>(com.rifqiananda.pokemonapp.R.id.tool_bar)
        toolBar.visibility = View.VISIBLE
        val navBar = requireActivity().findViewById<BottomNavigationView>(com.rifqiananda.pokemonapp.R.id.bottom_navigation)
        navBar.visibility = View.VISIBLE

        comm = requireActivity() as Communicator
        dataAdapter = PokemonAdapter(requireContext())

        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())[PokemonViewModel::class.java]
        viewModel.setPokemonList()
        viewModel.getPokemonList().observe(viewLifecycleOwner){ dataPokemon ->
            if(dataPokemon != null){
                dataAdapter.setData(dataPokemon)
            } else{
                Log.e("Data Empty", dataPokemon.toString())
            }
        }

        binding.apply {
            rvPokemon.layoutManager = LinearLayoutManager(context)
            rvPokemon.setHasFixedSize(true)
            rvPokemon.adapter = dataAdapter

            dataAdapter.setOnItemClick(object: PokemonAdapter.OnAdapterListener{
                override fun onClick(data: Result, name: String, url: String) {
                    comm.passDataCom(name, url)
                    Log.e("Nama", name)
                }
            })
        }

    }
}