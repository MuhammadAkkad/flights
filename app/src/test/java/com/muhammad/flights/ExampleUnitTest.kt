package com.muhammad.flights

import android.app.Application
import com.google.gson.Gson
import com.muhammad.flights.app.Constants
import com.muhammad.flights.app.viewmodels.FlightsViewModel
import com.muhammad.flights.data.model.FlightsModel
import com.muhammad.flights.data.usecase.FlightsUseCase
import com.muhammad.flights.data.usecase.State
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.TestObserver
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner
import java.io.InputStream


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
class ExampleUnitTest {

    private lateinit var viewModel: FlightsViewModel

    @Mock
    private lateinit var application: Application

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val useCase = FlightsUseCase(application)
        viewModel = FlightsViewModel(useCase)
    }

    @Test
    fun getFlightsTest() {
        val useCase = FlightsUseCase(application)
        val testObserver: TestObserver<State<FlightsModel>> = useCase.getFlightsUseCase().test()
        testObserver.await()
        testObserver
            .assertNoErrors()
            .assertValue { l: State<FlightsModel?> -> l.data?.data != null }

    }

}