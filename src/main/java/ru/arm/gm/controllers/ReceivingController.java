package ru.arm.gm.controllers;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import ru.arm.gm.domain.Detector;

import java.io.File;
import java.io.FileInputStream;
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
        RestTemplate restTemplate = new RestTemplate();
        List<Detector> detectors = new ArrayList<>();
        for (String url : getUrls()) {
            detectors.add(restTemplate.getForObject(
                    url + "/api/detector",
                    Detector.class));
        }

//        Detector detector = restTemplate.getForObject(
//                        "http://localhost:8080/api/detector",
//                        Detector.class);
        model.addAttribute("detectors", detectors);
        return "detector";
    }

    /**
     * Получает список всех url'ов из файла проперти.
     * @return список урлов
     * @throws IOException если нет файла проперти
     */
    private List<String> getUrls() throws IOException {
//        File file = new ClassPathResource("detectors-urls.properties").getFile();
        //Resource resource = getClass().getClassLoader().getResources("detectors-urls.properties");
        InputStream is = getClass().getClassLoader().getResourceAsStream("detectors-urls.properties");
        Properties properties = new Properties();
        properties.load(is);

        List<String> urls = new ArrayList<>();
        for (Object prop : properties.values()) {
            urls.add(prop.toString());
        }
        return urls;
    }

}
