/*
 * Copyright (c) 2023. PortSwigger Ltd. All rights reserved.
 *
 * This code may be used to extend the functionality of Burp Suite Community Edition
 * and Burp Suite Professional, provided that this usage does not violate the
 * license terms for those products.
 */

package duo.example;

import burp.api.montoya.http.handler.*;
import burp.api.montoya.http.message.HttpHeader;
import burp.api.montoya.http.message.requests.HttpRequest;
import duo.example.ui.SuiteTab;

import java.util.List;

import static burp.api.montoya.http.handler.RequestToBeSentAction.continueWith;
import static burp.api.montoya.http.handler.ResponseReceivedAction.continueWith;
import static duo.example.ui.DateComponent.DateMode.*;

class MyHttpHandler implements HttpHandler {
    private final SignatureUtil signatureUtil;
    private final SuiteTab ui;

    public MyHttpHandler(final SignatureUtil signatureUtil, final SuiteTab ui) {
        this.signatureUtil = signatureUtil;
        this.ui = ui;
    }

    @Override
    public RequestToBeSentAction handleHttpRequestToBeSent(HttpRequestToBeSent requestToBeSent) {
        final HttpRequest request;
        if (isPost(requestToBeSent)) {
            request = modify(requestToBeSent);
        } else {
            request = requestToBeSent;
        }
        return continueWith(request);
    }

    @Override
    public ResponseReceivedAction handleHttpResponseReceived(HttpResponseReceived responseReceived) {
        return continueWith(responseReceived);
    }

    private boolean isPost(final HttpRequestToBeSent httpRequestToBeSent) {
        return httpRequestToBeSent.method().equalsIgnoreCase("POST");
    }

    private HttpRequest modify(final HttpRequestToBeSent requestToBeSent) {
        final String sol = getSol(requestToBeSent);
        final String signature = signatureUtil.encode(
                ui.getSecretKey(),
                requestToBeSent.bodyToString(),
                sol
        );
        HttpRequest httpRequest = requestToBeSent.withRemovedHeader(ui.getSignatureHeaderKey())
                .withAddedHeader(ui.getSignatureHeaderKey(), signature);
        if (ui.getDateMode() != HEADER) {
            httpRequest = httpRequest.withRemovedHeader(ui.getDateHeaderKey())
                    .withAddedHeader(ui.getDateHeaderKey(), sol);
        }

        return httpRequest;
    }

    private String getSol(final HttpRequestToBeSent requestToBeSent) {
        switch (ui.getDateMode()) {
            case AUTO -> {
                return String.valueOf(System.currentTimeMillis());
            }
            case MANUAL -> {
                return ui.getManualDate();
            }
            case HEADER -> {
                return findDateInHeader(ui.getReadDateHeaderKey(), requestToBeSent.headers());
            }
            default -> {
                return "";
            }
        }
    }

    private String findDateInHeader(final String headerKey, final List<HttpHeader> headers) {
        if (headers == null || headers.isEmpty()) return "";
        for (HttpHeader header : headers) {
            if (header.name().equals(headerKey)) {
                return header.value();
            }
        }
        return "";
    }
}
