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
package com.connexta.transformation.pojo;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class DatasetPojoTest {
  private static final int VERSION = 1;
  private static final String ID = "1234";
  private static final String METACARD_LOCATION = "http://localhost/metacard";
  private static final String CURRENT_LOCATION = "http://localhost/current";
  private static final String FINAL_LOCATION = "http://localhost/final";

  private static final RequestInfoPojo POJO =
      new RequestInfoPojo()
          .setVersion(DatasetPojoTest.VERSION)
          .setId(DatasetPojoTest.ID)
          .setMetacardLocation(DatasetPojoTest.METACARD_LOCATION)
          .setCurrentLocation(DatasetPojoTest.CURRENT_LOCATION)
          .setFinalLocation(DatasetPojoTest.FINAL_LOCATION);

  private final RequestInfoPojo pojo2 =
      new RequestInfoPojo()
          .setVersion(DatasetPojoTest.VERSION)
          .setId(DatasetPojoTest.ID)
          .setMetacardLocation(DatasetPojoTest.METACARD_LOCATION)
          .setCurrentLocation(DatasetPojoTest.CURRENT_LOCATION)
          .setFinalLocation(DatasetPojoTest.FINAL_LOCATION);

  @Test
  public void testSetAndGetId() throws Exception {
    final RequestInfoPojo pojo = new RequestInfoPojo().setId(DatasetPojoTest.ID);

    Assert.assertThat(pojo.getId(), Matchers.equalTo(DatasetPojoTest.ID));
  }

  @Test
  public void testSetAndGetVersion() throws Exception {
    final RequestInfoPojo pojo = new RequestInfoPojo().setVersion(DatasetPojoTest.VERSION);

    Assert.assertThat(pojo.getVersion(), Matchers.equalTo(DatasetPojoTest.VERSION));
  }

  @Test
  public void testSetAndGetMetacardLocation() throws Exception {
    final RequestInfoPojo pojo =
        new RequestInfoPojo().setMetacardLocation(DatasetPojoTest.METACARD_LOCATION);

    Assert.assertThat(
        pojo.getMetacardLocation(), Matchers.equalTo(DatasetPojoTest.METACARD_LOCATION));
  }

  @Test
  public void testSetAndGetCurrentLocation() throws Exception {
    final RequestInfoPojo pojo =
        new RequestInfoPojo().setCurrentLocation(DatasetPojoTest.CURRENT_LOCATION);

    Assert.assertThat(
        pojo.getCurrentLocation(), Matchers.equalTo(DatasetPojoTest.CURRENT_LOCATION));
  }

  @Test
  public void testSetAndGetFinalLocation() throws Exception {
    final RequestInfoPojo pojo =
        new RequestInfoPojo().setFinalLocation(DatasetPojoTest.FINAL_LOCATION);

    Assert.assertThat(pojo.getFinalLocation(), Matchers.equalTo(DatasetPojoTest.FINAL_LOCATION));
  }

  @Test
  public void testHashCodeWhenEquals() throws Exception {
    Assert.assertThat(DatasetPojoTest.POJO.hashCode(), Matchers.equalTo(pojo2.hashCode()));
  }

  @Test
  public void testHashCodeWhenDifferent() throws Exception {
    pojo2.setId(DatasetPojoTest.ID + "2");

    Assert.assertThat(
        DatasetPojoTest.POJO.hashCode(), Matchers.not(Matchers.equalTo(pojo2.hashCode())));
  }

  @Test
  public void testEqualsWhenEquals() throws Exception {
    Assert.assertThat(DatasetPojoTest.POJO.equals(pojo2), Matchers.equalTo(true));
  }

  @Test
  public void testEqualsWhenIdentical() throws Exception {
    Assert.assertThat(DatasetPojoTest.POJO.equals(DatasetPojoTest.POJO), Matchers.equalTo(true));
  }

  @SuppressWarnings("PMD.EqualsNull" /* purposely testing equals() when called with null */)
  @Test
  public void testEqualsWhenNull() throws Exception {
    Assert.assertThat(DatasetPojoTest.POJO.equals(null), Matchers.equalTo(false));
  }

  @SuppressWarnings(
      "PMD.PositionLiteralsFirstInComparisons" /* purposely testing equals() when call with something else than expected */)
  @Test
  public void testEqualsWhenNotARequestInfoPojo() throws Exception {
    Assert.assertThat(DatasetPojoTest.POJO.equals("test"), Matchers.equalTo(false));
  }

  @Test
  public void testEqualsWhenIdIsDifferent() throws Exception {
    pojo2.setId(DatasetPojoTest.ID + "2");

    Assert.assertThat(DatasetPojoTest.POJO.equals(pojo2), Matchers.not(Matchers.equalTo(true)));
  }

  @Test
  public void testEqualsWhenVersionIsDifferent() throws Exception {
    pojo2.setVersion(DatasetPojoTest.VERSION + 2);

    Assert.assertThat(DatasetPojoTest.POJO.equals(pojo2), Matchers.not(Matchers.equalTo(true)));
  }

  @Test
  public void testEqualsWhenMetacardLocationIsDifferent() throws Exception {
    pojo2.setMetacardLocation(DatasetPojoTest.METACARD_LOCATION + "2");

    Assert.assertThat(DatasetPojoTest.POJO.equals(pojo2), Matchers.not(Matchers.equalTo(true)));
  }

  @Test
  public void testEqualsWhenCurrentLocationIsDifferent() throws Exception {
    pojo2.setCurrentLocation(DatasetPojoTest.CURRENT_LOCATION + "2");

    Assert.assertThat(DatasetPojoTest.POJO.equals(pojo2), Matchers.not(Matchers.equalTo(true)));
  }

  @Test
  public void testEqualsWhenFinalLocationIsDifferent() throws Exception {
    pojo2.setFinalLocation(DatasetPojoTest.FINAL_LOCATION + "2");

    Assert.assertThat(DatasetPojoTest.POJO.equals(pojo2), Matchers.not(Matchers.equalTo(true)));
  }
}
