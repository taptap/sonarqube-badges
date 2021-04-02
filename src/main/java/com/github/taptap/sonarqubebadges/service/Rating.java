package com.github.taptap.sonarqubebadges.service;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import static java.lang.String.format;
import static java.util.Arrays.stream;
import static com.github.taptap.sonarqubebadges.service.Severity.*;


public enum Rating {
  E(5),
  D(4),
  C(3),
  B(2),
  A(1);

  private final int index;

  Rating(int index) {
    this.index = index;
  }

  public int getIndex() {
    return index;
  }

  public static Rating valueOf(int index) {
    return stream(Rating.values())
      .filter(r -> r.getIndex() == index)
      .findFirst()
      .orElseThrow(() -> new IllegalArgumentException(format("Unknown value '%s'", index)));
  }

  public static final Map<String, Rating> RATING_BY_SEVERITY = ImmutableMap.of(
    BLOCKER, E,
    CRITICAL, D,
    MAJOR, C,
    MINOR, B,
    INFO, A);
}