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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yalematta.todoappwrite.R
import com.yalematta.todoappwrite.databinding.FragmentAccountBinding
import com.yalematta.todoappwrite.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {

    private lateinit var binding: FragmentSignupBinding
    private lateinit var viewModel: AccountsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(AccountsViewModel::class.java)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_signup,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner

        binding.loginHere.setOnClickListener {
            viewModel.navigateToLogin()
        }

        binding.signup.setOnClickListener {
            viewModel.onSignup(binding.email.text, binding.password.text, binding.name.text)
        }

        viewModel.error.observe(viewLifecycleOwner, { event ->
            event?.getContentIfNotHandled()?.let { // Only proceed if the event has never been handled
                Toast.makeText(requireContext(), it.message , Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.response.observe(viewLifecycleOwner, { event ->
            event?.getContentIfNotHandled()?.let {
                // do something with the response it
            }
        })

        return binding.root
    }
}