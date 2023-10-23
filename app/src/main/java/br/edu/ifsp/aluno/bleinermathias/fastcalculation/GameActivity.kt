package br.edu.ifsp.aluno.bleinermathias.fastcalculation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import br.edu.ifsp.aluno.bleinermathias.fastcalculation.Extras.EXTRA_SETTINGS
import br.edu.ifsp.aluno.bleinermathias.fastcalculation.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity(), OnPlayGame {

    private val activityGameBinding: ActivityGameBinding by lazy{
        ActivityGameBinding.inflate(layoutInflater)
    }

    private lateinit var settings: Settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityGameBinding.root)

        setSupportActionBar(activityGameBinding.gameToolbarIn.gameToolbar)
        supportActionBar?.apply {
            title = getString(R.string.app_name)
            subtitle = getString(R.string.game)
        }

        // Recuperar as configurações que estão em Extras
        settings = intent.getParcelableExtra<Settings>(EXTRA_SETTINGS)?: Settings()
        //Toast.makeText(this, settings.toString(), Toast.LENGTH_SHORT).show()

        // Para mostrar fragment, cria transição de fragment com fragment mananger
        // Fragment será ocupado por um layout
        supportFragmentManager.beginTransaction().replace(R.id.gameFrameLayout,WelcomeFragment.newInstance(settings)).commit()
    }

    // Associar menu na tela
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_game, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.restartGameMenuItem -> {
                onPlayGame()
                true
            }
            R.id.exitMenuItem ->{
                finish()
                true
            }
            else -> {false}
        }
    }

    override fun onPlayGame() {
       // Instancia o game fragment
        supportFragmentManager.beginTransaction().replace(R.id.gameFrameLayout, GameFragment.newInstance(settings)).commit()
    }

}