package com.example.tip_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.example.tip_calculator.databinding.ActivityMainBinding
import com.google.android.material.slider.LabelFormatter
import java.text.NumberFormat
import kotlin.math.ceil


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private var splitNum : Int = 1

    private var billAmount : Float = 0f

    private var tipPercentage : Float = 0f
    private var amount =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //disabe night mode

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.slider.setLabelFormatter(LabelFormatter { value ->  NumberFormat.getPercentInstance().format(value /100).toString()})

        binding.chkRounder.setOnCheckedChangeListener { _, b ->
                calculateTip()
        }

        binding.slider.addOnChangeListener { _, value, _ ->
            tipPercentage=value
                calculateTip()
        }

        binding.btnPlus.setOnClickListener {
            splitPlus()
        }

        binding.btnMinus.setOnClickListener {
           splitMinus()
        }

    }
    fun splitPlus(){
            splitNum += 1
            binding.txtSplitNum.text=splitNum.toString()
        calculateTip()
    }
    fun splitMinus(){
        if(splitNum > 1){
            splitNum -= 1
            binding.txtSplitNum.text=splitNum.toString()
        }
        calculateTip()
    }

    fun calculateTip(){

        if(amount.isNotEmpty()){

            val totalTip = (tipPercentage/100) * billAmount //Total Tip

            val formatedTip=NumberFormat.getCurrencyInstance().format(totalTip)

            binding.txtTotalTip.text=getString(R.string.tipAmount,formatedTip)

            var tip = totalTip / splitNum

            if(binding.chkRounder.isChecked)
                tip= ceil(tip)

            val formatedTip2=NumberFormat.getCurrencyInstance().format(tip)

            binding.txtPerPersonTip.text=getString(R.string.tipAmount,formatedTip2)
        }

    }

    fun keyboard(view: View){

        when(view.id){
            R.id.btn0-> {
                if(amount.isNotEmpty())
                    amount+="0"
            }
            R.id.btn1-> amount+="1"
            R.id.btn2-> amount+="2"
            R.id.btn3-> amount+="3"
            R.id.btn4-> amount+="4"
            R.id.btn5-> amount+="5"
            R.id.btn6-> amount+="6"
            R.id.btn7-> amount+="7"
            R.id.btn8-> amount+="8"
            R.id.btn9-> amount+="9"
            R.id.btnDot->{
            if(amount.indexOf(".")==-1 && amount.isNotEmpty())
                amount+="."
            }
            else-> {
                if(amount.isNotEmpty())
                    amount=amount.dropLast(1)
            }
        }
        if(amount.isNotEmpty()) {
            billAmount = amount.toFloat()

            val formatedTip=NumberFormat.getCurrencyInstance().format(billAmount)

            binding.txtBillAmount.text=getString(R.string.tipAmount,formatedTip)


            calculateTip()
        }

    }

}