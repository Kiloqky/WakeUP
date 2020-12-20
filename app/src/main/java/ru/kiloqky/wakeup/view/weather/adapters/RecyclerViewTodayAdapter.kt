package ru.kiloqky.wakeup.view.weather.adapters

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.kiloqky.wakeup.R
import ru.kiloqky.wakeup.rest.retrofit.openWeatherMap.forecast.entitiesOpenWeather.WeatherList
import ru.kiloqky.wakeup.rest.retrofit.openWeatherMap.onecall.entities.Hourly
import ru.kiloqky.wakeup.rest.retrofit.openWeatherMap.onecall.entities.WeatherMain
import java.text.SimpleDateFormat
import java.util.*

class RecyclerViewTodayAdapter(private val data: Array<Hourly>) :
    RecyclerView.Adapter<RecyclerViewTodayAdapter.ViewHolder>() {
    private val dateFormat: SimpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val dateFormatWithDay: SimpleDateFormat =
        SimpleDateFormat("HH:mm\nEEEE", Locale.getDefault())

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val weatherFont: Typeface =
            Typeface.createFromAsset(itemView.context.assets, "fonts/weather.ttf")
        val justFont: Typeface =
            Typeface.createFromAsset(itemView.context.assets, "fonts/regular.otf")
        val dateTV: TextView = itemView.findViewById(R.id.time)
        val tempTV: TextView = itemView.findViewById(R.id.temp_today)
        val iconTV: TextView = itemView.findViewById(R.id.icon_today)
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_weather_today, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 0) {
            holder.dateTV.typeface = holder.weatherFont
            holder.dateTV.text = ("\uF014" + String.format(
                Locale.getDefault(),
                "%1.0f",
                data[position].wind
            ) + " m/s")
            holder.iconTV.typeface = holder.weatherFont
            holder.iconTV.textSize = 18.0f
            holder.iconTV.text = ("\uF079" + data[position].pressure)
            holder.tempTV.typeface = holder.weatherFont
            holder.tempTV.text = ("\uF07A" + data[position].humidity + "%")

        } else {
            val truePosition = position - 1
            var date = (dateFormat.format(data[truePosition].dt * 1000)).toString()
            if (date == "00:00") {
                date = (dateFormatWithDay.format(data[truePosition].dt * 1000)).toString()
            }
            holder.dateTV.typeface = holder.justFont
            holder.dateTV.text = date
            holder.iconTV.typeface = holder.weatherFont
            holder.iconTV.text = getIcon(data[truePosition].weather[0].icon)
            val temp = String.format(
                Locale.getDefault(),
                "%.0f",
                data[truePosition].temp - 272
            ) + "\u2103"
            holder.tempTV.typeface = holder.justFont
            holder.tempTV.text = temp
        }
    }

    override fun getItemCount(): Int {
        return data.size + 1
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