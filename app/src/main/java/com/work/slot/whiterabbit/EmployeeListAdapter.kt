package com.work.slot.whiterabbit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.work.slot.whiterabbit.model.EmployeeModel
import kotlinx.android.synthetic.main.item_employee.view.*

/**
 * Adapter class.
 *
 * This class handles recyclerview item design and data.
 *
 * @param items is employee list.
 */
class EmployeeListAdapter(var items: List<EmployeeModel?>) :
    RecyclerView.Adapter<EmployeeListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /** bind function has bind the data with layout controls.
         * Set name, email and image data.
         */
        fun bind(item: EmployeeModel?) {
            item.let {
                itemView.txt_name.text = it?.name
                itemView.txt_user_name.text = it?.company?.name
                Glide.with(itemView.context).load(it?.profileImage)
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.user_icon)
                    .into(itemView.img_profile)
            }
            itemView.card.setOnClickListener {
                if (itemView.context is MainActivity) {
                    (itemView.context as MainActivity).goToDetailView(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_employee, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = this.items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /** Calling ViewHolder class bind function and passing single item from list
         */
        holder.bind(this.items.get(holder.adapterPosition))
    }

}