package nl.project.newsreader2022.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import nl.project.newsreader2022.converters.Converters
import nl.project.newsreader2022.model.NewsArticle

@Database(
    entities = [NewsArticle::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ArticleDB : RoomDatabase() {
    abstract val newsDao: ArticleDao
}

private lateinit var instance: ArticleDB

 fun createDB(context: Context): ArticleDB {
    synchronized(ArticleDB::class.java) {
        if (!::instance.isInitialized) {
            instance = Room.databaseBuilder(
                context.applicationContext,
                ArticleDB::class.java,
                "article_db.db"
            ).build()
        }
    }
    return instance
}
