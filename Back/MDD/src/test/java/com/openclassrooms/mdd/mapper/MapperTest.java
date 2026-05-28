package com.openclassrooms.mdd.mapper;

import com.openclassrooms.mdd.dto.comment.CommentDto;
import com.openclassrooms.mdd.dto.post.PostDto;
import com.openclassrooms.mdd.dto.topic.TopicDto;
import com.openclassrooms.mdd.dto.user.UserDto;
import com.openclassrooms.mdd.entity.Comment;
import com.openclassrooms.mdd.entity.Post;
import com.openclassrooms.mdd.entity.Topic;
import com.openclassrooms.mdd.entity.User;
import com.openclassrooms.mdd.entity.Subscription;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private final PostMapper postMapper = Mappers.getMapper(PostMapper.class);
    private final TopicMapper topicMapper = Mappers.getMapper(TopicMapper.class);
    private final CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    @Test
    void userMapper_shouldMapSubscriptionsToTopicNames() {
        Topic topic = Topic.builder()
                .id(1L)
                .name("Java")
                .build();

        Subscription subscription = Subscription.builder()
                .topic(topic)
                .build();

        User user = User.builder()
                .id(1L)
                .username("john")
                .email("john@test.com")
                .subscriptions(List.of(subscription))
                .build();

        UserDto result = userMapper.toDto(user);

        assertNotNull(result);
        assertEquals("john", result.getUsername());
        assertEquals(List.of("Java"), result.getSubscriptions());
    }

    @Test
    void postMapper_shouldMapPostFields() {
        User author = User.builder().username("john").build();
        Topic topic = Topic.builder().name("Java").build();
        Post post = Post.builder()
                .id(1L)
                .title("Title")
                .content("Content")
                .author(author)
                .topic(topic)
                .build();

        PostDto result = postMapper.toDto(post);

        assertNotNull(result);
        assertEquals("john", result.getAuthor());
        assertEquals("Java", result.getTopic());
        assertEquals("Title", result.getTitle());
    }

    @Test
    void topicMapper_shouldIgnoreSubscribedField() {
        Topic topic = Topic.builder()
                .id(1L)
                .name("Java")
                .description("Description")
                .build();

        TopicDto result = topicMapper.toDto(topic);

        assertNotNull(result);
        assertEquals("Java", result.getName());
        assertFalse(result.isSubscribed());
    }

    @Test
    void commentMapper_shouldMapAuthor() {
        User author = User.builder().username("john").build();
        Comment comment = Comment.builder()
                .id(1L)
                .content("Nice")
                .author(author)
                .build();

        CommentDto result = commentMapper.toDto(comment);

        assertNotNull(result);
        assertEquals("john", result.getAuthor());
        assertEquals("Nice", result.getContent());
    }
}
