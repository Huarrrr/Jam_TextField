package com.huarrrr.jam_editfield.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.huarrrr.jam_editfield.R
import com.huarrrr.jam_editfield.ui.theme.CompostablePosition
import com.huarrrr.jam_editfield.ui.theme.compostablePosition

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: Int,
    enabled: Boolean,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = { Text(text = stringResource(id = labelId)) },
        singleLine = true,
        textStyle = TextStyle(fontSize = 14.sp, color = MaterialTheme.colors.onBackground),
        modifier = modifier
            .padding(bottom = 10.dp, start = 20.dp, end = 20.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction
    )
}

@Composable
fun PasswordInput(
    modifier: Modifier = Modifier,
    passwordState: MutableState<String>,
    labelId: Int = R.string.password,
    enabled: Boolean = true,
    passwordVisibility: MutableState<Boolean>,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    val visualTransformation =
        if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation()

    OutlinedTextField(
        value = passwordState.value,
        onValueChange = { passwordState.value = it },
        label = { Text(text = stringResource(id = labelId)) },
        singleLine = true,
        textStyle = TextStyle(fontSize = 14.sp, color = MaterialTheme.colors.onBackground),
        modifier = modifier
            .padding(bottom = 40.dp, start = 20.dp, end =20.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        visualTransformation = visualTransformation,
        trailingIcon = { PasswordVisibility(passwordVisibility = passwordVisibility) },
        keyboardActions = onAction
    )
}

@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
    val visible = passwordVisibility.value
    val icon =
        painterResource(id = if (visible) R.drawable.ic_visibility_on else R.drawable.ic_visibility_off)

    IconButton(onClick = { passwordVisibility.value = !visible }) {
        Icon(painter = icon, contentDescription = null)
    }
}


@Composable
fun AccountInput(
    modifier: Modifier = Modifier,
    numberState: MutableState<String>,
    labelId: Int = R.string.phone_number,
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    InputField(
        modifier = modifier,
        valueState = numberState,
        labelId = labelId,
        enabled = enabled,
        keyboardType = KeyboardType.Phone,
        imeAction = imeAction,
        onAction = onAction
    )
}

@Composable
fun Title(textId: Int) {
    Text(
        text = stringResource(id = textId),
        modifier = Modifier.padding(vertical = 50.dp, horizontal = 15.dp),
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.onBackground
    )
}


@Composable
fun LoginButton(
    textId: Int,
    validInputs: Boolean,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .size(200.dp,48.dp),
        enabled =  validInputs,
        onClick = onClick
    ) {
        Text(text = stringResource(id = textId), modifier = Modifier.padding(5.dp))
    }
}

@ExperimentalComposeUiApi
@Composable
fun Login(
    onDone: (String, String) -> Unit
) {
    val phoneNumber = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequester = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(phoneNumber.value, password.value) {
        phoneNumber.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    val modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())

    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()){

        Image(
            alignment = Alignment.Center,
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.mipmap.bg_main),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            Title(textId = R.string.login_with_phone)

            AccountInput(numberState = phoneNumber, onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onDone(phoneNumber.value.trim(), password.value.trim())
                keyboardController?.hide()
            })

            PasswordInput(
                modifier = Modifier.focusRequester(passwordFocusRequester),
                passwordState = password,
                passwordVisibility = passwordVisibility,
                onAction = KeyboardActions {
                    if (!valid) return@KeyboardActions
                    onDone(phoneNumber.value.trim(), password.value.trim())
                    keyboardController?.hide()
                }
            )

            LoginButton(
                textId = R.string.login_with_phone,
                validInputs = valid
            ) {
                onDone(phoneNumber.value.trim(), password.value.trim())
                keyboardController?.hide()
            }
            Text(
                text = stringResource(id = R.string.forgot_password),
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 10.dp),
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Box(modifier = Modifier
            .compostablePosition(CompostablePosition.CenterBottom))
        {
            Text(
                text = stringResource(id = R.string.login_tip),
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 10.dp),
                fontSize = 12.sp,
                color = Color.LightGray
            )
        }

    }



}


