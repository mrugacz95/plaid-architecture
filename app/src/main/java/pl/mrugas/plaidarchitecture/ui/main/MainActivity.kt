package pl.mrugas.plaidarchitecture.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import org.koin.android.ext.android.inject
import pl.mrugas.plaidarchitecture.R
import pl.mrugas.plaidarchitecture.databinding.ActivityMainBinding
import pl.mrugas.plaidarchitecture.util.Event
import pl.mrugas.repository.Repo

class MainActivity : AppCompatActivity() {
    private val _viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.view = _viewModel

        _viewModel.notification.observe(this, Observer<Event<String>> { eventMessage ->
            Toast.makeText(this@MainActivity, eventMessage.consume(), Toast.LENGTH_LONG).show()
        })

        val adapter = ReposAdapter()
        val reposRecyclerView: RecyclerView = findViewById(R.id.rv_repos)
        reposRecyclerView.adapter = adapter
        _viewModel.repos.observe(this, Observer<List<Repo>> { repos ->
            adapter.repos = repos
            adapter.notifyDataSetChanged()
        })


    }
}
