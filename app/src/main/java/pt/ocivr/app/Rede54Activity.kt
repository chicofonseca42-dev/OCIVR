package pt.ocivr.app

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Rede54Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rede54)

        val main = findViewById<View>(R.id.main)
        val grid = findViewById<GridLayout>(R.id.gridRede)
        val btnVoltar = findViewById<Button>(R.id.btnVoltar)

        // 👉 ORDEM VISUAL FIXA
        val botoes = listOf(
            "54RG01","54RG02","54RG03",
            "54AM01","54AS01","54CS01",
            "54GS01","54LG01","54LG02",
            "54MB01","54MB02","54MB03",
            "54MJ01","54MJ02","54PB01",
            "54PN01","54RD01","54RD02",
            "54SL01","54SL02","54SM01",
            "54SM02","54VL01","54PC01",
            "54MZ01","54MZ02","54PJ01",
            "54PS01","54SC01","54SP01",
            "54ST01","54SZ01","54SZ02",
            "54TB01","54TR01","54TR02",
            "54PI01"
        )

        // 👉 BOTÃO → LINK
        val links = mapOf(
            "54RG01" to "https://docs.google.com/spreadsheets/d/18ypESvYdj4lKWrl1arxMiZb08WwyYuPu/edit?gid=1509660899",
            "54RG02" to "https://docs.google.com/spreadsheets/d/145vRyrWeXdpj9efOTctwDCgGYN0oiX_4/edit?gid=572026419",
            "54RG03" to "https://docs.google.com/spreadsheets/d/1PlhvpI5PJIJwpvYguyL5AtMGq3RGZVMZ/edit?gid=442194035",
            "54AM01" to "https://docs.google.com/spreadsheets/d/1I_meYSLVlHOTt2V3VLQV-T7QQOVErM7G/edit?gid=324992055",
            "54AS01" to "https://docs.google.com/spreadsheets/d/1k9iHOYTacya6NUADqrfJ7R77morCo01J/edit?gid=342297860",
            "54CS01" to "https://docs.google.com/spreadsheets/d/18R_vrQD5n3m7a4_cEv87bAI0aun4NVQJ/edit?gid=1159019424",
            "54GS01" to "https://docs.google.com/spreadsheets/d/1Ehl3Gd0FOBfcsy-yG66sYozjXhUN9Zr5/edit?gid=1925410218",
            "54LG01" to "https://docs.google.com/spreadsheets/d/18I1da34pDIb0LgJjTbL5UZATwP2p5VP8/edit?gid=759085587",
            "54LG02" to "https://docs.google.com/spreadsheets/d/1tTFSLVIPMG5x3W9IuAsFtFziz6aaUGHd/edit?gid=864754503",
            "54MB01" to "https://docs.google.com/spreadsheets/d/1NyrCeQlcqsmqhI_wRrxZ5dxIumu5SgsY/edit?gid=737970932",
            "54MB02" to "https://docs.google.com/spreadsheets/d/1JLxZ6-eiv0sJsZH1fv9gfzctZ00XAi-B/edit?gid=56278768",
            "54MB03" to "https://docs.google.com/spreadsheets/d/10mv9DibOR6rubYF1b429NGV5brrTCqVy/edit?gid=1967909802",
            "54MJ01" to "https://docs.google.com/spreadsheets/d/1UQjsdA-VFZaZ1Aw60Wc-UU3s4sNmw-MF/edit?gid=732043607",
            "54MJ02" to "https://docs.google.com/spreadsheets/d/1pFVjWuGInS8ZpYTTRAqldWM5m8Fwqe75/edit?gid=2128248665",
            "54PB01" to "https://docs.google.com/spreadsheets/d/1NkEVsHh5LBkCUHp5QYMRmZd6shNoUXZV/edit?gid=1861130720",
            "54PN01" to "https://docs.google.com/spreadsheets/d/1a0_roDCh5BhmBFo60thS7YAdFg4s94cK/edit?gid=1036718777",
            "54RD01" to "https://docs.google.com/spreadsheets/d/1DEH-3to6YaeTcZIBiDONV70oXQmugfql/edit?gid=1112336056",
            "54RD02" to "https://docs.google.com/spreadsheets/d/1SLpVgQD9IVz9ZSnwbGDpmQmxzrMQvOJ9/edit?gid=58946481",
            "54SL01" to "https://docs.google.com/spreadsheets/d/1gL3loN3pdgWnbuB_KOWkzYP7Z0kcmbUE/edit?gid=481029323",
            "54SL02" to "https://docs.google.com/spreadsheets/d/1NVDaGwxk2pry_IhYPTfYv0TltkzvJu55/edit?gid=1279931600",
            "54SM01" to "https://docs.google.com/spreadsheets/d/1crCP4JRDR58eVhwB-_7ni8SQcohZxEnL/edit?gid=1588077753",
            "54SM02" to "https://docs.google.com/spreadsheets/d/1ELricpIv8CXlhFf_-_dOt41QVUVkPVds/edit?gid=62012763",
            "54VL01" to "https://docs.google.com/spreadsheets/d/1qwYFnoY_5iyXTGv3Dd4LAuGbAK0x7-dX/edit?gid=1914216836",
            "54PC01" to "https://docs.google.com/spreadsheets/d/18j191wszsRLGk_rkZoKL5q8iJptXw5Ed/edit?gid=349918661",
            "54MZ01" to "https://docs.google.com/spreadsheets/d/1XxyEaO_tVHutnjjkhzdhL-WubH0fzJkg/edit?gid=1613089017",
            "54MZ02" to "https://docs.google.com/spreadsheets/d/1ehwpTsGWn7AWiohIS8_kcG4GJbjHj0SO/edit?gid=1464942565",
            "54PJ01" to "https://docs.google.com/spreadsheets/d/1bAkHp6re7KPcNAjuop43mAN9Fy_xe8n-/edit?gid=283643944",
            "54PS01" to "https://docs.google.com/spreadsheets/d/10mkzN5aErAjoUxym0GHs3BkR3EZgiLNt/edit?gid=2109252468",
            "54SC01" to "https://docs.google.com/spreadsheets/d/1pBHlY2iYSTPvRHjnRHi-Cq5zjgD9jTiW/edit?gid=1278468788",
            "54SP01" to "https://docs.google.com/spreadsheets/d/1Eb7aLbWpbiEls09ar_yDr7lB2QOrK6Fl/edit?gid=666651066",
            "54ST01" to "https://docs.google.com/spreadsheets/d/1G5PDplcSUuOiSw_1YiWWuw--o6A5qXkG/edit?gid=566798981",
            "54SZ01" to "https://docs.google.com/spreadsheets/d/1njhd3_pjMbF9wDOuef4fDzLbTcA0KiyJ/edit?gid=1931584533",
            "54SZ02" to "https://docs.google.com/spreadsheets/d/1dhjsmReXHL7NVkd9bLGebKZWo0W7-n85/edit?gid=510567618",
            "54TB01" to "https://docs.google.com/spreadsheets/d/1FgdNXoOALy8KmTlkjsS_M0bd2NQd1sgo/edit?gid=1807481253",
            "54TR01" to "https://docs.google.com/spreadsheets/d/1X4l61pwpr2eeoSrKlbvaLl3q0cAdcFh-/edit?gid=1735171556",
            "54TR02" to "https://docs.google.com/spreadsheets/d/1u8iIyPeZAv-nRNTQCnJtfpGRzwFPxuZj/edit?gid=2130932481",
            "54PI01" to "https://docs.google.com/spreadsheets/d/1TAC_VZQnm1B28TY-ilZcD0oZgVzK8oMV/edit?gid=1446719977"
        )

        // 👉 CRIAÇÃO DOS BOTÕES
        for (nome in botoes) {

            val btn = Button(this).apply {
                text = nome
                setTextColor(Color.parseColor("#F5F5F5"))
                setBackgroundColor(Color.TRANSPARENT)
                textSize = 16f
                isAllCaps = false
                setShadowLayer(4f, 2f, 2f, Color.BLACK)
            }

            val params = GridLayout.LayoutParams().apply {
                width = 0
                setMargins(6, 4, 6, 4)
                columnSpec =
                    if (nome == "54PI01") GridLayout.spec(2, 1f)
                    else GridLayout.spec(GridLayout.UNDEFINED, 1f)
            }

            btn.layoutParams = params

            btn.setOnClickListener {
                feedback(btn)
                links[nome]?.let { url ->
                    btn.postDelayed({
                        startActivity(
                            Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        )
                    }, 120)
                }
            }

            grid.addView(btn)
        }

        // 👉 VOLTAR
        btnVoltar.setOnClickListener {
            feedback(btnVoltar)
            btnVoltar.postDelayed({
                finish()
            }, 120)
        }

        // 👉 AJUSTE AUTOMÁTICO (S24 / A13)
        ViewCompat.setOnApplyWindowInsetsListener(main) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            btnVoltar.translationY = -16f
            insets
        }
    }

    // 👉 FEEDBACK VISUAL (CLIQUE)
    private fun feedback(btn: Button) {
        btn.alpha = 0.6f
        btn.animate()
            .scaleX(0.96f)
            .scaleY(0.96f)
            .setDuration(90)
            .withEndAction {
                btn.alpha = 1f
                btn.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(90)
                    .start()
            }
            .start()
    }
}
