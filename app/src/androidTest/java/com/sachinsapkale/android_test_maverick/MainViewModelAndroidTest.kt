package com.sachinsapkale.android_test_maverick

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.android_test_maverick.UserModel
import com.android_test_maverick.local.AppDatabase
import com.android_test_maverick.local.repository.LocalRepository
import com.android_test_maverick.remote.RetrofitService
import com.android_test_maverick.remote.repository.MainRepository
import com.sachin_sapkale_android_challenge.viewmodel.MainViewModel
import com.sachinsapkale.android_test_maverick.utils.getOrAwaitValue
import com.sachinsapkale.android_test_maverick.utils.getRandomEmail
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class MainViewModelAndroidTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: MainViewModel
    private lateinit var userModel: UserModel
    private lateinit var randomeEmail: String


    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        val localDataSource = LocalRepository(db.accessDao())
        val retrofitService = RetrofitService.getInstance()
        val remoteDatasource = MainRepository(retrofitService)
        randomeEmail = getRandomEmail()
        userModel = UserModel(0,"sachin", randomeEmail,"male","active")
        viewModel = MainViewModel(remoteDatasource, localDataSource)
    }

    @Test
    fun testGetUserApi() {
        viewModel.getLastPageNumbner(1) // adding default pagenumber
        val valuelist = viewModel.userList.getOrAwaitValue().get(0)
        assertNotNull(valuelist)
    }

    @Test
    fun testAddUserApi() {
        viewModel.createNewUser(BuildConfig.ACCESS_TOKEN,userModel)
        val valuelist = viewModel.singleUser.getOrAwaitValue()
        assertEquals(valuelist.email,randomeEmail)
    }

// Delete user method will need to get usermodel which needs to be deleted as a user input, thus, commenting this method
//    @Test
//    fun testDeleteUserApi() {
//        Log.d("randomemail",randomeEmail)
//        viewModel.deleteUser(BuildConfig.ACCESS_TOKEN,userModel)
//        val valuelist = viewModel.deleteUser.getOrAwaitValue()
//        assertEquals(valuelist.email,randomeEmail)
//        Log.d("randomemail",randomeEmail)
//    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}