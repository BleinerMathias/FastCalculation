package br.edu.ifsp.aluno.bleinermathias.fastcalculation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.TextView
import br.edu.ifsp.aluno.bleinermathias.fastcalculation.Extras.EXTRA_SETTINGS
import br.edu.ifsp.aluno.bleinermathias.fastcalculation.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    // instanciado somente no primeiro uso
    private val activitySettingsBinding: ActivitySettingsBinding by lazy {
        ActivitySettingsBinding.inflate(layoutInflater) // em Java getLayoutInflater
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activitySettingsBinding.root)

        // Cria uma toolbar -> precisa dizer para a activity
        setSupportActionBar(activitySettingsBinding.gameToolbarIn.gameToolbar)
        supportActionBar?.title = getString(R.string.app_name)
        supportActionBar?.subtitle = getString(R.string.settings)

        // Apply é função de T->UNIT | RUN T->R
        activitySettingsBinding.apply {
            letsGoButton.setOnClickListener{
                val settings = Settings(
                    playerNameEditText.text.toString(),
                    (roundsSpinner.selectedView as TextView).text.toString().toInt(),
                    roundIntervalRadioGroup.run {
                      findViewById<RadioButton>(checkedRadioButtonId).text.toString().toLong()*1000L
                    }
                )

                // Abrir uma nova tela de this para Game
                val gameActivityIntent = Intent(this@SettingsActivity, GameActivity::class.java)
                gameActivityIntent.putExtra(EXTRA_SETTINGS, settings)
                startActivity(gameActivityIntent)
                finish()
            }
        }

    }
}