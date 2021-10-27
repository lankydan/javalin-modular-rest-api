package dev.lankydan.web

import io.javalin.apibuilder.EndpointGroup

interface Controller {

    /**
     * The base path of the endpoints within the controller.
     */
    val path: String

    /**
     * Use [io.javalin.apibuilder.ApiBuilder] functions within this method to register endpoints.
     */
    val endpoints: EndpointGroup
}