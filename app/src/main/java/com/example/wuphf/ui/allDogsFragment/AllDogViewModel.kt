package com.example.wuphf.ui.allDogsFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wuphf.data.DogRepository
import com.example.wuphf.data.local.model.Dog
import com.example.wuphf.data.remote.model.AllDogs
import com.example.wuphf.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllDogViewModel @Inject constructor(val dogRepository: DogRepository) : ViewModel() {

    var dogList: MutableLiveData<Resource<AllDogs>> = MutableLiveData()
    var search: AllDogs ?= null

    fun getAllDogs(){
        viewModelScope.launch {
            val response = dogRepository.getAllDogs()

            if(response.isSuccessful && response.body()?.status.equals("success")){
                search = response.body()
                dogList.postValue(Resource.Success(response.body()!!))
            }else{
                dogList.postValue(Resource.Error(response.message()))
            }
        }
    }
    fun add(dog: Dog){
        viewModelScope.launch {
            dogRepository.insertIntoFav(dog)
        }
    }

    fun remove(dog: Dog){
        viewModelScope.launch {
            dogRepository.removeFromFav(dog)
        }
    }
}