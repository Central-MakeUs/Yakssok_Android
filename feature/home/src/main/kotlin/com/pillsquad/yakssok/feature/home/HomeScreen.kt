package com.pillsquad.yakssok.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pillsquad.yakssok.feature.home.model.HomeUiState

@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    var userName by remember { mutableStateOf("") }

    HomeScreen(
        uiState = uiState.value,
        searchUser = viewModel::searchUser,
        userName = userName,
        changeUserName = { userName = it }
    )
}

@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    userName: String,
    changeUserName: (String) -> Unit,
    searchUser: (userName: String) -> Unit
) {
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "유저 검색",
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            value = userName,
            onValueChange = changeUserName,
            label = { Text("이름 입력") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { searchUser(userName) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("검색")
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        when (uiState) {
            HomeUiState.Init ->{
                Box(modifier = Modifier.fillMaxSize())
            }

            is HomeUiState.Loading -> {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier.size(40.dp))
                }
            }

            is HomeUiState.Success -> {
                Text(
                    text = "이름: ${uiState.user.name}\nID: ${uiState.user.id}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            is HomeUiState.Failure -> {
                Text(
                    text = "유저 정보를 불러오지 못했습니다.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}