package com.masonsmith.smithwedding.controller;

import com.masonsmith.smithwedding.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;

@Controller
public class WeddingSiteController {

    /**
     * Variables
     */

    private static final String BASE_PATH = "/";
    private static final String FILENAME = "{filename:.+}";

    private ImageService imageService;

    /**
     * Constructor
     */

    @Autowired
    public WeddingSiteController(ImageService imageService) {
        this.imageService = imageService;
    }

    /**
     * Image API
     */

    @GetMapping(value = BASE_PATH + FILENAME + "/raw")
    @ResponseBody
    public ResponseEntity<?> getImage(@PathVariable String filename) {
        Resource file = imageService.findImage(filename);
        try {
            return ResponseEntity.ok()
                    .contentLength(file.contentLength())
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new InputStreamResource(file.getInputStream()));
        } catch (IOException e) {
            return ResponseEntity.badRequest()
                    .body("Something went wrong when uploading file " + filename + " => " + e.getMessage());
        }
    }

    @PostMapping(value = BASE_PATH)
    @ResponseBody
    public ResponseEntity<?> createImage(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        try {
            imageService.createImage(file);
            final URI locationUri = new URI(request.getRequestURL().toString() + "/")
                    .resolve(file.getOriginalFilename() + "/raw");
            return ResponseEntity.created(locationUri)
                    .body("Successfully uploaded " + file.getOriginalFilename());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload " + file.getOriginalFilename() + " => " + e.getMessage());
        }
    }
}
