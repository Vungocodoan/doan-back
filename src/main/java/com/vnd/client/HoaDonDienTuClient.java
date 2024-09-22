package com.vnd.client;

import org.springframework.stereotype.Component;

@Component
public class HoaDonDienTuClient {

    public String login(String username, String password) {
        return "token";
    }

    public String exportXml() {
        return null;

    }

}
