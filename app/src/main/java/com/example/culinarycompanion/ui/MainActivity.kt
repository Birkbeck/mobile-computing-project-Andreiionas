package com.example.culinarycompanion.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.culinarycompanion.adapter.RecipeAdapter
import com.example.culinarycompanion.databinding.ActivityMainBinding
import com.example.culinarycompanion.model.Recipe
import com.example.culinarycompanion.viewmodel.RecipeViewModel

class MainActivity : AppCompatActivity(), RecipeAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private val recipeViewModel: RecipeViewModel by viewModels()
    private lateinit var adapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RecipeAdapter(this)
        binding.rvRecipes.layoutManager = LinearLayoutManager(this)
        binding.rvRecipes.adapter = adapter

        recipeViewModel.allRecipes.observe(this) { recipes ->
            adapter.setRecipes(recipes)
        }

        binding.btnAddRecipe.setOnClickListener {
            val intent = Intent(this, AddEditRecipeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onItemClick(recipe: Recipe) {
        val intent = Intent(this, AddEditRecipeActivity::class.java).apply {
            putExtra("RECIPE_ID", recipe.id)
            putExtra("TITLE", recipe.title)
            putExtra("INGREDIENTS", recipe.ingredients)
            putExtra("INSTRUCTIONS", recipe.instructions)
            putExtra("CATEGORY", recipe.category)
        }
        startActivity(intent)
    }

    override fun onDeleteClick(recipe: Recipe) {
        recipeViewModel.delete(recipe)
    }
}
