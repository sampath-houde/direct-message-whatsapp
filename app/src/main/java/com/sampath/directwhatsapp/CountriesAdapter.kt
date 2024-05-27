package com.codecat.directwhatsapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codecat.directwhatsapp.databinding.CountryListItemBinding
import com.l4digital.fastscroll.FastScroller

/**Extends from [RecyclerView.Adapter] and is used to display a list of countries the user can select from.
 *
 * This is required because WhatsApp API requires ISD Code of countries.*/
class CountriesAdapter(private val clickListener: (Int) -> Unit)
    : RecyclerView.Adapter<CountriesAdapter.CountryHolder>(), FastScroller.SectionIndexer {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = CountryListItemBinding.inflate(inflater, parent, false)
        return CountryHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {

        val country = countries[position]
        val name = "${country.name}, ${country.isoCode}"
        val code = "+${country.isdCode}"

        holder.flag.setImageResource(country.flagResource)
        holder.countryName.text = name
        holder.isdCode.text = code
    }

    override fun getItemCount(): Int = countries.size

    //The fast scroller shows ISO Code, like IN, USA, UK, etc.
    override fun getSectionText(position: Int): CharSequence = countries[position].isoCode

    /**Extends from [RecyclerView.ViewHolder] and is a ViewHolder for the layout [R.layout.country_list_item]
     *
     * @param binding View binding for the inflated layout
     * @param clickListener Kotlin allows us to pass functions without the need for interfaces. This function will be invoked upon click.
     * */
    class CountryHolder(binding: CountryListItemBinding, clickListener: (Int) -> Unit)
        : RecyclerView.ViewHolder(binding.root) {

        val countryName: TextView = binding.countryName
        val isdCode: TextView = binding.countryCode
        val flag: ImageView = binding.flag

        init {
            //set the item click listener here. This reduces overhead significantly.
            binding.root.setOnClickListener { clickListener.invoke(adapterPosition) }
        }
    }
}