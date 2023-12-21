package eu.mobcomputing.dima.registration.api


import eu.mobcomputing.dima.registration.BuildConfig.API_KEY
import eu.mobcomputing.dima.registration.utils.Constants
import eu.mobcomputing.dima.registration.utils.Constants.Companion.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
/**
 * The [APIService] class provides a set of utilities for handling API calls using Retrofit.
 *
 * <p>
 * It includes interceptors for logging and adding an API key to the request header. The class
 * also creates a custom HTTP client with the configured interceptors and sets up a Retrofit instance
 * with the base URL, HTTP client, and Gson converter factory.
 * </p>
 *
 * <p>
 * The [APIService] class follows the Spoonacular API authentication guidelines
 * (https://spoonacular.com/food-api/docs#Authentication). Two approaches are described to add authentication
 * to our request:
 *      - insert the api key as param in the request every time we make it (like so ?apiKey=YOUR-API-KEY)
 *      - insert the API key in the request header as x-api-key.
 * In our implementation we choose the second approach adding the API key as a header (x-api-key)
 * in the request.
 * </p>
 *
 * <p>
 * The class also provides a lazy-initialized [api] property, which is an instance of [RecipesAPICalls],
 * created using the Retrofit instance.
 * </p>
 *
 * @see Constants The class containing constant values used by the [APIService].
 * @see RecipesAPICalls The interface representing the API endpoints for recipe-related calls.
 * @see HttpLoggingInterceptor The interceptor for logging HTTP requests and responses.
 * @see Interceptor The interface for intercepting requests and responses.
 * @see OkHttpClient The HTTP client for making network requests.
 * @see Retrofit The type-safe HTTP client for Android and Java.
 */
class APIService {

    /**
     * Create an interceptor used to add logging capabilities to our APIService for debugging purpose.
     * We set the level of the Logger to BASIC.
     */
    val loggingInterceptor:HttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BASIC)


    /**
     *  Create a interceptor that adds the api key to the header for all the api request.
     *  From the Spoonacular documentation page (https://spoonacular.com/food-api/docs#Authentication)
     *  we have two ways to add authentication to our request:
     *      - insert the api key as param in the request every time we make it (like so ?apiKey=YOUR-API-KEY)
     *      - insert the API key in the request header as x-api-key.
     *
     *  We choose the second approach so we created this interceptor.
     */
    val APIKeyInterceptor = Interceptor { chain ->
        val originalRequest: Request = chain.request()

        // Add x-api-key header to the request
        val requestWithApiKey: Request = originalRequest.newBuilder()
            .header("x-api-key", API_KEY )
            .build()

        // Proceed with the modified request
        chain.proceed(requestWithApiKey)
    }


    /**
     * Create a custom http client to add the previously implemented interceptors
     */
    val httpClient =  OkHttpClient.Builder()
        .addInterceptor(APIKeyInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()


    /**
     * Retrofit builder to build the retrofit object.
     * We attached the http client wih his interceptors and chose Gson as converter for our response.
     */
    val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    /**
     * Create the service to make the api calls
     */
    val api: RecipesAPICalls by lazy {
        retrofit.create(RecipesAPICalls::class.java)
    }

}