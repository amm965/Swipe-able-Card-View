# Swipe-able-Card-View
This library creates a custom implementation of the android card view which adds the ability to swipe the card and its content off the screen, and you'll be able to listen to card swipes and do some action.


![](https://media.giphy.com/media/0JXxqjhsGJUiEyNdqL/giphy.gif)

Implementation

***

### Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }


### Step 2. Add the dependency

    dependencies {
        implementation 'com.github.amm965:Swipe-able-Card-View:1.0.1'
    }


### Step 3. Add the card layout to your XML

    <com.bitorbit.swipeablecardview.SwipeableCardView
        android:id="@+id/swipeableCardView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:swipeThreshold="300"
        ...
        >
        ...
        ...
    </com.bitorbit.swipeablecardview.SwipeableCardView>

the `swipeThreshold` attribute controls the minimum distance that the card should be dragged right or left for the swipe to get registered and for the card to get swiped off the screen.


### Step 4. Listen to Swipe in your code

Add it in you Activity or Fragment code file

    swipeableCardView.addOnSwipedListener {
        //do something here
    }


You can find the code of the example shown in the GIF [here](https://github.com/amm965/Swipeable-CardView-Library-Example.git)
