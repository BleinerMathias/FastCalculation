package br.edu.ifsp.aluno.bleinermathias.fastcalculation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import br.edu.ifsp.aluno.bleinermathias.fastcalculation.Extras.EXTRA_SETTINGS
import br.edu.ifsp.aluno.bleinermathias.fastcalculation.databinding.FragmentGameBinding
import br.edu.scl.ifsp.sdm.fastcalculation.CalculationGame


class GameFragment : Fragment() {

    private lateinit var fragmentGameBinding: FragmentGameBinding

    private lateinit var settings: Settings
    private lateinit var calculationGame: CalculationGame
    private var currentRound:CalculationGame.Round? = null
    private var startRoundTime = 0L
    private var totalGameTime = 0L
    private var hits = 0

    private lateinit var resultGame:ResultGame

    // passar pela próxima rodada automaticamente
    private val roundDeadlineHandler = object: Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            // TOda vez que tirar da fila
            super.handleMessage(msg)
            totalGameTime += settings.roundsInterval
            play()

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            settings = it.getParcelable(EXTRA_SETTINGS)?: Settings()
        }

        // Instancia novo jogo com os rounds selecionados
        calculationGame = CalculationGame(settings.rounds)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        fragmentGameBinding = FragmentGameBinding.inflate(inflater,container, false);

        val onClickListener = View.OnClickListener {
            val value = (it as Button).text.toString().toInt()
            if(value == currentRound?.answer){
                totalGameTime += System.currentTimeMillis() - startRoundTime
                hits++
            }else{
                totalGameTime += settings.roundsInterval
                hits--
            }
            roundDeadlineHandler.removeMessages(MSG_ROUND_DEADLINE)
            play()
        }

        fragmentGameBinding.apply {
            alternativeOneButton.setOnClickListener(onClickListener)
            alternativeTwoButton.setOnClickListener(onClickListener)
            alternativeThreeButton.setOnClickListener(onClickListener)
        }
        play()

        return fragmentGameBinding.root
    }

    companion object {

        private const val MSG_ROUND_DEADLINE = 0

        @JvmStatic
        fun newInstance(settings: Settings) =
            GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_SETTINGS,settings)
                }
            }
    }

    private fun settingGameView(currentRound:CalculationGame.Round){
        fragmentGameBinding.apply {
            "Round: ${currentRound!!.round}/${settings.rounds}".also{
                roundsTextView.text = it
            }
            questionTextView.text = currentRound!!.question
            alternativeOneButton.text = currentRound!!.alt1.toString()
            alternativeTwoButton.text = currentRound!!.alt2.toString()
            alternativeThreeButton.text = currentRound!!.alt3.toString()
        }

    }

    private fun setVisibilityButtons(propVisibility: Int){
        fragmentGameBinding.apply {
            alternativeOneButton.visibility = propVisibility
            alternativeTwoButton.visibility = propVisibility
            alternativeThreeButton.visibility = propVisibility
        }
    }

    private fun play(){
        currentRound = calculationGame.nextRound()
        if(currentRound != null){
            settingGameView(currentRound!!)
            startRoundTime = System.currentTimeMillis()
            roundDeadlineHandler.sendEmptyMessageDelayed(MSG_ROUND_DEADLINE, settings.roundsInterval)
            setVisibilityButtons(View.VISIBLE)
        }else{
            with(fragmentGameBinding){
                roundsTextView.text = getString(R.string.points)

                val points = hits * 10f/ (totalGameTime/1000L)
                "%.1f".format(points).also {
                    questionTextView.text = it
                    resultGame = ResultGame(it)
                }

                val resultGameFragment = ResultGameFragment.newInstance(resultGame)
                val fragmentManager = requireActivity().supportFragmentManager
                val transaction = fragmentManager.beginTransaction()

                transaction.replace(R.id.gameFrameLayout, resultGameFragment).commit()

            }

            //setVisibilityButtons(View.GONE) // Alterar visibilidade dos botões
        }
    }
}