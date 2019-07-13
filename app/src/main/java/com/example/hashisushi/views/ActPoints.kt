package com.example.hashisushi.views

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Vibrator
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.TextView
import android.widget.Toast
import com.example.hashisushi.R
import com.example.hashisushi.dao.UserFirebase
import com.example.hashisushi.model.User
import com.example.hashisushi.views.cardap.*
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.act_points.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class ActPoints : AppCompatActivity() {

    private var points: Int = 0
    // Activity read code
    private var retornIdUser: String? = null
    private var user: User? = null

    private var user_points_name:TextView? = null
    private var txtponts:TextView? = null

    private var reference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_points)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        //supportActionBar!!.hide()

        //mostra toobar s/ titulu cor preta
        val bar = supportActionBar
        bar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#000000")))
        bar.title = ""

        //inicia componebtes
        startComponent()

        //inicia DB
        initDB()

        retornIdUser = UserFirebase.getIdUser()

        //controla exibição de imagens pontos
        controlImgView()
        //controla os pontos
        controlPonts()
        fontLogo()

        flotBntOrderPont!!.setOnClickListener { startVibrate(90); initOrder() }
        flotBntHomePont!!.setOnClickListener { startVibrate(90); initHome() }
        btnRescuePont!!.setOnClickListener { startVibrate(90); pontinsTest() }

        //recupera dado user
        recoveryDataUser()
    }

    //finaliza se voltar
    override fun onBackPressed() {
        finish()
    }

    //recupera dados do usuario esta com
    private fun recoveryDataUser()
    {
        val usuariosDB = retornIdUser?.let { reference?.child("users")?.child(it) }

        usuariosDB?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.value != null)
                {
                    user = dataSnapshot.getValue(User::class.java)
                }

                user_points_name?.setText(user?.name)

                var num: Int? = user?.ponts
                var strPonts:String = num.toString()
                txtponts?.setText(strPonts)

                points = user?.ponts!!
                controlPonts()
                controlImgView()

            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    fun initDB()
    {
        FirebaseApp.initializeApp(this@ActPoints)
        this.reference = FirebaseDatabase.getInstance().reference
    }

    fun startComponent()
    {
        user_points_name = findViewById(R.id. user_points_name)
        txtponts = findViewById(R.id.txtPonts)

    }
    //altera fonts
    override fun attachBaseContext(newBase: Context)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    //Altera fonte do txtLogo
    private fun fontLogo() {
        val font = Typeface.createFromAsset(assets, "RagingRedLotusBB.ttf")
       // txtLogoC!!.typeface = font
        txtTitlePonts!!.typeface = font
    }


    private fun alerta(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
    }

    private fun initHome() {
        val it = Intent(this, ActPromotion::class.java)
        startActivity(it)
        finish()
    }

    private fun initOrder() {
        val it = Intent(this, ActOrder::class.java)
        startActivity(it)
        finish()
    }

    private fun pontinsTest() {

        if (points == 0) alerta("Voçê não possui points para resgatar !")

        if (points in 1..14) {

            alerta("Voçê não completou 15 atualmente voçê tem : $points ,points !")
            val p = points - 15
            alerta("Faltam :$p points para voçê fazer o resgate !")

        } else if (points == 15) {

            val desconto = "30,00"
            System.setProperty("DESCONTO_ENV", desconto)

            points = 0
            txtPonts!!.text = "0"
            alerta("Pontos regatados !")
            controlImgView()

            finish()

        }
    }

    @Suppress("DEPRECATION")
    fun startVibrate(time: Long) {
        // cria um obj atvib que recebe seu valor de context
        val atvib = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        atvib.vibrate(time)
    }

    //==> MENUS
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_promotion, menu)
        return true
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.menu_enter) {
            val it = Intent(this, ActSaleCardap::class.java)
            startActivity(it)
            finish()
            return true
        }

        if (id == R.id.menu_plat_hot) {
            val it = Intent(this, ActPlatHot::class.java)
            startActivity(it)
            finish()
            return true
        }

        if (id == R.id.menu_plat_ace) {
            val it = Intent(this, ActPlatAce::class.java)
            startActivity(it)
            finish()
            return true
        }

        if (id == R.id.menu_combo) {
            val it = Intent(this, ActCombo::class.java)
            startActivity(it)
            finish()
            return true
        }

        if (id == R.id.menu_drinks) {
            val it = Intent(this, ActDrinks::class.java)
            startActivity(it)
            finish()
            return true
        }
        if (id == R.id.menu_temakis) {
            val it = Intent(this, ActTemakis::class.java)
            startActivity(it)
            finish()
            return true
        }
        if (id == R.id.menu_edit_cadastro) {
            val it = Intent(this, ActSignup::class.java)
            startActivity(it)
            finish()
            return true
        }
        if (id == R.id.menu_points) {
            val it = Intent(this, ActPoints::class.java)
            startActivity(it)
            finish()
            return true
        }
        if (id == R.id.menu_satus) {
            val it = Intent(this, ActWait::class.java)
            startActivity(it)
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
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
        }
    }
}
