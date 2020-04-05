package com.hitenderpannu.common.utils.di

interface ComponentProvider<T : SupportInjection> {
    fun inject(t: T)
}

interface SupportInjection

inline fun <reified T> Any.isOfType(t: T): Boolean {
    return this is T
}
