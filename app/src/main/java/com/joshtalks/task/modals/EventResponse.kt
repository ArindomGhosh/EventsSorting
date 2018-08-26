package com.joshtalks.task.modals

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
/*{"posts":[{
    "id" : "HktWbZ1ox",
    "thumbnail_image" : "http://i.imgur.com/wn58o7W.jpg",
    "event_name" : "Helasdaasdadasdasdadadasdasdasdaasdadasdaslo1",
    "event_date" : 1511481600,
    "views" : 7,
    "likes" : 3,
    "shares" : 1
}],"page":3}*/

@Parcelize
data class EventResponse(val posts: List<Posts>, val page: Int) : Parcelable

@Entity(tableName = "PostTable")
@Parcelize
data class Posts(@PrimaryKey(autoGenerate = true) val primaryKey:Int=0,
                 val id: String,
                 val thumbnail_image: String,
                 val event_name: String,
                 val event_date: Long,
                 val views: Double,
                 val likes: Double,
                 val shares: Double) : Parcelable