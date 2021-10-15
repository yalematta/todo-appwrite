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
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yalematta.todoappwrite.R
import com.yalematta.todoappwrite.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: AccountsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentLoginBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.apply {

            login.setOnClickListener {
                viewModel.onLogin(binding.email.text, binding.password.text)
            }

            signupHere.setOnClickListener {
                viewModel.navigateToSignup()
            }
        }

//        viewModel.error.observe(viewLifecycleOwner, { event ->
//            event?.getContentIfNotHandled()
//                ?.let { // Only proceed if the event has never been handled
//                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
//                }
//        })
//
//        viewModel.response.observe(viewLifecycleOwner, { event ->
//            event?.getContentIfNotHandled()?.let {
//                // do something with the response (it)
//            }
//        })
    }
}