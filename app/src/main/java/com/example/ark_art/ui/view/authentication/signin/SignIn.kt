package com.example.ark_art.ui.view.authentication.signin

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ark_art.R
import com.example.ark_art.model.viewmodel.AuthenticationsViewModel

@Composable
fun SignIn(
    authViewModel : AuthenticationsViewModel? = null,
    navToHomeUi : () -> Unit,
    navToSignUpUi : () -> Unit,
){
    val authState = authViewModel?.signInState
    val isError = authState?.loginError != null
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        content = { paddingValue->

            Box(
                modifier = Modifier
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center,
                content = {
                    SignInItem(
                        modifier = Modifier
                            .padding(paddingValue),
                        onNavToHomePage = {
                            authViewModel?.signIn(context = context)
                        },
                        onNavToSingUpPage = {
                            navToSignUpUi.invoke()
                        },
                        username = authState?.email ?:"",
                        password = authState?.password ?:"",
                        onUsernameChange = { value ->
                            authViewModel?.onUserNameSignInChange(value)
                        },
                        onPasswordChange = { value ->
                            authViewModel?.onPasswordSignInChange(value)
                        },
                        isError = isError
                    )

                    if (authState?.isLoading == true){
                        CircularProgressIndicator()
                    }

                    //LaunchedEffect(key1 = authState?.isLoading, block = {})
                }
            )
        }
    )
}

@Composable
private fun SignInItem(
    modifier: Modifier = Modifier,
    onNavToHomePage: () -> Unit,
    onNavToSingUpPage : () -> Unit,
    username : String,
    password : String,
    onPasswordChange : (String)-> Unit,
    onUsernameChange : (String)-> Unit,
    isError : Boolean
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        content = {
            TextField(
                modifier = Modifier,
                value = username,
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.email_or_username)
                    )
                },
                shape= RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                onValueChange ={
                    Log.d("SignInItem", "onValueChange: $it")
                    onUsernameChange.invoke(it)
                } ,
                isError = isError
            )
            TextField(
                modifier = Modifier,
                value = password,
                singleLine = true,
                placeholder = {
                    Text(text = stringResource(id = R.string.password))
                },
                shape= RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                onValueChange ={
                    Log.d("SignInItem", "onValueChange: $it")
                    onPasswordChange.invoke(it)
                }
            )
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                content = {
                    Row (
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        content = {
                            OutlinedButton(
                                onClick = {
                                    onNavToHomePage()
                                },
                                colors= ButtonDefaults.buttonColors(Color.LightGray),
                                content ={
                                    Text(
                                        text = stringResource(id = R.string.sign_in),
                                        color = Color.Black
                                    )
                                }
                            )

                            TextButton(
                                onClick = {
                                    onNavToSingUpPage()
                                },
                                content = {
                                    Text(
                                        modifier = Modifier.drawBehind {
                                            val strokeWidthPx = 1.dp.toPx()
                                            val verticalOffset = size.height - 2.sp.toPx()

                                            drawLine(
                                                color = Color.Blue,
                                                strokeWidth = strokeWidthPx,
                                                start = Offset(0f,verticalOffset),
                                                end = Offset(size.width, verticalOffset)
                                            )
                                        },
                                        text = stringResource(id = R.string.sign_up),
                                    )
                                }
                            )
                        }
                    )
                }
            )
            TextButton(
                onClick = {
                },
                content = {
                    Text(text = stringResource(id = R.string.email_account))
                }
            )
        }
    )
}

@Preview(showSystemUi = true)
@Composable
fun SignInView(){
//    SignIn()
}