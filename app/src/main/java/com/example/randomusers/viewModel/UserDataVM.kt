package com.example.randomusers.viewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.randomusers.Util.Constant
import com.example.randomusers.Util.Extensions
import com.example.randomusers.model.RandomUserListResponse
import com.example.randomusers.network.ApiInterface
import com.example.randomusers.network.ServiceClient
import isInternetAvailable

class UserDataVM(var mContext: Context) : ViewModel() {

    var apiInterface = ServiceClient.apiClient().create(ApiInterface::class.java)

    suspend fun getUsersDataVM(result: Int): LiveData<RandomUserListResponse> {
        val mutableLiveData = MutableLiveData<RandomUserListResponse>()

        if (mContext.isInternetAvailable()) {

            try {
                Extensions.showProgess(mContext)
                var response = apiInterface.getUserdata(result)
                if (response.code() == 200) {
                    Extensions.stopProgress()
                    mutableLiveData.postValue(response.body())
                } else {
                    Extensions.stopProgress()
                    Toast.makeText(
                        mContext,
                        Extensions.errorMessage(response.errorBody()!!),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Extensions.stopProgress()
                Toast.makeText(mContext, Constant.SOMETHING_WENT_WRONG, Toast.LENGTH_SHORT).show()

            }

        } else {
            Toast.makeText(mContext, Constant.NETWORK_NOT_AVAILABLE, Toast.LENGTH_SHORT).show()
        }
        return mutableLiveData
    }


}






