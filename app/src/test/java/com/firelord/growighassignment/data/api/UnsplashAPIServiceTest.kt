package com.firelord.growighassignment.data.api

import com.firelord.growighassignment.data.model.RemoteFetchItem
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UnsplashAPIServiceTest {
    private lateinit var service:UnsplashAPIService
    private lateinit var server: MockWebServer
    private val photoList = ArrayList<RemoteFetchItem>()
    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UnsplashAPIService::class.java)
    }

    private fun enqueueMockResponse(
        fileName:String
    ){
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    @Test
    fun getCurrentWeather_sentRequest_receivedExpected(){
        runBlocking {
            enqueueMockResponse("unsplashResponse.json")
            val responseBody = service.getPhotos(1).body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/photos?page=1&client_id=ynGtshOo2lOwRVcAFoJv-C2_Zc0ldQTw94-1LVCPmII&per_page=10")
        }
    }

    @Test
    fun getCurrentWeather_sentRequest_correctContent(){
        runBlocking {
            enqueueMockResponse("unsplashResponse.json")
            val responseBody = service.getPhotos(1).body()
            photoList.clear()
            photoList.addAll(responseBody!!)
            assertThat(photoList[0].id).isEqualTo("wPVEHAQsYQw")
            assertThat(photoList[0].urls.small).isEqualTo("https://images.unsplash.com/photo-1674574124475-16dd78234342?crop=entropy\u0026cs=tinysrgb\u0026fit=max\u0026fm=jpg\u0026ixid=M3w0NzEwMDZ8MXwxfGFsbHwxfHx8fHx8Mnx8MTY4ODYzNjgwMXw\u0026ixlib=rb-4.0.3\u0026q=80\u0026w=400")
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

}