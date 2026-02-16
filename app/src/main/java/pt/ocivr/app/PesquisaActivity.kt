package pt.ocivr.app

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import okhttp3.*
import java.io.IOException

class PesquisaActivity : AppCompatActivity() {

    private val sheetUrl =
        "https://docs.google.com/spreadsheets/d/e/2PACX-1vSW12c1Df2sJq9F1vyWfBlnbVzcYhLBuzIG9CruJiTjwCxWaHO8UOYj11KX3hnGRn-yejD-r5dbe_X2/pub?gid=0&single=true&output=csv"

    private val nomeFicheiroCache = "base_cache.csv"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pesquisa)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnPesquisar = findViewById<Button>(R.id.btnPesquisar)
        val etNumero = findViewById<EditText>(R.id.etNumero)
        val container = findViewById<LinearLayout>(R.id.containerResultados)

        btnPesquisar.setOnClickListener {

            // Fechar teclado
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

            val numeroDigitado = etNumero.text.toString().trim()

            if (numeroDigitado.isEmpty()) {
                Toast.makeText(this, "Introduz um número", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            container.removeAllViews()

            buscarResultadosAgrupados(numeroDigitado) { resultados ->
                runOnUiThread {

                    if (resultados.isEmpty()) {
                        Toast.makeText(this, "Número não encontrado", Toast.LENGTH_SHORT).show()
                        return@runOnUiThread
                    }

                    for (item in resultados) {
                        val card = criarCard(
                            numero = item["numero"] ?: "",
                            odf = item["odf"] ?: "",
                            posicao = item["posicoes"] ?: "",
                            mfr = item["mfr"] ?: "",
                            sb = item["sb"] ?: "",
                            rede = item["rede"] ?: ""
                        )
                        container.addView(card)
                    }
                }
            }
        }
    }

    private fun buscarResultadosAgrupados(
        numero: String,
        callback: (List<Map<String, String>>) -> Unit
    ) {

        val cacheLocal = lerCache()

        if (cacheLocal != null) {
            processarCSV(cacheLocal, numero, callback)
            return
        }

        val client = OkHttpClient()
        val request = Request.Builder().url(sheetUrl).build()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                callback(emptyList())
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()

                if (body != null) {
                    guardarCache(body)
                    processarCSV(body, numero, callback)
                } else {
                    callback(emptyList())
                }
            }
        })
    }

    private fun processarCSV(
        body: String,
        numero: String,
        callback: (List<Map<String, String>>) -> Unit
    ) {

        val grupos = mutableMapOf<String, MutableList<String>>()
        val dadosBase = mutableMapOf<String, List<String>>()

        val linhas = body.split("\n")

        for (linha in linhas.drop(1)) {
            val colunas = linha.split(",")

            if (colunas.size >= 7 &&
                colunas[0].trim().equals(numero.trim(), ignoreCase = true)
            ) {

                val chave =
                    "${colunas[5]}_${colunas[4]}_${colunas[3]}_${colunas[6]}"

                if (!grupos.containsKey(chave)) {
                    grupos[chave] = mutableListOf()
                    dadosBase[chave] = colunas
                }

                grupos[chave]?.add(colunas[2])
            }
        }

        val resultadoFinal = mutableListOf<Map<String, String>>()

        for (chave in grupos.keys) {
            val base = dadosBase[chave]!!

            val posicoes = grupos[chave]!!
                .sortedBy { it.toIntOrNull() ?: 0 }
                .joinToString(" e ")

            val sbLimpo = base[4]
                .replace("SB", "", ignoreCase = true)
                .trim()

            resultadoFinal.add(
                mapOf(
                    "numero" to base[0],
                    "odf" to base[5],
                    "sb" to sbLimpo,
                    "mfr" to base[3],
                    "rede" to base[6],
                    "posicoes" to posicoes
                )
            )
        }

        callback(resultadoFinal)
    }

    private fun guardarCache(conteudo: String) {
        openFileOutput(nomeFicheiroCache, MODE_PRIVATE).use {
            it.write(conteudo.toByteArray())
        }
    }

    private fun lerCache(): String? {
        return try {
            openFileInput(nomeFicheiroCache).bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            null
        }
    }

    private fun criarCard(
        numero: String,
        odf: String,
        posicao: String,
        mfr: String,
        sb: String,
        rede: String
    ): CardView {

        val card = CardView(this)
        card.radius = 24f
        card.cardElevation = 12f
        card.setCardBackgroundColor(Color.WHITE)

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, 0, 0, 40)
        card.layoutParams = params

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(40, 40, 40, 40)

        layout.addView(criarTexto("Número: $numero", true))
        layout.addView(criarTexto("ODF: $odf"))
        layout.addView(criarTexto("SB: $sb"))
        layout.addView(criarTexto("MFR: $mfr"))
        layout.addView(criarTexto("Posição: $posicao"))
        layout.addView(criarTexto("Rede: $rede"))

        card.addView(layout)

        return card
    }

    private fun criarTexto(texto: String, titulo: Boolean = false): TextView {
        val tv = TextView(this)
        tv.text = texto
        tv.setTextColor(Color.BLACK)
        tv.textSize = if (titulo) 18f else 16f
        if (titulo) tv.setPadding(0, 0, 0, 20)
        return tv
    }
}
