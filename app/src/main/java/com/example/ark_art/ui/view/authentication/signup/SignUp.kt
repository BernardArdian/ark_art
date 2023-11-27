package com.example.ark_art.ui.view.authentication.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.ark_art.R
import com.example.ark_art.model.viewmodel.AuthenticationsViewModel

@Composable
fun SignUp(
    authViewModel : AuthenticationsViewModel ? = null,
    navController : NavController = NavController(LocalContext.current)
){


    val signUpState = authViewModel?.signUpState
    val isError = signUpState?.signUpError != null
    val context = LocalContext.current


    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        content = { paddingValue ->
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .fillMaxHeight()
//                    .padding(paddingValue),
//                contentAlignment = Alignment.Center,
//                content = {
//                    SignInContent(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(350.dp),
//                        signUp = {},
//                        signIn = {}
//                    )
//                }
//            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight().padding(paddingValue),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    if (isError) {
                        Text(
                            text = signUpState?.signUpError ?: "unknown error",
                            color = Color.Red
                        )
                    }

                    OutlinedTextField(
                        value = signUpState?.userNameSignUp ?: "",
                        onValueChange = {
                            authViewModel?.onUserNameSignUpChange(it)
                        },
                        label = {
                            Text("email")
                        },
                        isError = isError
                    )

                    OutlinedTextField(
                        value = signUpState?.passwordSignUp?:"",
                        onValueChange = {
                            authViewModel?.onPasswordSignUpChange(it)
                        },
                        label = {
                            Text("password")
                        },
                        isError = isError
                    )

                    OutlinedTextField(
                        value = signUpState?.confirmPasswordSignUp ?: "",
                        onValueChange = {
                            authViewModel?.onConfirmPassword(it)
                        },
                        label = {
                            Text("confirm password")
                        },
                        isError = isError
                    )

                    Spacer(modifier = Modifier.size(16.dp))

                    Button(
                        onClick = {
                            authViewModel?.signUp(context = context)
                        },
                        content = {
                            Text("Sign Up")
                        }
                    )

                    Row(
                        content = {
                            TextButton(
                                onClick = {
                                    //onNavToSignInPage.invoke()
                                },
                                content = {
                                    Text("Sign In")
                                }
                            )
                        }
                    )

                }
            )
        }
    )
}

@Composable
fun SignInContent(
    modifier: Modifier = Modifier,
    signIn : () -> Unit,
    signUp : () -> Unit
){
    val focusRequester = remember { FocusRequester() }

    var username by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        content =  {
            TextField(
                modifier = Modifier
                    .focusRequester(focusRequester),
                value = username,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.username)
                    )
                },
                shape= RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                onValueChange = {
                    username = it
                }
            )
            TextField(

                modifier = Modifier
                    .focusRequester(focusRequester),
                value = username,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.email)
                    )
                },
                shape= RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                onValueChange = {
                    username = it
                }
            )
            TextField(
                modifier = Modifier
                    .focusRequester(focusRequester),
                value = username,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.password_signup)
                    )
                },
                shape= RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                onValueChange = {
                    username = it
                }
            )

            TextField(
                modifier = Modifier
                    .focusRequester(focusRequester),
                value = username,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.confirm_password)
                    )
                },
                shape= RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                onValueChange = {
                    username = it
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        content =  {
                            OutlinedButton(
                                onClick = { },
                                colors= ButtonDefaults.buttonColors(Color.LightGray),
                                content ={
                                    Text(
                                        text = "SignUp",
                                        color = Color.Black
                                    )
                                }
                            )
                            TextButton(
                                onClick = {
                                    signIn.invoke()
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
                                        text = stringResource(id = R.string.sign_in)
                                    )
                                }
                            )
                        }
                    )
                }
            )
        }
    )
}

@Preview(showSystemUi = true)
@Composable
fun SingUpView(){
    SignUp()
}