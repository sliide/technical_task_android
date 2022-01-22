package com.sachinsapkale.android_test_maverick.view

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android_test_maverick.UserModel
import com.android_test_maverick.view.MainActivity
import com.android_test_maverick.view.fragments.ListFragment
import com.sachin_sapkale_android_challenge.viewmodel.MainViewModel
import com.sachinsapkale.android_test_maverick.R
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Matcher
import org.hamcrest.Matchers.hasEntry
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.EnumSet.allOf


@Suppress("UNCHECKED_CAST")
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest{
    @BindValue
    @JvmField
    val viewModel = mockk<MainViewModel>(relaxed = true)

    private val userListLiveData = MutableLiveData<List<UserModel>>()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Before
    fun setUp() {
        every { viewModel.userList } returns userListLiveData
    }

    @Test
    fun testLoadUserListFromLastPage() {
        val scenario = launchActivity<MainActivity>()
        val classType = object : TypeToken<List<UserModel>?>() {}.type
        val dummyData : List<UserModel> = Gson().fromJson("[{\"id\":989,\"name\":\"The Hon. Subhashini Menon\",\"email\":\"the_hon_subhashini_menon@bechtelar.com\",\"gender\":\"male\",\"status\":\"active\"},{\"id\":990,\"name\":\"Dandapaani Devar\",\"email\":\"devar_dandapaani@ohara.com\",\"gender\":\"female\",\"status\":\"inactive\"},{\"id\":991,\"name\":\"Aaryan Deshpande\",\"email\":\"deshpande_aaryan@braun.net\",\"gender\":\"female\",\"status\":\"inactive\"},{\"id\":992,\"name\":\"Ambar Acharya\",\"email\":\"ambar_acharya@reynolds.name\",\"gender\":\"female\",\"status\":\"inactive\"},{\"id\":993,\"name\":\"Bhaanumati Bandopadhyay\",\"email\":\"bhaanumati_bandopadhyay@jacobi-kuvalis.org\",\"gender\":\"male\",\"status\":\"active\"},{\"id\":994,\"name\":\"Girika Menon\",\"email\":\"girika_menon@baumbach.info\",\"gender\":\"male\",\"status\":\"inactive\"},{\"id\":995,\"name\":\"Achalesvara Sharma\",\"email\":\"sharma_achalesvara@smitham-walsh.name\",\"gender\":\"male\",\"status\":\"inactive\"},{\"id\":996,\"name\":\"Subhashini Tandon Sr.\",\"email\":\"sr_tandon_subhashini@sanford.info\",\"gender\":\"male\",\"status\":\"inactive\"},{\"id\":997,\"name\":\"Bhasvan Trivedi\",\"email\":\"trivedi_bhasvan@howell.biz\",\"gender\":\"female\",\"status\":\"inactive\"},{\"id\":998,\"name\":\"Eekalabya Varrier\",\"email\":\"varrier_eekalabya@hessel.name\",\"gender\":\"male\",\"status\":\"active\"},{\"id\":999,\"name\":\"Bhargava Khan\",\"email\":\"bhargava_khan@schimmel-littel.org\",\"gender\":\"male\",\"status\":\"active\"},{\"id\":1000,\"name\":\"Baalagopaal Shukla\",\"email\":\"shukla_baalagopaal@gerlach-mckenzie.biz\",\"gender\":\"male\",\"status\":\"active\"},{\"id\":1001,\"name\":\"Kalinda Kakkar\",\"email\":\"kalinda_kakkar@hegmann-hahn.net\",\"gender\":\"male\",\"status\":\"active\"},{\"id\":1002,\"name\":\"Balaaditya Joshi\",\"email\":\"balaaditya_joshi@smitham.net\",\"gender\":\"male\",\"status\":\"inactive\"},{\"id\":1003,\"name\":\"Rajinder Jha\",\"email\":\"jha_rajinder@daugherty-schmidt.name\",\"gender\":\"male\",\"status\":\"active\"},{\"id\":1004,\"name\":\"Sweta Acharya\",\"email\":\"sweta_acharya@okon.com\",\"gender\":\"male\",\"status\":\"inactive\"},{\"id\":1005,\"name\":\"Rahul Deshpande\",\"email\":\"deshpande_rahul@langosh-miller.name\",\"gender\":\"female\",\"status\":\"active\"},{\"id\":1006,\"name\":\"Ganapati Iyer\",\"email\":\"ganapati_iyer@littel-ziemann.info\",\"gender\":\"female\",\"status\":\"inactive\"},{\"id\":1007,\"name\":\"Chandradev Ganaka\",\"email\":\"chandradev_ganaka@jacobson.biz\",\"gender\":\"male\",\"status\":\"active\"},{\"id\":1008,\"name\":\"Chandraswaroopa Bhat II\",\"email\":\"ii_chandraswaroopa_bhat@hoppe.info\",\"gender\":\"male\",\"status\":\"active\"}]",classType);
        viewModel.userList.postValue(dummyData)
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()));// most of the time, click() also act as lognclick.
        onView(ViewMatchers.isRoot()).perform(waitFor(4000))
    }

    fun waitFor(delay: Long): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = ViewMatchers.isRoot()
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }
}