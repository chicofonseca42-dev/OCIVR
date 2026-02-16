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

class Rede59Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rede59)

        val main = findViewById<View>(R.id.main)
        val grid = findViewById<GridLayout>(R.id.gridRede)
        val btnVoltar = findViewById<Button>(R.id.btnVoltar)

        // 👉 ORDEM VISUAL FIXA
        val botoes = listOf(
            "59AJ01","59MR01","59VM01",
            "59AJ02","59PG01","59VP01",
            "59CD01","59PG02","59VR01_Se",
            "59CJ01","59PR01","59VR01_Do",
            "59CL01","59RP01","59VR02",
            "59CM01","59SB01","59VR03",
            "59CP01","59SD01","59VR04",
            "59CV01","59ST01","59VR05",
            "59JT01","59TS01","59VR06",
            "59JU01","","59VR07"
        )

        // 👉 BOTÃO → LINK
        val links = mapOf(
            "59AJ01" to "https://docs.google.com/spreadsheets/d/1xGidkES2GhW3Tp6FShAdp74ulCW__BQobPDI4LtXRKY/edit?gid=1235425075#gid=1235425075",
            "59MR01" to "https://docs.google.com/spreadsheets/d/1mw9fFBVcwgGJ8hdShPkZDzYsq7BJ2OHTCodn2uQWuS4/edit?gid=1057174623#gid=1057174623",
            "59VM01" to "https://docs.google.com/spreadsheets/d/1HSInzXolfiDikF-itEY46fYizsrtXCwH/edit?gid=1975701994",
            "59AJ02" to "https://docs.google.com/spreadsheets/d/1h-B2-ufBqZIoJslCfgIKMEZGaOCe_r_G/edit?gid=1008076586",
            "59PG01" to "https://docs.google.com/spreadsheets/d/1UHybURMGQO2CoCYqlO8TA7LEyduU_z8Rogsa3PGgiKI/edit?gid=1171740721#gid=1171740721",
            "59VP01" to "https://docs.google.com/spreadsheets/d/1SbBCEhBgV2qJsekk-gSR7V1MSMCOWpeXIpg4joz2njc/edit?gid=1065374551#gid=1065374551",
            "59CD01" to "https://docs.google.com/spreadsheets/d/1lOHfodA-VBenuGNHqdmK8cFoQErgW9ot/edit?gid=937755512",
            "59PG02" to "https://docs.google.com/spreadsheets/d/1C2RlaAAt2yI2aWP5evQZer7zmlYtaVdgX0n3pf8SMlE/edit?gid=1666071963#gid=1666071963",
            "59VR01_Se" to "https://docs.google.com/spreadsheets/d/1A_FECGRyie7L7oiSImvqa3M06NNcrTjmgt-DuHDykX0/edit?gid=1694471561#gid=1694471561",
            "59CJ01" to "https://docs.google.com/spreadsheets/d/1cfZLBcSKdb62TtLp_AW-0jvbV65PhOCn/edit?gid=1735608677",
            "59PR01" to "https://docs.google.com/spreadsheets/d/1R3GIDNEOQOPBuGFcalGWlVzBzEgSDsAc/edit?gid=872177912",
            "59VR01_Do" to "https://docs.google.com/spreadsheets/d/1OpTcSXmdY6n_gyTYJi6fPKNGT8KZCgvm/edit?gid=1339391795",
            "59CL01" to "https://docs.google.com/spreadsheets/d/15Ct7xJ4CoUCLpKVwxeE5FWndZ13HyaEa/edit?gid=56998952",
            "59RP01" to "https://docs.google.com/spreadsheets/d/1Qk6CKjXl0Oh_cMt7SYRE3W_BC701_nAkm8tN5lzcOW0/edit?gid=1236142387#gid=1236142387",
            "59VR02" to "https://docs.google.com/spreadsheets/d/1BPN3A4xaEzli__uoG7hennU73s3C2cfv/edit?gid=1573707259",
            "59CM01" to "https://docs.google.com/spreadsheets/d/1pA70F4lfAGSZE1dUPTP4QuJvdMcXcIjR/edit?gid=1494899945",
            "59SB01" to "https://docs.google.com/spreadsheets/d/1GzlB6-cnrP5Uckgr4dCnnke6azwJ8rKZ/edit?gid=697690586",
            "59VR03" to "https://docs.google.com/spreadsheets/d/1rTEJ_Xbd2naeeWlD3ZiQxB5V4C1ha9WEN3s48_X1Ls8/edit?gid=1307981599#gid=1307981599",
            "59CP01" to "https://docs.google.com/spreadsheets/d/1LLX_bPwUZgwrcxuaCUo3VSunydgW7eXr/edit?gid=1699604314",
            "59SD01" to "https://docs.google.com/spreadsheets/d/1PMuxpIfNGfZpXiDv4opu1UyQ6nPNFA3m/edit?gid=1159729653",
            "59VR04" to "https://docs.google.com/spreadsheets/d/1qYuc74JFRkeYxIsqQssParTYMHwRPaHQeHA29558Gnk/edit?gid=2060761066#gid=2060761066",
            "59CV01" to "https://docs.google.com/spreadsheets/d/1WIUxq3ZRJKlNppZE8bOQnvEOdhgSGsqCfdcrDanratA/edit?gid=365677335#gid=365677335",
            "59ST01" to "https://docs.google.com/spreadsheets/d/1LBTNeBpR1IaqDdojeCHL_7yruyYWewo5/edit?gid=1357713474",
            "59VR05" to "https://docs.google.com/spreadsheets/d/1OUaiMCL2Q9j_NuykdKMg81s9lDFstr-WQK90odebDtc/edit?gid=1878134753#gid=1878134753",
            "59JT01" to "https://docs.google.com/spreadsheets/d/1-mCDpqIDF4ktZcwwkLPzBHPHSvAzWdmO/edit?gid=2017794267",
            "59TS01" to "https://docs.google.com/spreadsheets/d/1-eo2oB2c804l0dRrHgUF05SOT_lNw8jq/edit?gid=1595926146",
            "59VR06" to "https://docs.google.com/spreadsheets/d/16BwTK92cfVMoInerIep-PJkjBSQY9A_pSUyocUZpGrI/edit?gid=346357645#gid=346357645",
            "59JU01" to "https://docs.google.com/spreadsheets/d/1ZzwLzMP_XEZAEpQvX0YfxEaCfLHZwt8S/edit?gid=62066123",
            "59VR07" to "https://docs.google.com/spreadsheets/d/1tzzZnxXcuagn5NIvMUej50_RqGW6rkdFlQYmxaKL0Dg/edit?gid=1541379891#gid=1541379891"
        )

        // 👉 CRIAR BOTÕES
        for (nome in botoes) {

            // espaço vazio (alinhamento)
            if (nome.isBlank()) {
                val vazio = Button(this)
                vazio.isEnabled = false
                vazio.setBackgroundColor(Color.TRANSPARENT)

                val p = GridLayout.LayoutParams().apply {
                    width = 0
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                }
                vazio.layoutParams = p
                grid.addView(vazio)
                continue
            }

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
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            }
            btn.layoutParams = params

            btn.setOnClickListener {
                feedback(btn)
                links[nome]?.let { url ->
                    btn.postDelayed({
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    }, 120)
                }
            }

            grid.addView(btn)
        }

        // 👉 VOLTAR
        btnVoltar.setOnClickListener {
            feedback(btnVoltar)
            btnVoltar.postDelayed({ finish() }, 120)
        }

        // 👉 AJUSTE AUTOMÁTICO (S24 / A13)
        ViewCompat.setOnApplyWindowInsetsListener(main) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            btnVoltar.translationY = -16f
            insets
        }
    }

    // 👉 FEEDBACK VISUAL
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
