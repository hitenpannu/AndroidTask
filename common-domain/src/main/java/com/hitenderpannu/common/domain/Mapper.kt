package com.hitenderpannu.common.domain

interface Mapper<T, R> {

    fun to(t: T): R

    fun from(r: R): T
}
