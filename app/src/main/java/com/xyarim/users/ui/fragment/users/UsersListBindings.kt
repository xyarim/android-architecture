package com.xyarim.users.ui.fragment.users

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xyarim.users.api.User


/**
 * [BindingAdapter]s for the [User]s list.
 */
@BindingAdapter("items")
fun setItems(listView: RecyclerView, items: List<User>) {
    (listView.adapter as UsersAdapter).submitList(items)
}

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    url?.let {
        Glide.with(view.context).load(url).into(view)
    }
}