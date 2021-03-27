package com.uzlov.moviefind.model

import android.os.Parcel
import android.os.Parcelable

data class TestFilm(val name:String = "", val genre:String = "", val rating: Double = 0.0,
                    val description: String = "", val studio: String = "", val year: String = "") : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString()  ?: "",
        parcel.readDouble(),
        parcel.readString()  ?: "",
        parcel.readString()  ?: "",
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(genre)
        parcel.writeDouble(rating)
        parcel.writeString(description)
        parcel.writeString(studio)
        parcel.writeString(year)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TestFilm> {
        override fun createFromParcel(parcel: Parcel): TestFilm {
            return TestFilm(parcel)
        }

        override fun newArray(size: Int): Array<TestFilm?> {
            return arrayOfNulls(size)
        }
    }
}