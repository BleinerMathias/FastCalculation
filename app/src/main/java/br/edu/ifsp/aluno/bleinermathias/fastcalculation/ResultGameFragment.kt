package br.edu.ifsp.aluno.bleinermathias.fastcalculation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.edu.ifsp.aluno.bleinermathias.fastcalculation.databinding.FragmentResultGameBinding

class ResultGameFragment : Fragment() {

    private lateinit var resultGameFragmentBinding: FragmentResultGameBinding

    private lateinit var resultGame: ResultGame

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            resultGame = it.getParcelable(Extras.EXTRA_RESULT_GAME)?: ResultGame()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        resultGameFragmentBinding = FragmentResultGameBinding.inflate(layoutInflater,container,false)

        resultGameFragmentBinding.apply {
            playAgainButton.setOnClickListener{
                (context as OnPlayGame).onPlayGame()
            }
            scoreTextView.text = resultGame.points
        }

        return resultGameFragmentBinding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(resultGame: ResultGame) =
            ResultGameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(Extras.EXTRA_RESULT_GAME,resultGame)
                }
            }
    }

}