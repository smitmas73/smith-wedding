package com.masonsmith.smithwedding.controller;

import com.masonsmith.smithwedding.data.Image;
import com.masonsmith.smithwedding.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;

@Controller
public class WeddingSiteController {

  /**
   * Variables
   */

  @Autowired
  private ImageService imageService;

  private static final String BASE_PATH = "/";
  private static final String IMAGE_PATH = "images/";
  private static final String FILENAME = "{filename:.+}";

  /**
   * Constructor
   */

  @Autowired
  public WeddingSiteController(ImageService imageService) {
    this.imageService = imageService;
  }

  /**
   * Home page view rendering
   */

  @RequestMapping(value = BASE_PATH)
  public String index() {
    return "index";
  }

  /**
   * Image page view rendering
   */

  @RequestMapping(value = BASE_PATH + IMAGE_PATH)
  public String images(Model model, Pageable pageable) {
    final Page<Image> page = imageService.findPage(pageable);
    model.addAttribute("page", page);
    if (page.hasPrevious()) {
      model.addAttribute("prev", pageable.previousOrFirst());
    }
    if (page.hasNext()) {
      model.addAttribute("next", pageable.next());
    }
    return "images";
  }

  /**
   * Image page view rendering redirect from image upload
   *
   * @param file image to save
   * @param redirectAttributes flash message attribute
   * @return redirect to images view
   */

  // Create one image
  @PostMapping(value = BASE_PATH + IMAGE_PATH)
  public String createImage(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
    try {
      imageService.createImage(file);
      redirectAttributes.addFlashAttribute("flash.message", "Successfully uploaded "
        + file.getOriginalFilename());
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("flash.message", "Unable to upload "
        + file.getOriginalFilename() + " => " + e.getMessage());
    }
    return "redirect:" + BASE_PATH + IMAGE_PATH;
  }

  /**
   * Image APIs
   */

  // Get one image
  @GetMapping(value = BASE_PATH + IMAGE_PATH + FILENAME + "/raw")
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

}
