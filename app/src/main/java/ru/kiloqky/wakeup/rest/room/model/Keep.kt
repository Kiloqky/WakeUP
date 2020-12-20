package ru.kiloqky.wakeup.rest.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = [Keep.KEEP_BODY])])
class Keep {
    companion object {
        const val KEEP_TITLE = "keep_title"
        const val KEEP_BODY = "keep_body"
        const val ID = "id"
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    var id: Long = 0

    @ColumnInfo(name = KEEP_TITLE)
    var keepTitle: String = ""

    @ColumnInfo(name = KEEP_BODY)
    var keepBody: String? = null


}
