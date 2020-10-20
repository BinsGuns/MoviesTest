package com.vince.movies.data.entity
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/*
Copyright (c) 2020 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */

@Entity
data class Movies (
	@ColumnInfo(name = "title")
	@SerializedName("Title") val title : String? = "",

	@ColumnInfo(name = "year")
	@SerializedName("Year") val year : Int? = 0,


	@ColumnInfo(name = "imdbID")
	@SerializedName("imdbID") val imdbID : String?=  "",

	@ColumnInfo(name = "type")
	@SerializedName("Type") val type : String? = "",

	@ColumnInfo(name = "poster")
	@SerializedName("Poster") val poster : String?  ="",

	@ColumnInfo(name = "Actors")
	@SerializedName("Actors") val Actors:  String?  ="",

	@ColumnInfo(name = "Awards")
	@SerializedName("Awards") val Awards:  String?  ="",

	@ColumnInfo(name = "BoxOffice")
	@SerializedName("BoxOffice")val BoxOffice:  String?  ="",

	@ColumnInfo(name = "Country")
	@SerializedName("Country")val Country:  String?  ="",

	@ColumnInfo(name = "DVD")
	@SerializedName("DVD")val DVD:  String?  ="",

	@ColumnInfo(name = "Director")
	@SerializedName("Director")val Director:  String?  ="",

	@ColumnInfo(name = "Genre")
	@SerializedName("Genre")val Genre:  String?  ="",

	@ColumnInfo(name = "Language")
	@SerializedName("Language")val Language:  String?  ="",

	@ColumnInfo(name = "Metascore")
	@SerializedName("Metascore")val Metascore:  String?  ="",

	@ColumnInfo(name = "Plot")
	@SerializedName("Plot")val Plot:  String?  ="",

	@ColumnInfo(name = "Production")
	@SerializedName("Production")val Production:  String?  ="",

	@ColumnInfo(name = "Rated")
	@SerializedName("Rated")val Rated:  String?  ="",

	@ColumnInfo(name = "Released")
	@SerializedName("Released")val Released:  String?  ="",

	@ColumnInfo(name = "Response")
	@SerializedName("Response")val Response:  String?  ="",

	@ColumnInfo(name = "Runtime")
	@SerializedName("Runtime")val Runtime:  String?  ="",

	@ColumnInfo(name = "Website")
	@SerializedName("Website")val Website:  String?  ="",

	@ColumnInfo(name = "Writer")
	@SerializedName("Writer")val Writer:  String?  ="",

//	@ColumnInfo(name = "Year_detail")
//	@SerializedName("Year_detail")val Year:  String?  ="",

	@ColumnInfo(name = "imdbRating")
	@SerializedName("imdbRating")val imdbRating:  String?  ="",

	@ColumnInfo(name = "imdbVotes")
	@SerializedName("imdbVotes")val imdbVotes:  String?  ="",

	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "movie_id")
	var id :  Long? = 0,


)