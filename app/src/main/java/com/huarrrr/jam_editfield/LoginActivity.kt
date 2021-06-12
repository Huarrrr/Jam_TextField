package com.huarrrr.jam_editfield

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.tooling.preview.Preview
import com.gyf.immersionbar.ImmersionBar
import com.huarrrr.jam_editfield.composable.Login
import com.huarrrr.jam_editfield.ui.theme.Jam_EditFieldTheme
import com.huarrrr.jam_editfield.utils.DayModeUtil

class LoginActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImmersionBar.with(this)
            .statusBarDarkFont(!DayModeUtil.isNightMode(this), 0.2f)
            .transparentStatusBar().init()
        setContent {
            login()
        }
    }

    @ExperimentalComposeUiApi
    @Composable
    private fun login() {
        Jam_EditFieldTheme {
            Login { account, pwd ->
                Toast.makeText(this,"$account:$pwd",Toast.LENGTH_SHORT).show()
            }
        }

    }

    @ExperimentalComposeUiApi
    @Preview
    @Composable
    fun PreviewLogin() {
        login()
    }

}
