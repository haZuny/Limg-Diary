package com.hayden.limg_diary.api.karlo;

import com.hayden.limg_diary.api.karlo.dto.KarloImageForm;
import com.hayden.limg_diary.api.karlo.dto.KarloResponseForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class KarloApiHelper {
    @Value("${karlo.url}")
    String url;

    @Value("${karlo.auth}")
    String auth;

    public Optional<KarloResponseForm> post(String prompt) {
        WebClient webClient = WebClient.builder().baseUrl(url).build();

        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("version", "v2.1");
        bodyMap.put("prompt", "Cute Cartoon image, " + prompt);
        bodyMap.put("width", "1024");
        bodyMap.put("height", "1024");

        try {
            return Optional.ofNullable(
                    webClient.post()
                            .header("Authorization", auth)
                            .header("Content-Type", "application/json")
                            .bodyValue(bodyMap)
                            .retrieve()
                            .bodyToMono(KarloResponseForm.class)
                            .block()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    KarloImageForm getFirstImage(KarloResponseForm responseForm) {
        return responseForm.getImages().getFirst();
    }

    // get image path from karlo response
    String getImageUrl(KarloImageForm imageForm) {
        return imageForm.getImage();
    }

    // save url image to local
    boolean saveImageUrl(String imgUrl, String savePath) {
        try{

            // open stream, path
            InputStream inputStream = new URL(imgUrl).openStream();
            Path path = Path.of(savePath);

            // 없으면 경로 생성
            if (!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            // save img file
            Files.copy(inputStream, Path.of(savePath), StandardCopyOption.REPLACE_EXISTING);

            // close
            inputStream.close();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean createAndSaveImage(String prompt, String savePath){
        try{
            // create image from Karlo
            KarloResponseForm responseForm = post(prompt).get();
            KarloImageForm imageForm = getFirstImage(responseForm);
            String imgUrl = getImageUrl(imageForm);
            // save url image to local
            boolean res = saveImageUrl(imgUrl, savePath);
            if (res)    return true;
            else throw new Exception("이미지 저장 실패");
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
