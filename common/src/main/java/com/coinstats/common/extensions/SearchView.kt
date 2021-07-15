package com.coinstats.common.extensions

import androidx.appcompat.widget.SearchView

fun SearchView.setOnQueryListener(listener: (String?) -> Unit) {
    setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    listener(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    listener(newText)
                    return true
                }
            }
    )
}