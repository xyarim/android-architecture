package com.xyarim.users.ui.fragment.users

import agency.tango.android.avatarview.IImageLoader
import agency.tango.android.avatarview.views.AvatarView
import agency.tango.android.avatarviewglide.GlideLoader
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xyarim.users.api.User


/**e
 * [BindingAdapter]s for the [User]s list.
 */
@BindingAdapter("items")
fun setItems(listView: RecyclerView, items: List<User>) {
    (listView.adapter as UsersAdapter).submitList(items)
}

@BindingAdapter("avatarUrl", "name")
fun loadImage(avatarView: AvatarView?, avatarUrl: String?, name: String) {
    val imageLoader:IImageLoader = GlideLoader()
    if (avatarView != null) {
        imageLoader.loadImage(avatarView, avatarUrl, name, avatarView.textSizePercentage())
    }
}