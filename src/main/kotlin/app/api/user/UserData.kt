package app.api.user

import app.types.*

data class UserData(
    var userId: UserId = "",
    var password: Password = "",
)