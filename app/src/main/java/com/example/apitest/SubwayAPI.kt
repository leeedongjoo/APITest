package com.example.apitest

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.opencsv.CSVReader
import com.opencsv.exceptions.CsvException
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

class SubwayAPI : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchField = findViewById<EditText>(R.id.etSearch)
        val searchButton = findViewById<Button>(R.id.btnSearch)

        searchButton.setOnClickListener {
            val searchText = searchField.text.toString()
            try {
                if (searchText.isNotEmpty()) {
                    searchDepartureStation(searchText) // 출발역 검색 메서드 호출
                } else {
                    Log.d("Search", "Please enter a departure station to search.") // 검색어가 비어있을 때 로그 출력
                }
            } catch (e: IOException) {
                Log.e("MainActivity", "Failed to load data: ", e) // 데이터 로드 실패 시 로그 출력
            } catch (e: CsvException) {
                Log.e("MainActivity", "CSV parsing error: ", e) // CSV 파싱 오류 시 로그 출력
            }
        }
    }

    @Throws(IOException::class, CsvException::class)
    // CSV 파일에서 가져온 내용을 위의 코드에 들어가 입력한 값이 파일내에 있는지 확인해서 출력해줌
    private fun searchDepartureStation(departureStation: String) {
        val assetManager = this.assets // AssetManager 객체 가져오기
        val inputStream = assetManager.open("subway.csv") // assets 폴더에 있는 CSV 파일 열기
        val csvReader = CSVReader(InputStreamReader(inputStream, "EUC-KR")) // CSVReader 초기화
        val allContent = csvReader.readAll() // CSV 파일 전체 내용 읽어오기

        val calendar = Calendar.getInstance() // 현재 시간 가져오기
        val minutes = calendar.get(Calendar.MINUTE) // 현재 분 가져오기
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY) // 현재 시간(24시간 형식) 가져오기
        val timeBlockStart = if (minutes < 30) currentHour * 100 else currentHour * 100 + 30 // 현재 시간 기준 시작 시간대 계산
        val timeBlockEnd = if (minutes < 30) currentHour * 100 + 30 else (currentHour + 1) * 100 // 현재 시간 기준 종료 시간대 계산

        var found = false // 결과를 찾았는지 여부를 나타내는 플래그 변수
        for (row in allContent.drop(1)) { // CSV 파일 내 각 행에 대해 반복 (첫 번째 행은 헤더이므로 건너뜀)
            val timeRange = row[6].split("-").map { it.trim().replace(":", "").toIntOrNull() ?: 0 } // 시간대 범위 파싱
            if (timeRange.size > 1 && timeRange[0] <= timeBlockEnd && timeRange[1] >= timeBlockStart && row[4].contains(departureStation, ignoreCase = true)) {
                // 시간대가 현재 시간대에 해당하고 출발역이 일치하는 경우
                val serialNumber = row[0] // 연번
                val dayDivision = row[1] // 요일구분
                val lineName = row[2] // 호선
                val stationCode = row[3] // 역번호
                val direction = row[5] // 상하구분
                Log.d("SearchResult", "연번: $serialNumber, 요일구분: $dayDivision, 호선: $lineName, 역번호: $stationCode, 출발역: $departureStation, 상하구분: $direction, 시간: ${row[6]}")
                found = true // 결과를 찾았으므로 플래그 변수 설정
            }
        }

        if (!found) {
            Log.d("Search", "No results found for: $departureStation in the current time block.") // 검색 결과가 없을 때 로그 출력
        }
    }
}
