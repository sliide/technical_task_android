package eu.andreihaiu.task.viewmodels.users

import androidx.lifecycle.LiveData
import eu.andreihaiu.task.data.models.Users
import eu.andreihaiu.task.viewmodels.base.BaseViewModel

interface UsersViewModel : BaseViewModel {
    val users: LiveData<List<Users>>
    val userEvent: LiveData<UserEvent>

    fun getUsers()
    fun addUser(userName: String?, userEmail: String?, userGender: String?)
    fun deleteUser(userId: Long?)
}

enum class UserEvent {
    ADD_SUCCES,
    DELETE_SUCCES
}