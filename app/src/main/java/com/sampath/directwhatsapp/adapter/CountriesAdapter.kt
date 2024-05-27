package com.sampath.directwhatsapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.sampath.directwhatsapp.model.Country
import com.codecat.directwhatsapp.countries
import com.codecat.directwhatsapp.databinding.CountryListItemBinding
import com.codecat.directwhatsapp.diffUtil


/**Extends from [RecyclerView.Adapter] and is used to display a list of countries the user can select from.
 *
 * This is required because WhatsApp API requires ISD Code of countries.*/
class CountriesAdapter(private val clickListener: (String) -> Unit)
    : RecyclerView.Adapter<CountriesAdapter.CountryHolder>() {

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    init {
        asyncListDiffer.submitList(countries)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = CountryListItemBinding.inflate(inflater, parent, false)
        return CountryHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {
        holder.bind(asyncListDiffer.currentList[position])
    }

    open fun onFilter(list: List<Country>) {
        asyncListDiffer.submitList(list)
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size
    /**Extends from [RecyclerView.ViewHolder] and is a ViewHolder for the layout [R.layout.country_list_item]
     *
     * @param binding View binding for the inflated layout
     * @param clickListener Kotlin allows us to pass functions without the need for interfaces. This function will be invoked upon click.
     * */
    inner class CountryHolder(private val binding: CountryListItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(country: Country) {
            binding.root.setOnClickListener { clickListener.invoke(country.isoCode) }
            binding.flag.setImageResource(country.flagResource)
            binding.countryName.text = country.name
            binding.countryCode.text = "+${country.isdCode}"

        }
    }


}