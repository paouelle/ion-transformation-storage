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
package com.connexta.transformation.commons.api.impl;

import com.connexta.transformation.commons.api.exceptions.InvalidFieldException;
import com.connexta.transformation.commons.api.exceptions.UnsupportedVersionException;
import com.connexta.transformation.pojo.RequestInfoPojo;
import com.connexta.transformation.pojo.unknown.UnknownRequestInfoPojo;
import java.net.MalformedURLException;
import java.net.URL;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class DatasetImplTest {
  private static final String ID = "1234";
  private static final URL CURRENT_LOCATION;
  private static final URL FINAL_LOCATION;
  private static final URL METACARD_LOCATION;
  private static final URL LOCATION;

  static {
    try {
      CURRENT_LOCATION = new URL("https://localhost/current");
      FINAL_LOCATION = new URL("https://localhost/final");
      METACARD_LOCATION = new URL("https://localhost/metacard");
      LOCATION = new URL("https://localhost/other");
    } catch (MalformedURLException e) {
      throw new AssertionError(e);
    }
  }

  private static final DatasetImpl PERSISTABLE =
      new DatasetImpl(
          DatasetImplTest.CURRENT_LOCATION,
          DatasetImplTest.FINAL_LOCATION,
          DatasetImplTest.METACARD_LOCATION);

  static {
    DatasetImplTest.PERSISTABLE.setId(DatasetImplTest.ID);
  }

  @Rule public ExpectedException exception = ExpectedException.none();

  private final DatasetImpl persistable2 =
      new DatasetImpl(
          DatasetImplTest.CURRENT_LOCATION,
          DatasetImplTest.FINAL_LOCATION,
          DatasetImplTest.METACARD_LOCATION);

  private final RequestInfoPojo pojo =
      new RequestInfoPojo()
          .setVersion(RequestInfoPojo.CURRENT_VERSION)
          .setId(DatasetImplTest.ID)
          .setMetacardLocation(DatasetImplTest.METACARD_LOCATION)
          .setCurrentLocation((DatasetImplTest.CURRENT_LOCATION))
          .setFinalLocation(DatasetImplTest.FINAL_LOCATION);

  public DatasetImplTest() {
    persistable2.setId(DatasetImplTest.ID);
  }

  @Test
  public void testConstructor() throws Exception {
    Assert.assertThat(
        DatasetImplTest.PERSISTABLE.getCurrentLocation(),
        Matchers.equalTo(DatasetImplTest.CURRENT_LOCATION));
    Assert.assertThat(
        DatasetImplTest.PERSISTABLE.getFinalLocation(),
        Matchers.equalTo(DatasetImplTest.FINAL_LOCATION));
    Assert.assertThat(
        DatasetImplTest.PERSISTABLE.getMetacardLocation(),
        Matchers.equalTo(DatasetImplTest.METACARD_LOCATION));
  }

  @Test
  public void testCopyConstructor() throws Exception {
    final DatasetImpl request = new DatasetImpl(DatasetImplTest.PERSISTABLE);

    Assert.assertThat(
        request.getCurrentLocation(), Matchers.equalTo(DatasetImplTest.CURRENT_LOCATION));
    Assert.assertThat(request.getFinalLocation(), Matchers.equalTo(DatasetImplTest.FINAL_LOCATION));
    Assert.assertThat(
        request.getMetacardLocation(), Matchers.equalTo(DatasetImplTest.METACARD_LOCATION));
  }

  @Test
  public void testWriteTo() throws Exception {
    final RequestInfoPojo pojo = new RequestInfoPojo();

    DatasetImplTest.PERSISTABLE.writeTo(pojo);

    Assert.assertThat(pojo.getId(), Matchers.equalTo(DatasetImplTest.ID));
    Assert.assertThat(pojo.getVersion(), Matchers.equalTo(RequestInfoPojo.CURRENT_VERSION));
    Assert.assertThat(
        pojo.getMetacardLocation(), Matchers.equalTo(DatasetImplTest.METACARD_LOCATION.toString()));
    Assert.assertThat(
        pojo.getCurrentLocation(), Matchers.equalTo(DatasetImplTest.CURRENT_LOCATION.toString()));
    Assert.assertThat(
        pojo.getFinalLocation(), Matchers.equalTo(DatasetImplTest.FINAL_LOCATION.toString()));
  }

  @Test
  public void testWriteToWhenMetacardLocationIsNull() throws Exception {
    exception.expect(InvalidFieldException.class);
    exception.expectMessage(Matchers.matchesPattern(".*missing.*metacardLocation.*"));

    final RequestInfoPojo pojo = new RequestInfoPojo();

    persistable2.setMetacardLocation(null);

    persistable2.writeTo(pojo);
  }

  @Test
  public void testWriteToWhenCurrentLocationIsNull() throws Exception {
    exception.expect(InvalidFieldException.class);
    exception.expectMessage(Matchers.matchesPattern(".*missing.*currentLocation.*"));

    final RequestInfoPojo pojo = new RequestInfoPojo();

    persistable2.setCurrentLocation(null);

    persistable2.writeTo(pojo);
  }

  @Test
  public void testWriteToWhenFinalLocationIsNull() throws Exception {
    exception.expect(InvalidFieldException.class);
    exception.expectMessage(Matchers.matchesPattern(".*missing.*finalLocation.*"));

    final RequestInfoPojo pojo = new RequestInfoPojo();

    persistable2.setFinalLocation(null);

    persistable2.writeTo(pojo);
  }

  @Test
  public void testWriteToWhenHasUnknowns() throws Exception {
    exception.expect(InvalidFieldException.class);
    exception.expectMessage(Matchers.equalTo("unknown request info"));

    final RequestInfoPojo unknownPojo =
        new UnknownRequestInfoPojo()
            .setVersion(RequestInfoPojo.CURRENT_VERSION)
            .setId(DatasetImplTest.ID)
            .setMetacardLocation(DatasetImplTest.METACARD_LOCATION)
            .setCurrentLocation((DatasetImplTest.CURRENT_LOCATION))
            .setFinalLocation(DatasetImplTest.FINAL_LOCATION);
    final DatasetImpl persistable3 = new DatasetImpl();

    persistable3.readFrom(unknownPojo);

    final RequestInfoPojo pojo = new RequestInfoPojo();

    persistable3.writeTo(pojo);
  }

  @Test
  public void testReadFromCurrentVersion() throws Exception {
    final DatasetImpl persistable = new DatasetImpl();

    persistable.readFrom(pojo);

    Assert.assertThat(persistable.getId(), Matchers.equalTo(pojo.getId()));
    Assert.assertThat(
        persistable.getMetacardLocation().toString(), Matchers.equalTo(pojo.getMetacardLocation()));
    Assert.assertThat(
        persistable.getCurrentLocation().toString(), Matchers.equalTo(pojo.getCurrentLocation()));
    Assert.assertThat(
        persistable.getFinalLocation().toString(), Matchers.equalTo(pojo.getFinalLocation()));
    Assert.assertThat(persistable.hasUnknowns(), Matchers.equalTo(false));
  }

  @Test
  public void testReadFromFutureVersion() throws Exception {
    pojo.setVersion(9999999);
    final DatasetImpl persistable = new DatasetImpl();

    persistable.readFrom(pojo);

    Assert.assertThat(persistable.getId(), Matchers.equalTo(pojo.getId()));
    Assert.assertThat(
        persistable.getMetacardLocation().toString(), Matchers.equalTo(pojo.getMetacardLocation()));
    Assert.assertThat(
        persistable.getCurrentLocation().toString(), Matchers.equalTo(pojo.getCurrentLocation()));
    Assert.assertThat(
        persistable.getFinalLocation().toString(), Matchers.equalTo(pojo.getFinalLocation()));
  }

  @Test
  public void testReadFromUnsupportedVersion() throws Exception {
    exception.expect(UnsupportedVersionException.class);
    exception.expectMessage(Matchers.matchesPattern(".*unsupported.*version.*"));

    pojo.setVersion(-1);
    final DatasetImpl persistable = new DatasetImpl();

    persistable.readFrom(pojo);
  }

  @Test
  public void testReadFromCurrentVersionWithNullMetacardLocation() throws Exception {
    exception.expect(InvalidFieldException.class);
    exception.expectMessage(Matchers.matchesPattern(".*missing.*metacardLocation.*"));

    pojo.setMetacardLocation((String) null);
    final DatasetImpl persistable = new DatasetImpl();

    persistable.readFrom(pojo);
  }

  @Test
  public void testReadFromCurrentVersionWithEmptyMetacardLocation() throws Exception {
    exception.expect(InvalidFieldException.class);
    exception.expectMessage(Matchers.matchesPattern(".*empty.*metacardLocation.*"));

    pojo.setMetacardLocation("");
    final DatasetImpl persistable = new DatasetImpl();

    persistable.readFrom(pojo);
  }

  @Test
  public void testReadFromCurrentVersionWithNullCurrentLocation() throws Exception {
    exception.expect(InvalidFieldException.class);
    exception.expectMessage(Matchers.matchesPattern(".*missing.*currentLocation.*"));

    pojo.setCurrentLocation((String) null);
    final DatasetImpl persistable = new DatasetImpl();

    persistable.readFrom(pojo);
  }

  @Test
  public void testReadFromCurrentVersionWithEmptyCurrentLocation() throws Exception {
    exception.expect(InvalidFieldException.class);
    exception.expectMessage(Matchers.matchesPattern(".*empty.*currentLocation.*"));

    pojo.setCurrentLocation("");
    final DatasetImpl persistable = new DatasetImpl();

    persistable.readFrom(pojo);
  }

  @Test
  public void testReadFromCurrentVersionWithNullFinalLocation() throws Exception {
    exception.expect(InvalidFieldException.class);
    exception.expectMessage(Matchers.matchesPattern(".*missing.*finalLocation.*"));

    pojo.setFinalLocation((String) null);
    final DatasetImpl persistable = new DatasetImpl();

    persistable.readFrom(pojo);
  }

  @Test
  public void testReadFromCurrentVersionWithEmptyFinalLocation() throws Exception {
    exception.expect(InvalidFieldException.class);
    exception.expectMessage(Matchers.matchesPattern(".*empty.*finalLocation.*"));

    pojo.setFinalLocation("");
    final DatasetImpl persistable = new DatasetImpl();

    persistable.readFrom(pojo);
  }

  @Test
  public void testReadFromUnknownPojo() throws Exception {
    final RequestInfoPojo unknownPojo =
        new UnknownRequestInfoPojo()
            .setVersion(RequestInfoPojo.CURRENT_VERSION)
            .setId(DatasetImplTest.ID)
            .setMetacardLocation(DatasetImplTest.METACARD_LOCATION)
            .setCurrentLocation((DatasetImplTest.CURRENT_LOCATION))
            .setFinalLocation(DatasetImplTest.FINAL_LOCATION);

    final DatasetImpl persistable = new DatasetImpl();

    persistable.readFrom(unknownPojo);

    Assert.assertThat(persistable.getId(), Matchers.equalTo(unknownPojo.getId()));
    Assert.assertThat(
        persistable.getMetacardLocation().toString(),
        Matchers.equalTo(unknownPojo.getMetacardLocation()));
    Assert.assertThat(
        persistable.getCurrentLocation().toString(),
        Matchers.equalTo(unknownPojo.getCurrentLocation()));
    Assert.assertThat(
        persistable.getFinalLocation().toString(),
        Matchers.equalTo(unknownPojo.getFinalLocation()));
    Assert.assertThat(persistable.hasUnknowns(), Matchers.equalTo(true));
  }

  @Test
  public void testHashCodeWhenEquals() throws Exception {
    Assert.assertThat(
        DatasetImplTest.PERSISTABLE.hashCode(), Matchers.equalTo(persistable2.hashCode()));
  }

  @Test
  public void testHashCodeWhenDifferent() throws Exception {
    persistable2.setCurrentLocation(DatasetImplTest.LOCATION);

    Assert.assertThat(
        DatasetImplTest.PERSISTABLE.hashCode(),
        Matchers.not(Matchers.equalTo(persistable2.hashCode())));
  }

  @Test
  public void testEqualsWhenEquals() throws Exception {
    Assert.assertThat(DatasetImplTest.PERSISTABLE.equals(persistable2), Matchers.equalTo(true));
  }

  @Test
  public void testEqualsWhenIdentical() throws Exception {
    Assert.assertThat(
        DatasetImplTest.PERSISTABLE.equals(DatasetImplTest.PERSISTABLE), Matchers.equalTo(true));
  }

  @SuppressWarnings("PMD.EqualsNull" /* purposely testing equals() when called with null */)
  @Test
  public void testEqualsWhenNull() throws Exception {
    Assert.assertThat(DatasetImplTest.PERSISTABLE.equals(null), Matchers.equalTo(false));
  }

  @SuppressWarnings(
      "PMD.PositionLiteralsFirstInComparisons" /* purposely testing equals() when call with something else than expected */)
  @Test
  public void testEqualsWhenNotTheSameClass() throws Exception {
    Assert.assertThat(DatasetImplTest.PERSISTABLE.equals("test"), Matchers.equalTo(false));
  }

  @Test
  public void testEqualsWhenIdIsDifferent() throws Exception {
    persistable2.setId(DatasetImplTest.ID + "2");

    Assert.assertThat(
        DatasetImplTest.PERSISTABLE.equals(persistable2), Matchers.not(Matchers.equalTo(true)));
  }

  @Test
  public void testEqualsWhenCurrentLocationIsDifferent() throws Exception {
    persistable2.setCurrentLocation(DatasetImplTest.LOCATION);

    Assert.assertThat(
        DatasetImplTest.PERSISTABLE.equals(persistable2), Matchers.not(Matchers.equalTo(true)));
  }

  @Test
  public void testEqualsWhenFinalLocationIsDifferent() throws Exception {
    persistable2.setFinalLocation(DatasetImplTest.LOCATION);

    Assert.assertThat(
        DatasetImplTest.PERSISTABLE.equals(persistable2), Matchers.not(Matchers.equalTo(true)));
  }

  @Test
  public void testEqualsWhenMetacardLocationIsDifferent() throws Exception {
    persistable2.setMetacardLocation(DatasetImplTest.LOCATION);

    Assert.assertThat(
        DatasetImplTest.PERSISTABLE.equals(persistable2), Matchers.not(Matchers.equalTo(true)));
  }

  @Test
  public void testWrapWhenInfoNotARequestInfoImpl() throws Exception {
    final RequestInfo info = Mockito.mock(RequestInfo.class);

    Mockito.when(info.getMetacardLocation()).thenReturn(DatasetImplTest.METACARD_LOCATION);
    Mockito.when(info.getCurrentLocation()).thenReturn(DatasetImplTest.CURRENT_LOCATION);
    Mockito.when(info.getFinalLocation()).thenReturn(DatasetImplTest.FINAL_LOCATION);

    final DatasetImpl request = DatasetImpl.wrap(info);

    Assert.assertThat(request, Matchers.not(Matchers.sameInstance(info)));
    Assert.assertThat(
        request.getMetacardLocation(), Matchers.equalTo(DatasetImplTest.METACARD_LOCATION));
    Assert.assertThat(
        request.getCurrentLocation(), Matchers.equalTo(DatasetImplTest.CURRENT_LOCATION));
    Assert.assertThat(request.getFinalLocation(), Matchers.equalTo(DatasetImplTest.FINAL_LOCATION));
  }

  @Test
  public void testWrapWhenInfoIsARequestInfoImpl() throws Exception {
    final DatasetImpl request = DatasetImpl.wrap(DatasetImplTest.PERSISTABLE);

    Assert.assertThat(request, Matchers.sameInstance(DatasetImplTest.PERSISTABLE));
  }

  @Test
  public void testToPojo() throws Exception {
    final RequestInfoPojo pojo = DatasetImpl.toPojo(DatasetImplTest.PERSISTABLE);

    Assert.assertThat(pojo.getId(), Matchers.equalTo(DatasetImplTest.ID));
    Assert.assertThat(pojo.getVersion(), Matchers.equalTo(RequestInfoPojo.CURRENT_VERSION));
    Assert.assertThat(
        pojo.getMetacardLocation(), Matchers.equalTo(DatasetImplTest.METACARD_LOCATION.toString()));
    Assert.assertThat(
        pojo.getCurrentLocation(), Matchers.equalTo(DatasetImplTest.CURRENT_LOCATION.toString()));
    Assert.assertThat(
        pojo.getFinalLocation(), Matchers.equalTo(DatasetImplTest.FINAL_LOCATION.toString()));
  }
}
