/*
 * Copyright 2022 The Blaze Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.spark.sql.blaze.execution.shuffle

import scala.reflect.ClassTag

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.StructType
import org.apache.spark.Aggregator
import org.apache.spark.Partitioner
import org.apache.spark.ShuffleDependency
import org.apache.spark.SparkEnv
import org.apache.spark.serializer.Serializer
import org.apache.spark.shuffle.ShuffleWriteProcessor

class ShuffleDependencySchema[K: ClassTag, V: ClassTag, C: ClassTag](
    @transient private val _rdd: RDD[_ <: Product2[K, V]],
    override val partitioner: Partitioner,
    override val serializer: Serializer = SparkEnv.get.serializer,
    override val keyOrdering: Option[Ordering[K]] = None,
    override val aggregator: Option[Aggregator[K, V, C]] = None,
    override val mapSideCombine: Boolean = false,
    override val shuffleWriterProcessor: ShuffleWriteProcessor = new ShuffleWriteProcessor,
    val schema: StructType)
    extends ShuffleDependency[K, V, C](
      _rdd,
      partitioner,
      serializer,
      keyOrdering,
      aggregator,
      mapSideCombine,
      shuffleWriterProcessor) {}
