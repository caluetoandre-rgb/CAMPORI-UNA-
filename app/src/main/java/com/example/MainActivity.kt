package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.CamporiApp
import com.example.ui.theme.CamporiTheme
import com.example.viewmodel.CamporiViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CamporiTheme {
                val viewModel: CamporiViewModel = viewModel()
                CamporiApp(viewModel = viewModel)
            }
        }
    }
}
