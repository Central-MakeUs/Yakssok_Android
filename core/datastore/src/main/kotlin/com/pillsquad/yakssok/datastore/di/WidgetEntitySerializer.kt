package com.pillsquad.yakssok.datastore.di

import androidx.datastore.core.Serializer
import com.pillsquad.yakssok.datastore.model.WidgetEntity
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object WidgetEntitySerializer : Serializer<WidgetEntity> {
    override val defaultValue: WidgetEntity
        get() = WidgetEntity()

    override suspend fun readFrom(input: InputStream): WidgetEntity {
        return runCatching { Json.decodeFromString<WidgetEntity>(input.readBytes().decodeToString()) }
            .getOrDefault(defaultValue)
    }

    override suspend fun writeTo(
        t: WidgetEntity,
        output: OutputStream
    ) {
        output.write(Json.encodeToString(t).encodeToByteArray())
    }
}