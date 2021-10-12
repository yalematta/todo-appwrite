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
package com.yalematta.todoappwrite.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.yalematta.todoappwrite.R
import com.yalematta.todoappwrite.ui.accounts.AccountsFragment
import com.yalematta.todoappwrite.ui.accounts.LoginFragment
import com.yalematta.todoappwrite.ui.accounts.SignupFragment
import com.yalematta.todoappwrite.utils.Client

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Client.create(applicationContext)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<SignupFragment>(R.id.fragment_container_view)
            }
        }
    }
}