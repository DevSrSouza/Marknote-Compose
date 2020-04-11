package br.com.devsrsouza.marknote

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.ui.core.setContent
import br.com.devsrsouza.marknote.repository.Repository
import br.com.devsrsouza.marknote.ui.MarknoteApp

class MainActivity : AppCompatActivity() {
    private lateinit var repository: Repository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        repository = Repository(this, lifecycle)

        setContent {
            MarknoteApp(
                repository
            )
        }
    }
}