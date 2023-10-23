package br.edu.ifsp.aluno.bleinermathias.fastcalculation

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// Um bean
@Parcelize
data class ResultGame(
    val points:String = "0,0",
):Parcelable
