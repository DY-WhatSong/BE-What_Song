package dy.whatsong.domain.story.repo;

import dy.whatsong.domain.story.entity.Story;
import org.springframework.data.repository.CrudRepository;

public interface StoryRepository extends CrudRepository<Story,String> {
}
