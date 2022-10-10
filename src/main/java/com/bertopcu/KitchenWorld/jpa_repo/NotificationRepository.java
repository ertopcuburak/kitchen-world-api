package com.bertopcu.KitchenWorld.jpa_repo;

import com.bertopcu.KitchenWorld.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    @Query(value = "SELECT * FROM notification WHERE user_id = ?1 ORDER BY id DESC", nativeQuery = true)
    List<Notification> findByUserId(Integer userId);

    @Query(value = "SELECT * FROM notification WHERE user_id = ?1 AND is_read = 0 ORDER BY id DESC", nativeQuery = true)
    List<Notification> findUnreadsByUser(Integer userId);

    @Query(value = "SELECT * FROM notification WHERE user_id = ?1 AND type =?2 ORDER BY id DESC", nativeQuery = true)
    List<Notification> findByUserAndType(Integer userId, String type);

    @Modifying
    @Query(value = "UPDATE notification set is_read = 1 WHERE id = ?1 AND user_id = ?2", nativeQuery = true)
    int readNotification(Integer notifId, Integer userId);

    @Modifying
    @Query(value = "UPDATE notification set is_read = 0 WHERE id = ?1 AND user_id = ?2", nativeQuery = true)
    int unreadNotification(Integer notifId, Integer userId);
}
