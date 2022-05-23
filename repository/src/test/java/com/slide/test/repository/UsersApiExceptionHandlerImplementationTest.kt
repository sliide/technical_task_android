package com.slide.test.repository

import com.slide.test.core.errors.ApiError
import com.slide.test.core.errors.EmailAlreadyExistsException
import com.slide.test.core.errors.GoRestApiException
import com.slide.test.repository.exceptions.UsersApiExceptionHandlerImplementation
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

/**
 * Created by Stefan Halus on 23 May 2022
 */
internal class UsersApiExceptionHandlerImplementationTest {

    private lateinit var objectUnderTest: UsersApiExceptionHandlerImplementation

    @Before
    fun setUp(){
        objectUnderTest = UsersApiExceptionHandlerImplementation()
    }

    @Test
    fun `Test generic Exception output`() {
        assertTrue(objectUnderTest.handleException(Exception()) is Exception)
        assertTrue(objectUnderTest.handleException(IOException()) is IOException)
    }

    @Test
    fun `Test generic GoRestApiException output`() {
        assertTrue(objectUnderTest.handleException(GoRestApiException(400L, emptyList())) is GoRestApiException)
        assertTrue(objectUnderTest.handleException(GoRestApiException(422L, emptyList())) is GoRestApiException)
    }

    @Test
    fun `Test EmailAlreadyExistsException output`() {
        val apiErrors = listOf(ApiError(field = "email", message = "has already been taken"))
        assertTrue(objectUnderTest.handleException(GoRestApiException(422L, apiErrors)) is EmailAlreadyExistsException)
    }
}