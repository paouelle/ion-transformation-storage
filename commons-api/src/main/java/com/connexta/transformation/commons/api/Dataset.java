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
package com.connexta.transformation.commons.api;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * The {@link Dataset} interface is used to describe a set of intelligence data that has a logical
 * relationship. Each intelligence piece of data is represented using the {@link Data} interface and
 * only one data of each type can exist in a given dataset.
 *
 * @param <D> the actual class of data being managed by this dataset
 */
public interface Dataset<D extends Data> {
  /**
   * Gets the time that the intelligence represented by this dataset was last modified.
   *
   * <p><i>Note:</i> The regeneration of specific metadata would not change the time reported here
   * as the actual intelligence represented by the datset would till be the same.
   *
   * @return the time this dataset was last modified
   */
  public OffsetDateTime getLastModified();

  /**
   * Gets a data given its type contained in this dataset.
   *
   * @param dataType the data type for which to get data from this dataset
   * @return the corresponding data or empty if none exist
   */
  public Optional<? extends D> getData(String dataType);

  /**
   * Gets all datas contained in this dataset.
   *
   * @return a stream of all datas contained in this dataset
   */
  public Stream<? extends D> datas();

  /**
   * Gets all available type of datas contained in this dataset.
   *
   * @return a stream of all types of datas contained in this dataset
   */
  public default Stream<String> dataTypes() {
    return datas().map(Data::getDataType);
  }
}
