package com.sergo_smyk.monobankcurrency.data.datasource

interface DataSource {
    val remote: Remote
    val local: Local

    interface Remote
    interface Local
}