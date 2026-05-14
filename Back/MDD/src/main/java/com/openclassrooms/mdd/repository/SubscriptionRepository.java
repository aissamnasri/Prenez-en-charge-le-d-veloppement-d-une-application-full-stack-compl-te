package com.openclassrooms.mdd.repository;

import com.openclassrooms.mdd.entity.Subscription;
import com.openclassrooms.mdd.entity.Topic;
import com.openclassrooms.mdd.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository
        extends JpaRepository<Subscription, Long> {

    boolean existsByUserAndTopic(User user, Topic topic);

    Optional<Subscription> findByUserAndTopic(User user, Topic topic);
}