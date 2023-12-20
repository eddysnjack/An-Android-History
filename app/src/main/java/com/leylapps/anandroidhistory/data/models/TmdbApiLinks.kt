package com.leylapps.anandroidhistory.data.models

object TmdbApiLinks {
    var base = "https://api.themoviedb.org/3"

    object Images {
        val base = "http://image.tmdb.org/t/p/"
        val baseSecure = "https://image.tmdb.org/t/p/"

        enum class backdropSizes(value: String) {
            w300("w300"),
            w780("w780"),
            w1280("w1280"),
            original("original")
        }

    }
    /*
      "images": {
    "base_url": "http://image.tmdb.org/t/p/",
    "secure_base_url": "https://image.tmdb.org/t/p/",
    "backdrop_sizes": [
      "w300",
      "w780",
      "w1280",
      "original"
    ],
    "logo_sizes": [
      "w45",
      "w92",
      "w154",
      "w185",
      "w300",
      "w500",
      "original"
    ],
    "poster_sizes": [
      "w92",
      "w154",
      "w185",
      "w342",
      "w500",
      "w780",
      "original"
    ],
    "profile_sizes": [
      "w45",
      "w185",
      "h632",
      "original"
    ],
    "still_sizes": [
      "w92",
      "w185",
      "w300",
      "original"
    ]
  }
  */

    object Search {
        private var base = "${TmdbApiLinks.base}/search"
        val multi = "${this.base}/multi"
    }

    object Movie {
        private var base = "${TmdbApiLinks.base}/movie"
        val detail = fun(movieId: String): String { return "${this.base}/$movieId" }
    }

    object Trending {
        object Params {
            enum class TimeWindow(val value: String) {
                day("day"),
                week("week")
            }
        }

        private val base = "${TmdbApiLinks.base}/trending"
        val all = fun(timeWindow: Params.TimeWindow): String { return "${this.base}/all/${timeWindow.value}" }
        val movies = fun(timeWindow: Params.TimeWindow): String { return "${this.base}/movie/${timeWindow.value}" }
        val people = fun(timeWindow: Params.TimeWindow): String { return "${this.base}/person/${timeWindow.value}" }
    }
}