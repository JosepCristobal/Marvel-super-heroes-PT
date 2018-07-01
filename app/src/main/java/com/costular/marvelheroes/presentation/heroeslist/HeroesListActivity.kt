package com.costular.marvelheroes.presentation.heroeslist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.Toast
import com.costular.marvelheroes.R
import com.costular.marvelheroes.R.id.heroesListLoading
import com.costular.marvelheroes.di.modules.GetMarvelHeroesListModule
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import com.costular.marvelheroes.presentation.MainApp
import com.costular.marvelheroes.presentation.util.Navigator
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class HeroesListActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var heroesListViewModel: HeroesListViewModel

    private val adapter = HeroesListAdapter { hero, image -> goToHeroDetail(hero, image) }
    //private val adapter = HeroesListAdapter{ goToHeroDetail(it,image: View)}

    //@Inject
    //lateinit var presenter: HeroesListPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setUp()
        setUpRecycler()
        setUpViewModel()
    }


    fun inject() {
        (application as MainApp).component.inject(this)
        /*DaggerGetMarvelHeroesListComponent.builder()
                .applicationComponent((application as MainApp).component)
                .getMarvelHeroesListModule(GetMarvelHeroesListModule(this))
                .build()
                .inject(this)*/
    }

    //private fun setUp() {
       // setUpRecycler()
       // presenter.loadMarvelHeroes()
    //}

    private fun setUpRecycler() {
        //adapter = HeroesListAdapter { hero, image -> goToHeroDetail(hero, image) }
        heroesListRecycler.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        heroesListRecycler.itemAnimator = DefaultItemAnimator()
        heroesListRecycler.adapter = adapter
    }


    private fun setUpViewModel() {
        heroesListViewModel = ViewModelProviders.of(this, viewModelFactory).get(HeroesListViewModel::class.java)
        bindEvents()
        heroesListViewModel.loadMarvelHeroesList()
    }

    private fun bindEvents() {
        heroesListViewModel.isLoadingState.observe(this, Observer { isLoading ->
            isLoading?.let {
                showLoading(it)
            }
        })

        heroesListViewModel.marvelHeroListState.observe(this, Observer { userList ->
            userList?.let {
                onheroListLoaded(it)
            }
        })
    }

    private fun onheroListLoaded(heroList: List<MarvelHeroEntity>) {
       adapter.submitList(heroList)
    }



    private fun goToHeroDetail(hero: MarvelHeroEntity, image: View) {
        navigator.goToHeroDetail(this, hero, image)
    }





    private fun showLoading(isLoading: Boolean) {
        heroesListLoading.visibility  = if(isLoading) View.VISIBLE else View.GONE
    }


   // private fun onUserClicked(marvelHeroEntity: MarvelHeroEntity) {
      //  navigator.goToHeroDetail(this, marvelHeroEntity, image: View)
   // }


    /* override fun showLoading(isLoading: Boolean) {
       heroesListLoading.visibility = if(isLoading) View.VISIBLE else View.GONE
   }

   override fun showHeroesList(heroes: List<MarvelHeroEntity>) {
       adapter.swapData(heroes)
   }

   override fun onDestroy() {
       presenter.destroy()
       super.onDestroy()
   }

   override fun showError(message: String) {
       Toast.makeText(this, message, Toast.LENGTH_LONG).show()
   }

   override fun showError(messageRes: Int) {
       Toast.makeText(this, messageRes, Toast.LENGTH_LONG).show()
   }*/
}
