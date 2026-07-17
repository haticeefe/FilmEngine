package com.example.filmengine.data.remote.dto

data class CreditsDto(
    val id: Int,
    val cast: List<CastMemberDto>
)

data class CastMemberDto(
    val id: Int,
    val name: String,
    val character: String,
    val profile_path: String?
)

// Bu dosya, bir filmin oyuncu kadrosu bilgisini (credits) API'den
// Kotlin nesnelerine dönüştürmek için kullanılan DTO sınıflarını içerir.