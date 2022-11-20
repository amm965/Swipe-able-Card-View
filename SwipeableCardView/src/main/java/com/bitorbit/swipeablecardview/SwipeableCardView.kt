package com.bitorbit.swipeablecardview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import androidx.cardview.widget.CardView
import kotlin.math.abs


class SwipeableCardView(
    context: Context,
    attrs: AttributeSet?,
) : CardView(
    context,
    attrs,
) {

   private var callback: () -> Unit = {}

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.SwipeableCardView, 0, 0).apply {
            try {
                minSwipeDistance = getInteger(R.styleable.SwipeableCardView_swipeThreshold, 250)
            } finally {
                recycle()
            }
        }

        setOnTouchListener { view, event ->
            val displayMetrics = resources.displayMetrics
            val cardWidth = view?.width
            //getting the start point of the card
            val cardStart = (displayMetrics.widthPixels.toFloat() / 2) - ((cardWidth ?: 1) / 2)

            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    originalRawX = event.rawX
                }
                MotionEvent.ACTION_MOVE -> {
                    moveCard(event, cardStart)
                }
                MotionEvent.ACTION_UP -> {
                    //calculates the distance that the card has been moved and trigger the action if the distance is bigger than the threshold
                    finalRawX = event.rawX
                    val distance = finalRawX - originalRawX
                    if (abs(distance) > minSwipeDistance) {
                        moveCarOutOfScreen(distance, displayMetrics, cardWidth)
                    } else {
                        returnCardToOrgPosition(cardStart)
                    }
                }
            }
            view?.performClick()
            return@setOnTouchListener true
        }
    }

    /**
     * moving the card from the current x position to a position out of the screen borders
     *
     * and after the card is invisible the onSwipeListener will be called
     * */
    private fun moveCarOutOfScreen(
        distance: Float,
        displayMetrics: DisplayMetrics,
        cardWidth: Int?
    ) {
        val hidingAnimationDistance1 = if (distance > 0) {
            displayMetrics.widthPixels.toFloat() + 100
        } else {
            (-(cardWidth ?: 0) - 100).toFloat()
        }
        this.animate().x(hidingAnimationDistance1)
            .setDuration(100)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    visibility = GONE
                    callback.invoke()
                }
            }).start()
    }

    /**
     * returns the card view to it's starting position
     * */
    private fun returnCardToOrgPosition(cardStart: Float) {
        animate().x(cardStart)
            .setDuration(100)
            .start()
    }

    /**
     * moves the card along the touch of the user
     * */
    private fun moveCard(event: MotionEvent, cardStart: Float) {
        val newRawX = event.rawX
        animate().x(
            cardStart + (newRawX - originalRawX)
        )
            .setDuration(0)
            .start()
    }

    /**
     * sends a callback when the card is swiped over the activation threshold, and removed from the screen
     * */
    fun addOnSwipedListener(callback: () -> Unit){
        this.callback = callback
    }

    companion object {
        //original x value where the user started the swipe action
        private var originalRawX = 0f
        //the last x value where the user let go of the card view
        private var finalRawX = 0f
        //the minimum swipe distance to trigger the remove and action of the card
        private var minSwipeDistance = 250
    }

}