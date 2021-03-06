package com.example.kmm_apollo.android.ui.auth.form

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginFormView(handler: (login: Boolean, email: String, password: String) -> Unit) {
    val (email, setEmail) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }
    val (login, setLogin) = remember { mutableStateOf(true) }
    val label = if (login) "Login" else "Sign Up"

    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .padding(8.dp)
        .fillMaxWidth()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Email",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.body1
            )
            TextField(
                value = email,
                onValueChange = { setEmail(it) }

            )
            Text(
                text = "Password",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.body1
            )
            TextField(
                value = password,
                onValueChange = { setPassword(it) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    handler(login, email, password)
                }, modifier = Modifier
                    .padding(16.dp)
                    .width(320.dp)
            ) {
                Text(label)
            }

            Button(
                onClick = {
                    setLogin(!login)
                }, modifier = Modifier
                    .padding(16.dp)
                    .width(320.dp)
            ) {
                val text = if (login) "Need an account? Sign Up" else "Have an account? Log in"
                Text(text)
            }
        }
    }
}