package nl.project.newsreader2022.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "nextId_Table")
class MyNextId(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nextId: Int
)