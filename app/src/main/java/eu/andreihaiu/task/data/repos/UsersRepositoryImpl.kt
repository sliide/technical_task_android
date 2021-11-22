package eu.andreihaiu.task.data.repos

import eu.andreihaiu.task.data.api.UserService
import eu.andreihaiu.task.data.base.ApiResponse
import eu.andreihaiu.task.data.models.AddUserResponse
import eu.andreihaiu.task.data.models.Users
import eu.andreihaiu.task.util.extensions.safeApiCall
import retrofit2.Response

class UsersRepositoryImpl(
    private val userService: UserService
) : UsersRepository {

    private var lastPage = 1

    override suspend fun getUsers(): ApiResponse<List<Users>> =
        safeApiCall {
            var response = userService.getUsers(lastPage)

            val currentPage = response.meta?.pagination?.page ?: 0
            lastPage = response.meta?.pagination?.pages ?: 1

            if (currentPage < lastPage) {
                response = userService.getUsers(lastPage)
            }

            return@safeApiCall response.data ?: emptyList()
        }

    override suspend fun addUser(
        name: String,
        email: String,
        gender: String
    ): ApiResponse<AddUserResponse> =
        safeApiCall {
            return@safeApiCall userService.createUser(
                Users(
                    name = name,
                    email = email,
                    gender = gender,
                    status = "active"
                )
            )
        }

    override suspend fun deleteUser(userId: Long): ApiResponse<Response<Unit>> =
        safeApiCall {
            return@safeApiCall userService.deleteUser(userId = userId)
        }
}