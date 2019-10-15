/**
 * Copyright (c) Connexta
 *
 * <p>This is free software: you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or any later version.
 *
 * <p>This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details. A copy of the GNU Lesser General Public
 * License is distributed along with this program and can be found at
 * <http://www.gnu.org/licenses/lgpl.html>.
 */
package com.connexta.transformation.commons.api.status;

import com.connexta.transformation.commons.api.exceptions.TransformationException;
import java.util.stream.Stream;

/**
 * A Transformation handles information about the entire process associated with a single transform
 * request, i.e. info about an entire transformation.
 */
public interface Transformation extends TransformationStatus {

  /**
   * Adds the creation of a new metadata type as part of this transformation.
   *
   * @param metadataType the type of metadata to be generated (e.g. metacard, irm...)
   * @return a {@link MetadataTransformation} for the metadata to be generated
   * @throws TransformationException if an error occurs while executing this method
   * @throws IllegalArgumentException if this transformation has already been completed
   */
  MetadataTransformation add(String metadataType) throws TransformationException;

  /**
   * Returns a stream of the types of metadata that were/will be created as part of this
   * transformation.
   *
   * @return a stream of metadata types in the form of strings.
   */
  default Stream<String> metadataTypes() {
    return metadatas().map(MetadataTransformation::getMetadataType);
  }

  /**
   * Returns a stream of {@link MetadataTransformation}s, one for each type of metadata that
   * was/will be created as part of this transformation.
   *
   * @return A stream of {@link MetadataTransformation}s describing each metadata created for this
   *     transformation.
   */
  Stream<MetadataTransformation> metadatas();

  @Override
  default State getState() {
    return metadatas()
        .map(TransformationStatus::getState)
        .reduce(State::reduce)
        .orElse(State.IN_PROGRESS);
  }

  @Override
  default boolean isCompleted() {
    return metadatas().allMatch(MetadataTransformation::isCompleted);
  }

  @Override
  default boolean hasFailed() {
    return isCompleted() && metadatas().anyMatch(MetadataTransformation::hasFailed);
  }

  @Override
  default boolean wasSuccessful() {
    return metadatas().allMatch(MetadataTransformation::wasSuccessful);
  }

  @Override
  default boolean isUnknown() {
    return isCompleted() && metadatas().anyMatch(MetadataTransformation::isUnknown);
  }
}
