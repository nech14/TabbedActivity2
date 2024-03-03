package com.example.tabbedactivity.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tabbedactivity.R
import com.example.tabbedactivity.WeatherData
import com.example.tabbedactivity.databinding.FragmentTabbedBinding
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private var _binding: FragmentTabbedBinding? = null


    private lateinit var icon: ImageView
    private lateinit var descriptionWeather: TextView

    private lateinit var temp: TextView
    private lateinit var pressure: TextView
    private lateinit var humidity: TextView

    private lateinit var speed: TextView
    private lateinit var deg: TextView

    private val API_KEY = "afd3f31c472731bed0074b6a14cbf7f1"
    private var weatherURL: String = "https://api.openweathermap.org/data/2.5/weather?q=Voronezh&appid=$API_KEY&units=metric"


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTabbedBinding.inflate(inflater, container, false)
        val root = binding.root

        val textView: TextView = binding.descriptionWeather1
        pageViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
            weatherURL = "https://api.openweathermap.org/data/2.5/weather?q=${it}&appid=$API_KEY&units=metric"

            updateWeatheer()
        })



        return root
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        icon = view.findViewById(R.id.icon)
        descriptionWeather = view.findViewById(R.id.descriptionWeather1)

        temp = view.findViewById(R.id.tempData)
        pressure = view.findViewById(R.id.pressureData)
        humidity = view.findViewById(R.id.humidityData)

        speed = view.findViewById(R.id.speedData)
        deg = view.findViewById(R.id.degData)


    }

    private suspend fun loadWeather(): WeatherData? {

        return try {
            val stream = URL(weatherURL).openStream()

            // JSON отдаётся одной строкой,
            val data = stream.bufferedReader().use { it.readText() }

            val gson = Gson()

            gson.fromJson(data, WeatherData::class.java)

        } catch (e: IOException) {
            // Обработка ошибок при работе с сетью
            e.printStackTrace()
            null
        }


    }

    private fun updateWeatheer(){
        var weatherData: WeatherData?
        CoroutineScope(Dispatchers.IO).launch {
            weatherData = loadWeather()
            withContext(Dispatchers.Main) {
                if (weatherData != null) {
                    val iconWeatherURL = "https://openweathermap.org/img/wn/${weatherData!!.weather[0].icon}@4x.png"
                    Picasso.get().load(iconWeatherURL).into(icon)

                    descriptionWeather.text = weatherData!!.weather[0].description

                    temp.text = weatherData!!.main.temp.toString()
                    pressure.text = weatherData!!.main.pressure.toString()
                    humidity.text = weatherData!!.main.humidity.toString()

                    speed.text = weatherData!!.wind.speed.toString()
                    deg.text = weatherData!!.wind.deg.toString()

                }
            }
        }
    }


}