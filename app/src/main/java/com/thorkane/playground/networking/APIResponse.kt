package com.thorkane.playground.networking

/**
 * Wraps API Responses from Retrofit with more state friendly sealed classes.
 */
sealed class APIResponse<T>(val data: T? = null, val message: String? = null) {
    /**
     * Represents a successful request.
     * @param data the data from the retrofit response.
     */
    class Success<T>(data: T) : APIResponse<T>(data)

    /**
     * Represents a request Error state.
     * @param data will always be null here since the request failed.
     * @param message consumer will have the opportunity to pass along a friendly error message via
     * this parameter.
     */
    class Error<T>(
        data: T? = null,
        message: String? = null
    ) : APIResponse<T>(data, message)
}