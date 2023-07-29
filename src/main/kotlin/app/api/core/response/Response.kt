package app.api.core.response

import app.types.Page
import app.types.PageSize
import app.types.Total

data class ResponseStatus(val code: String, val message: String? = "메세지")

data class Response<T>(
	val data: T?, 
	val status: ResponseStatus = ResponseStatus(code = "0000", message = null),
)

data class ResponsePagination<T>(
	val page: Page, 
	val pageSize: PageSize, 
	val total: Total, 
	val data: T, 
	val status: ResponseStatus = ResponseStatus(code = "0000", message = null),
)
