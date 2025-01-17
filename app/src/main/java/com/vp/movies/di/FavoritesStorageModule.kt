package com.vp.movies.di

import android.app.Application
import com.vp.detail.model.MovieDetail
import com.vp.favorites.service.FavoritesSQLDataBaseHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class FavoritesStorageModule {

    @Provides
    fun getFavoritesDbHelper(application: Application): FavoritesSQLDataBaseHelper {
        return FavoritesSQLDataBaseHelper(application)
    }

    @Singleton
    @Provides
    fun providesFavoritesStorageAccessor(dbHelper: FavoritesSQLDataBaseHelper) = object : com.vp.detail.service.FavoriteService {
        override fun isFavorite(movieId: String) = dbHelper.exists(movieId)

        override fun saveToFavorites(movieId: String, movie: MovieDetail) = movie.run {
            dbHelper.addFavoriteMovie(movieId, title, year, runtime, director, plot, poster)
        }

        override fun removeFromFavorites(movieId:String) = dbHelper.removeFromFavoriteMovie(movieId)
    }

    @Singleton
    @Provides
    fun providesFavoritesStorageGetter(dbHelper: FavoritesSQLDataBaseHelper) = object : com.vp.favorites.service.FavoriteService {
        override fun getFavoritesMovies() = dbHelper.getAllFavoriteMovies()
    }
}