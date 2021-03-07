package com.vitaly.skywebtest.utils

import androidx.paging.PagedList


 fun createPageListConfig() = PagedList.Config.Builder()
    .setPageSize(5)
    .setInitialLoadSizeHint(10)
    .setEnablePlaceholders(false)
    .build()