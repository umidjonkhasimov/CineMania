package uz.john.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import uz.john.datastore.UserPreferences
import uz.john.datastore.UserPreferencesSerializer
import javax.inject.Singleton

const val USER_PREFERENCES = "user_prefs.pb"

@Module
@InstallIn(SingletonComponent::class)
class DatastoreModule {
    @Provides
    @Singleton
    fun provideDataStorePreferences(
        @ApplicationContext context: Context,
    ): DataStore<UserPreferences> {
        return DataStoreFactory.create(
            serializer = UserPreferencesSerializer,
            corruptionHandler = null,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        ) {
            context.dataStoreFile(USER_PREFERENCES)
        }
    }
}