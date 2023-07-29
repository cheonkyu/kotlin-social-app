package app.api.post

import app.types.*

data class PostData(
    var title: Title = "",
    var content: Content = "",
)