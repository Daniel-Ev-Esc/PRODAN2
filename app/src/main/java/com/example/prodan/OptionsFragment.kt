package com.example.prodan

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.prodan.databinding.FragmentOptionsBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OptionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OptionsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentOptionsBinding? = null
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
        // Inflate the layout for this fragment
        _binding = FragmentOptionsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonFB.setOnClickListener {
            openFacebook()
        }
        binding.buttonIG.setOnClickListener {
            openInstagram()
        }

    }

    private fun openFacebook() {
        val facebookId = "fb://page/267399839949060"
        val urlPage = "https://www.facebook.com/ProdanAC"
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(facebookId)))
        } catch (e: Exception) {
            Log.e("LOGLOG", "Application not intalled.")
            //Open url web page.
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(urlPage)))
        }
    }
    private fun openInstagram() {
        val uri = Uri.parse("https://www.instagram.com/prodanmty/")
        val i = Intent(Intent.ACTION_VIEW, uri)
        i.setPackage("com.instagram.android")

        try {
            startActivity(i)
        } catch (e: ActivityNotFoundException) {
            Log.e("LOGLOG", "Application not intalled.")
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/prodanmty/")
                )
            )
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OptionsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OptionsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}