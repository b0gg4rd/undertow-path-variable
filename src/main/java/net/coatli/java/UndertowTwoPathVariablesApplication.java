package net.coatli.java;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.util.Headers;
import io.undertow.util.StatusCodes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UndertowTwoPathVariablesApplication {

  private static final Logger LOGGER = LoggerFactory.getLogger(UndertowTwoPathVariablesApplication.class);

  public static void main(final String[] args) {

    Undertow.builder()
      .addHttpListener(8080, "localhost")
      .setHandler(
          Handlers.routing()
            .get("/api/v1/orders/{orderId}/items/", (exchange) -> {
                LOGGER.info("The path template defined is '/api/v1/orders/{orderId}/items/'");
                LOGGER.info("The request URI '{}'", exchange.getRequestURI());
                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                exchange.setStatusCode(StatusCodes.OK)
                        .getResponseSender().send("Retrieve all Items of the Order '"
                                                  + exchange.getQueryParameters().get("orderId").getLast() + "'");
              })
            .get("/api/v1/orders/{orderId}/items/{itemId}", (exchange) -> {
                LOGGER.info("The path template defined is '/api/v1/orders/{orderId}/items/{itemId}'");
                LOGGER.info("The request URI '{}'", exchange.getRequestURI());
                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                exchange.setStatusCode(StatusCodes.OK)
                        .getResponseSender().send(
                            "Retrieve the Item '" + exchange.getQueryParameters().get("itemId").getLast()
                            + "' of the Order '" + exchange.getQueryParameters().get("orderId").getLast() + "'");
              }))
      .build()
      .start();

  }

}
