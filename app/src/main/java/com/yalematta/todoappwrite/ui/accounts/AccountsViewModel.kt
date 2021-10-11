/*
 * Designed and developed by 2021 yalematta (Layale Matta)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.yalematta.todoappwrite.ui.accounts

import android.text.Editable
import androidx.activity.ComponentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yalematta.todoappwrite.BuildConfig
import com.yalematta.todoappwrite.utils.Client.client
import com.yalematta.todoappwrite.utils.Event
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Account
import kotlinx.coroutines.launch
import org.json.JSONObject

class AccountsViewModel : ViewModel() {

    private val _error = MutableLiveData<Event<Exception>>().apply {
        value = null
    }
    val error: LiveData<Event<Exception>> = _error

    private val _response = MutableLiveData<Event<String>>().apply {
        value = null
    }
    val response: LiveData<Event<String>> = _response

    private val accountService by lazy {
        Account(client)
    }

    fun onLogin(email: Editable, password: Editable) {
        viewModelScope.launch {
            try {
                var response = accountService.createSession(email.toString(), password.toString())
                var json = response.body?.string() ?: ""
                json = JSONObject(json).toString(8)
                _response.postValue(Event(json))
            } catch (e: AppwriteException) {
                _error.postValue(Event(e))
            }
        }
    }

    fun oAuthLogin(activity: ComponentActivity) {
        viewModelScope.launch {
            try {
                accountService.createOAuth2Session(
                    activity,
                    "google",
                    BuildConfig.SUCCESS_URL,
                    BuildConfig.FAILURE_URL
                )
            } catch (e: Exception) {
                _error.postValue(Event(e))
            } catch (e: AppwriteException) {
                _error.postValue(Event(e))
            }
        }
    }

    fun onSignup(email: Editable, password: Editable, name: Editable) {
        viewModelScope.launch {
            try {
                var response =
                    accountService.create(email.toString(), password.toString(), name.toString())
                var json = response.body?.string() ?: ""
                json = JSONObject(json).toString(2)
                _response.postValue(Event(json))
            } catch (e: AppwriteException) {
                _error.postValue(Event(e))
            }
        }

    }

    fun onGetUser() {
        viewModelScope.launch {
            try {
                var response = accountService.get()
                var json = response.body?.string() ?: ""
                json = JSONObject(json).toString(2)
                _response.postValue(Event(json))
            } catch (e: AppwriteException) {
                _error.postValue(Event(e))
            }
        }
    }

    fun onLogout() {
        viewModelScope.launch {
            try {
                var response = accountService.deleteSession("current")
                var json = response.body?.string()?.ifEmpty { "{}" }
                json = JSONObject(json).toString(4)
                _response.postValue(Event(json))
            } catch (e: AppwriteException) {
                _error.postValue(Event(e))
            }
        }
    }
}