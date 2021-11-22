package eu.andreihaiu.task.di

import eu.andreihaiu.task.data.api.UserService
import eu.andreihaiu.task.data.repos.UsersRepository
import eu.andreihaiu.task.data.repos.UsersRepositoryImpl
import eu.andreihaiu.task.data.shared.PreferenceDatabase
import eu.andreihaiu.task.util.constants.API_KEY
import eu.andreihaiu.task.util.constants.GO_REST_DOMAIN
import eu.andreihaiu.task.util.extensions.DependencyCreator
import eu.andreihaiu.task.util.extensions.MoshiBuilderProvider
import eu.andreihaiu.task.util.extensions.OkHttpClientProvider
import eu.andreihaiu.task.viewmodels.users.UsersViewModelImpl
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    // Network Clients
    single {
        OkHttpClientProvider.provideBuilder(
            context = androidContext(),
            authToken = "Bearer $API_KEY"
        ).build()
    }
    single { MoshiBuilderProvider.provideMoshi() }
    single {
        DependencyCreator.createNetworkClient(GO_REST_DOMAIN, get(), get())
            .create(UserService::class.java)
    }

    // Storage
    single { PreferenceDatabase(get()) }

    // Repositories
    single<UsersRepository> { UsersRepositoryImpl(userService = get()) }

    // Schedulers
    single { Dispatchers.IO }

    // ViewModels
    viewModel { UsersViewModelImpl(usersRepository = get()) }
}
