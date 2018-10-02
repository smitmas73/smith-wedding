package com.masonsmith.smithwedding.service;

import com.masonsmith.smithwedding.data.Image;
import com.masonsmith.smithwedding.data.repository.ImageRepository;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.OffsetDateTime;

@Service
public class ImageService {

    /**
     * Variables
     */

    private static final String UPLOAD_DIRECTORY = "uploads";

    private final ImageRepository repository;
    private final ResourceLoader resourceLoader;

    /**
     * Constructor
     */

    @Autowired
    public ImageService(ImageRepository repository, ResourceLoader resourceLoader) {
        this.repository = repository;
        this.resourceLoader = resourceLoader;
    }

    /**
     * Find one image from upload directory
     *
     * @param filename file name of image to be found
     * @return found image
     */

    public Resource findImage(String filename) {
        return resourceLoader.getResource("file:" + UPLOAD_DIRECTORY + "/" + filename);
    }

    /**
     * Create new image from upload to UPLOAD_DIRECTORY
     *
     * @param file file to upload
     */

    public void createImage(MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                Files.copy(file.getInputStream(), Paths.get(UPLOAD_DIRECTORY,
                        file.getOriginalFilename() + OffsetDateTime.now()));
                repository.save(new Image(file.getOriginalFilename() + OffsetDateTime.now()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
