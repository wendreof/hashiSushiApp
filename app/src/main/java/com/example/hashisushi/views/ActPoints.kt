package com.example.hashisushi.views

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Typeface
import android.os.Bundle
import android.os.Vibrator
import android.support.v7.app.AppCompatActivity
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import com.example.hashisushi.R
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.act_points.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class ActPoints : AppCompatActivity() {
    private var points: Int = 0
    // Activity read code
    private val actScanCod = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_points)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        supportActionBar!!.hide()

        points = 14
        val p = points.toString()
        txtPonts!!.text = p
        controlImgView()
        controlPonts()
        fontLogo()

        flotBntOrderPont!!.setOnClickListener { startVibrate(90); initOrder() }
        flotBntHomePont!!.setOnClickListener { startVibrate(90); initHome() }
        flotBntScanQcodePont!!.setOnClickListener { startVibrate(90); scanerCode(actScanCod) }
        btnRescuePont!!.setOnClickListener { startVibrate(90); pontinsTest() }
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    //Altera fonte do txtLogo
    private fun fontLogo() {
        val font = Typeface.createFromAsset(assets, "RagingRedLotusBB.ttf")
        txtLogoC!!.typeface = font
        txtTitlePonts!!.typeface = font
    }

    //Implemention Scan
    private fun scanerCode(activity: Activity) {
        val integrator = IntentIntegrator(activity)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt("Scaneando codigo...")
        integrator.setCameraId(0)
        integrator.initiateScan()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        when {
            result != null -> if (result.contents != null) {
                alerta("Codigo recebido :" + result.contents)
                //val  codigo = result.contents
                points++

                val p = points.toString()
                txtPonts!!.text = p
                controlPonts()

                if (points > 15) points = 0

            } else alerta("Scan cancelado!")

            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun alerta(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
    }

    private fun initHome() {
        val it = Intent(this, ActPromotion::class.java)
        startActivity(it)
    }

    private fun initOrder() {
        val it = Intent(this, ActOrder::class.java)
        startActivity(it)
    }

    private fun pontinsTest() {

        if (points == 0) alerta("Voçê não possui points para resgatar !")

        if (points in 1..14) {

            alerta("Voçê não completou 15 atualmente voçê tem : $points ,points !")
            val p = points - 15
            alerta("Faltam :$p points para voçê fazer o resgate !")

        } else if (points == 15) {

            val p = points.toString()
            System.setProperty("PONTS_ENV", p)

            points = 0
            txtPonts!!.text = "0"
            alerta("Pontos regatados !")
            controlImgView()
        }
    }

    @Suppress("DEPRECATION")
    fun startVibrate(time: Long) {
        // cria um obj atvib que recebe seu valor de context
        val atvib = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        atvib.vibrate(time)
    }

    private fun controlImgView() {
        when {
            points <= 0 -> {
                imgVw1!!.visibility = INVISIBLE
                imgVw2!!.visibility = INVISIBLE
                imgVw3!!.visibility = INVISIBLE
                imgVw4!!.visibility = INVISIBLE
                imgVw5!!.visibility = INVISIBLE
                imgVw6!!.visibility = INVISIBLE
                imgVw7!!.visibility = INVISIBLE
                imgVw8!!.visibility = INVISIBLE
                imgVw9!!.visibility = INVISIBLE
                imgVw10!!.visibility = INVISIBLE
                imgVw11!!.visibility = INVISIBLE
                imgVw12!!.visibility = INVISIBLE
                imgVw13!!.visibility = INVISIBLE
                imgVw14!!.visibility = INVISIBLE
                imgVw15!!.visibility = INVISIBLE
                imgVw16!!.visibility = INVISIBLE
                imgVw17!!.visibility = INVISIBLE
                imgVw18!!.visibility = INVISIBLE
                imgVw19!!.visibility = INVISIBLE
                imgVw20!!.visibility = INVISIBLE
                imgVw21!!.visibility = INVISIBLE
                imgVw22!!.visibility = INVISIBLE
                imgVw23!!.visibility = INVISIBLE
                imgVw24!!.visibility = INVISIBLE
                imgVw25!!.visibility = INVISIBLE
            }
        }
    }
    private fun controlPonts() {
        when (points) {
            1 -> {
                imgVw1!!.visibility = VISIBLE

                imgVw2!!.visibility = INVISIBLE
                imgVw3!!.visibility = INVISIBLE
                imgVw4!!.visibility = INVISIBLE
                imgVw5!!.visibility = INVISIBLE
                imgVw6!!.visibility = INVISIBLE
                imgVw7!!.visibility = INVISIBLE
                imgVw8!!.visibility = INVISIBLE
                imgVw9!!.visibility = INVISIBLE
                imgVw10!!.visibility = INVISIBLE
                imgVw11!!.visibility = INVISIBLE
                imgVw12!!.visibility = INVISIBLE
                imgVw13!!.visibility = INVISIBLE
                imgVw14!!.visibility = INVISIBLE
                imgVw15!!.visibility = INVISIBLE
                imgVw16!!.visibility = INVISIBLE
                imgVw17!!.visibility = INVISIBLE
                imgVw18!!.visibility = INVISIBLE
                imgVw19!!.visibility = INVISIBLE
                imgVw20!!.visibility = INVISIBLE
                imgVw21!!.visibility = INVISIBLE
                imgVw22!!.visibility = INVISIBLE
                imgVw23!!.visibility = INVISIBLE
                imgVw24!!.visibility = INVISIBLE
                imgVw25!!.visibility = INVISIBLE
            }
            2 -> {
                imgVw2!!.visibility = VISIBLE
                imgVw1!!.visibility = VISIBLE

                imgVw3!!.visibility = INVISIBLE
                imgVw4!!.visibility = INVISIBLE
                imgVw5!!.visibility = INVISIBLE
                imgVw6!!.visibility = INVISIBLE
                imgVw7!!.visibility = INVISIBLE
                imgVw8!!.visibility = INVISIBLE
                imgVw9!!.visibility = INVISIBLE
                imgVw10!!.visibility = INVISIBLE
                imgVw11!!.visibility = INVISIBLE
                imgVw12!!.visibility = INVISIBLE
                imgVw13!!.visibility = INVISIBLE
                imgVw14!!.visibility = INVISIBLE
                imgVw15!!.visibility = INVISIBLE
                imgVw16!!.visibility = INVISIBLE
                imgVw17!!.visibility = INVISIBLE
                imgVw18!!.visibility = INVISIBLE
                imgVw19!!.visibility = INVISIBLE
                imgVw20!!.visibility = INVISIBLE
                imgVw21!!.visibility = INVISIBLE
                imgVw22!!.visibility = INVISIBLE
                imgVw23!!.visibility = INVISIBLE
                imgVw24!!.visibility = INVISIBLE
                imgVw25!!.visibility = INVISIBLE
            }
            3 -> {
                imgVw3!!.visibility = VISIBLE

                imgVw1!!.visibility = VISIBLE
                imgVw2!!.visibility = VISIBLE

                imgVw4!!.visibility = INVISIBLE
                imgVw5!!.visibility = INVISIBLE
                imgVw6!!.visibility = INVISIBLE
                imgVw7!!.visibility = INVISIBLE
                imgVw8!!.visibility = INVISIBLE
                imgVw9!!.visibility = INVISIBLE
                imgVw10!!.visibility = INVISIBLE
                imgVw11!!.visibility = INVISIBLE
                imgVw12!!.visibility = INVISIBLE
                imgVw13!!.visibility = INVISIBLE
                imgVw14!!.visibility = INVISIBLE
                imgVw15!!.visibility = INVISIBLE
                imgVw16!!.visibility = INVISIBLE
                imgVw17!!.visibility = INVISIBLE
                imgVw18!!.visibility = INVISIBLE
                imgVw19!!.visibility = INVISIBLE
                imgVw20!!.visibility = INVISIBLE
                imgVw21!!.visibility = INVISIBLE
                imgVw22!!.visibility = INVISIBLE
                imgVw23!!.visibility = INVISIBLE
                imgVw24!!.visibility = INVISIBLE
                imgVw25!!.visibility = INVISIBLE
            }
            4 -> {
                imgVw4!!.visibility = VISIBLE

                imgVw1!!.visibility = VISIBLE
                imgVw2!!.visibility = VISIBLE
                imgVw3!!.visibility = VISIBLE

                imgVw5!!.visibility = INVISIBLE
                imgVw6!!.visibility = INVISIBLE
                imgVw7!!.visibility = INVISIBLE
                imgVw8!!.visibility = INVISIBLE
                imgVw9!!.visibility = INVISIBLE
                imgVw10!!.visibility = INVISIBLE
                imgVw11!!.visibility = INVISIBLE
                imgVw12!!.visibility = INVISIBLE
                imgVw13!!.visibility = INVISIBLE
                imgVw14!!.visibility = INVISIBLE
                imgVw15!!.visibility = INVISIBLE
                imgVw16!!.visibility = INVISIBLE
                imgVw17!!.visibility = INVISIBLE
                imgVw18!!.visibility = INVISIBLE
                imgVw19!!.visibility = INVISIBLE
                imgVw20!!.visibility = INVISIBLE
                imgVw21!!.visibility = INVISIBLE
                imgVw22!!.visibility = INVISIBLE
                imgVw23!!.visibility = INVISIBLE
                imgVw24!!.visibility = INVISIBLE
                imgVw25!!.visibility = INVISIBLE
            }
            5 -> {
                imgVw5!!.visibility = VISIBLE

                imgVw1!!.visibility = VISIBLE
                imgVw2!!.visibility = VISIBLE
                imgVw3!!.visibility = VISIBLE
                imgVw4!!.visibility = VISIBLE

                imgVw6!!.visibility = INVISIBLE
                imgVw7!!.visibility = INVISIBLE
                imgVw8!!.visibility = INVISIBLE
                imgVw9!!.visibility = INVISIBLE
                imgVw10!!.visibility = INVISIBLE
                imgVw11!!.visibility = INVISIBLE
                imgVw12!!.visibility = INVISIBLE
                imgVw13!!.visibility = INVISIBLE
                imgVw14!!.visibility = INVISIBLE
                imgVw15!!.visibility = INVISIBLE
                imgVw16!!.visibility = INVISIBLE
                imgVw17!!.visibility = INVISIBLE
                imgVw18!!.visibility = INVISIBLE
                imgVw19!!.visibility = INVISIBLE
                imgVw20!!.visibility = INVISIBLE
                imgVw21!!.visibility = INVISIBLE
                imgVw22!!.visibility = INVISIBLE
                imgVw23!!.visibility = INVISIBLE
                imgVw24!!.visibility = INVISIBLE
                imgVw25!!.visibility = INVISIBLE
            }
            6 -> {
                imgVw6!!.visibility = VISIBLE

                imgVw1!!.visibility = VISIBLE
                imgVw2!!.visibility = VISIBLE
                imgVw3!!.visibility = VISIBLE
                imgVw4!!.visibility = VISIBLE
                imgVw5!!.visibility = VISIBLE

                imgVw7!!.visibility = INVISIBLE
                imgVw8!!.visibility = INVISIBLE
                imgVw9!!.visibility = INVISIBLE
                imgVw10!!.visibility = INVISIBLE
                imgVw11!!.visibility = INVISIBLE
                imgVw12!!.visibility = INVISIBLE
                imgVw13!!.visibility = INVISIBLE
                imgVw14!!.visibility = INVISIBLE
                imgVw15!!.visibility = INVISIBLE
                imgVw16!!.visibility = INVISIBLE
                imgVw17!!.visibility = INVISIBLE
                imgVw18!!.visibility = INVISIBLE
                imgVw19!!.visibility = INVISIBLE
                imgVw20!!.visibility = INVISIBLE
                imgVw21!!.visibility = INVISIBLE
                imgVw22!!.visibility = INVISIBLE
                imgVw23!!.visibility = INVISIBLE
                imgVw24!!.visibility = INVISIBLE
                imgVw25!!.visibility = INVISIBLE
            }
            7 -> {
                imgVw7!!.visibility = VISIBLE

                imgVw1!!.visibility = VISIBLE
                imgVw2!!.visibility = VISIBLE
                imgVw3!!.visibility = VISIBLE
                imgVw4!!.visibility = VISIBLE
                imgVw5!!.visibility = VISIBLE
                imgVw6!!.visibility = VISIBLE

                imgVw8!!.visibility = INVISIBLE
                imgVw9!!.visibility = INVISIBLE
                imgVw10!!.visibility = INVISIBLE
                imgVw11!!.visibility = INVISIBLE
                imgVw12!!.visibility = INVISIBLE
                imgVw13!!.visibility = INVISIBLE
                imgVw14!!.visibility = INVISIBLE
                imgVw15!!.visibility = INVISIBLE
                imgVw16!!.visibility = INVISIBLE
                imgVw17!!.visibility = INVISIBLE
                imgVw18!!.visibility = INVISIBLE
                imgVw19!!.visibility = INVISIBLE
                imgVw20!!.visibility = INVISIBLE
                imgVw21!!.visibility = INVISIBLE
                imgVw22!!.visibility = INVISIBLE
                imgVw23!!.visibility = INVISIBLE
                imgVw24!!.visibility = INVISIBLE
                imgVw25!!.visibility = INVISIBLE
            }
            8 -> {
                imgVw8!!.visibility = VISIBLE

                imgVw1!!.visibility = VISIBLE
                imgVw2!!.visibility = VISIBLE
                imgVw3!!.visibility = VISIBLE
                imgVw4!!.visibility = VISIBLE
                imgVw5!!.visibility = VISIBLE
                imgVw6!!.visibility = VISIBLE
                imgVw7!!.visibility = VISIBLE

                imgVw9!!.visibility = INVISIBLE
                imgVw10!!.visibility = INVISIBLE
                imgVw11!!.visibility = INVISIBLE
                imgVw12!!.visibility = INVISIBLE
                imgVw13!!.visibility = INVISIBLE
                imgVw14!!.visibility = INVISIBLE
                imgVw15!!.visibility = INVISIBLE
                imgVw16!!.visibility = INVISIBLE
                imgVw17!!.visibility = INVISIBLE
                imgVw18!!.visibility = INVISIBLE
                imgVw19!!.visibility = INVISIBLE
                imgVw20!!.visibility = INVISIBLE
                imgVw21!!.visibility = INVISIBLE
                imgVw22!!.visibility = INVISIBLE
                imgVw23!!.visibility = INVISIBLE
                imgVw24!!.visibility = INVISIBLE
                imgVw25!!.visibility = INVISIBLE
            }
            9 -> {
                imgVw9!!.visibility = VISIBLE

                imgVw1!!.visibility = VISIBLE
                imgVw2!!.visibility = VISIBLE
                imgVw3!!.visibility = VISIBLE
                imgVw4!!.visibility = VISIBLE
                imgVw5!!.visibility = VISIBLE
                imgVw6!!.visibility = VISIBLE
                imgVw7!!.visibility = VISIBLE
                imgVw8!!.visibility = VISIBLE

                imgVw10!!.visibility = INVISIBLE
                imgVw11!!.visibility = INVISIBLE
                imgVw12!!.visibility = INVISIBLE
                imgVw13!!.visibility = INVISIBLE
                imgVw14!!.visibility = INVISIBLE
                imgVw15!!.visibility = INVISIBLE
                imgVw16!!.visibility = INVISIBLE
                imgVw17!!.visibility = INVISIBLE
                imgVw18!!.visibility = INVISIBLE
                imgVw19!!.visibility = INVISIBLE
                imgVw20!!.visibility = INVISIBLE
                imgVw21!!.visibility = INVISIBLE
                imgVw22!!.visibility = INVISIBLE
                imgVw23!!.visibility = INVISIBLE
                imgVw24!!.visibility = INVISIBLE
                imgVw25!!.visibility = INVISIBLE
            }
            10 -> {
                imgVw10!!.visibility = VISIBLE

                imgVw1!!.visibility = VISIBLE
                imgVw2!!.visibility = VISIBLE
                imgVw3!!.visibility = VISIBLE
                imgVw4!!.visibility = VISIBLE
                imgVw5!!.visibility = VISIBLE
                imgVw6!!.visibility = VISIBLE
                imgVw7!!.visibility = VISIBLE
                imgVw8!!.visibility = VISIBLE
                imgVw9!!.visibility = VISIBLE

                imgVw11!!.visibility = INVISIBLE
                imgVw12!!.visibility = INVISIBLE
                imgVw13!!.visibility = INVISIBLE
                imgVw14!!.visibility = INVISIBLE
                imgVw15!!.visibility = INVISIBLE
                imgVw16!!.visibility = INVISIBLE
                imgVw17!!.visibility = INVISIBLE
                imgVw18!!.visibility = INVISIBLE
                imgVw19!!.visibility = INVISIBLE
                imgVw20!!.visibility = INVISIBLE
                imgVw21!!.visibility = INVISIBLE
                imgVw22!!.visibility = INVISIBLE
                imgVw23!!.visibility = INVISIBLE
                imgVw24!!.visibility = INVISIBLE
                imgVw25!!.visibility = INVISIBLE
            }
            11 -> {
                imgVw11!!.visibility = VISIBLE

                imgVw1!!.visibility = VISIBLE
                imgVw2!!.visibility = VISIBLE
                imgVw3!!.visibility = VISIBLE
                imgVw4!!.visibility = VISIBLE
                imgVw5!!.visibility = VISIBLE
                imgVw6!!.visibility = VISIBLE
                imgVw7!!.visibility = VISIBLE
                imgVw8!!.visibility = VISIBLE
                imgVw9!!.visibility = VISIBLE
                imgVw10!!.visibility = VISIBLE

                imgVw12!!.visibility = INVISIBLE
                imgVw13!!.visibility = INVISIBLE
                imgVw14!!.visibility = INVISIBLE
                imgVw15!!.visibility = INVISIBLE
                imgVw16!!.visibility = INVISIBLE
                imgVw17!!.visibility = INVISIBLE
                imgVw18!!.visibility = INVISIBLE
                imgVw19!!.visibility = INVISIBLE
                imgVw20!!.visibility = INVISIBLE
                imgVw21!!.visibility = INVISIBLE
                imgVw22!!.visibility = INVISIBLE
                imgVw23!!.visibility = INVISIBLE
                imgVw24!!.visibility = INVISIBLE
                imgVw25!!.visibility = INVISIBLE
            }
            12 -> {
                imgVw12!!.visibility = VISIBLE

                imgVw1!!.visibility = VISIBLE
                imgVw2!!.visibility = VISIBLE
                imgVw3!!.visibility = VISIBLE
                imgVw4!!.visibility = VISIBLE
                imgVw5!!.visibility = VISIBLE
                imgVw6!!.visibility = VISIBLE
                imgVw7!!.visibility = VISIBLE
                imgVw8!!.visibility = VISIBLE
                imgVw9!!.visibility = VISIBLE
                imgVw10!!.visibility = VISIBLE
                imgVw11!!.visibility = VISIBLE

                imgVw13!!.visibility = INVISIBLE
                imgVw14!!.visibility = INVISIBLE
                imgVw15!!.visibility = INVISIBLE
                imgVw16!!.visibility = INVISIBLE
                imgVw17!!.visibility = INVISIBLE
                imgVw18!!.visibility = INVISIBLE
                imgVw19!!.visibility = INVISIBLE
                imgVw20!!.visibility = INVISIBLE
                imgVw21!!.visibility = INVISIBLE
                imgVw22!!.visibility = INVISIBLE
                imgVw23!!.visibility = INVISIBLE
                imgVw24!!.visibility = INVISIBLE
                imgVw25!!.visibility = INVISIBLE
            }
            13 -> {
                imgVw13!!.visibility = VISIBLE

                imgVw1!!.visibility = VISIBLE
                imgVw2!!.visibility = VISIBLE
                imgVw3!!.visibility = VISIBLE
                imgVw4!!.visibility = VISIBLE
                imgVw5!!.visibility = VISIBLE
                imgVw6!!.visibility = VISIBLE
                imgVw7!!.visibility = VISIBLE
                imgVw8!!.visibility = VISIBLE
                imgVw9!!.visibility = VISIBLE
                imgVw10!!.visibility = VISIBLE
                imgVw11!!.visibility = VISIBLE
                imgVw12!!.visibility = VISIBLE

                imgVw14!!.visibility = INVISIBLE
                imgVw15!!.visibility = INVISIBLE
                imgVw16!!.visibility = INVISIBLE
                imgVw17!!.visibility = INVISIBLE
                imgVw18!!.visibility = INVISIBLE
                imgVw19!!.visibility = INVISIBLE
                imgVw20!!.visibility = INVISIBLE
                imgVw21!!.visibility = INVISIBLE
                imgVw22!!.visibility = INVISIBLE
                imgVw23!!.visibility = INVISIBLE
                imgVw24!!.visibility = INVISIBLE
                imgVw25!!.visibility = INVISIBLE
            }
            14 -> {
                imgVw14!!.visibility = VISIBLE

                imgVw1!!.visibility = VISIBLE
                imgVw2!!.visibility = VISIBLE
                imgVw3!!.visibility = VISIBLE
                imgVw4!!.visibility = VISIBLE
                imgVw5!!.visibility = VISIBLE
                imgVw6!!.visibility = VISIBLE
                imgVw7!!.visibility = VISIBLE
                imgVw8!!.visibility = VISIBLE
                imgVw9!!.visibility = VISIBLE
                imgVw10!!.visibility = VISIBLE
                imgVw11!!.visibility = VISIBLE
                imgVw12!!.visibility = VISIBLE
                imgVw13!!.visibility = VISIBLE

                imgVw15!!.visibility = INVISIBLE
                imgVw16!!.visibility = INVISIBLE
                imgVw17!!.visibility = INVISIBLE
                imgVw18!!.visibility = INVISIBLE
                imgVw19!!.visibility = INVISIBLE
                imgVw20!!.visibility = INVISIBLE
                imgVw21!!.visibility = INVISIBLE
                imgVw22!!.visibility = INVISIBLE
                imgVw23!!.visibility = INVISIBLE
                imgVw24!!.visibility = INVISIBLE
                imgVw25!!.visibility = INVISIBLE
            }
            15 -> {
                imgVw15!!.visibility = VISIBLE

                imgVw1!!.visibility = VISIBLE
                imgVw2!!.visibility = VISIBLE
                imgVw3!!.visibility = VISIBLE
                imgVw4!!.visibility = VISIBLE
                imgVw5!!.visibility = VISIBLE
                imgVw6!!.visibility = VISIBLE
                imgVw7!!.visibility = VISIBLE
                imgVw8!!.visibility = VISIBLE
                imgVw9!!.visibility = VISIBLE
                imgVw10!!.visibility = VISIBLE
                imgVw11!!.visibility = VISIBLE
                imgVw12!!.visibility = VISIBLE
                imgVw13!!.visibility = VISIBLE
                imgVw14!!.visibility = VISIBLE

                imgVw16!!.visibility = INVISIBLE
                imgVw17!!.visibility = INVISIBLE
                imgVw18!!.visibility = INVISIBLE
                imgVw19!!.visibility = INVISIBLE
                imgVw20!!.visibility = INVISIBLE
                imgVw21!!.visibility = INVISIBLE
                imgVw22!!.visibility = INVISIBLE
                imgVw23!!.visibility = INVISIBLE
                imgVw24!!.visibility = INVISIBLE
                imgVw25!!.visibility = INVISIBLE
            }
            16 -> {
                imgVw16!!.visibility = VISIBLE

                imgVw1!!.visibility = VISIBLE
                imgVw2!!.visibility = VISIBLE
                imgVw3!!.visibility = VISIBLE
                imgVw4!!.visibility = VISIBLE
                imgVw5!!.visibility = VISIBLE
                imgVw6!!.visibility = VISIBLE
                imgVw7!!.visibility = VISIBLE
                imgVw8!!.visibility = VISIBLE
                imgVw9!!.visibility = VISIBLE
                imgVw10!!.visibility = VISIBLE
                imgVw11!!.visibility = VISIBLE
                imgVw12!!.visibility = VISIBLE
                imgVw13!!.visibility = VISIBLE
                imgVw14!!.visibility = VISIBLE
                imgVw15!!.visibility = VISIBLE

                imgVw17!!.visibility = INVISIBLE
                imgVw18!!.visibility = INVISIBLE
                imgVw19!!.visibility = INVISIBLE
                imgVw20!!.visibility = INVISIBLE
                imgVw21!!.visibility = INVISIBLE
                imgVw22!!.visibility = INVISIBLE
                imgVw23!!.visibility = INVISIBLE
                imgVw24!!.visibility = INVISIBLE
                imgVw25!!.visibility = INVISIBLE
            }
            17 -> {
                imgVw17!!.visibility = VISIBLE

                imgVw1!!.visibility = VISIBLE
                imgVw2!!.visibility = VISIBLE
                imgVw3!!.visibility = VISIBLE
                imgVw4!!.visibility = VISIBLE
                imgVw5!!.visibility = VISIBLE
                imgVw6!!.visibility = VISIBLE
                imgVw7!!.visibility = VISIBLE
                imgVw8!!.visibility = VISIBLE
                imgVw9!!.visibility = VISIBLE
                imgVw10!!.visibility = VISIBLE
                imgVw11!!.visibility = VISIBLE
                imgVw12!!.visibility = VISIBLE
                imgVw13!!.visibility = VISIBLE
                imgVw14!!.visibility = VISIBLE
                imgVw15!!.visibility = VISIBLE
                imgVw16!!.visibility = VISIBLE

                imgVw18!!.visibility = INVISIBLE
                imgVw19!!.visibility = INVISIBLE
                imgVw20!!.visibility = INVISIBLE
                imgVw21!!.visibility = INVISIBLE
                imgVw22!!.visibility = INVISIBLE
                imgVw23!!.visibility = INVISIBLE
                imgVw24!!.visibility = INVISIBLE
                imgVw25!!.visibility = INVISIBLE
            }
            18 -> {
                imgVw18!!.visibility = VISIBLE

                imgVw1!!.visibility = VISIBLE
                imgVw2!!.visibility = VISIBLE
                imgVw3!!.visibility = VISIBLE
                imgVw4!!.visibility = VISIBLE
                imgVw5!!.visibility = VISIBLE
                imgVw6!!.visibility = VISIBLE
                imgVw7!!.visibility = VISIBLE
                imgVw8!!.visibility = VISIBLE
                imgVw9!!.visibility = VISIBLE
                imgVw10!!.visibility = VISIBLE
                imgVw11!!.visibility = VISIBLE
                imgVw12!!.visibility = VISIBLE
                imgVw13!!.visibility = VISIBLE
                imgVw14!!.visibility = VISIBLE
                imgVw15!!.visibility = VISIBLE
                imgVw16!!.visibility = VISIBLE
                imgVw17!!.visibility = VISIBLE

                imgVw18!!.visibility = INVISIBLE
                imgVw19!!.visibility = INVISIBLE
                imgVw20!!.visibility = INVISIBLE
                imgVw21!!.visibility = INVISIBLE
                imgVw22!!.visibility = INVISIBLE
                imgVw23!!.visibility = INVISIBLE
                imgVw24!!.visibility = INVISIBLE
                imgVw25!!.visibility = INVISIBLE
            }
            19 -> {
                imgVw19!!.visibility = VISIBLE

                imgVw1!!.visibility = VISIBLE
                imgVw2!!.visibility = VISIBLE
                imgVw3!!.visibility = VISIBLE
                imgVw4!!.visibility = VISIBLE
                imgVw5!!.visibility = VISIBLE
                imgVw6!!.visibility = VISIBLE
                imgVw7!!.visibility = VISIBLE
                imgVw8!!.visibility = VISIBLE
                imgVw9!!.visibility = VISIBLE
                imgVw10!!.visibility = VISIBLE
                imgVw11!!.visibility = VISIBLE
                imgVw12!!.visibility = VISIBLE
                imgVw13!!.visibility = VISIBLE
                imgVw14!!.visibility = VISIBLE
                imgVw15!!.visibility = VISIBLE
                imgVw16!!.visibility = VISIBLE
                imgVw17!!.visibility = VISIBLE
                imgVw18!!.visibility = VISIBLE

                imgVw20!!.visibility = INVISIBLE
                imgVw21!!.visibility = INVISIBLE
                imgVw22!!.visibility = INVISIBLE
                imgVw23!!.visibility = INVISIBLE
                imgVw24!!.visibility = INVISIBLE
                imgVw25!!.visibility = INVISIBLE
            }
            20 -> {
                imgVw20!!.visibility = VISIBLE

                imgVw1!!.visibility = VISIBLE
                imgVw2!!.visibility = VISIBLE
                imgVw3!!.visibility = VISIBLE
                imgVw4!!.visibility = VISIBLE
                imgVw5!!.visibility = VISIBLE
                imgVw6!!.visibility = VISIBLE
                imgVw7!!.visibility = VISIBLE
                imgVw8!!.visibility = VISIBLE
                imgVw9!!.visibility = VISIBLE
                imgVw10!!.visibility = VISIBLE
                imgVw11!!.visibility = VISIBLE
                imgVw12!!.visibility = VISIBLE
                imgVw13!!.visibility = VISIBLE
                imgVw14!!.visibility = VISIBLE
                imgVw15!!.visibility = VISIBLE
                imgVw16!!.visibility = VISIBLE
                imgVw17!!.visibility = VISIBLE
                imgVw18!!.visibility = VISIBLE
                imgVw19!!.visibility = VISIBLE

                imgVw21!!.visibility = INVISIBLE
                imgVw22!!.visibility = INVISIBLE
                imgVw23!!.visibility = INVISIBLE
                imgVw24!!.visibility = INVISIBLE
                imgVw25!!.visibility = INVISIBLE
            }
            21 -> {
                imgVw21!!.visibility = VISIBLE

                imgVw1!!.visibility = VISIBLE
                imgVw2!!.visibility = VISIBLE
                imgVw3!!.visibility = VISIBLE
                imgVw4!!.visibility = VISIBLE
                imgVw5!!.visibility = VISIBLE
                imgVw6!!.visibility = VISIBLE
                imgVw7!!.visibility = VISIBLE
                imgVw8!!.visibility = VISIBLE
                imgVw9!!.visibility = VISIBLE
                imgVw10!!.visibility = VISIBLE
                imgVw11!!.visibility = VISIBLE
                imgVw12!!.visibility = VISIBLE
                imgVw13!!.visibility = VISIBLE
                imgVw14!!.visibility = VISIBLE
                imgVw15!!.visibility = VISIBLE
                imgVw16!!.visibility = VISIBLE
                imgVw17!!.visibility = VISIBLE
                imgVw18!!.visibility = VISIBLE
                imgVw19!!.visibility = VISIBLE
                imgVw20!!.visibility = VISIBLE

                imgVw22!!.visibility = INVISIBLE
                imgVw23!!.visibility = INVISIBLE
                imgVw24!!.visibility = INVISIBLE
                imgVw25!!.visibility = INVISIBLE
            }
            22 -> {
                imgVw22!!.visibility = VISIBLE

                imgVw1!!.visibility = VISIBLE
                imgVw2!!.visibility = VISIBLE
                imgVw3!!.visibility = VISIBLE
                imgVw4!!.visibility = VISIBLE
                imgVw5!!.visibility = VISIBLE
                imgVw6!!.visibility = VISIBLE
                imgVw7!!.visibility = VISIBLE
                imgVw8!!.visibility = VISIBLE
                imgVw9!!.visibility = VISIBLE
                imgVw10!!.visibility = VISIBLE
                imgVw11!!.visibility = VISIBLE
                imgVw12!!.visibility = VISIBLE
                imgVw13!!.visibility = VISIBLE
                imgVw14!!.visibility = VISIBLE
                imgVw15!!.visibility = VISIBLE
                imgVw16!!.visibility = VISIBLE
                imgVw17!!.visibility = VISIBLE
                imgVw18!!.visibility = VISIBLE
                imgVw19!!.visibility = VISIBLE
                imgVw20!!.visibility = VISIBLE
                imgVw21!!.visibility = VISIBLE

                imgVw23!!.visibility = INVISIBLE
                imgVw24!!.visibility = INVISIBLE
                imgVw25!!.visibility = INVISIBLE
            }
            23 -> {
                imgVw23!!.visibility = VISIBLE

                imgVw1!!.visibility = VISIBLE
                imgVw2!!.visibility = VISIBLE
                imgVw3!!.visibility = VISIBLE
                imgVw4!!.visibility = VISIBLE
                imgVw5!!.visibility = VISIBLE
                imgVw6!!.visibility = VISIBLE
                imgVw7!!.visibility = VISIBLE
                imgVw8!!.visibility = VISIBLE
                imgVw9!!.visibility = VISIBLE
                imgVw10!!.visibility = VISIBLE
                imgVw11!!.visibility = VISIBLE
                imgVw12!!.visibility = VISIBLE
                imgVw13!!.visibility = VISIBLE
                imgVw14!!.visibility = VISIBLE
                imgVw15!!.visibility = VISIBLE
                imgVw16!!.visibility = VISIBLE
                imgVw17!!.visibility = VISIBLE
                imgVw18!!.visibility = VISIBLE
                imgVw19!!.visibility = VISIBLE
                imgVw20!!.visibility = VISIBLE
                imgVw21!!.visibility = VISIBLE
                imgVw22!!.visibility = VISIBLE

                imgVw24!!.visibility = INVISIBLE
                imgVw25!!.visibility = INVISIBLE
            }
            24 -> {
                imgVw24!!.visibility = VISIBLE

                imgVw1!!.visibility = VISIBLE
                imgVw2!!.visibility = VISIBLE
                imgVw3!!.visibility = VISIBLE
                imgVw4!!.visibility = VISIBLE
                imgVw5!!.visibility = VISIBLE
                imgVw6!!.visibility = VISIBLE
                imgVw7!!.visibility = VISIBLE
                imgVw8!!.visibility = VISIBLE
                imgVw9!!.visibility = VISIBLE
                imgVw10!!.visibility = VISIBLE
                imgVw11!!.visibility = VISIBLE
                imgVw12!!.visibility = VISIBLE
                imgVw13!!.visibility = VISIBLE
                imgVw14!!.visibility = VISIBLE
                imgVw15!!.visibility = VISIBLE
                imgVw16!!.visibility = VISIBLE
                imgVw17!!.visibility = VISIBLE
                imgVw18!!.visibility = VISIBLE
                imgVw19!!.visibility = VISIBLE
                imgVw20!!.visibility = VISIBLE
                imgVw21!!.visibility = VISIBLE
                imgVw22!!.visibility = VISIBLE
                imgVw23!!.visibility = VISIBLE

                imgVw25!!.visibility = INVISIBLE
            }
            25 -> {
                imgVw25!!.visibility = VISIBLE

                imgVw1!!.visibility = VISIBLE
                imgVw2!!.visibility = VISIBLE
                imgVw3!!.visibility = VISIBLE
                imgVw4!!.visibility = VISIBLE
                imgVw5!!.visibility = VISIBLE
                imgVw6!!.visibility = VISIBLE
                imgVw7!!.visibility = VISIBLE
                imgVw8!!.visibility = VISIBLE
                imgVw9!!.visibility = VISIBLE
                imgVw10!!.visibility = VISIBLE
                imgVw11!!.visibility = VISIBLE
                imgVw12!!.visibility = VISIBLE
                imgVw13!!.visibility = VISIBLE
                imgVw14!!.visibility = VISIBLE
                imgVw15!!.visibility = VISIBLE
                imgVw16!!.visibility = VISIBLE
                imgVw17!!.visibility = VISIBLE
                imgVw18!!.visibility = VISIBLE
                imgVw19!!.visibility = VISIBLE
                imgVw20!!.visibility = VISIBLE
                imgVw21!!.visibility = VISIBLE
                imgVw22!!.visibility = VISIBLE
                imgVw23!!.visibility = VISIBLE
                imgVw24!!.visibility = VISIBLE
            }
        }
    }
}
