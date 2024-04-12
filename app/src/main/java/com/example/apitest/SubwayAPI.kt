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
        val assetManager = this.assets
        val inputStream = assetManager.open("subway.csv")
        val csvReader = CSVReader(InputStreamReader(inputStream, "EUC-KR"))
        val allContent = csvReader.readAll()

        var found = false
        for (row in allContent.drop(1)) { // Skip header
            if (row[4].contains(departureStation, ignoreCase = true)) {
                val serialNumber = row[0]
                val dayDivision = row[1]
                val lineName = row[2]
                val stationCode = row[3]
                val direction = row[5]
                Log.d("SearchResult", "연번: $serialNumber, 요일구분: $dayDivision, 호선: $lineName, 역번호: $stationCode, 출발역: $departureStation, 상하구분: $direction, 시간: ${row[6]}")
                found = true
            }
        }

        if (!found) {
            Log.d("Search", "No results found for: $departureStation in the current time block.")
        }
    }
}


