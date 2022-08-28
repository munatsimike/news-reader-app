package nl.project.newsreader2022.hilt

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nl.project.newsreader2022.adapters.NewsAdapter
import nl.project.newsreader2022.database.ArticleDB
import nl.project.newsreader2022.database.createDB
import nl.project.newsreader2022.ui.ClickListener
import nl.project.newsreader2022.ui.fragments.BaseFragment
import nl.project.newsreader2022.ui.fragments.HomeFragment

@InstallIn(SingletonComponent::class)
@Module
abstract class AppModule {

    companion object {
        @Provides
        fun provideArticleDao(@ApplicationContext appContext: Context) = createDB(appContext)
    }
}