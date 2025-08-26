package io.github.brenthaertlein.census.acs

sealed class Geography {
    abstract val type: String // for
    abstract val target: String? // in

    class US : Geography() {
        override val type = "us:*"
        override val target: String? = null
    }

    data class State(val fips: String? = null) : Geography() {
        override val type = if (fips == null) "state:*" else "state:$fips"
        override val target: String? = null
    }

    data class County(val stateFips: String, val countyFips: String? = null) : Geography() {
        override val type = if (countyFips == null) "county:*" else "county:$countyFips"
        override val target = "state:$stateFips"
    }

    data class Tract(val stateFips: String, val countyFips: String, val tract: String? = null) : Geography() {
        override val type = if (tract == null) "tract:*" else "tract:$tract"
        override val target = "state:$stateFips+county:$countyFips"
    }

    data class BlockGroup(
        val stateFips: String,
        val countyFips: String,
        val tract: String,
        val blockGroup: String? = null
    ) : Geography() {
        override val type = if (blockGroup == null) "block group:*" else "block group:$blockGroup"
        override val target = "state:$stateFips+county:$countyFips+tract:$tract"
    }

    // You can keep adding Place, MetroArea, SchoolDistrict, etc.
}
