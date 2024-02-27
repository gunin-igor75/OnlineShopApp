package com.github.gunin_igor75.onlineshopapp.presentation.component

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.sp
import com.github.gunin_igor75.onlineshopapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarApp(
    modifier: Modifier = Modifier,
    titleResId: Int
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(titleResId),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 16.sp
                )
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarAppWithNav(
    modifier: Modifier = Modifier,
    titleResId: Int,
    onClickBack: () -> Unit
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(titleResId),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 16.sp
                )
            )
        },
        navigationIcon = {
            IconButton(onClick = onClickBack) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = R.drawable.ic_left_arrow_default
                    ),
                    contentDescription = stringResource(
                        R.string.icon_arrow_left_description
                    )
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarAppWithNavAction(
    modifier: Modifier = Modifier,
    titleResId: Int,
    onClickBack: () -> Unit,
    onClickAction:() -> Unit
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(titleResId),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 16.sp
                )
            )
        },
        navigationIcon = {
            IconButton(onClick = onClickBack) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = R.drawable.ic_left_arrow_default
                    ),
                    contentDescription = stringResource(
                        R.string.icon_arrow_left_description
                    )
                )
            }
        },
        actions = {
            IconButton(onClick = onClickAction) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = R.drawable.ic_send_default
                    ),
                    contentDescription = stringResource(
                        R.string.icon_send_description
                    )
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarAppWithNavActionNotTitle(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    onClickAction:() -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {},
        navigationIcon = {
            IconButton(onClick = onClickBack) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = R.drawable.ic_left_arrow_default
                    ),
                    contentDescription = stringResource(
                        R.string.icon_arrow_left_description
                    )
                )
            }
        },
        actions = {
            IconButton(onClick = onClickAction) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = R.drawable.ic_send_default
                    ),
                    contentDescription = stringResource(
                        R.string.icon_send_description
                    )
                )
            }
        }
    )
}