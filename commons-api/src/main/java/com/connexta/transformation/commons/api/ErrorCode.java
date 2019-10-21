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

/** Error codes indicating failures that can occur in the transformation process. */
public enum ErrorCode {
  // TODO: more specific codes should be added as we go
  TRANSFORMATION_FAILURE,

  /**
   * The unknown value is used for forward compatibility where the current code might not be able to
   * understand a new error code that it read and would map this new error code to <code>UNKNOWN
   * </code>. Then it would most likely ignore it.
   */
  UNKNOWN
}
