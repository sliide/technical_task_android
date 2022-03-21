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
        private const val EMAIL_TAKEN = "has already been taken"
    }

    override suspend fun addUser(name: String, email: String): AddUserResult {
        return try {
            val user = usersRepo.create(name, email)
            Created(user.toUserItem())
        } catch (ex: FieldsProblem) {
            if (ex.fields[EMAIL] == EMAIL_TAKEN) {
                EmailAlreadyTaken(email)

            } else {
                val fields = ex.fields.keys
                if (fields.contains(EMAIL) || fields.contains(NAME)) {
                    FieldsError
                } else UnknownError(ex)
            }
        } catch (ex: Exception) {
            UnknownError(ex)
        }
    }
}