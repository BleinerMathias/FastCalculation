package br.edu.ifsp.aluno.bleinermathias.fastcalculation

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// Um bean
@Parcelize
data class Settings(
    val playerName: String = "",
    val rounds: Int = 0,
    val roundsInterval:Long = 0L
):Parcelable
