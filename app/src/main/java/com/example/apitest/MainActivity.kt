package com.example.apitest

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.apitest.databinding.ActivityMainBinding
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import javax.xml.parsers.DocumentBuilderFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //키값
        val key = "EDOPjH4oTjGZ6Bzr6ylwb8Rl3LFddWjT8SRbfK4MVLzclnUfD7HEx6%2FXAIshUMmoB%2B9Jc%2FFaZi8Lgb97nKRq%2Fg%3D%3D"
    }
}