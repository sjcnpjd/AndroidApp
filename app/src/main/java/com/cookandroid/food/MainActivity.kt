
package com.cookandroid.food

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cookandroid.food.databinding.ActivityMainBinding
import com.cookandroid.food.vm.MyViewModel


class MainActivity : AppCompatActivity() {
    init{
        instance = this
    }

    companion object {
        lateinit var instance: MainActivity
        fun ApplicationContext() : Context {
            return instance.applicationContext
        }
    }

    private lateinit var binding: ActivityMainBinding

    private var name = ""
    private val fragmentMain by lazy { MainFragment() }
    private val fragmentSearch by lazy { SearchFragment() }
    private val fragmentCalendar by lazy { CalendarFragment() }
    private val fragmentProfile by lazy { ProfileFragment() }
    val viewModel by viewModels<MyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.mainToolbar
        setSupportActionBar(toolbar)
        if (intent.hasExtra("name")) {
            name = intent.getStringExtra("name")!!
            viewModel.username = name

        } else {
            Toast.makeText(this, "전달된 이름이 없습니다", Toast.LENGTH_SHORT).show()
        }

        if (savedInstanceState == null) {

            viewModel.sendRequest(this)
            viewModel.sendProfileRequest(this)


       }



        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        changeFragment(fragmentMain)
        initNavigationBar()

        binding.mainToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_logo -> {

                    true
                }
                R.id.action_search-> {

                    true
                }
                else -> false
            }
        }



    }

    private  fun initNavigationBar(){
        binding.bnvMain.run {
            setOnNavigationItemSelectedListener{

                when(it.itemId) {
                    R.id.action_home -> {
                        changeFragment(fragmentMain)
                    }
                    R.id.action_search -> {
                        changeFragment(fragmentSearch)
                    }
                    R.id.action_calendar -> {
                        changeFragment(fragmentCalendar) }
                    R.id.action_profile-> {
                        changeFragment(fragmentProfile)

                    }
                }
                true
            }
            selectedItemId = R.id.action_home
        }


    }
    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_frame, fragment)
            .commit()

    }
    fun getName(): String {
        return name
    }




}















