package com.example.mynews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mynews.data.model.Article
import com.example.mynews.data.model.MainViewModel
import com.example.mynews.data.model.Source
import com.example.mynews.ui.theme.MyNewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mainViewModel = remember { MainViewModel() }
            val topic = remember { mutableStateOf("") }

            val news = mainViewModel.newsResponse.collectAsState()
            MyNewsTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color.Black ) {
                    Column (modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()){
                        TextField(
                            value = topic.value, onValueChange ={topic.value = it},
                            placeholder = { Text(text = "Enter Topic" , color = Color.Gray) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                                .border(
                                    BorderStroke(1.dp, Color.Gray), shape = CircleShape
                                )
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Button(onClick = {mainViewModel.getNews(topic.value)},
                            modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White

                            )
                        ) {
                            Text(text = "Get New's ")
                            
                        }

                        LazyColumn{
                            items(news.value?.articles ?: emptyList()){article ->
                                NewsCard(article)
                            }
                        }


                    }
                }
            }
        }
    }
}

@Composable
fun NewsCard(article: Article) {
    Card(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = article.author ?: "Unknown Author", style = MaterialTheme.typography.titleMedium)
            Text(text = article.title ?: "No Title", style = MaterialTheme.typography.titleLarge)
            Text(text = article.description ?: "No Description", style = MaterialTheme.typography.bodyMedium)
            AsyncImage(model = article.urlToImage ?: "", contentDescription = null, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsCardPreview() {
    MyNewsTheme {
        NewsCard(Article("Author", "Title", "Description", "Published At", Source("id", "name"), "Title", "Url", "Url To Image"))
    }
}
