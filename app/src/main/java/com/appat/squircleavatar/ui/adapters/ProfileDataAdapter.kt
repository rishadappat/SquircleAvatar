package com.appat.squircleavatar.ui.adapters

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.appat.squircleavatar.databinding.ProfileItemBinding
import com.appat.squircleavatar.models.PersonsData
import com.appat.squircleavatar.ui.SquircleDrawable
import com.appat.squircleavatar.ui.activities.MainActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget

class ProfileDataAdapter(private val activity: MainActivity
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class ItemType(val item: Int)
    {
        ITEM(0)
    }

    private val persons = ArrayList<PersonsData>()


    inner class ProfileDataViewHolder(itemBinding: ProfileItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        val profileImage: AppCompatImageView = itemBinding.profileImage
        val name: TextView = itemBinding.name
        val phone: TextView = itemBinding.phone
        val email: TextView = itemBinding.email
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding = ProfileItemBinding.inflate(LayoutInflater.from(parent.context))
        return ProfileDataViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return persons.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ProfileDataViewHolder) {
            val person = persons[position]

            Glide.with(activity).asBitmap()
                .load(person.image)
                .into(object : CustomTarget<Bitmap>(){
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                    ) {
                        holder.profileImage.setImageDrawable(SquircleDrawable(resource, 2.5f))
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }

                })
            holder.name.text = person.firstname + person.lastname
            holder.phone.text = person.phone
            holder.email.text = person.email
        }
    }

    override fun getItemViewType(position: Int): Int {
        return ItemType.ITEM.item
    }

    fun setData(data: List<PersonsData>) {
        if(data.isNotEmpty()) {
            persons.clear()
            persons.addAll(data)
            notifyDataSetChanged()
        }
    }
}