package pt.ocivr.app

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class AgendamentosTesteActivity : AppCompatActivity() {

    private val sheetId = "1t3cZeesYG4JSvl9hoiTvMZWK5lEPpd1IHHKnS0-ZrsQ"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agendamentos_teste)

        val viewPager = findViewById<ViewPager2>(R.id.viewPagerDias)
        val tituloTopo = findViewById<TextView>(R.id.textDiaTopo)

        val dias = listOf(
            "Segunda",
            "Terça",
            "Quarta",
            "Quinta",
            "Sexta",
            "Sábado",
            "Domingo"
        )

        // Adapter temporário vazio
        val adapter = DiasPagerAdapter(dias, List(7) { emptyList() })
        viewPager.adapter = adapter

        tituloTopo.text = dias[0]

        viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    tituloTopo.text = dias[position]
                }
            }
        )

        buscarDados()
    }

    private fun buscarDados() {

        val url =
            "https://docs.google.com/spreadsheets/d/$sheetId/gviz/tq?tqx=out:json&sheet=Semana%20atual"

        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        Thread {
            try {
                val response = client.newCall(request).execute()
                val body = response.body?.string()

                if (body != null) {

                    val start = body.indexOf("{")
                    val end = body.lastIndexOf("}")

                    val json = body.substring(start, end + 1)

                    val jsonObject = JSONObject(json)
                    val table = jsonObject.getJSONObject("table")
                    val rows = table.getJSONArray("rows")
                    Log.d("SHEET", "Total rows: ${rows.length()}")



                    val dadosPorDia = MutableList(7) { mutableListOf<String>() }

                    // Ignorar primeiras 4 linhas
                    for (i in 4 until rows.length()) {

                        val row = rows.getJSONObject(i)
                        Log.d("SHEET", "Linha $i -> ${row.toString()}")
                        val cells = row.getJSONArray("c")

                        for (col in 0 until minOf(7, cells.length())) {

                            val cell = cells.optJSONObject(col)
                            val valor = cell?.opt("v")?.toString() ?: ""

                            if (valor.isNotBlank() && valor != "null") {
                                dadosPorDia[col].add(valor)
                            }
                        }
                    }

                    runOnUiThread {
                        atualizarAdapter(dadosPorDia)
                    }
                }

            } catch (e: Exception) {
                Log.e("SHEET", "Erro: ${e.message}")
            }
        }.start()
    }

    private fun atualizarAdapter(dados: List<List<String>>) {

        val viewPager = findViewById<ViewPager2>(R.id.viewPagerDias)

        val dias = listOf(
            "Segunda",
            "Terça",
            "Quarta",
            "Quinta",
            "Sexta",
            "Sábado",
            "Domingo"
        )

        val adapter = DiasPagerAdapter(dias, dados)
        viewPager.adapter = adapter
    }
}