package pl.mrugas.plaidarchitecture.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.mrugas.plaidarchitecture.databinding.RepoItemBinding
import pl.mrugas.repository.Repo

class ReposAdapter : RecyclerView.Adapter<ReposAdapter.ViewHolder>() {
    var repos: List<Repo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RepoItemBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount() = repos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(repos[position])

    inner class ViewHolder(private val binding: RepoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: Repo) {
            binding.repo = repo
            binding.executePendingBindings()
        }
    }

}