package net.coatli.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.util.Headers;
import io.undertow.util.StatusCodes;

public class UndertowOnePathVariableApplication {

  private static final Logger LOGGER = LoggerFactory.getLogger(UndertowOnePathVariableApplication.class);

  public static void main(final String[] args) {

    Undertow.builder()
      .addHttpListener(6543, "localhost")
      .setHandler(
          Handlers.routing()
            .get("/api/v1/orders/", (exchange) -> {

                LOGGER.info("Path template defined '/api/v1/orders/'");
                LOGGER.info("Request URI '{}'", exchange.getRequestURI());

                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                exchange.setStatusCode(StatusCodes.FOUND)
                        .getResponseSender().send(exchange.getQueryParameters().get("orderId"));

              })
            .get("/api/v1/orders/{orderId}", (exchange) -> {

                LOGGER.info("Path template defined '/api/v1/orders/{orderId}'");
                LOGGER.info("Request URI '{}'", exchange.getRequestURI());

                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                exchange.setStatusCode(StatusCodes.FOUND)
                        .getResponseSender().send(exchange.getQueryParameters().get("orderId").getLast());

              }))
      .build()
      .start();

  }

}
