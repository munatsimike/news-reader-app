package nl.project.newsreader2022.hilt

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nl.project.newsreader2022.database.ArticleDB
import nl.project.newsreader2022.database.createDB

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Provides
    fun provideArticleDao(@ApplicationContext appContext: Context) = createDB(appContext)
}