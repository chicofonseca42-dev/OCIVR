package pt.ocivr.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class TodosSitesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todos_sites)

        val btnRedeMovel = findViewById<Button>(R.id.btnRedeMovel)
        val btnRedeSiresp = findViewById<Button>(R.id.btnRedeSiresp)
        val btnRedeFixa = findViewById<Button>(R.id.btnRedeFixa)

        btnRedeMovel.setOnClickListener {
            startActivity(Intent(this, PesquisaRedeMovelActivity::class.java))
        }

        btnRedeSiresp.setOnClickListener {
            startActivity(Intent(this, PesquisaRedeSirespActivity::class.java))
        }
        btnRedeFixa.setOnClickListener {
            startActivity(Intent(this, PesquisaRedeFixaActivity::class.java))
        }
    }
}


