package com.koba.bookabulary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.koba.bookabulary.ui.theme.BookabularyTheme
import com.koba.presenter.main.MainScreen
import com.koba.presenter.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BookabularyTheme {
                // // A surface container using the 'background' color from the theme
                // Surface(
                //     modifier = Modifier.fillMaxSize(),
                //     color = MaterialTheme.colorScheme.background
                // ) {
                //     Greeting("Android")
                // }
                MainScreen(mainViewModel = mainViewModel)
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BookabularyTheme {
        Greeting("Android")
    }
}
