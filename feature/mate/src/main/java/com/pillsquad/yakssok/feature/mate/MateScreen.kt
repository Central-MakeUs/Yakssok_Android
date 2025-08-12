package com.pillsquad.yakssok.feature.mate

import android.content.ClipData
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.toClipEntry
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pillsquad.yakssok.core.designsystem.component.YakssokButton
import com.pillsquad.yakssok.core.designsystem.component.YakssokTextField
import com.pillsquad.yakssok.core.designsystem.component.YakssokTopAppBar
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.feature.mate.component.DashedBorderBox
import com.pillsquad.yakssok.feature.mate.component.MateCompleteDialog
import com.pillsquad.yakssok.feature.mate.model.MateUiModel
import kotlinx.coroutines.launch

@Composable
internal fun MateRoute(
    viewModel: MateViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val event by viewModel.event.collectAsStateWithLifecycle(initialValue = null)
    val context = LocalContext.current
    var isComplete by remember { mutableStateOf(false) }

    LaunchedEffect(event) {
        when (event) {
            is MateEvent.PostSuccess -> isComplete = true
            is MateEvent.ShowToast -> {
                Toast.makeText(context, (event as MateEvent.ShowToast).message, Toast.LENGTH_SHORT).show()
            }
            else -> Unit
        }
    }

    if (isComplete) {
        MateCompleteDialog(
            nickName = uiState.friendNickName,
            relationName = uiState.relationName,
            imgUrl = uiState.friendImageUrl,
            onNavigateBack = {
                isComplete = false
                onNavigateBack()
            }
        )
    }

    when (uiState.curPage) {
        0 -> MateScreen(
            uiState = uiState,
            updateInputCode = viewModel::updateInputCode,
            onNavigateBack = onNavigateBack,
            onNavigatePlus = { viewModel.getFriendInfo() }
        )

        1 -> PlusMateScreen(
            nickName = uiState.friendNickName,
            relationName = uiState.relationName,
            profileImageUrl = uiState.friendImageUrl,
            enabled = uiState.isEnabled,
            onValueChange = viewModel::updateNickName,
            onNavigateBack = { viewModel.updateCurPage(0) },
            onNavigateHome = { viewModel.postAddFriend() }
        )
    }
}

@Composable
private fun MateScreen(
    uiState: MateUiModel,
    updateInputCode: (String) -> Unit,
    onNavigateBack: () -> Unit,
    onNavigatePlus: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(YakssokTheme.color.grey100)
            .statusBarsPadding()
    ) {
        YakssokTopAppBar(
            modifier = Modifier.padding(horizontal = 16.dp),
            title = "메이트 추가",
            onBackClick = onNavigateBack
        )

        Spacer(modifier = Modifier.height(16.dp))

        IconBox()

        Spacer(modifier = Modifier.height(13.dp))

        Text(
            modifier = Modifier.padding(start = 32.dp),
            text = "메이트 코드 입력",
            style = YakssokTheme.typography.body2,
            color = YakssokTheme.color.grey950
        )

        Spacer(modifier = Modifier.height(7.dp))

        YakssokTextField(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxWidth(),
            backgroundColor = YakssokTheme.color.grey50,
            value = uiState.friendCode,
            onValueChange = updateInputCode,
            hint = "코드입력",
            maxLength = 9,
            isSuffixEnabled = uiState.friendCode.isNotEmpty(),
            suffixButtonText = "추가",
            onSuffixButtonClick = onNavigatePlus,
        )

        Spacer(modifier = Modifier.height(21.dp))

        CodeContent(
            modifier = Modifier.weight(1f),
            userName = uiState.myName,
            inviteCode = uiState.myCode,
        )
    }
}

@Composable
private fun IconBox(

) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(153.dp),
        contentAlignment = Alignment.Center
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
                .background(
                    color = YakssokTheme.color.grey150,
                    shape = RoundedCornerShape(24.dp)
                )
        )

        Icon(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxSize(),
            painter = painterResource(R.drawable.img_mate_main),
            contentDescription = "mate add",
            tint = Color.Unspecified
        )
    }
}

@Composable
private fun CodeContent(
    modifier: Modifier,
    userName: String,
    inviteCode: String,
) {
    val clipboard = LocalClipboard.current
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = YakssokTheme.color.grey50,
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            )
            .navigationBarsPadding()
            .padding(start = 16.dp, end = 16.dp, top = 44.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "내 코드 알려주고,\n팔로우 요청해보세요!",
            textAlign = TextAlign.Center,
            style = YakssokTheme.typography.body1,
            color = YakssokTheme.color.grey950
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "내 코드 복사",
            style = YakssokTheme.typography.body2,
            color = YakssokTheme.color.grey950
        )

        Spacer(modifier = Modifier.height(9.dp))

        DashedBorderBox {
            Box(
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        val clipData = ClipData.newPlainText("초대코드", inviteCode)

                        scope.launch {
                            clipboard.setClipEntry(clipData.toClipEntry())
                        }
                    }
                )
            ) {
                Text(
                    text = inviteCode,
                    textDecoration = TextDecoration.Underline,
                    style = YakssokTheme.typography.body0,
                    color = YakssokTheme.color.grey950
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        ShareInviteButton(
            userName = userName,
            inviteCode = inviteCode,
        )
    }
}

@Composable
private fun ShareInviteButton(
    userName: String,
    inviteCode: String,
    inviteLink: String = "https://yakssok.onelink.me/ggOB/uvut58xg"
) {
    val context = LocalContext.current

    YakssokButton(
        modifier = Modifier
            .fillMaxWidth()
            .imePadding(),
        text = "내 코드 공유하기",
        backgroundColor = YakssokTheme.color.primary400,
        contentColor = YakssokTheme.color.grey50,
        onClick = {
            val sharingMessage = buildInviteMessage(userName, inviteCode, inviteLink)
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, sharingMessage)
            }
            context.startActivity(
                Intent.createChooser(intent, null)
            )
        },
    )
}

private fun buildInviteMessage(userName: String, code: String, link: String): String {
    return """
        ${userName}님이 함께 약 챙기자고 해요.
        가끔 잊어버릴 수도 있으니까,
        서로 약 잘 먹고 있는지 확인하며 챙기는 건 어때요?
        필요할 땐 잔소리도 살짝😉
        
        ${userName}님의 코드: $code
        
        👇 여기를 들어오면 같이 챙길 수 있어요
        $link
    """.trimIndent()
}