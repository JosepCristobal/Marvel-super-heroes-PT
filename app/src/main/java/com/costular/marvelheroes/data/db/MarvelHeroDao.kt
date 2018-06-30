package com.costular.marvelheroes.data.db

import android.arch.persistence.room.*
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import io.reactivex.Maybe

@Dao

abstract class MarvelHeroDao{

    @Query("SELECT * FROM marvelhero")

    abstract fun getAllUsers(): Maybe<List<MarvelHeroEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(users: List<MarvelHeroEntity>)

    @Query("DELETE FROM marvelhero")
    abstract fun deleteAllUsers()

    @Transaction
    open fun removeAndInsertUsers(users: List<MarvelHeroEntity>) {
        deleteAllUsers()
        insertAll(users)
    }

}