package br.com.andersoncfsilva.androidThingsRestful.services.android

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log

import org.restlet.Component
import org.restlet.data.Protocol
import org.restlet.engine.Engine
import org.restlet.ext.nio.HttpServerHelper
import org.restlet.routing.Router

import br.com.andersoncfsilva.androidThingsRestful.services.rest.LedService

/**
 * Created by Anderson Silva on 25/08/17.
 */

class RestService : IntentService("RestService") {

    private val mComponent: Component

    init {
        Engine.getInstance().registeredServers.clear()
        Engine.getInstance().registeredServers.add(HttpServerHelper(null))
        mComponent = Component()
        mComponent.servers.add(Protocol.HTTP, 8080)
        val router = Router(mComponent.context.createChildContext())
        router.attach("/led", LedService::class.java)
        mComponent.defaultHost.attach("/rest", router)
    }


    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            val action = intent.action
            if (ACTION_START == action) {
                handleStart()
            } else if (ACTION_STOP == action) {
                handleStop()
            }
        }
    }

    private fun handleStart() {
        try {
            mComponent.start()
        } catch (e: Exception) {
            Log.e(javaClass.simpleName, e.toString())
        }

    }

    private fun handleStop() {
        try {
            mComponent.stop()
        } catch (e: Exception) {
            Log.e(javaClass.simpleName, e.toString())
        }

    }

    companion object {
        // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
        private val ACTION_START = "br.com.andersoncfsilva.androidThingsRestful.action.START"
        private val ACTION_STOP = "br.com.andersoncfsilva.androidThingsRestful.action.STOP"

        fun startServer(context: Context) {
            val intent = Intent(context, RestService::class.java)
            intent.action = ACTION_START
            context.startService(intent)
        }

        fun stopServer(context: Context) {
            val intent = Intent(context, RestService::class.java)
            intent.action = ACTION_STOP
            context.startService(intent)
        }
    }
}