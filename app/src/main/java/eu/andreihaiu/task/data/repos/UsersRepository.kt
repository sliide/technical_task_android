package eu.andreihaiu.task.data.repos

import eu.andreihaiu.task.data.base.ApiResponse
import eu.andreihaiu.task.data.models.AddUserResponse
import eu.andreihaiu.task.data.models.Users
import retrofit2.Response

interface UsersRepository {

    suspend fun getUsers(): ApiResponse<List<Users>>
    suspend fun addUser(name: String, email: String, gender: String): ApiResponse<AddUserResponse>
    suspend fun deleteUser(userId: Long): ApiResponse<Response<Unit>>
}