package ru.arm.gm.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Controller
public class UrlsSettingsController {

    @Value("${url.settings}")
    String urlSettings;

    @GetMapping("/url-settings")
    public String getUrlsSetting(Model model) throws IOException {

        model.addAttribute("urls", getUrls());
        return "urlsSettings";
    }

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

}
