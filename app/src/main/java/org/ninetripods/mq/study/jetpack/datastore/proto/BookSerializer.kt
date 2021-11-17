package org.ninetripods.mq.study.jetpack.datastore.proto

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import org.ninetripods.mq.study.BookProto
import java.io.InputStream
import java.io.OutputStream

object BookSerializer : Serializer<BookProto.Book> {
    override val defaultValue: BookProto.Book = BookProto.Book.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): BookProto.Book {
        try {
            return BookProto.Book.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: BookProto.Book, output: OutputStream) {
        t.writeTo(output)
    }
}