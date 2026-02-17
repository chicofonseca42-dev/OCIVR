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

    // Circuitos
    private val nomeFicheiroCache = "base_cache.csv"
    private val sheetUrl =
        "https://docs.google.com/spreadsheets/d/e/2PACX-1vSW12c1Df2sJq9F1vyWfBlnbVzcYhLBuzIG9CruJiTjwCxWaHO8UOYj11KX3hnGRn-yejD-r5dbe_X2/pub?gid=0&single=true&output=csv"

    // Rede Móvel
    private val sheetUrlMovel =
        "https://docs.google.com/spreadsheets/d/e/2PACX-1vTOTDRMPpRIaj-TZCXRtO_2bZhnqdFxyua9ZvKdlG5tZ12PYHKxXfWcxKItuM4HUxJMl4mqjRaol7i_/pub?gid=1608582643&single=true&output=csv"
    private val ficheiroMovel = "rede_movel_cache.csv"

    // Rede SIRESP
    private val sheetUrlSiresp =
        "https://docs.google.com/spreadsheets/d/e/2PACX-1vTOTDRMPpRIaj-TZCXRtO_2bZhnqdFxyua9ZvKdlG5tZ12PYHKxXfWcxKItuM4HUxJMl4mqjRaol7i_/pub?gid=293131231&single=true&output=csv"
    private val ficheiroSiresp = "rede_siresp_cache.csv"

    // Rede Fixa
    private val sheetUrlFixa =
        "https://docs.google.com/spreadsheets/d/e/2PACX-1vTOTDRMPpRIaj-TZCXRtO_2bZhnqdFxyua9ZvKdlG5tZ12PYHKxXfWcxKItuM4HUxJMl4mqjRaol7i_/pub?gid=836241093&single=true&output=csv"
    private val ficheiroFixa = "rede_fixa_cache.csv"

    private lateinit var txtUltimaAtualizacao: TextView
    private lateinit var txtEstadoBase: TextView
    private lateinit var txtTotalRegistos: TextView

    private lateinit var txtCircuitos: TextView
    private lateinit var txtMovel: TextView
    private lateinit var txtSiresp: TextView
    private lateinit var txtFixa: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracoes)

        txtUltimaAtualizacao = findViewById(R.id.txtUltimaAtualizacao)
        txtEstadoBase = findViewById(R.id.txtEstadoBase)
        txtTotalRegistos = findViewById(R.id.txtTotalRegistos)

        txtCircuitos = findViewById(R.id.txtCircuitos)
        txtMovel = findViewById(R.id.txtMovel)
        txtSiresp = findViewById(R.id.txtSiresp)
        txtFixa = findViewById(R.id.txtFixa)

        val btnAtualizarBase = findViewById<Button>(R.id.btnAtualizarBase)
        val btnLimparCache = findViewById<Button>(R.id.btnLimparCache)

        atualizarTextoData()
        verificarEstadoBase()

        btnAtualizarBase.setOnClickListener {
            atualizarBase()
        }

        btnLimparCache.setOnClickListener {

            deleteFile(nomeFicheiroCache)
            deleteFile(ficheiroMovel)
            deleteFile(ficheiroSiresp)
            deleteFile(ficheiroFixa)

            Toast.makeText(this, "Todas as bases apagadas", Toast.LENGTH_SHORT).show()
            verificarEstadoBase()
        }
    }

    private fun atualizarBase() {

        val client = OkHttpClient()

        val bases = listOf(
            Pair(sheetUrl, nomeFicheiroCache),
            Pair(sheetUrlMovel, ficheiroMovel),
            Pair(sheetUrlSiresp, ficheiroSiresp),
            Pair(sheetUrlFixa, ficheiroFixa)
        )

        var concluido = 0

        for (base in bases) {

            val request = Request.Builder().url(base.first).build()

            client.newCall(request).enqueue(object : okhttp3.Callback {

                override fun onFailure(call: okhttp3.Call, e: IOException) {
                    runOnUiThread {
                        Toast.makeText(
                            this@ConfiguracoesActivity,
                            "Erro ao atualizar uma das bases",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {

                    val body = response.body?.string()

                    if (body != null) {
                        openFileOutput(base.second, MODE_PRIVATE).use {
                            it.write(body.toByteArray())
                        }
                    }

                    concluido++

                    if (concluido == bases.size) {

                        val formato = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                        val data = formato.format(Date())
                        guardarDataAtualizacao(data)

                        runOnUiThread {
                            atualizarTextoData()
                            verificarEstadoBase()
                            Toast.makeText(
                                this@ConfiguracoesActivity,
                                "Todas as bases atualizadas com sucesso",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            })
        }
    }

    private fun verificarEstadoBase() {

        val bases = listOf(
            Triple(nomeFicheiroCache, txtCircuitos, "Circuitos"),
            Triple(ficheiroMovel, txtMovel, "Rede Móvel"),
            Triple(ficheiroSiresp, txtSiresp, "Rede Siresp"),
            Triple(ficheiroFixa, txtFixa, "Rede Fixa")
        )

        var basesProntas = 0

        for (base in bases) {

            val existe = try {
                openFileInput(base.first).close()
                true
            } catch (e: Exception) {
                false
            }

            if (existe) {
                base.second.text = "✅ ${base.third}: OK"
                base.second.setTextColor(Color.parseColor("#4CAF50"))
                basesProntas++
            } else {
                base.second.text = "❌ ${base.third}: Em falta"
                base.second.setTextColor(Color.parseColor("#F44336"))
            }
        }

        if (basesProntas == bases.size) {
            txtEstadoBase.text = "🟢 Bases prontas ($basesProntas/4)"
            txtEstadoBase.setTextColor(Color.parseColor("#4CAF50"))
        } else if (basesProntas > 0) {
            txtEstadoBase.text = "🟡 Bases parciais ($basesProntas/4)"
            txtEstadoBase.setTextColor(Color.parseColor("#FFC107"))
        } else {
            txtEstadoBase.text = "🔴 Nenhuma base disponível (0/4)"
            txtEstadoBase.setTextColor(Color.parseColor("#F44336"))
        }

        txtTotalRegistos.text = ""
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
