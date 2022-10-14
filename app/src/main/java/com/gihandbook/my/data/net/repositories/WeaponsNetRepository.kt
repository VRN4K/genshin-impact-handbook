package com.gihandbook.my.data.net.repositories

import com.gihandbook.my.data.net.model.Weapon
import com.gihandbook.my.di.KtorModule.Companion.BASE_URL
import com.gihandbook.my.domain.datacontracts.IWeaponNetRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject

class WeaponsNetRepository @Inject constructor(private val httpClient: HttpClient) :
    IWeaponNetRepository {
    override suspend fun getAllWeapons(): List<String> {
        return httpClient.get("${BASE_URL}/weapons").body()
    }

    override suspend fun getWeaponDetailsByName(name: String): Weapon {
        return httpClient.get("${BASE_URL}/weapons/${name}").body()
    }
}