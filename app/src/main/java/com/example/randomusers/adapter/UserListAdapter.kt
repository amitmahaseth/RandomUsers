package com.example.randomusers.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.randomusers.R
import com.example.randomusers.model.Clicklistener
import com.example.randomusers.model.Result
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class UserListAdapter(
    var mContext: Context,
    var userList: ArrayList<Result>,
    var userList1: ArrayList<Result>,
    var onclickItem: Clicklistener
) :
    RecyclerView.Adapter<UserViewHolder>(), Filterable {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.user_list, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        var mItem = userList[position]
        Glide
            .with(mContext)
            .load(mItem.picture.large)
            .into(holder.userImage)

        holder.userFullName.text = mItem.name.title + " " + mItem.name.first + " " + mItem.name.last


        holder.itemView.setOnClickListener {
            onclickItem.onItemClicked(position)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                var resultList = java.util.ArrayList<Result>()
                resultList.clear()
                if (charSearch.isEmpty()) {
                    resultList = userList1
                } else {

                    for (userData in userList1) {
                        if (userData.name.title.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                            || userData.name.first.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                            || userData.name.last.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(userData)
                        }
                    }
                    //  userList = resultList
                }
                var filterResults = FilterResults()
                filterResults.values = resultList
                return filterResults
            }


            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                userList = results?.values as java.util.ArrayList<Result>
                notifyDataSetChanged()
            }

        }
    }

}

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var userImage: CircleImageView = itemView.findViewById(R.id.iUserProfile)
    var userFullName: AppCompatTextView = itemView.findViewById(R.id.tvUserName)
}