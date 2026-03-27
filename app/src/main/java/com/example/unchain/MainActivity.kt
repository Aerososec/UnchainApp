package com.example.unchain

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.unchain.data.remote.ContentRequestDto
import com.example.unchain.data.remote.GeminiApiService
import com.example.unchain.data.remote.GeminiRequestDto
import com.example.unchain.data.remote.PartRequestDto
import com.example.unchain.databinding.ActivityMainBinding
import com.example.unchain.domain.usecases.InitDbUseCase
import com.example.unchain.presentation.allAddictionsScreen.fragment.AllAddictionsFragment
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var geminiApiService: GeminiApiService
    @Inject
    lateinit var initDbUseCase: InitDbUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as UnchainApp).appComponent.inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.fragmentContainer) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }

        lifecycleScope.launch {
            try {
                initDbUseCase.execute()

                val text = "Привет, до тебя дошел мой запрос?"
                val partRequest = PartRequestDto(text)
                val contentRequest = ContentRequestDto(listOf(partRequest))
                val geminiRequest = GeminiRequestDto(listOf(contentRequest))

                val response = geminiApiService.getGeminiResponse(
                    model = "gemini-2.5-flash",
                    apiKey = BuildConfig.GEMINI_API_KEY,
                    geminiRequest = geminiRequest
                )

                val responseText =
                    response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: "EMPTY"

                Log.d("GEMINI_API_TEST", responseText)

            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                Log.e("GEMINI_API_TEST", "HTTP 400 Error: $errorBody", e)
            } catch (e: Exception) {
                Log.e("GEMINI_API_TEST", "ERROR", e)
            }
        }


        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, AllAddictionsFragment())
            .commit()
    }
}