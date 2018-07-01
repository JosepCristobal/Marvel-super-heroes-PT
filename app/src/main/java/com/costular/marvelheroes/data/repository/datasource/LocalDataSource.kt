package com.costular.marvelheroes.data.repository.datasource

import com.costular.marvelheroes.data.db.MarvelHeroDatabase
import com.costular.marvelheroes.data.model.MarvelHero
import com.costular.marvelheroes.data.model.mapper.MarvelHeroMapper
import com.costular.marvelheroes.data.repository.MarvelHeroesRepository
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers


class LocalDataSource (val marvelHeroDatabase: MarvelHeroDatabase
                         ): MarvelHeroesRepository {

   override fun getMarvelHeroesList(): Observable<List<MarvelHeroEntity>> =
                    marvelHeroDatabase
                            .getMarvelHeroDao()
                            .getAllUsers()
                            .toObservable()

   fun saveHeroes(users: List<MarvelHeroEntity>) {
      Observable.fromCallable {
         marvelHeroDatabase.getMarvelHeroDao().removeAndInsertUsers(users)
      }
              .subscribeOn(Schedulers.io())
              .subscribe()
   }

    override fun deleteMarvelHeroesList() {
        marvelHeroDatabase
                .getMarvelHeroDao()
                .deleteAllUsers()
    }


}
