package pl.polsl.workflow.manager.client.ui.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import pl.polsl.workflow.manager.client.model.remote.api.LocalizationApi
import pl.polsl.workflow.manager.client.util.validator.InputValidator
import pl.polsl.workflow.manager.client.util.validator.InputValidatorImpl
import retrofit2.Retrofit
import retrofit2.create

@Module(includes = [AppModule::class])
class ValidatorModule {

    @Provides
    fun provideInputValidator(context: Context): InputValidator = InputValidatorImpl(context)

}