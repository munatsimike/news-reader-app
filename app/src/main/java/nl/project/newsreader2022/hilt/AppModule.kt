package nl.project.newsreader2022.hilt

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nl.project.newsreader2022.database.createDB
import nl.project.newsreader2022.network.UserService
import nl.project.newsreader2022.repository.UserRepository

@InstallIn(SingletonComponent::class)
@Module
abstract class AppModule {

    companion object {
        @Provides
        fun provideArticleDao(@ApplicationContext appContext: Context) = createDB(appContext)
    }
}
