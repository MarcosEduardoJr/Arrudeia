package com.arrudeia.feature.sign.presentation.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arrudeia.core.data.navigation.homeRoute
import com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
import com.arrudeia.core.designsystem.R.color.colorGreen
import com.arrudeia.core.designsystem.R.color.colorPrimary
import com.arrudeia.core.designsystem.R.color.colorRed
import com.arrudeia.core.designsystem.R.color.colorWhite
import com.arrudeia.core.designsystem.R.color.text_grey
import com.arrudeia.core.designsystem.component.ArrudeiaButtonColor
import com.arrudeia.core.designsystem.component.ArrudeiaLoadingWheel
import com.arrudeia.core.designsystem.component.DefaultLinkMovementMethod
import com.arrudeia.core.designsystem.theme.ArrudeiaTheme
import com.arrudeia.feature.sign.R
import com.arrudeia.feature.sign.R.drawable.ic_bg_onboarding
import com.arrudeia.feature.sign.R.string.sign
import com.arrudeia.feature.sign.R.string.sign_description_tired_job
import com.arrudeia.feature.sign.R.string.sign_email
import com.arrudeia.feature.sign.R.string.sign_password
import com.arrudeia.feature.sign.R.string.sign_password_again
import com.arrudeia.feature.sign.R.string.sign_password_lenght_error
import com.arrudeia.feature.sign.R.string.sign_password_not_equals_to_confirm_password_error
import com.arrudeia.feature.sign.R.string.sign_register
import com.arrudeia.feature.sign.presentation.viewmodel.SignViewModel
import com.arrudeia.feature.sign.presentation.viewmodel.SignViewModel.SignUiState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.max


private const val LINK_1 = "link_1"
private const val LINK_2 = "link_2"
private const val SPACING_FIX = 3f


@Composable
internal fun SignRoute(
    onRouteClick: (String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    viewModel: SignViewModel = hiltViewModel(),
    showBottomBar: (Boolean) -> Unit,
) {
    showBottomBar(false)
    Sign(
        onRouteClick,
        viewModel,
        onShowSnackbar
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("DesignSystem")
@Composable
internal fun Sign(
    onRouteClick: (String) -> Unit,
    viewModel: SignViewModel,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    var isRegisterState by remember { mutableStateOf(true) }
    var isResetPasswordState by remember { mutableStateOf(false) }

    var emailValueState by rememberSaveable { mutableStateOf("") }
    var emailIconValueState by rememberSaveable { mutableStateOf(false) }
    var passwordValueState by rememberSaveable { mutableStateOf("") }
    var passwordIconValueState by rememberSaveable { mutableStateOf(false) }
    var confirmPasswordValueState by rememberSaveable { mutableStateOf("") }
    var confirmPasswordIconValueState by rememberSaveable { mutableStateOf(false) }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    val sharedFlow by viewModel.sharedFlow.collectAsStateWithLifecycle()
    when (sharedFlow) {
        is SignUiState.Loading -> {
            isLoading = true
        }

        is SignUiState.Success -> {
            onRouteClick(homeRoute)
            isLoading = false
        }

        is SignUiState.Error -> {
            val message = stringResource((sharedFlow as SignUiState.Error).message)
            LaunchedEffect(true) {
                onShowSnackbar(message, null)
            }
            isLoading = false
        }

        else -> {
            isLoading = false
        }
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode != Activity.RESULT_OK) {
            // The user cancelled the login, was it due to an Exception?
            if (result.data?.action == ActivityResultContracts.StartIntentSenderForResult.ACTION_INTENT_SENDER_REQUEST) {
                val exception = result.data?.getSerializableExtra(
                    ActivityResultContracts.StartIntentSenderForResult.EXTRA_SEND_INTENT_EXCEPTION
                )
                // Log.e("LOG", "Couldn't start One Tap UI: ${e?.localizedMessage}")
            }
            return@rememberLauncherForActivityResult
        }
    }


    ArrudeiaTheme {
        val context = LocalContext.current
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            //body

            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = ic_bg_onboarding),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clipToBounds(),
                    contentScale = ContentScale.Crop,
                )

            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f)),
            ) {

                HtmlText(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 80.dp),
                    html = stringResource(id = sign_description_tired_job)
                )

                if (isLoading)
                    ArrudeiaLoadingWheel(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .align(Alignment.Center),
                    )


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                ) {
                    val scope = rememberCoroutineScope()
                    signTextField(
                        onSearchQueryChanged = {},
                        textValueFieldParam = emailValueState,
                        onSearchTriggered = {},
                        icon = Icons.Rounded.Email,
                        imeAction = ImeAction.Next,
                        requestFocus = false,
                        placeHolderValue = stringResource(id = sign_email),
                        keyboardType = KeyboardType.Email,
                        onTextChange = { emailValueState = it },
                        statusIconValid = { emailIconValueState },
                        showIconIsvalid = isRegisterState
                    )
                    if (emailValueState.isNotEmpty())
                        emailIconValueState = isValidEmail(emailValueState)

                    if (!isResetPasswordState)
                        signTextField(
                            onSearchQueryChanged = {},
                            textValueFieldParam = passwordValueState,
                            onSearchTriggered = {},
                            icon = Icons.Rounded.Lock,
                            imeAction = ImeAction.Done,
                            requestFocus = false,
                            placeHolderValue = stringResource(id = sign_password),
                            useMaskDots = true,
                            keyboardType = KeyboardType.Password,
                            onTextChange = { passwordValueState = it },
                            statusIconValid = { passwordIconValueState },
                            showIconIsvalid = isRegisterState
                        )

                    if (isRegisterState)
                        passwordIconValueState =
                            passwordBehaviour(
                                passwordValueState,
                                passwordIconValueState
                            )

                    if (isRegisterState && !isResetPasswordState)
                        signTextField(
                            onSearchQueryChanged = {},
                            textValueFieldParam = confirmPasswordValueState,
                            onSearchTriggered = {},
                            icon = Icons.Rounded.Lock,
                            imeAction = ImeAction.Done,
                            requestFocus = false,
                            placeHolderValue = stringResource(id = sign_password_again),
                            useMaskDots = true,
                            keyboardType = KeyboardType.Password,
                            onTextChange = { confirmPasswordValueState = it },
                            statusIconValid = { confirmPasswordIconValueState },
                            showIconIsvalid = isRegisterState
                        )


                    if (confirmPasswordValueState.isNotEmpty() && !confirmPasswordValueState.contentEquals(
                            passwordValueState
                        ) && isRegisterState
                    ) {
                        confirmPasswordIconValueState = false
                        Text(
                            text = stringResource(sign_password_not_equals_to_confirm_password_error),
                            modifier = Modifier.padding(4.dp),
                            color = colorResource(id = colorWhite)
                        )
                    } else {
                        if (isRegisterState)
                            confirmPasswordIconValueState =
                                passwordBehaviour(
                                    confirmPasswordValueState,
                                    confirmPasswordIconValueState
                                )
                    }
                    var colorBtn = background_grey_F7F7F9
                    var enableBtn = false

                    if (isResetPasswordState && emailIconValueState) {
                        enableBtn = true
                        colorBtn = colorPrimary
                    } else if (!isRegisterState && passwordBehaviour(
                            passwordValueState,
                            passwordIconValueState
                        ) && emailIconValueState
                        || isRegisterState && passwordBehaviour(
                            passwordValueState,
                            passwordIconValueState
                        ) && passwordValueState.contentEquals(confirmPasswordValueState) && emailIconValueState
                    ) {
                        enableBtn = true
                        colorBtn = colorPrimary
                    } else {
                        colorBtn =  text_grey
                        enableBtn = false
                    }

                    val resetPasswordSuccessMsg =
                        stringResource(id = R.string.open_email_reset_password)
                    val resetPasswordFailureMsg =
                        stringResource(id = R.string.erro_reset_password_try_later)

                    ArrudeiaButtonColor(
                        onClick = {
                            if (isResetPasswordState)
                                resetPassword(
                                    emailValueState,
                                    onShowSnackbar,
                                    resetPasswordSuccessMsg,
                                    resetPasswordFailureMsg
                                )
                            else if (enableBtn)
                                scope.launch {
                                    signClient(
                                        isRegisterState,
                                        email = emailValueState,
                                        password = passwordValueState,
                                        viewmodel = viewModel
                                    )
                                }
                        },
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                            .fillMaxWidth(),
                        colorButton = colorResource(colorBtn),
                    ) {
                        Text(
                            text = stringResource(id = if (isResetPasswordState) R.string.reset_password else if (isRegisterState) sign_register else sign),
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                        )
                    }

                    if (!isResetPasswordState)

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 30.dp)
                                .clickable {

                                    isRegisterState = !isRegisterState
                                },
                            text = stringResource(if (isRegisterState) sign else sign_register),
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 10.dp)
                            .clickable { isResetPasswordState = !isResetPasswordState },
                        text = stringResource(if (!isResetPasswordState) R.string.forgot_password else sign),
                        color = Color.White.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                }

            }
        }
    }
}

fun resetPassword(
    emailValue: String,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    resetPasswordSuccessMsg: String,
    resetPasswordFailureMsg: String
) {
    FirebaseAuth.getInstance().sendPasswordResetEmail(emailValue).addOnSuccessListener {
        CoroutineScope(Dispatchers.IO).launch { onShowSnackbar(resetPasswordSuccessMsg, null) }
    }.addOnFailureListener {
        CoroutineScope(Dispatchers.IO).launch { onShowSnackbar(resetPasswordFailureMsg, null) }
    }
}

@Composable
private fun passwordBehaviour(
    passwordValueState: String,
    passwordIconValueState: Boolean
): Boolean {
    var passwordIconValueState1 = passwordIconValueState
    if (passwordValueState.isNotEmpty())
        if (isValidPassword(passwordValueState)) {
            passwordIconValueState1 = isValidPassword(passwordValueState)
        } else {
            passwordIconValueState1 = isValidPassword(passwordValueState)
            Text(
                text = stringResource(sign_password_lenght_error),
                modifier = Modifier.padding(4.dp),
                color = colorResource(id = colorWhite)
            )
        }
    return passwordIconValueState1
}

fun isValidEmail(text: String): Boolean {
    val emailPattern =
        "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"
    return text.matches(emailPattern.toRegex())
}

fun isValidPassword(text: String): Boolean {
    if (text.length < 6)
        return false
    return true
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun signTextField(
    onSearchQueryChanged: (String) -> Unit,
    textValueFieldParam: String,
    onSearchTriggered: (String) -> Unit,
    icon: ImageVector,
    imeAction: ImeAction,
    requestFocus: Boolean,
    placeHolderValue: String,
    useMaskDots: Boolean = false,
    keyboardType: KeyboardType,
    onTextChange: (String) -> Unit,
    statusIconValid: () -> Boolean,
    showIconIsvalid: Boolean,
) {
    var focusRequester = FocusRequester()

    val keyboardController = LocalSoftwareKeyboardController.current

    val onSearchExplicitlyTriggered = {
        keyboardController?.hide()
        onSearchTriggered(textValueFieldParam)
    }
    val visualTransformation =
        if (useMaskDots) PasswordVisualTransformation(mask = '\u25CF') else VisualTransformation.None

    TextField(
        visualTransformation = visualTransformation,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            cursorColor = Color.LightGray,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = stringResource(
                    id = sign,
                ),
                tint = Color.LightGray,
            )
        },

        trailingIcon = {
            if (showIconIsvalid)
                if (statusIconValid() && textValueFieldParam.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            onSearchQueryChanged("")
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.CheckCircle,
                            contentDescription = stringResource(
                                id = sign,
                            ),
                            tint = colorResource(colorGreen),
                        )
                    }
                } else if (!statusIconValid() && textValueFieldParam.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            onSearchQueryChanged("")
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = stringResource(
                                id = sign,
                            ),
                            tint = colorResource(colorRed),
                        )
                    }
                }
        },
        value = textValueFieldParam,
        onValueChange = { onTextChange(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp, start = 16.dp, end = 16.dp)
            .focusRequester(focusRequester)
            .onKeyEvent {
                if (it.key == Key.Enter) {
                    onSearchExplicitlyTriggered()
                    true
                } else {
                    false
                }
            }
            .testTag("searchTextField"),
        shape = RoundedCornerShape(32.dp),
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = keyboardType
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchExplicitlyTriggered()
            },
        ),
        maxLines = 1,
        singleLine = true,
        placeholder = { Text(placeHolderValue, color = colorResource(id = text_grey)) },
    )
    LaunchedEffect(Unit) {
        if (requestFocus)
            focusRequester.requestFocus()
    }
}

fun signClient(
    isRegister: Boolean,
    email: String,
    password: String,
    viewmodel: SignViewModel
) {
    if (isRegister)
        viewmodel.signUp(email, password)
    else
        viewmodel.signIn(email, password)

}

@Composable
fun HtmlText(
    modifier: Modifier = Modifier,
    html: String,
    onLink1Clicked: (() -> Unit)? = null,
    onLink2Clicked: (() -> Unit)? = null,
) {
    var textStyle = MaterialTheme.typography.headlineMedium
    AndroidView(
        modifier = modifier,
        update = { it.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY) },
        factory = { context ->
            val spacingReady =
                max(textStyle.lineHeight.value - textStyle.fontSize.value - SPACING_FIX, 0f)
            val extraSpacing = spToPx(spacingReady.toInt(), context)
            val gravity = when (textStyle.textAlign) {
                TextAlign.Center -> Gravity.CENTER
                TextAlign.End -> Gravity.END
                else -> Gravity.START
            }

            TextView(context).apply {

                textAlignment = View.TEXT_ALIGNMENT_CENTER
                textSize = textStyle.fontSize.value
                setLineSpacing(extraSpacing, 1f)
                val colorText = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    resources.getColorStateList(colorWhite, null)
                } else {
                    resources.getColorStateList(colorWhite)
                }
                setTextColor(
                    colorText
                )
                setGravity(gravity)
                val colorTextHighLight = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    resources.getColorStateList(colorPrimary, null)
                } else {
                    resources.getColorStateList(colorPrimary)
                }
                setLinkTextColor(colorTextHighLight)
                movementMethod = DefaultLinkMovementMethod(object :
                    DefaultLinkMovementMethod.OnLinkClickedListener {
                    override fun onLinkClicked(url: String?): Boolean {
                        when (url) {
                            LINK_1 -> onLink1Clicked?.invoke()
                            LINK_2 -> onLink2Clicked?.invoke()
                        }
                        return true
                    }

                })
            }
        }
    )
}

fun spToPx(sp: Int, context: Context): Float =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        sp.toFloat(),
        context.resources.displayMetrics
    )
