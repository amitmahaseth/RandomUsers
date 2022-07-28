package com.example.randomusers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.randomusers.Util.Constant
import com.example.randomusers.databinding.ActivityUserDetailsBinding
import com.example.randomusers.model.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**user data come from mainActivity**/
        var userDataValue = intent.extras!!.getString(Constant.SINGLEUSERDATA)
        val myType = object : TypeToken<Result>() {}.type
        val userDataValue1 =
            Gson().fromJson<Result>(userDataValue, myType)

        setUserData(userDataValue1)


    }

    private fun setUserData(userDataValue1: Result) {
        Glide
            .with(this)
            .load(userDataValue1.picture.large)
            .into(binding.UserProfile)

        binding.tvUserNameData.text =
            userDataValue1.name.title + " " + userDataValue1.name.first + " " + userDataValue1.name.last
        binding.tvUserGenderData.text = userDataValue1.gender
        binding.tvUserDobData.text = userDataValue1.dob.date + "(${userDataValue1.dob.age})"
        binding.tvUserCountryData.text = userDataValue1.location.country
        binding.tvUserAddressData.text =
            userDataValue1.location.street.number.toString() + "(${userDataValue1.location.street.name})" + "," + userDataValue1.location.city + " ," + userDataValue1.location.state
        binding.tvUserNumberData.text = userDataValue1.phone


    }
}