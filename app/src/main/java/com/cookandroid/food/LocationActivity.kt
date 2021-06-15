package com.cookandroid.food

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.cookandroid.food.databinding.ActivityLocationBinding
import com.mancj.materialsearchbar.MaterialSearchBar

class LocationActivity : AppCompatActivity() {

    var name3:String=""

    private lateinit var binding: ActivityLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra("name2")) {
            name3 = intent.getStringExtra("name2")!!

        } else {
            Toast.makeText(this, "전달된 이름이 없습니다", Toast.LENGTH_SHORT).show()
        }


        //View - 변수 연결
        val lv = findViewById(R.id.mListView) as ListView
        val searchBar = findViewById<MaterialSearchBar>(R.id.searchBar)
        searchBar.setHint("Search")
        //음성검색모드 끄기
        searchBar.setSpeechMode(false)
        //검색어 목록 넣기
        var galaxies = arrayOf("맥도날드 삼선점","롯데리아 한성대입구역점", )

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, galaxies)
        //리스트뷰 초기에 안보이게 설정
        lv.visibility = View.INVISIBLE
        //SearchBar와 ListView 연동
        lv.setAdapter(adapter)
        searchBar.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener{
            override fun onButtonClicked(buttonCode: Int) {
                TODO("Not yet implemented")
            }
            //검색창 누른 상태 여부 확인
            override fun onSearchStateChanged(enabled: Boolean) {
                //맞으면 리스트뷰 보이게 설정
                if(enabled){
                    lv.visibility = View.VISIBLE
                }else{ //아니면 안 보이게
                    lv.visibility = View.INVISIBLE
                }
            }

            override fun onSearchConfirmed(text: CharSequence?) {
                TODO("Not yet implemented")
            }

        })

        searchBar.addTextChangeListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            //검색어 변경하면 ListView 내용 변경
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.getFilter().filter(s)
            }

        })

        //ListView 내의 아이템 누르면 Toast 발생
        lv.setOnItemClickListener(object : AdapterView.OnItemClickListener{
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@LocationActivity, adapter.getItem(position)!!.toString(), Toast.LENGTH_SHORT).show()
                val num = position+1
                val intent = Intent(this@LocationActivity,PostActivity::class.java)
                intent.putExtra("num",num.toString())
                intent.putExtra("name3",name3)
                startActivity(intent)
            }

        })


    }
}