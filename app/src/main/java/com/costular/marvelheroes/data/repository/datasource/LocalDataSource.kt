package com.costular.marvelheroes.data.repository.datasource

import com.costular.marvelheroes.data.db.MarvelHeroDatabase
import com.costular.marvelheroes.data.model.MarvelHero
import com.costular.marvelheroes.data.model.mapper.MarvelHeroMapper
import com.costular.marvelheroes.data.repository.MarvelHeroesRepository
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable


class LocalDataSource (val marvelHeroDatabase: MarvelHeroDatabase,
                         val marvelHeroesMapper: MarvelHeroMapper): MarvelHeroesRepository {

   override fun getMarvelHeroesList(): Observable<List<MarvelHeroEntity>> =
                    marvelHeroDatabase
                            .getMarvelHeroDao()
                            .getAllUsers()
                            .toObservable()


//

   // fun saveMarvelHeros(){}
}
