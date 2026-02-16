package pt.ocivr.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val nomeFicheiroCache = "base_cache.csv"
    private val sheetUrl =
        "https://docs.google.com/spreadsheets/d/e/2PACX-1vSW12c1Df2sJq9F1vyWfBlnbVzcYhLBuzIG9CruJiTjwCxWaHO8UOYj11KX3hnGRn-yejD-r5dbe_X2/pub?gid=0&single=true&output=csv"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 🔹 Verificação automática da cache ao abrir
        verificarCacheAoAbrir()

        // 🔹 CONFIGURAÇÕES (⚙)
        val btnConfig = findViewById<ImageView>(R.id.btnConfig)

        btnConfig.setOnClickListener {

            val vibrator = getSystemService(android.content.Context.VIBRATOR_SERVICE) as android.os.Vibrator

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                val effect = android.os.VibrationEffect.createOneShot(
                    40,
                    android.os.VibrationEffect.DEFAULT_AMPLITUDE
                )
                vibrator.vibrate(effect)
            } else {
                vibrator.vibrate(40)
            }

            startActivity(Intent(this, ConfiguracoesActivity::class.java))
        }


        // 🔹 Agendamentos Semanais
        configurarBotaoLink(
            R.id.buttonAgendamentos,
            "https://docs.google.com/spreadsheets/d/1t3cZeesYG4JSvl9hoiTvMZWK5lEPpd1IHHKnS0-ZrsQ/edit?gid=1804031589#gid=1804031589"
        )

        // 🔹 Todos os Sites
        configurarBotaoLink(
            R.id.button2,
            "https://docs.google.com/spreadsheets/d/1MiuLS5EgRGWq8VY1CrCJ4zRlbv1Mm8Clg9QfgKZntK0/edit?gid=1220188742#gid=1220188742"
        )

        // 🔹 Prevenção
        configurarBotaoLink(
            R.id.button4,
            "https://docs.google.com/spreadsheets/d/1iRu6GlZ9GNlipGW2NXt17Qea-TTRKlssql5BOfWd-J4/edit?gid=1444605401#gid=1444605401"
        )

        // 🔹 CPL WEB
        configurarBotaoLink(
            R.id.button5,
            "http://10.18.25.100:91/Index.aspx"
        )

        // 🔹 RETA / SGA
        configurarBotaoLink(
            R.id.button6,
            "http://sga.telecom.pt/cgi-bin/sgaffm.cgi/SDA1?&SZ=10&ALM=Lista+de+Alarmes"
        )

        // 🔹 NETQ
        configurarBotaoLink(
            R.id.button7,
            "https://netq.telecom.pt/"
        )

        // 🔹 CIRCUITOS
        val btnCircuitos = findViewById<Button>(R.id.btnCircuitos)
        btnCircuitos.setOnClickListener {
            feedback(btnCircuitos)
            btnCircuitos.postDelayed({
                startActivity(Intent(this, PesquisaActivity::class.java))
            }, 120)
        }

        // 🔹 Cadastro
        val btnCadastro = findViewById<Button>(R.id.button3)
        btnCadastro.setOnClickListener {
            feedback(btnCadastro)
            btnCadastro.postDelayed({
                startActivity(Intent(this, CadastroActivity::class.java))
            }, 120)
        }
    }

    // 🔍 Verifica se já existe cache ao abrir a app
    private fun verificarCacheAoAbrir() {
        val ficheiroExiste = try {
            openFileInput(nomeFicheiroCache).close()
            true
        } catch (e: Exception) {
            false
        }

        if (!ficheiroExiste) {
            descarregarBaseInicial()
        }
    }

    // 🌐 Descarrega base automaticamente se não existir cache
    private fun descarregarBaseInicial() {

        val client = OkHttpClient()
        val request = Request.Builder().url(sheetUrl).build()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(
                        this@MainActivity,
                        "Sem ligação à internet. Base não disponível.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()

                if (body != null) {
                    openFileOutput(nomeFicheiroCache, MODE_PRIVATE).use {
                        it.write(body.toByteArray())
                    }
                }
            }
        })
    }

    // 🔗 Função genérica para abrir links
    private fun configurarBotaoLink(buttonId: Int, url: String) {
        val btn = findViewById<Button>(buttonId)
        btn.setOnClickListener {
            feedback(btn)
            btn.postDelayed({
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }, 120)
        }
    }

    // 👉 Feedback visual consistente
    private fun feedback(btn: Button) {
        btn.alpha = 0.6f
        btn.postDelayed({
            btn.alpha = 1f
        }, 120)
    }
}
