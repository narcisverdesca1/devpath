package com.narcis.devpath.apigateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class CorrelationIdGlobalFilter implements GlobalFilter, Ordered {

    private static final String CORRELATION_ID_HEADER = "X-Correlation-Id";
    private static final Logger log = LoggerFactory.getLogger(CorrelationIdGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String existingCorrelationId = exchange.getRequest()
                .getHeaders()
                .getFirst(CORRELATION_ID_HEADER);

        String correlationId = existingCorrelationId != null
                ? existingCorrelationId
                : UUID.randomUUID().toString();

        log.info("[{}] Incoming request: {} {}",
                correlationId,
                exchange.getRequest().getMethod(),
                exchange.getRequest().getURI().getPath());

        exchange.getResponse()
                .getHeaders()
                .add(CORRELATION_ID_HEADER, correlationId);

        ServerHttpRequest mutatedRequest = exchange.getRequest()
                .mutate()
                .header(CORRELATION_ID_HEADER, correlationId)
                .build();

        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(mutatedRequest)
                .build();

        return chain.filter(mutatedExchange)
                .doFinally(signalType -> log.info("[{}] Response status: {} for {} {}",
                        correlationId,
                        mutatedExchange.getResponse().getStatusCode(),
                        mutatedExchange.getRequest().getMethod(),
                        mutatedExchange.getRequest().getURI().getPath()));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}