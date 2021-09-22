package com.peb.pebb.doc;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin("*")
@RestController
public class DocController {

    @Autowired
    private DocService docService;

    @PostMapping(value = "/uploadMulti")
    public String uploadMultiFiles(@RequestParam("files") MultipartFile[] files) {
        for (MultipartFile file : files) {
            docService.saveFile(file);
        }
        // TODO: process POST request

        return "ok";
    }

    @PostMapping(value = "/upload")
    public String uploadSingleFile(@RequestParam("file") MultipartFile file) {
        docService.saveFile(file);

        // TODO: process POST request

        return "ok snglefile";
    }

    @GetMapping(value = "/download/{filedId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Long filedId) {
        Doc doc = docService.getFile(filedId).get();

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(doc.getDocType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=\"" + doc.getDocName() + "\"")
                .body(new ByteArrayResource(doc.getData()));
    }

}
