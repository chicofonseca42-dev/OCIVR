package pt.ocivr.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CadastroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cadastro)

        // 🔹 Rede 54
        val btnRede54 = findViewById<Button>(R.id.rede54)
        btnRede54.setOnClickListener {
            feedback(btnRede54)
            btnRede54.postDelayed({
                startActivity(Intent(this, Rede54Activity::class.java))
            }, 120)
        }


        // 🔹 Rede 59
        val btnRede59 = findViewById<Button>(R.id.rede59)
        btnRede59.setOnClickListener {
            feedback(btnRede59)
            btnRede59.postDelayed({
                startActivity(Intent(this, Rede59Activity::class.java))
            }, 120)
        }


        // 🔹 Rede 76
        val btnRede76 = findViewById<Button>(R.id.rede76)
        btnRede76.setOnClickListener {
            feedback(btnRede76)
            btnRede76.postDelayed({
                startActivity(Intent(this, Rede76Activity::class.java))
            }, 120)
        }


        // 🔙 Voltar
        val btnVoltar = findViewById<Button>(R.id.btnVoltar)
        btnVoltar.setOnClickListener {
            feedback(btnVoltar)
            btnVoltar.postDelayed({
                finish()
            }, 120)
        }


        // Insets (mantém como estava)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }
    }

    // 👉 Feedback visual simples e seguro
    private fun feedback(btn: Button) {
        btn.alpha = 0.6f
        btn.postDelayed({
            btn.alpha = 1f
        }, 120)
    }
}


