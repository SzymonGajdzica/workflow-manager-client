package pl.polsl.workflow.manager.client.ui.di.map

import dagger.Component
import pl.polsl.workflow.manager.client.ui.map.MapSelectFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [MapSelectModule::class])
interface MapSelectComponent {
    fun inject(mapSelectFragment: MapSelectFragment)
}