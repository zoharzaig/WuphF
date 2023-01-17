package com.example.wuphf.ui.allDogsFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.wuphf.R
import com.example.wuphf.data.local.model.Dog
import com.example.wuphf.util.Resource
import com.yuyakaido.android.cardstackview.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SwipingFragment(): Fragment(), CardStackListener {

    private lateinit var cardStackView : CardStackView
    private lateinit var error_txt : TextView
    val allDogViewModels: AllDogViewModel by viewModels()
    private val cardStackAdapter: CardStackAdapter by lazy { CardStackAdapter(requireContext()) }
    var list = mutableListOf<String>()
    lateinit var manager: CardStackLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_swiping, container, false)
        cardStackView = v.findViewById(R.id.card_stack_view)
        error_txt = v.findViewById(R.id.error_txt)
        return v.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        allDogViewModels.getAllDogs()
        observeUI()
    }
    fun observeUI(){
        allDogViewModels.dogList.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    cardStackView.visibility = View.VISIBLE
                    error_txt.visibility = View.GONE
                    list = it.data?.message?.asSequence()?.shuffled()?.take(it.data.message.size)!!.toMutableList()

                    cardStackAdapter.changeData(list) //it.data?.message?.asSequence()?.shuffled()?.take(it.data.message.size)!!.toList())
                }
                is Resource.Error -> {
                    cardStackView.visibility = View.GONE
                    error_txt.visibility = View.VISIBLE
                    error_txt.text = "Some Error Occured"
                }
            }
        }
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {}

    override fun onCardSwiped(direction: Direction?) {
        if(direction.toString().equals("Right", true)){
            allDogViewModels.add(Dog(list.get(manager.topPosition-1)))
        }
        else if(direction.toString() == "Left"){
            allDogViewModels.remove(Dog(list.get(manager.topPosition-1)))
        }
    }

    override fun onCardRewound() {
    }

    override fun onCardCanceled() {
    }

    override fun onCardAppeared(view: View?, position: Int) {
    }

    override fun onCardDisappeared(view: View?, position: Int) {
    }

    private fun initialize() {
        manager = CardStackLayoutManager(context, this)

        manager.setStackFrom(StackFrom.None)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(true)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())
        cardStackView.layoutManager = manager
        cardStackView.adapter = cardStackAdapter
        cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }
}