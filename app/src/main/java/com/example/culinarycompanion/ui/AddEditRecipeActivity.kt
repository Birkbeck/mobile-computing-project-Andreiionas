package com.example.culinarycompanion.ui

import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.culinarycompanion.R
import com.example.culinarycompanion.model.Recipe
import com.example.culinarycompanion.viewmodel.RecipeViewModel

class AddEditRecipeActivity : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var ingredientsEditText: EditText
    private lateinit var instructionsEditText: EditText
    private lateinit var categorySpinner: Spinner
    private lateinit var saveButton: Button

    private val viewModel: RecipeViewModel by viewModels()
    private var existingRecipe: Recipe? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        // Bind UI elements
        titleEditText = findViewById(R.id.et_title)
        ingredientsEditText = findViewById(R.id.et_ingredients)
        instructionsEditText = findViewById(R.id.et_instructions)
        categorySpinner = findViewById(R.id.spinner_category)
        saveButton = findViewById(R.id.btn_save)

        // Set up the spinner with category options
        ArrayAdapter.createFromResource(
            this,
            R.array.recipe_categories,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categorySpinner.adapter = adapter
        }

        // If editing an existing recipe, populate fields
        existingRecipe = intent.getSerializableExtra("recipe") as? Recipe
        existingRecipe?.let { recipe ->
            titleEditText.setText(recipe.title)
            ingredientsEditText.setText(recipe.ingredients)
            instructionsEditText.setText(recipe.instructions)
            val categoryIndex = resources.getStringArray(R.array.recipe_categories).indexOf(recipe.category)
            if (categoryIndex >= 0) categorySpinner.setSelection(categoryIndex)
        }

        // Save or update recipe
        saveButton.setOnClickListener {
            val title = titleEditText.text.toString().trim()
            val ingredients = ingredientsEditText.text.toString().trim()
            val instructions = instructionsEditText.text.toString().trim()
            val category = categorySpinner.selectedItem.toString()

            if (title.isNotEmpty() && ingredients.isNotEmpty() && instructions.isNotEmpty()) {
                val recipe = Recipe(
                    id = existingRecipe?.id ?: 0,
                    title = title,
                    ingredients = ingredients,
                    instructions = instructions,
                    category = category
                )

                if (existingRecipe != null) {
                    viewModel.update(recipe)
                } else {
                    viewModel.insert(recipe)
                }

                finish()
            } else {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
