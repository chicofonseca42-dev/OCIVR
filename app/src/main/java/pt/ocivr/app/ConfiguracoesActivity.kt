package pt.ocivr.app

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ConfiguracoesActivity : AppCompatActivity() {

    private val nomeFicheiroCache = "base_cache.csv"
    private val sheetUrl =
        "https://docs.google.com/spreadsheets/d/e/2PACX-1vSW12c1Df2sJq9F1vyWfBlnbVzcYhLBuzIG9CruJiTjwCxWaHO8UOYj11KX3hnGRn-yejD-r5dbe_X2/pub?gid=0&single=true&output=csv"

    private lateinit var txtUltimaAtualizacao: TextView
    private lateinit var txtEstadoBase: TextView
    private lateinit var txtTotalRegistos: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracoes)

        txtUltimaAtualizacao = findViewById(R.id.txtUltimaAtualizacao)
        txtEstadoBase = findViewById(R.id.txtEstadoBase)
        txtTotalRegistos = findViewById(R.id.txtTotalRegistos)

        val btnAtualizarBase = findViewById<Button>(R.id.btnAtualizarBase)
        val btnLimparCache = findViewById<Button>(R.id.btnLimparCache)

        atualizarTextoData()
        verificarEstadoBase()

        btnAtualizarBase.setOnClickListener {
            atualizarBase()
        }

        btnLimparCache.setOnClickListener {
            deleteFile(nomeFicheiroCache)
            Toast.makeText(this, "Cache apagada", Toast.LENGTH_SHORT).show()
            verificarEstadoBase()
        }
    }

    private fun atualizarBase() {

        val client = OkHttpClient()
        val request = Request.Builder().url(sheetUrl).build()

        client.newCall(request).enqueue(object : okhttp3.Callback {

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@ConfiguracoesActivity, "Erro ao atualizar", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                val body = response.body?.string()

                if (body != null) {
                    openFileOutput(nomeFicheiroCache, MODE_PRIVATE).use {
                        it.write(body.toByteArray())
                    }

                    val formato = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                    val data = formato.format(Date())
                    guardarDataAtualizacao(data)

                    runOnUiThread {
                        atualizarTextoData()
                        verificarEstadoBase()
                        Toast.makeText(
                            this@ConfiguracoesActivity,
                            "Base atualizada com sucesso",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }

    private fun verificarEstadoBase() {

        val ficheiroExiste = try {
            openFileInput(nomeFicheiroCache).close()
            true
        } catch (e: Exception) {
            false
        }

        if (ficheiroExiste) {

            txtEstadoBase.text = "🟢 Base pronta"
            txtEstadoBase.setTextColor(Color.parseColor("#4CAF50"))

            try {
                val linhas = openFileInput(nomeFicheiroCache)
                    .bufferedReader()
                    .readLines()

                val total = if (linhas.isNotEmpty()) linhas.size - 1 else 0
                txtTotalRegistos.text = "Total de registos: $total"

            } catch (e: Exception) {
                txtTotalRegistos.text = "Total de registos: --"
            }

        } else {

            txtEstadoBase.text = "🔴 Base não disponível"
            txtEstadoBase.setTextColor(Color.parseColor("#F44336"))
            txtTotalRegistos.text = "Total de registos: --"
        }
    }

    private fun guardarDataAtualizacao(data: String) {
        val prefs = getSharedPreferences("config", Context.MODE_PRIVATE)
        prefs.edit().putString("ultima_atualizacao", data).apply()
    }

    private fun obterDataAtualizacao(): String {
        val prefs = getSharedPreferences("config", Context.MODE_PRIVATE)
        return prefs.getString("ultima_atualizacao", "--") ?: "--"
    }

    private fun atualizarTextoData() {
        txtUltimaAtualizacao.text =
            "Última atualização: ${obterDataAtualizacao()}"
    }
}
