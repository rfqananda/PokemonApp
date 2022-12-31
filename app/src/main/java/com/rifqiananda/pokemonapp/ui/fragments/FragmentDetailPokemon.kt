package com.rifqiananda.pokemonapp.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rifqiananda.pokemonapp.adapter.MovesAdapter
import com.rifqiananda.pokemonapp.databinding.FragmentDetailBinding
import com.rifqiananda.pokemonapp.ui.view.MovesViewModel
import com.rifqiananda.pokemonapp.util.LoadingDialog
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random


class FragmentDetailPokemon : Fragment(com.rifqiananda.pokemonapp.R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private var pokemonName: String? = ""
    private var pokemonImage: String? = ""

    private lateinit var viewModel: MovesViewModel
    private lateinit var dataAdapter: MovesAdapter

    private lateinit var loading: LoadingDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)

        loading = LoadingDialog(requireActivity())

        val navBar = requireActivity().findViewById<BottomNavigationView>(com.rifqiananda.pokemonapp.R.id.bottom_navigation)
        val toolBar = requireActivity().findViewById<View>(com.rifqiananda.pokemonapp.R.id.tool_bar)

        navBar.visibility = View.GONE
        toolBar.visibility = View.GONE

        dataAdapter = MovesAdapter()

        pokemonName = arguments?.getString("name")
        pokemonImage = arguments?.getString("image")

        Glide.with(requireActivity())
            .load(pokemonImage)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .circleCrop()
            .into(iv_detail_pokemon)

        binding.apply {
            viewModel = ViewModelProvider(requireActivity())[MovesViewModel::class.java]
            viewModel.setMovesPokemon(pokemonName!!)
            viewModel.getTypePokemon().observe(viewLifecycleOwner) { pokeType ->
                for (type in pokeType) {
                    val typeX = type.type
                    tvType.text = typeX.name
                }
            }

            viewModel.getMovesPokemon().observe(viewLifecycleOwner) { pokeMove ->
                if (pokeMove != null) {
                    dataAdapter.setData(pokeMove)
                } else {
                    Log.e("Data Empty", pokeMove.toString())
                }
            }
            rvDetail.layoutManager = LinearLayoutManager(context)
            rvDetail.setHasFixedSize(true)
            rvDetail.adapter = dataAdapter
            tvDetailName.text = pokemonName
            btnTangkap.setOnClickListener {
                Log.e("Nama Detail", pokemonName.toString())
            }

            var caught = false
            btnTangkap.setOnClickListener {
                loading.startLoading()
                caught = !caught
                Handler(Looper.getMainLooper()).postDelayed({
                    loading.isDismiss()
                    val random = Random
                    val chance = random.nextInt(2)
                    if (chance == 0){
                        viewModel.catchPokemon(0, pokemonName.toString(), pokemonImage.toString())
                        Toast.makeText(requireActivity(),"Pokemon caught!",Toast.LENGTH_SHORT).show()
                        isPokemonBeenCaught(true)
                    } else{
                        Toast.makeText(requireActivity(),"pokemon not caught :(",Toast.LENGTH_SHORT).show()
                        isPokemonBeenCaught(false)
                    }
                }, 2300)
            }

            CoroutineScope(Dispatchers.IO).launch {
                val caughtPokemon = viewModel.checkPokemon(pokemonName.toString())
                withContext(Dispatchers.Main) {
                    if (caughtPokemon != null) {
                        caught = true
                        isPokemonBeenCaught(caught)
                    } else {
                        caught = false
                        isPokemonBeenCaught(caught)
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun isPokemonBeenCaught(caught: Boolean) {
        if (caught) {
            binding.apply {
                btnTangkap.visibility = View.INVISIBLE
                tvBeenCaught.visibility = View.VISIBLE
            }
        } else {
            binding.apply {
                btnTangkap.visibility = View.VISIBLE
                tvBeenCaught.visibility = View.INVISIBLE
            }
        }
    }

}