package com.example.culinarycompanion.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.culinarycompanion.model.Recipe

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe_table ORDER BY id DESC")
    fun getAllRecipes(): LiveData<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: Recipe)

    @Update
    suspend fun update(recipe: Recipe)

    @Delete
    suspend fun delete(recipe: Recipe)
}
