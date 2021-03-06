package com.example.kmm_apollo.android.ui.desserts.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.annotation.ExperimentalCoilApi
import com.apollographql.apollo.api.ApolloExperimental
import com.example.kmmapollo.shared.cache.Dessert
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.compose.getViewModel

@ApolloExperimental
@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@Composable
fun DessertListView(bottomBar: @Composable () -> Unit, dessertSelected: (dessert: Dessert) -> Unit) {
    val dessertListViewModel = getViewModel<DessertListViewModel>()
    val lazyDessertList = dessertListViewModel.desserts.collectAsLazyPagingItems()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Desserts") }) },
        bottomBar = bottomBar) {
            LazyColumn(contentPadding = it) {
                items(lazyDessertList) { item ->
                    item?.let { dessert ->
                        DessertListRowView(dessert, dessertSelected)
                    }
                }
                lazyDessertList.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally) {
                                    CircularProgressIndicator (modifier = Modifier.padding(16.dp))
                                }
                            }
                        }
                        loadState.append is LoadState.Loading -> {
                            item {
                                Column(modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally) {
                                    CircularProgressIndicator (modifier = Modifier.padding(16.dp))
                                }
                            }
                        }
                        loadState.refresh is LoadState.Error -> {
                            val e = lazyDessertList.loadState.refresh as LoadState.Error
                            item {
                                Text(
                                    text = e.error.localizedMessage!!,
                                )
                            }
                        }
                        loadState.append is LoadState.Error -> {
                            val e = lazyDessertList.loadState.append as LoadState.Error
                            item {
                                Text(
                                    text = e.error.localizedMessage!!
                                )
                            }
                        }
                    }
                }
            }
        }
}