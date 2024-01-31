package eu.mobcomputing.dima.registration.api


import eu.mobcomputing.dima.registration.utils.Constants.Companion.BASE_URL
import org.junit.Assert.assertEquals
import org.junit.Test

class APIServiceTest {

    @Test
    fun testRetrofitInitialization() {
        val apiService = APIService()

        // Verify that the retrofit object is initialized with the correct parameters
        assertEquals(BASE_URL, apiService.retrofit.baseUrl().toString())

        // Ensure that the interceptors are added to the OkHttpClient
        val interceptors = apiService.httpClient.interceptors
        assertEquals(2, interceptors.size)

    }
}