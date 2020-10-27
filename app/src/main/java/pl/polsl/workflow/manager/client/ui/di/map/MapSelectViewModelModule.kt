package pl.polsl.workflow.manager.client.ui.di.map

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pl.polsl.workflow.manager.client.ui.di.base.ViewModelKey
import pl.polsl.workflow.manager.client.ui.di.base.ViewModelModule
import pl.polsl.workflow.manager.client.ui.map.MapSelectViewModelImpl

@Module(includes = [ViewModelModule::class])
abstract class MapSelectViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MapSelectViewModelImpl::class)
    abstract fun bindsViewModel(mapSelectViewModelImpl: MapSelectViewModelImpl): ViewModel
}