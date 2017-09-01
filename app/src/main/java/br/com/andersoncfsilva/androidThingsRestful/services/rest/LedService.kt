package br.com.andersoncfsilva.androidThingsRestful.services.rest

/**
 * Created by Anderson Silva on 25/08/17.
 */

import android.util.Log

import org.json.JSONObject
import org.restlet.data.MediaType
import org.restlet.ext.json.JsonRepresentation
import org.restlet.representation.Representation
import org.restlet.representation.StringRepresentation
import org.restlet.resource.Get
import org.restlet.resource.Post
import org.restlet.resource.ServerResource

import br.com.andersoncfsilva.androidThingsRestful.models.Led

class LedService : ServerResource() {

    @Get("json")
    fun getState(): Representation {
        val result = JSONObject()
        try {
            result.put("state", Led.getState())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return StringRepresentation(result.toString(), MediaType.APPLICATION_ALL_JSON)
    }

    @Post("json")
    fun postState(entity: Representation): Representation {
        var result = JSONObject()
        try {
            val json = JsonRepresentation(entity)
            result = json.jsonObject
            val state = result.get("state") as Boolean
            Log.d(this.javaClass.simpleName, "new LED state: " + state)
            Led.setState(state)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return StringRepresentation(result.toString(), MediaType.APPLICATION_ALL_JSON)
    }
}
