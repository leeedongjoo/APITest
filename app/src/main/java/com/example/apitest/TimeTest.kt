package com.example.apitest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.apitest.databinding.TestTimeBinding
import java.text.SimpleDateFormat
import java.util.*

class TimeTest : AppCompatActivity() {
    private lateinit var binding: TestTimeBinding  // ViewBinding 선언
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TestTimeBinding.inflate(layoutInflater)   // ViewBinding 초기화
        setContentView(binding.root)

        binding.btnTime.setOnClickListener {  // 버튼 클릭시 실행하는 코드
            val calendar = Calendar.getInstance()    // calendar = 현재 날짜와 시간
            calendar.set(Calendar.HOUR_OF_DAY, 10)   // 시간 지정
            calendar.set(Calendar.MINUTE, 30)        // 분 지정

            val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())  // 시간 분(HH:mm) 형식으로 출력
            val formattedTime = dateFormat.format(calendar.time)

            binding.txtTime.text = formattedTime
        }
    }
}

//    private lateinit var binding: TestTimeBinding
//    private val timeFormat = SimpleDateFormat("HH:mm", Locale.KOREA)  // "시:분" 형식으로 시간 지정
//    private val handler = Handler(Looper.getMainLooper())
//    private val runnable = object : Runnable {
//        override fun run() {
//            val currentTime = timeFormat.format(Date())  // 현재 시간 가져오기
//            binding.tvTime.text = currentTime  // TextView 업데이트
//            handler.postDelayed(this, 1000)  // 1초 후에 다시 실행
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        // 레이아웃 인플레이터 생성 및 바인딩 객체 초기화
//        val inflater = layoutInflater
//        binding = TestTimeBinding.inflate(inflater)
//        setContentView(binding.root)
//
//        // 최초 실행
//        handler.post(runnable)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        handler.removeCallbacks(runnable)  // 액티비티 종료 시 반복 종료
//    }
//}
