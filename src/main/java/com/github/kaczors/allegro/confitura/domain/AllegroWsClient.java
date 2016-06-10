package com.github.kaczors.allegro.confitura.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import pl.allegro.*;

import javax.annotation.PostConstruct;
import java.util.List;

public class AllegroWsClient extends WebServiceGatewaySupport {
    private static Logger log = LoggerFactory.getLogger(AllegroWsClient.class);

    private static final int POLAND_ID = 1;
    private static final long BUY_IT_NOW_YES = 1L;

    private final String login;
    private final String password;
    private final String apiKey;
    private final String uri;
    private final String namespace;
    private String sessionKey;

    public AllegroWsClient(String login, String password, String apiKey, String uri, String namespace) {
        this.login = login;
        this.password = password;
        this.apiKey = apiKey;
        this.uri = uri;
        this.namespace = namespace;
    }

    @PostConstruct
    private void init() {
        sessionKey = login();
    }

    public String buyItNow(long itemId, float price, long quantity) {
        DoBidItemRequest request = new DoBidItemRequest();
        request.setSessionHandle(sessionKey);
        request.setBidItId(itemId);
        request.setBidUserPrice(price);
        request.setBidQuantity(quantity);
        request.setBidBuyNow(BUY_IT_NOW_YES);

        DoBidItemResponse response = sendRequest(request, "doBidItem");
        return response.getBidPrice();
    }

    private String login() {
        DoLoginEncRequest request = new DoLoginEncRequest();
        request.setUserLogin(login);
        request.setUserHashPassword(password);
        request.setCountryCode(POLAND_ID);
        request.setWebapiKey(apiKey);
        request.setLocalVersion(getAnyVersion());

        log.info("Trying login with login: {}, password: {}", login, password);
        DoLoginEncResponse response = sendRequest(request, "doLoginEnc");
        log.info("Logged in, userId: {}, server time: {}", response.getUserId(), response.getServerTime());

        return response.getSessionHandlePart();
    }

    private long getAnyVersion() {
        DoQueryAllSysStatusRequest request = new DoQueryAllSysStatusRequest();
        request.setCountryId(POLAND_ID);
        request.setWebapiKey(apiKey);

        DoQueryAllSysStatusResponse response = sendRequest(request, "doQueryAllSysStatus");

        return response.getSysCountryStatus().getItem().stream()
                .findAny()
                .map(SysStatusType::getVerKey)
                .orElseThrow(() -> new RuntimeException("No API version found..."));
    }

    @SuppressWarnings("unchecked")
    private <Req, Res> Res sendRequest(Req request, String endpoint) {
        return (Res) getWebServiceTemplate()
                .marshalSendAndReceive(
                        uri,
                        request,
                        new SoapActionCallback(namespace + "/" + endpoint));


    }

    public List<ItemsListType> listItems(String userId) {
        DoGetItemsListRequest request = new DoGetItemsListRequest();
        request.setWebapiKey(apiKey);
        request.setCountryId(POLAND_ID);

        request.setFilterOptions(filterByUser(userId));

        DoGetItemsListResponse response = sendRequest(request, "doGetItemsList");

        return response.getItemsList().getItem();
    }

    private ArrayOfFilteroptionstype filterByUser(String userId) {
        FilterOptionsType userIdFilter = new FilterOptionsType();
        userIdFilter.setFilterId("userId");

        ArrayOfString userIds = new ArrayOfString();
        userIds.getItem().add(userId);

        userIdFilter.setFilterValueId(userIds);

        ArrayOfFilteroptionstype filtersArray = new ArrayOfFilteroptionstype();
        filtersArray.getItem().add(userIdFilter);
        return filtersArray;
    }


}
