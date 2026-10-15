package be.woutcrevits.androidapp.models

data class User(
    var id: Int,
    var userName: String,
    var firstName: String,
    var lastName: String,
    var password: String,
    var isActive: Boolean
) {
    constructor() : this(0, "", "", "", "", false)
}
