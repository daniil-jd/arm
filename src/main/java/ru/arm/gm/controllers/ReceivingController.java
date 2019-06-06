package ru.arm.gm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import ru.arm.gm.domain.Detector;
import ru.arm.gm.dto.DetectorDTO;
import ru.arm.gm.service.DetectorService;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Controller
public class ReceivingController {

    @Autowired
    private DetectorService service;

    @Value("${url.settings}")
    String urlSettings;

    @GetMapping("/")
    public String getDetectorInfo(Model model) throws IOException {
        List<String> errors = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        List<Detector> detectors = new ArrayList<>();
        List<DetectorDTO> dtos = new ArrayList<>();
        for (String url : getUrls()) {
            if (isURL(url)) {
                try {
                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Content-Type", "application/xml");
                    HttpEntity entity = new HttpEntity(headers);

                    DetectorDTO dto = restTemplate.exchange(
                            url + "/api/detector",
                            HttpMethod.GET, entity,
                            DetectorDTO.class).getBody();
                    detectors.add(new Detector(dto));
                    dtos.add(dto);
                } catch (ResourceAccessException e) {
                    errors.add("Ресурс по адресу " + url
                            + " не доступен.");
                }
            }
        }
        detectors = service.saveDetectors(detectors);

        model.addAttribute("errors", errors);
        model.addAttribute("detectors", dtos);
        return "mainArm";
    }

    /**
     * Получает список всех url'ов из файла проперти.
     * @return список урлов
     * @throws IOException если нет файла проперти
     */
    private List<String> getUrls() throws IOException {
        FileSystemXmlApplicationContext ctx = new FileSystemXmlApplicationContext();
        Resource res = ctx.getResource(urlSettings);

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
