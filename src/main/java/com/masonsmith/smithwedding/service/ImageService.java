package com.masonsmith.smithwedding.service;

import com.masonsmith.smithwedding.data.Image;
import com.masonsmith.smithwedding.data.repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.OffsetDateTime;

@Service
@Transactional
public class ImageService {

  /**
   * Variables
   */

  private static final Logger logger = LoggerFactory.getLogger(ImageService.class);
  private static final String UPLOAD_DIRECTORY = "src/main/resources/static/img/uploads";

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
   * Find all images and load into one page
   */
  public Page<Image> findPage(Pageable pageable) {
    return repository.findAll(pageable);
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
    String fileName = strip(OffsetDateTime.now().toString()) + ".JPG";
    try {
      if (!file.isEmpty()) {
        Files.copy(file.getInputStream(), Paths.get(UPLOAD_DIRECTORY, fileName));
        repository.save(new Image(fileName));
      }
    } catch (IOException e) {
      logger.error(e.getLocalizedMessage());
    }
  }

  /**
   * Strip all unwanted chars from file name
   */

  private String strip(String toStrip) {
    return toStrip.replaceAll("[^a-zA-Z0-9]+", "");
  }

}
