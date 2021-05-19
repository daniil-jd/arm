package ru.arm.gm.controllers;

import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import ru.arm.gm.domain.Detector;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Controller
public class ReceivingController {

    /**
     * Файл со списком URL'ов.
     */
    private final String fileName = "target\\classes\\detectors-urls.properties";

    @GetMapping("/")
    public String getDetectorInfo(Model model) throws IOException {
        //comment that need to be removed
        RestTemplate restTemplate = new RestTemplate();
        List<Detector> detectors = new ArrayList<>();
        for (String url : getUrls()) {
            if (isURL(url)) {
                detectors.add(restTemplate.getForObject(
                        url + "/api/detector",
                        Detector.class));
            }
        }
        model.addAttribute("detectors", detectors);
        return "detector";
    }

    /**
     * Получает список всех url'ов из файла проперти.
     * @return список урлов
     * @throws IOException если нет файла проперти
     */
    private List<String> getUrls() throws IOException {
        FileSystemXmlApplicationContext ctx = new FileSystemXmlApplicationContext();
        Resource res = ctx.getResource("config/detectors-urls.properties");

        File file = res.getFile();
        Properties properties = new Properties();
        properties.load(new FileReader(file));

        List<String> urls = new ArrayList<>();
        for (Object prop : properties.values()) {
            urls.add(prop.toString());
        }
        return urls;
    }

    /**
     * Является ли строка URL'ом.
     * @param url url
     * @return true - является
     */
    private boolean isURL(String url) {
        return url.contains("http://") && url.contains(":80");
    }
}
