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
import java.io.StringReader;
import java.nio.charset.Charset;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.ReaderInputStream;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class DataTest {
  private static final String CONTENT = "some random content I put together here";
  private static final Charset CHARSET = Charset.defaultCharset();

  @Rule public ExpectedException exception = ExpectedException.none();

  private final Data data =
      Mockito.mock(
          TestData.class, Mockito.withSettings().defaultAnswer(Mockito.CALLS_REAL_METHODS));

  @Test
  public void testGetContentAsReader() throws Exception {
    Mockito.when(data.getContent())
        .thenReturn(new ReaderInputStream(new StringReader(DataTest.CONTENT), DataTest.CHARSET));

    Assert.assertThat(
        IOUtils.toString(data.getContent(DataTest.CHARSET)), Matchers.equalTo(DataTest.CONTENT));
  }

  @Test
  public void testGetContentAsReaderPropagatesIOException() throws Exception {
    final IOException error = new IOException();

    exception.expect(Matchers.sameInstance(error));

    Mockito.when(data.getContent()).thenThrow(error);

    data.getContent(DataTest.CHARSET);
  }

  @Test
  public void testGetContentAsReaderPropagatesTransformationException() throws Exception {
    final TransformationException error = new TransformationException("testing");

    exception.expect(Matchers.sameInstance(error));

    Mockito.when(data.getContent()).thenThrow(error);

    data.getContent(DataTest.CHARSET);
  }

  @Test
  public void testGetContentAsReaderPropagatesRuntimeException() throws Exception {
    final RuntimeException error = new RuntimeException();

    exception.expect(Matchers.sameInstance(error));

    Mockito.when(data.getContent()).thenThrow(error);

    data.getContent(DataTest.CHARSET);
  }

  private abstract static class TestData implements Data {}
}
