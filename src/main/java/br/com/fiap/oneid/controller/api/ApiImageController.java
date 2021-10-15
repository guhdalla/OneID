package br.com.fiap.oneid.controller.api;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.activation.FileTypeMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/img ")
public class ApiImageController {

    static final String PATH_LOCAL_IMG = "src/main/resources/static/img/usuarios/";

    @RequestMapping(value = "/download/{linkImage}", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<byte[]> photoDownload(@PathVariable("linkImage") String imagem) throws IOException {
        File file = new File(PATH_LOCAL_IMG + imagem);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + file.getName())
                .contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(file)))
                .body(Files.readAllBytes(file.toPath()));
    }

    //Mostrar imagem
    @RequestMapping(value = "/{linkImage}", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("linkImage") String imagem) throws IOException {
        File img = new File(PATH_LOCAL_IMG + imagem);
        return ResponseEntity.ok().contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(img))).body(Files.readAllBytes(img.toPath()));
    }

}
