package com.example.android.guesstheword.screens.game



import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*


/**
 * ViewModel containing all the logic needed to run the game
 */
class GameViewModel : ViewModel() {
    private var mCountDownTimer: CountDownTimer? = null
    //The current Word
    val _word =MutableLiveData<String>()
    val word:LiveData<String>
        get() = _word


    // The current score
     val _score =MutableLiveData<Int>()
     val score:LiveData<Int>
        get() = _score
    // Game finished boolean variable
    val _gameFinished= MutableLiveData<Boolean>()
    val gameFinished:LiveData<Boolean>
        get() = _gameFinished
    //Timer value variable
    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime
    val _mTimerRunning= MutableLiveData<Boolean>()
    val mTimerRunning:LiveData<Boolean>
        get() = _mTimerRunning


//Timer object
    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 60000L
    private var mTimeLeftInMillis: Long = COUNTDOWN_TIME
    }
//    private val timer:CountDownTimer = TODO()

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    init {
        Log.i("GameViewModel", "GameViewModel created!")
        resetList()
        nextWord()

//Needed intializations
        _score.value=0
        _gameFinished.value=false


    }
    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }



    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()

        }
            _word.value = wordList.removeAt(0)
        }



    /** Methods for buttons presses **/

    fun onSkip() {
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value=(score.value)?.plus(1)
        nextWord()
    }
    fun timerHandler(){
        if (_mTimerRunning.value==false) {
            pauseTimer()
        } else {
            startTimer();
        }
    }

    private fun startTimer() {

        mCountDownTimer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {

                _currentTime.value = (millisUntilFinished / ONE_SECOND)
            }

            override fun onFinish() {
                _currentTime.value=DONE
                _gameFinished.value=true

                _mTimerRunning.value = false

            }
        }.start()
        _mTimerRunning.value = true

    }
//
//    private fun updateCountDownText() {
//        val minutes = (mTimeLeftInMillis / 1000).toInt() / 60
//        val seconds = (mTimeLeftInMillis / 1000).toInt() % 60
//        _currentTime.value=
//            java.lang.String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
//    }

    private fun pauseTimer() {
        mCountDownTimer?.cancel()
        _mTimerRunning.value = false
    }


    override fun onCleared() {
        super.onCleared()
        mCountDownTimer?.cancel()
    }
    fun onGameFinishedComplete(){
        _gameFinished.value=false

    }
} 