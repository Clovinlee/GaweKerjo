package com.example.gawekerjo.model.country

data class Country(
    var id: Int? = null,
    var name: Name? = null,
    var unMember: Boolean? = null,
    var capital: ArrayList<String>? = null,
    var region: String? = null,
    var subregion: String? = null,
    var languages: HashMap<String, String> = HashMap(),
    var currencies: HashMap<String, CurrencyDetail> = HashMap(),
    var population: Long? = null,
    var timezones: ArrayList<String> = ArrayList(),
    var continents: ArrayList<String> = ArrayList(),
    var flags: Flags? = null,
    var startOfWeek: String? = null,
) {
}