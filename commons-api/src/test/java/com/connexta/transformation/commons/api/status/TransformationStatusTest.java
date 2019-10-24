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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import com.connexta.transformation.commons.api.status.TransformationStatus.State;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class TransformationStatusTest {
  private static final long ELAPSED = 5000L;
  private static final Instant START_TIME =
      Instant.now().minusMillis(TransformationStatusTest.ELAPSED);
  private static final long DURATION = 23456L;
  private static final Instant COMPLETION_TIME =
      TransformationStatusTest.START_TIME.plusMillis(TransformationStatusTest.DURATION);

  private TransformationStatus status;

  @Test
  public void isCompletedSuccessful() {
    status = mockTransformationStatus(State.SUCCESSFUL);
    assertTrue(status.isCompleted());
  }

  @Test
  public void isCompletedFailed() {
    status = mockTransformationStatus(State.FAILED);
    assertTrue(status.isCompleted());
  }

  @Test
  public void isCompletedInProgress() {
    status = mockTransformationStatus(State.IN_PROGRESS);
    assertFalse(status.isCompleted());
  }

  @Test
  public void isCompletedUnknown() {
    status = mockTransformationStatus(State.UNKNOWN);
    assertFalse(status.isCompleted());
  }

  @Test
  public void hasFailedTrue() {
    status = mockTransformationStatus(State.FAILED);
    assertTrue(status.hasFailed());
  }

  @Test
  public void hasFailedFalse() {
    status = mockTransformationStatus(State.IN_PROGRESS);
    assertFalse(status.hasFailed());
  }

  @Test
  public void wasSuccessfulTrue() {
    status = mockTransformationStatus(State.SUCCESSFUL);
    assertTrue(status.wasSuccessful());
  }

  @Test
  public void wasSuccessfulFalse() {
    status = mockTransformationStatus(State.FAILED);
    assertFalse(status.wasSuccessful());
  }

  @Test
  public void isUnknownTrue() {
    status = mockTransformationStatus(State.UNKNOWN);
    assertTrue(status.isUnknown());
  }

  @Test
  public void isUnknownFalse() {
    status = mockTransformationStatus(State.FAILED);
    assertFalse(status.isUnknown());
  }

  @Test
  public void testGetDurationWhenCompleted() throws Exception {
    status = mockTransformationStatus(State.SUCCESSFUL);
    when(status.getStartTime()).thenReturn(TransformationStatusTest.START_TIME);
    when(status.getCompletionTime())
        .thenReturn(Optional.of(TransformationStatusTest.COMPLETION_TIME));

    Assert.assertThat(
        status.getDuration(),
        Matchers.equalTo(Duration.ofMillis(TransformationStatusTest.DURATION)));
  }

  @Test
  public void testGetDurationWhenNotCompleted() throws Exception {
    status = mockTransformationStatus(State.IN_PROGRESS);
    when(status.getStartTime()).thenReturn(TransformationStatusTest.START_TIME);
    when(status.getCompletionTime()).thenReturn(Optional.empty());
    final long now = System.currentTimeMillis();

    final long duration = status.getDuration().toMillis();

    // the clock keeps moving so we can only ensure we are close to the target
    Assert.assertThat(
        duration,
        Matchers.lessThanOrEqualTo(now - TransformationStatusTest.START_TIME.toEpochMilli()));
    Assert.assertThat(duration, Matchers.greaterThan(TransformationStatusTest.ELAPSED));
  }

  // lets us use Mocktio.CALLS_REAL_METHODS which will call the implemented methods and mock others
  // normally
  private abstract class testStatus implements TransformationStatus {}

  private TransformationStatus mockTransformationStatus(State state) {
    TransformationStatus testStatus =
        Mockito.mock(
            testStatus.class, Mockito.withSettings().defaultAnswer(Mockito.CALLS_REAL_METHODS));
    when(testStatus.getState()).thenReturn(state);
    return testStatus;
  }
}
