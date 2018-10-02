package com.masonsmith.smithwedding.data;

import com.masonsmith.smithwedding.data.repository.ImageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ImageRepositoryTest {

    @Resource
    private ImageRepository repository;

    @Test
    public void testSaveImage() {
        Image image = new Image("test1");
        repository.save(image);

        assertThat(repository.existsById(image.getId())).isTrue();
        assertThat(repository.findImageByName("test1")).isNotNull();
    }

}
