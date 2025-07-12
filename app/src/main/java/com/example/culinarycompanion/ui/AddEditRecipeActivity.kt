package com.example.culinarycompanion.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.culinarycompanion.R
import com.example.culinarycompanion.model.Recipe
import com.example.culinarycompanion.viewmodel.RecipeViewModel

class AddEditRecipeActivity : AppCompatActivity() {

    private lateinit var etTitle: EditText
    private lateinit var etIngredients: EditText
    private lateinit var etInstructions: EditText
    private lateinit var spinnerCategory: Spinner
    private lateinit var btnSave: Button

    private lateinit var recipeViewModel: RecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        etTitle = findViewById(R.id.et_title)
        etIngredients = findViewById(R.id.et_ingredients)
        etInstructions = findViewById(R.id.et_instructions)
        spinnerCategory = findViewById(R.id.spinner_category)
        btnSave = findViewById(R.id.btn_save)

        recipeViewModel = ViewModelProvider(this)[RecipeViewModel::class.java]

        // Spinner values
        val categories = listOf("Breakfast", "Brunch", "Lunch", "Dinner", "Desserts", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = adapter

        btnSave.setOnClickListener {
            saveRecipe()
        }
    }

    private fun saveRecipe() {
        val title = etTitle.text.toString().trim()
        val ingredients = etIngredients.text.toString().trim()
        val instructions = etInstructions.text.toString().trim()
        val category = spinnerCategory.selectedItem.toString()

        if (title.isEmpty() || ingredients.isEmpty() || instructions.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val recipe = Recipe(
            title = title,
            ingredients = ingredients,
            instructions = instructions,
            category = category
        )

        recipeViewModel.insert(recipe)
        Toast.makeText(this, "Recipe saved", Toast.LENGTH_SHORT).show()
        finish()
    }
}
