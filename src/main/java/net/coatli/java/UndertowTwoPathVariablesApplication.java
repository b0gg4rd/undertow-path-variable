package net.coatli.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.util.Headers;
import io.undertow.util.HttpString;
import io.undertow.util.StatusCodes;

public class UndertowTwoPathVariablesApplication {

  private static final Logger LOGGER = LoggerFactory.getLogger(UndertowTwoPathVariablesApplication.class);

  public static void main(final String[] args) {

    Undertow.builder()
      .addHttpListener(6543, "localhost")
      .setHandler(
          Handlers.routing()
            .get("/api/v1/orders/{orderId}/items/", (exchange) -> {

                LOGGER.info("Path template defined '/api/v1/orders/{orderId}/items/'");
                LOGGER.info("Request URI '{}'", exchange.getRequestURI());
                LOGGER.info("Relative path '{}'", exchange.getRelativePath());

                exchange.getResponseHeaders().put(new HttpString("orderId"), headerValue);

                exchange.setStatusCode(StatusCodes.FOUND)
                        .getResponseSender().send(exchange.getQueryParameters().get("orderId").getLast());

              })
            .get("/api/v1/orders/{orderId}/items/{itemId}", (exchange) -> {

                LOGGER.info("Path template defined '/api/v1/orders/{orderId}/items/{itemId}'");
                LOGGER.info("Request URI '{}'", exchange.getRequestURI());
                LOGGER.info("Relative path is '{}'", exchange.getRelativePath());

                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                exchange.setStatusCode(StatusCodes.FOUND)
                        .getResponseSender().send(exchange.getQueryParameters().get("itemId").getLast());

              }))
      .build()
      .start();

  }

}
