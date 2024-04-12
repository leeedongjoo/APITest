package com.example.apitest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.apitest.databinding.TestTimeBinding
import java.text.SimpleDateFormat
import java.util.*

class TimeTest : AppCompatActivity() {

    private lateinit var binding: TestTimeBinding
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.KOREA)  // "시:분" 형식으로 시간 지정
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            val currentTime = timeFormat.format(Date())  // 현재 시간 가져오기
            binding.tvTime.text = currentTime  // TextView 업데이트
            handler.postDelayed(this, 1000)  // 1초 후에 다시 실행
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 레이아웃 인플레이터 생성 및 바인딩 객체 초기화
        val inflater = layoutInflater
        binding = TestTimeBinding.inflate(inflater)
        setContentView(binding.root)

        // 최초 실행
        handler.post(runnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)  // 액티비티 종료 시 반복 종료
    }
}
