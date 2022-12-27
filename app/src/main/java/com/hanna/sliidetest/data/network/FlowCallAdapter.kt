package com.hanna.sliidetest.data.network


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.awaitResponse
import java.lang.reflect.Type

class FlowCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, Flow<ApiResponse<R>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<R>): Flow<ApiResponse<R>> {
        return flow {
            val response = call.awaitResponse()
            if (response.isSuccessful) {
                emit(ApiResponse.create(response))
            } else {
                emit(ApiResponse.create<R>(response.code(), Throwable("An error occurred")))
            }
        }.catch {
            emit(ApiResponse.create(500, it))
        }
    }
}