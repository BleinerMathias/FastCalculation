package br.edu.ifsp.aluno.bleinermathias.fastcalculation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import br.edu.ifsp.aluno.bleinermathias.fastcalculation.Extras.EXTRA_SETTINGS
import br.edu.ifsp.aluno.bleinermathias.fastcalculation.databinding.ActivityGameBinding
import br.edu.ifsp.aluno.bleinermathias.fastcalculation.databinding.FragmentWelcomeBinding
import kotlin.math.E


class WelcomeFragment : Fragment() {

    // Fragment será inflado dentro de uma activity associado a um container (framelayout)
    private lateinit var fragmentWelcomeBinding: FragmentWelcomeBinding
    private lateinit var settings: Settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            settings = it.getParcelable(EXTRA_SETTINGS)?: Settings()
        }

        // Alterar o menu de opções
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentWelcomeBinding = FragmentWelcomeBinding.inflate(inflater, container,false)

        "${getString(R.string.welcome)}, ${settings.playerName}!".also {
            fragmentWelcomeBinding.welcomeTextView.text = it
        }

        fragmentWelcomeBinding.playButton.setOnClickListener{
            (context as OnPlayGame).onPlayGame()
        }

        return fragmentWelcomeBinding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(settings: Settings) =
            WelcomeFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_SETTINGS, settings)
                }
            }
    }

    // re implementar onPrepare (mudança no ciclo de vida)
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.restartGameMenuItem).isVisible = false
    }
}