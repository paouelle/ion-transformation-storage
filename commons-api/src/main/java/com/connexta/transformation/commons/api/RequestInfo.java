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

import java.net.URI;

/** A data structure to hold the information of an incoming transformation request. */
public interface RequestInfo {

  /**
   * Returns a URI which can be used to download the metadata that is to be transformed.
   *
   * @return a URI for retrieving the metadata to be transformed
   */
  URI getMetadataLocation();

  /**
   * Returns a URI which can be used to download the resource that will be used for the
   * transformation.
   *
   * @return a URI for retrieving the resource for a transformation
   */
  URI getCurrentLocation();

  /**
   * Returns a URI representing the final resting place of a resource for retrieval. This is the URI
   * that will be added to metadata wherever a resource URI is needed. This URI will NOT be used to
   * retrieve information during the transformation process.
   *
   * @return a URI representing where the resource will be saved once returned from the
   *     transformation service
   */
  URI getFinalLocation();
}
