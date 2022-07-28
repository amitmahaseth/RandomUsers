package com.example.randomusers

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.example.randomusers.Util.Constant
import com.example.randomusers.adapter.UserListAdapter
import com.example.randomusers.databinding.ActivityMainBinding
import com.example.randomusers.model.Clicklistener
import com.example.randomusers.model.RandomUserListResponse
import com.example.randomusers.model.Result
import com.example.randomusers.viewModel.UserDataVM
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), Clicklistener {

    private lateinit var binding: ActivityMainBinding
    private var userListVM: UserDataVM? = null
    private var userAdapter: UserListAdapter? = null
    lateinit var userList: java.util.ArrayList<Result>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        userListVM = UserDataVM(this)

        setContentView(binding.root)
        init()
        setApi()


    }

    fun init() {

        binding.btnMen.setOnClickListener {
            setApiForMen()
        }
        binding.btnWomen.setOnClickListener {
            setApiForWomen()
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                userAdapter!!.filter.filter(newText)
                return false
            }
        })
    }

    private fun setApi() {
        CoroutineScope(Dispatchers.Main).launch {
            val miniMumList = 50
            userListVM!!.getUsersDataVM(miniMumList, "").observe(this@MainActivity, Observer {
                var response = it as RandomUserListResponse
                setAdapter(response.results)
            })
        }
    }

    private fun setApiForMen() {
        CoroutineScope(Dispatchers.Main).launch {
            val gender = "male"
            val miniMumList = 50
            userListVM!!.getUsersDataVM(miniMumList, gender).observe(this@MainActivity, Observer {
                var response = it as RandomUserListResponse
                setAdapter(response.results)
            })
        }
    }

    private fun setApiForWomen() {
        CoroutineScope(Dispatchers.Main).launch {
            val gender = "female"
            val miniMumList = 50
            userListVM!!.getUsersDataVM(miniMumList, gender).observe(this@MainActivity, Observer {
                var response = it as RandomUserListResponse
                setAdapter(response.results)
            })
        }
    }


    private fun setAdapter(results: ArrayList<Result>) {
        userList = results
        userAdapter = UserListAdapter(this, results, userList, this)
        binding.rvUserList.adapter = userAdapter
    }

    override fun onItemClicked(position: Int) {
        var intent = Intent(this, UserDetailsActivity::class.java)
        intent.putExtra(Constant.SINGLEUSERDATA,Gson().toJson(userAdapter!!.userList[position]))
        startActivity(intent)
    }
}