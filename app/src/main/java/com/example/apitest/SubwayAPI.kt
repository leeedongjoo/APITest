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
import java.util.Calendar

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
                    searchDepartureStation(searchText)
                } else {
                    Log.d("Search", "Please enter a departure station to search.")
                }
            } catch (e: IOException) {
                Log.e("MainActivity", "Failed to load data: ", e)
            } catch (e: CsvException) {
                Log.e("MainActivity", "CSV parsing error: ", e)
            }
        }
    }

    @Throws(IOException::class, CsvException::class)
    private fun searchDepartureStation(departureStation: String) {
        // assets 폴더에 있는 CSV 파일 열기
        val assetManager = this.assets
        val inputStream = assetManager.open("subway.csv")
        val csvReader = CSVReader(InputStreamReader(inputStream, "EUC-KR"))

        // CSV 파일의 모든 내용 읽기
        val allContent = csvReader.readAll()

        // 현재 시간 정보 가져오기
        val currentTime = Calendar.getInstance()
        val currentHour = currentTime.get(Calendar.HOUR_OF_DAY)
        val currentMinute = currentTime.get(Calendar.MINUTE)
        val closestHalfHour = "${currentHour}:${if (currentMinute < 30) "00" else "30"}"

        // 첫 번째 행(헤더)에서 현재 시간대에 해당하는 열 인덱스 찾기
        val headerRow = allContent[0]
        val timeColumnIndex = headerRow.indexOf(closestHalfHour)

        // 현재 시간대에 해당하는 데이터가 없으면 로그 출력 후 함수 종료
        if (timeColumnIndex == -1) {
            Log.d("Search", "No data available for the current time block.")
            return
        }

        // 해당 시간대와 출발역이 일치하는 데이터 찾기
        var found = false
        for (row in allContent) { // 첫 번째 행을 제외하고 반복
            if (row[4].contains(departureStation, ignoreCase = true)) { // 열 인덱스 4가 출발역 정보
                val time = headerRow[timeColumnIndex] // 현재 시간대
                val serialNumber = row[0]
                val dayDivision = row[1]
                val lineName = row[2]
                val stationCode = row[3]
                val direction = row[5]
                Log.d(
                    "SearchResult",
                    "연번: $serialNumber, 요일구분: $dayDivision, 호선: $lineName, 역번호: $stationCode, 출발역: $departureStation, 상하구분: $direction, 시간: $time"
                )
                found = true
            }
        }

        // 검색 결과가 없으면 로그 출력
        if (!found) {
            Log.d("Search", "No results found for: $departureStation in the current time block.")
        }
    }
}



//package com.example.apitest
//
//import android.os.Bundle
//import android.util.Log
//import android.widget.Button
//import android.widget.EditText
//import androidx.appcompat.app.AppCompatActivity
//import com.opencsv.CSVReader
//import com.opencsv.exceptions.CsvException
//import java.io.IOException
//import java.io.InputStreamReader
//
//class SubwayAPI : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        val searchField = findViewById<EditText>(R.id.etSearch)
//        val searchButton = findViewById<Button>(R.id.btnSearch)
//
//        searchButton.setOnClickListener {
//            val searchText = searchField.text.toString()
//            try {
//                if (searchText.isNotEmpty()) {
//                    searchDepartureStation(searchText)
//                } else {
//                    Log.d("Search", "Please enter a departure station to search.")
//                }
//            } catch (e: IOException) {
//                Log.e("MainActivity", "Failed to load data: ", e)
//            } catch (e: CsvException) {
//                Log.e("MainActivity", "CSV parsing error: ", e)
//            }
//        }
//    }
//
//    @Throws(IOException::class, CsvException::class)
//    private fun searchDepartureStation(departureStation: String) {
//        val assetManager = this.assets
//        val inputStream = assetManager.open("subway.csv")
//        val csvReader = CSVReader(InputStreamReader(inputStream, "EUC-KR"))
//        val allContent = csvReader.readAll()
//
//        var found = false
//        for (row in allContent.drop(1)) { // Skip header
//            if (row[4].contains(departureStation, ignoreCase = true)) {
//                val serialNumber = row[0]
//                val dayDivision = row[1]
//                val lineName = row[2]
//                val stationCode = row[3]
//                val direction = row[5]
//                Log.d("SearchResult", "연번: $serialNumber, 요일구분: $dayDivision, 호선: $lineName, 역번호: $stationCode, 출발역: $departureStation, 상하구분: $direction, 시간: ${row[6]}")
//                found = true
//            }
//        }
//
//        if (!found) {
//            Log.d("Search", "No results found for: $departureStation in the current time block.")
//        }
//    }
//}

