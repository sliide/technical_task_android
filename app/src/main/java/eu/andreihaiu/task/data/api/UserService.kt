package eu.andreihaiu.task.data.api

import eu.andreihaiu.task.data.models.AddUserResponse
import eu.andreihaiu.task.data.models.Users
import eu.andreihaiu.task.data.models.UsersResponse
import retrofit2.Response
import retrofit2.http.*

interface UserService {

    @GET("/public/v1/users")
    suspend fun getUsers(
        @Query("page") pageNumber: Int = 1
    ): UsersResponse

    @POST("/public/v1/users")
    suspend fun createUser(@Body user: Users): AddUserResponse

    @DELETE("/public/v1/users/{userId}")
    suspend fun deleteUser(@Path("userId") userId: Long): Response<Unit>
}
