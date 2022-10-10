package com.example.prodan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.prodan.databinding.FragmentFilterBinding
import com.example.prodan.databinding.FragmentHomeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FilterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FilterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Se guardarán los valores a filtrar en un bundle (Para mas filtros se deben agregar más variables)
        val bundle = Bundle()
        var especie = ""
        var sexo = 0
        binding.ninguno.isChecked = true

        // Los filtros se aplican cuando se presiona el boton aplicar
        binding.aplicar.setOnClickListener {
            var id: Int = binding.especie.checkedRadioButtonId

            // Ya funciona
            // Radio group de la especie
            if(id == binding.perro.id) especie = "perro"
            else if (id == binding.gato.id) especie = "gato"
            else if (id == binding.otro.id) especie = "otro"
            else especie = ""

            // No funciona
            // Estamos guardando los id de las imágenes y no me los se
            if (binding.switch3.isChecked) sexo = 1
            else if (binding.switch2.isChecked) sexo = 2
            else sexo = 0

            // Se guardan todos los filtros en el bundle
            bundle.putString("especie",especie)
            bundle.putInt("sexo",sexo)

            // Se navega a la pantalla pricipal con el bundle
            Navigation.findNavController(view).navigate(R.id.action_filterFragment2_to_homeFragment,bundle)
        }

        binding.switch3.setOnClickListener {

            binding.switch2.isChecked = !binding.switch3.isChecked


        }

        binding.switch2.setOnClickListener {

            binding.switch3.isChecked = !binding.switch2.isChecked

        }

        binding.switch1.setOnClickListener {

            if (!binding.switch1.isChecked) {
                binding.switch3.isChecked = false
                binding.switch3.isEnabled = false
                binding.switch2.isChecked = false
                binding.switch2.isEnabled = false
            }
            else{
                binding.switch3.isEnabled = true
                binding.switch2.isEnabled = true
                binding.switch3.isChecked = true
            }
        }

        binding.cerrar.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_filterFragment2_to_homeFragment)
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FilterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FilterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}