package com.asierpc.portfoliobackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.junit.jupiter.api.Assertions.*;

class PortfolioBackendApplicationTest {
  @Test
  void hasSpringBootApplicationAnnotation() {
    assertNotNull(PortfolioBackendApplication.class.getAnnotation(SpringBootApplication.class));
  }
}
