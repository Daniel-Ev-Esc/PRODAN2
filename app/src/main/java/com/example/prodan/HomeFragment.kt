package com.example.prodan

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.example.prodan.data.petList
import com.example.prodan.data.Pet
import com.example.prodan.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    // Aquí van las variables de los filtros
    private var especie: String? = null
    private var sexo: Int? = null
    private var raza: String? = null
    private var privacyPolicy = false

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var datas: Pet? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        loadData()
        if(privacyPolicy == false){
            val mBuilder = AlertDialog.Builder(activity)
                .setTitle("AVISO DE PRIVACIDAD")
                .setMessage("Prodefensa Animal A.C., con domicilio en Plutarco Elías Calles 307, Tampiquito, 66240 San Pedro Garza García, N.L., es el responsable del uso y protección de sus datos personales, y al respecto le informamos lo siguiente:\n" +
                        "\n" +
                        "¿Para qué fines utilizaremos sus datos personales?\n\n" +
                        "Los datos personales que recabamos de usted, los utilizaremos para las siguientes finalidades que son necesarias para el servicio que solicita:\n" +
                        "\n" +
                        "Respuesta a mensajes del formulario de contacto\n" +
                        "\n" +
                        "¿Dónde puedo consultar el aviso de privacidad integral?\n\n" +
                        "Para conocer mayor información sobre los términos y condiciones en que serán tratados sus datos personales, como los terceros con quienes compartimos su información personal y la forma en que podrá ejercer sus derechos ARCO, puede consultar el aviso de privacidad integral con una petición vía correo electrónico:\n" +
                        "\n" +
                        "informes@prodan.org.mx\n\n" +
                        "Última actualización de este aviso de privacidad: 29/09/2022\n")
                .setPositiveButton("ACEPTAR"){dialog, which ->
                    saveData()
                }
                .setCancelable(false)

            val mAlertDialog = mBuilder.create()
            mAlertDialog.show()
        }

        super.onCreate(savedInstanceState)

        // Aquí tambien van las variables de los filtros
        arguments?.let {
            especie = it.getString("especie")
            sexo = it.getInt("sexo")
            raza = it.getString("raza")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showPets()
        binding.imageViewOptions.setOnClickListener {
            //Navegación por ID
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_optionsFragment)
        }

        // NAvegación a los filtros
        binding.floatingActionButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_filterFragment2)
        }

        // Variables no mutables de los filtros
        var _petList = datas
        var _especie = ""
        var _sexo = 0
        var _raza = ""

        especie?.let { _especie = it }
        sexo?.let { _sexo = it }
        raza?.let { _raza = it }

        // Esto en necesario para que la primera vez que se abre la app no se apliquen filtros
        // Esto solo se activará si hay algún filtro, en caso de que se agrequen mas hay que ponerlos en las variables
        if (_especie != "" || _sexo != 0 || _raza != ""){

            _petList = filter(_especie,_sexo, _raza, datas) as

            adapter.filterList(_petList)
        }

    }

    fun showPets() {
        val retrofit = RetroFit.getInstance().create(ApiPets:: class.java)

        retrofit.getAllPets().enqueue(object : Callback<Pet> {
            override fun onResponse(
                call: Call<Pet>?, response: Response<Pet>?
            ) {
                datas = response?.body()
                Log.i("Taggg", datas.toString())

                // Creando instancia de adaptador
                val adapter = datas?.let {
                    adapter(requireActivity(), it.data){
                        val bundle = Bundle()
                        bundle.putParcelable("pets",it)
                        findNavController().navigate(R.id.action_homeFragment_to_detailsFragment,bundle)
                    }
                }


                binding.rvpets.adapter = adapter
                Log.i("tah", adapter.toString())
                binding.rvpets.layoutManager =
                    GridLayoutManager(requireActivity(),
                        2, RecyclerView.VERTICAL, false)

            }

            override fun onFailure(call: Call<Pet>?, t: Throwable?) {
                t?.printStackTrace()
            }
        })

    }

    private fun filter(especie:String, sexo:Int, raza:String, lista: Pet?): List<Pet> {

        // Se crea una instancia de la lista inicial
        var listafiltrada  = lista

        // Por cada objeto si no cumple con las condiciones de los filtros se va quitando de la lista
        for (item in lista){

            // Especie
            if (especie == "Otro"){
                if (item.data[0].attributes.type == "Perro" || item.data[0].attributes.type == "Gato"){
                    listafiltrada = listafiltrada.minus(item)
                }
            }
            else if (especie != "" && item.data[0].attributes.type != especie){
                listafiltrada = listafiltrada.minus(item)
            }

            // Sexo
            if (sexo != 0 && (!item.data[0].attributes.male || item.data[0].attributes.male)){
                listafiltrada = listafiltrada.minus(item)
            }

            if (raza != "" && item.data[0].attributes.type != raza){
                listafiltrada = listafiltrada.minus(item)
            }

        }
        return listafiltrada
    }

    private fun saveData() {

        val acceptedPolicy = true

        val sharedPreferences = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply{
            putBoolean("BOOL_KEY", acceptedPolicy)
        }.apply()
    }

    private fun loadData() {
        val sharedPreferences = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val savedBool = sharedPreferences.getBoolean("BOOL_KEY", null == true)

        privacyPolicy = savedBool

    }

}