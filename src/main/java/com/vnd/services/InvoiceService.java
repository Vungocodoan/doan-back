package com.vnd.services;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

@Service
public class InvoiceService {

    private final RestTemplate restTemplate;


    public InvoiceService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void downloadInvoiceZip() throws IOException {
        // Tạo URL API
        String url = "https://hoadondientu.gdt.gov.vn:30000/query/invoices/export-xml?nbmst=0101243150&khhdon=C24TMS&shdon=163080&khmshdon=1";

        // Tạo headers cho request
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM)); // Expect ZIP file
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwMTA5MzI0ODkxIiwidHlwZSI6MiwiZXhwIjoxNzI3MDc3ODI2LCJpYXQiOjE3MjY5OTE0MjZ9.REV0FpBrine6owbmqH2srh7l6u3PoZEeDC5B6c4x7VZVZEfytAc0PuRKz8syZ7_aPbblqAZ1lgMdg0XmcVN9WA");
//        headers.set("Action", "Xu%E1%BA%A5t%20xml%20(h%C3%B3a%20%C4%91%C6%A1n%20b%C3%A1n%20ra)");
//        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36");

        // Tạo request
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Gửi request và nhận về phản hồi
        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);

        // Kiểm tra xem response có thành công không
        if (response.getStatusCode() == HttpStatus.OK) {
            // Đường dẫn lưu file ZIP
            Path path = Paths.get("invoice.zip");

            // Ghi nội dung file ZIP ra đĩa
            Files.write(path, response.getBody());

            System.out.println("Invoice ZIP file downloaded: " + path.toAbsolutePath());
        } else {
            System.out.println("Failed to download invoice ZIP, status code: " + response.getStatusCode());
        }
    }
}
