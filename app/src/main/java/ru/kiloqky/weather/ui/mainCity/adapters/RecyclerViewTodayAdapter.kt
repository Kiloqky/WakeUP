package ru.kiloqky.weather.ui.mainCity.adapters

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.kiloqky.weather.R
import ru.kiloqky.weather.rest.openWeatherMap.entitiesOpenWeather.WeatherList
import java.text.SimpleDateFormat
import java.util.*

class RecyclerViewTodayAdapter(private val data: ArrayList<WeatherList>) :
    RecyclerView.Adapter<RecyclerViewTodayAdapter.ViewHolder>() {
    private val dateFormat: SimpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val dateFormatWithDay: SimpleDateFormat =
        SimpleDateFormat("HH:mm\nEEEE", Locale.getDefault())

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTV: TextView = itemView.findViewById(R.id.time)
        val tempTV: TextView = itemView.findViewById(R.id.temp_today)
        val iconTV: TextView = itemView.findViewById(R.id.icon_today)

        init {
            val weatherFont: Typeface =
                Typeface.createFromAsset(itemView.context.assets, "fonts/weather.ttf")
            iconTV.typeface = weatherFont
            dateTV.typeface = weatherFont
            tempTV.typeface = weatherFont
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_weather_today, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 0) {
            holder.dateTV.text = ("\uF014" + String.format(
                Locale.getDefault(),
                "%1.0f",
                data[position].windRestModel.speed
            ) + " m/s")
            holder.iconTV.textSize = 14.0f
            holder.iconTV.text = (
                    "\uF079" + String.format(
                        Locale.getDefault(),
                        "%1.0f",
                        data[position].mainRestModel.pressure
                    ))
            holder.tempTV.text = (
                    "\uF07A" + String.format(
                        Locale.getDefault(),
                        "%1.0f",
                        data[position].mainRestModel.humidity
                    ) + "%"
                    )
        } else {
            val truePosition = position - 1
            var date = (dateFormat.format(data[truePosition].dt * 1000)).toString()
            if (date.equals("00:00")) {
                date = (dateFormatWithDay.format(data[truePosition].dt * 1000)).toString()
            }
            holder.dateTV.text = date
            holder.iconTV.text = getIcon(data[truePosition].weatherRestModel[0].icon)
            val temp = String.format(
                Locale.getDefault(),
                "%.0f",
                data[truePosition].mainRestModel.temp - 272
            ) + "\u2103"
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