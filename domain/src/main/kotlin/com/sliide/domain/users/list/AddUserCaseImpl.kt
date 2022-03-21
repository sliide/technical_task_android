package com.sliide.domain.users.list

import com.sliide.boundary.users.FieldsProblem
import com.sliide.boundary.users.UsersRepo
import com.sliide.interactor.users.list.AddUserResult
import com.sliide.interactor.users.list.AddUserResult.*
import javax.inject.Inject

class AddUserCaseImpl @Inject constructor(private val usersRepo: UsersRepo) : AddUserCase {

    private companion object {
        private const val EMAIL = "email"
        private const val NAME = "name"
    }

    override suspend fun addUser(name: String, email: String): AddUserResult {
        return try {
            val user = usersRepo.create(name, email)
            Created(user.toUserItem())
        } catch (ex: FieldsProblem) {
            val fields = ex.fields.keys
            if (fields.contains(EMAIL) || fields.contains(NAME)) FieldsError else UnknownError
        } catch (ex: Exception) {
            UnknownError
        }
    }
}