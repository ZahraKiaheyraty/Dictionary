package ir.afagh.dictionary.data.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.afagh.dictionary.data.db.DictionaryDataBase
import ir.afagh.dictionary.data.db.dao.DefineDao
import ir.afagh.dictionary.data.db.dao.LastSearchDao
import ir.afagh.dictionary.data.db.dao.WordsDao
import ir.afagh.dictionary.data.interactor.define.DefineRepository
import ir.afagh.dictionary.data.interactor.define.DefineRepositoryImp
import ir.afagh.dictionary.data.interactor.lastSearch.LastSearchRepository
import ir.afagh.dictionary.data.interactor.lastSearch.LastSearchRepositoryImp
import ir.afagh.dictionary.data.interactor.word.WordRepository
import ir.afagh.dictionary.data.interactor.word.WordRepositoryImp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Singleton
    @Provides
    fun provideDicDb(
        @ApplicationContext context: Context,
    ): DictionaryDataBase {
        return Room
            .databaseBuilder(
                context,
                DictionaryDataBase::class.java,
                "dbEnglishWords"
            )
//            .fallbackToDestructiveMigration()
            .createFromAsset("dbEnglishWords")
            .build()
    }

    @Provides
    fun provideWordDao(db: DictionaryDataBase): WordsDao = db.wordDao()

    @Provides
    fun provideDefineDao(db: DictionaryDataBase): DefineDao = db.defineDao()

    @Provides
    fun provideLastSeenDao(db: DictionaryDataBase): LastSearchDao = db.lastSearchDao()

    @Provides
    fun provideWordRepository(local: WordsDao): WordRepository {
        return WordRepositoryImp(local)
    }

    @Provides
    fun provideLastSearchRepository(local: LastSearchDao): LastSearchRepository {
        return LastSearchRepositoryImp(local)
    }

    @Provides
    fun provideDefineRepository(local: DefineDao): DefineRepository {
        return DefineRepositoryImp(local)
    }

    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope