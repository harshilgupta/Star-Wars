package com.example.star_wars.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.star_wars.databinding.GridViewLayoutBinding
import com.example.star_wars.models.ResultDataModel
import com.example.star_wars.utils.convertTime

/**
 * Adapter Class for Recycler View.
 * @author by Harshil Gupta
 *
 * This class is used to instantiate Recycler View objects.
 *
 * @property peopleList is the List used throughout the app for the people data.
 */

class GridViewAdapter : RecyclerView.Adapter<GridViewAdapter.ViewHolder>(), Filterable {
    private var peopleList = arrayListOf<ResultDataModel>()

    /**
     *
     * @return this function is used to set or update the peopleList.
     */
    fun setUserList(peopleList: ArrayList<ResultDataModel>) {
        this.peopleList = peopleList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GridViewLayoutBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.txtName.text = peopleList[position]?.name
        holder.binding.txtGender.text = peopleList[position]?.gender
        holder.binding.txtDateCreated.text = convertTime(peopleList[position]?.created)
        holder.binding.txtDateUpdated.text = convertTime(peopleList[position]?.edited)
    }

    override fun getItemCount(): Int {
        return peopleList.size
    }

    class ViewHolder(val binding: GridViewLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }
}

