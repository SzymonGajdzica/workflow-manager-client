package pl.polsl.workflow.manager.client.ui.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import pl.polsl.workflow.manager.client.utils.LocationReader
import pl.polsl.workflow.manager.client.utils.LocationReaderImpl

@Module(includes = [AppModule::class])
class LocalizationModule {

    @Provides
    fun provideLocationReader(context: Context): LocationReader = LocationReaderImpl(context)

}