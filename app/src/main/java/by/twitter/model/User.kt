package by.twitter.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val name: String = "name",
    val nameID: String = "@unique_name"
) : Parcelable {
    override fun toString(): String {
        return name
    }
}