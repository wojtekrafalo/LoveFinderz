package com.example.lovefinderz.ui.jokes.favorite.list

//import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lovefinderz.R
import com.example.lovefinderz.model.Joke
import com.example.lovefinderz.ui.jokes.all.list.JokeHolder

class FavoriteJokeAdapter(
    private val onFavoriteClickHandler: (Joke) -> Unit
) : RecyclerView.Adapter<JokeHolder>() {

  private val items = mutableListOf<Joke>()

  override fun getItemCount() = items.size

  fun setData(data: List<Joke>) {
    items.clear()
    items.addAll(data)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeHolder {
    //TODO: Delete this class and fragment_profile_browser below:
    val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_profile_browser, parent, false)

    return JokeHolder(view, onFavoriteClickHandler)
  }

  override fun onBindViewHolder(holder: JokeHolder, position: Int) = holder.displayData(items[position])

  fun clear() {
    items.clear()
    notifyDataSetChanged()
  }
}