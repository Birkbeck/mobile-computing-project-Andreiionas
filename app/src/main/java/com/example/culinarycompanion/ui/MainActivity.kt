package com.example.culinarycompanion.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.culinarycompanion.R
import com.example.culinarycompanion.adapter.RecipeAdapter
import com.example.culinarycompanion.model.Recipe
import com.example.culinarycompanion.viewmodel.RecipeViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var recipeViewModel: RecipeViewModel
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var addButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up views
        recyclerView = findViewById(R.id.rv_recipes)
        addButton = findViewById(R.id.btn_add_recipe)

        // Set up RecyclerView
        recipeAdapter = RecipeAdapter(object : RecipeAdapter.OnItemClickListener {
            override fun onItemClick(recipe: Recipe) {
                val intent = Intent(this@MainActivity, AddEditRecipeActivity::class.java)
                intent.putExtra("recipe_id", recipe.id)
                intent.putExtra("title", recipe.title)
                intent.putExtra("ingredients", recipe.ingredients)
                intent.putExtra("instructions", recipe.instructions)
                intent.putExtra("category", recipe.category)
                startActivity(intent)
            }
        })

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recipeAdapter

        // ViewModel
        recipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
        recipeViewModel.allRecipes.observe(this) { recipes ->
            recipeAdapter.setRecipes(recipes)
        }

        // Add button click
        addButton.setOnClickListener {
            val intent = Intent(this, AddEditRecipeActivity::class.java)
            startActivity(intent)
        }
    }
}
