package org.ninetripods.mq.study.jetpack.datastore.proto

import androidx.datastore.core.Serializer
import org.ninetripods.mq.study.BookProto
import java.io.InputStream
import java.io.OutputStream

class BookSerializer : Serializer<BookProto.Book> {

    override suspend fun readFrom(input: InputStream): BookProto.Book {
        return BookProto.Book.parseFrom(input)
    }

    override suspend fun writeTo(t: BookProto.Book, output: OutputStream) {
        t.writeTo(output)
    }

    override val defaultValue: BookProto.Book
        get() = BookProto.Book.getDefaultInstance()

}