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

import com.connexta.transformation.commons.api.exceptions.TransformationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.time.OffsetDateTime;

/**
 * The {@link Data} interface is used to describe intelligence information. It can either be a file
 * or metadata. Related data are typically contained in a data set and only one data of each type
 * can exist in a given dataset.
 */
public interface Data {
  /**
   * Gets a string representation for the actual type of data represented by this class. The format
   * and possible values for this string will be defined and agreed upon at the Ion level.
   *
   * @return the type of data this is
   */
  public String getDataType();

  /**
   * Gets a string representation for the content type of the data represented by this class.
   *
   * @return the content type for this data
   */
  public String getContentType();

  /**
   * Gets the length for the data content represented by this class.
   *
   * @return the content length in bytes
   */
  public long getContentLength();

  /**
   * Gets the time that this data was last modified.
   *
   * @return the time this data was last modified
   */
  public OffsetDateTime getLastModified();

  /**
   * Retrieves the content for this data as a binary stream.
   *
   * @return an input stream with the content for this data
   * @throws TransformationException if an error occurs while retrieving the content
   * @throws IOException if an I/O error occurred while retrieving the content
   */
  public InputStream getContent() throws TransformationException, IOException;

  /**
   * Retrieves the content for this data as a character stream.
   *
   * @param charset the character set to use for retrieving the content
   * @return a reader with the content for this data
   * @throws TransformationException if an error occurs while retrieving the content
   * @throws IOException if an I/O error occurred while retrieving the content
   */
  public default Reader getContent(Charset charset) throws TransformationException, IOException {
    return new InputStreamReader(getContent(), charset);
  }
}
