package com.masonsmith.smithwedding.data.repository;

import com.masonsmith.smithwedding.data.Image;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ImageRepository extends PagingAndSortingRepository<Image, Long> {

    public Image findImageByName(String name);

}
