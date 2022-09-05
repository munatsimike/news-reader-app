package nl.project.newsreader2022.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NextIdDao {
    @Query(" INSERT INTO nextId_Table (NextId) VALUES (:id)")
    suspend fun insertNextId(id: Int)

    @Query("SELECT nextId FROM nextId_Table order by id desc limit 1")
    fun fetchNextId(): LiveData<Int>

    @Query("DELETE FROM nextId_Table")
    fun deleteAllIds()
}