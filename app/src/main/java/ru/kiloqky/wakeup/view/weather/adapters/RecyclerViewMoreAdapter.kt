package ru.kiloqky.wakeup.view.weather.adapters

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.kiloqky.wakeup.R
import ru.kiloqky.wakeup.rest.retrofit.openWeatherMap.onecall.entities.Daily
import ru.kiloqky.wakeup.rest.retrofit.openWeatherMap.onecall.entities.WeatherMain
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RecyclerViewMoreAdapter(
    private val data: Array<Daily>
) :
    RecyclerView.Adapter<RecyclerViewMoreAdapter.ViewHolder>() {

    private val dateFormat: SimpleDateFormat = SimpleDateFormat("EEEE", Locale.getDefault())

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val dateTV: TextView = itemView.findViewById(R.id.time)
        val tempTV: TextView = itemView.findViewById(R.id.temp_today)
        val iconTV: TextView = itemView.findViewById(R.id.icon_today)

        init {
            val weatherFont: Typeface =
                Typeface.createFromAsset(itemView.context.assets, "fonts/weather.ttf")
            iconTV.typeface = weatherFont
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_list_weather_more, parent, false
                )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.iconTV.text = getIcon(data[position].weather[0].icon)
        holder.dateTV.text = (dateFormat.format(data[position].dt * 1000)).toString()
        val temp = String.format(
            Locale.getDefault(),
            "%.0f",
            data[position].temp.day - 272
        ) + "\u2103"
        holder.tempTV.text = temp
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private fun getIcon(icon: String): String {
        when (icon) {
            "01d" -> return "\uF06E"
            "01n" -> return "\uF0A3"
            "02d" -> return "\uF002"
            "02n" -> return "\uF031"
            "03d" -> return "\uF041"
            "03n" -> return "\uF041"
            "04d" -> return "\uF013"
            "04n" -> return "\uF013"
            "09d" -> return "\uF015"
            "09n" -> return "\uF015"
            "10d" -> return "\uF009"
            "10n" -> return "\uF027"
            "11d" -> return "\uF005"
            "11n" -> return "\uF02C"
            "13d" -> return "\uF00A"
            "13n" -> return "\uF02A"
            "50d" -> return "\uF082"
            "50n" -> return "\uF082"
        }
        return "\uF075"
    }

}