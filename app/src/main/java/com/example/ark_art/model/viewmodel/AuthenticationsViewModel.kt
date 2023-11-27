package com.example.ark_art.model.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.example.ark_art.model.data.apps_Model
import com.example.ark_art.model.repository.AuthenticationRepository
import kotlinx.coroutines.launch

@OptIn(SavedStateHandleSaveableApi::class)
class AuthenticationsViewModel(
    private val authRepository : AuthenticationRepository = AuthenticationRepository(),
    private val saveStateHandle : SavedStateHandle = SavedStateHandle()
) : ViewModel(){

    val currentUser = authRepository.currentUser
    val hasUser : Boolean get() = authRepository.hasUser()

    val email: MutableState<String> = mutableStateOf("")

    var signInState by saveStateHandle.saveable(
        key="${apps_Model.SignIn(email="",password="")}",
        init = {
            mutableStateOf(apps_Model.SignIn())
        }
    )
    var signUpState by saveStateHandle.saveable(
        key = "${apps_Model.SignUp()}",
        init = {
            mutableStateOf(apps_Model.SignUp())
        }
    )

    fun onUserNameSignInChange(Email: String){
        signInState = signInState.copy(email = Email)
    }

    fun onPasswordSignInChange(password : String){
        signInState = signInState.copy(password = password)
    }

    fun onUserNameSignUpChange(username : String){
        signUpState = signUpState.copy(userNameSignUp = username)
    }

    fun onPasswordSignUpChange(password : String){
        signUpState = signUpState.copy(passwordSignUp = password)
    }

    fun onConfirmPassword(password: String){
        signUpState = signUpState.copy(confirmPasswordSignUp = password)
    }

    private fun validateSignInForm() = signInState
        .email.isNotBlank() &&
            signInState.password.isNotBlank()

    private fun validateSingUpForm() = signUpState
        .userNameSignUp.isNotBlank() &&
            signUpState
                .passwordSignUp.isNotBlank() &&
            signUpState
                .confirmPasswordSignUp.isNotBlank()

    fun signUp(
        context: Context
    ) = viewModelScope.launch{
        try {
            if (!validateSingUpForm()){
                throw IllegalStateException("username and password are required")
            }
            signUpState = signUpState.copy(isLoading = true)

            if (signUpState.passwordSignUp != signUpState.confirmPasswordSignUp){
                throw IllegalStateException("password are not match")
            }

            signUpState = signUpState.copy(signUpError = null)

            authRepository.CreateUser(
                signUpState.userNameSignUp,
                signUpState.passwordSignUp,
                onComplete = { isSuccess ->
                    if (!isSuccess){
                        Toast.makeText(context,"failed", Toast.LENGTH_SHORT).show()
                        signUpState = signUpState.copy(isSuccessLogin = false)
                    }else{
                        Toast.makeText(context,"success", Toast.LENGTH_SHORT).show()
                        signUpState = signUpState.copy(isSuccessLogin = true)
                    }
                }
            )
        }catch (e : Exception){
            signUpState = signUpState.copy(signUpError = e.localizedMessage)
        } finally {
            signUpState = signUpState.copy(isLoading = false)
        }
    }

    fun signIn(context : Context) = viewModelScope.launch {
        try {
            if (!validateSignInForm()){
                throw IllegalStateException("username and password are required")
            }

            signInState = signInState.copy(isLoading = true)
            signInState = signInState.copy(loginError = null)

            authRepository.LoginUser(
                signInState.email,
                signInState.password,
                onComplete = {isSucsess ->
                    if (!isSucsess){
                        Toast.makeText(context,"failed", Toast.LENGTH_SHORT).show()
                        signInState = signInState.copy(isSuccessLogin = false)
                    }else{
                        Toast.makeText(context,"success", Toast.LENGTH_SHORT).show()
                        signInState = signInState.copy(isSuccessLogin = true)
                    }
                }
            )
        }catch (e : Exception){
            signInState = signInState.copy(loginError = e.localizedMessage)
        }finally {
            signInState = signInState.copy(isLoading = false)
        }
    }
}