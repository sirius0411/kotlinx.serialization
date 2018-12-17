/*
 * Copyright 2018 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kotlinx.serialization.schema

import kotlinx.serialization.*

/**
 * Base interface for a visitor of a [SerialDescriptor] tree,
 * which can return an tree processing result.
 *
 * Tree is formed from descriptors using [SerialDescriptor.getElementDescriptor] method.
 */
interface DescriptorVisitor<R> {
    fun visitDescriptor(descriptor: SerialDescriptor): R
}

/**
 * Visits [this] with a given [visitor].
 *
 * @see DescriptorVisitor
 * @see SerialDescriptor.getElementDescriptor
 */
fun <R> SerialDescriptor.accept(visitor: DescriptorVisitor<R>): R = visitor.visitDescriptor(this)

/**
 * Visits [this], making a [DescriptorVisitor] out of [visitor].
 *
 * @see DescriptorVisitor
 * @see SerialDescriptor.getElementDescriptor
 */
fun <R> SerialDescriptor.accept(visitor: (SerialDescriptor) -> R): R {
    val visitor = object : DescriptorVisitor<R> {
        override fun visitDescriptor(descriptor: SerialDescriptor): R = visitor(descriptor)
    }
    return visitor.visitDescriptor(this)
}
