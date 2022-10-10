package com.bertopcu.KitchenWorld.service;

import com.bertopcu.KitchenWorld.jpa_repo.NotificationRepository;
import com.bertopcu.KitchenWorld.jpa_repo.UserRepository;
import com.bertopcu.KitchenWorld.model.Favorite;
import com.bertopcu.KitchenWorld.model.Notification;
import com.bertopcu.KitchenWorld.model.User;
import com.bertopcu.KitchenWorld.util.NotificationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public void saveNotification(Notification notification) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        logger.debug("::saveNotification starts:: ::loggedinUser:: {} ::isSystemNotif:: {} ::triggerUser:: {}", currentPrincipalName, notification.getOriginSystem(), notification.getTriggerUser());
        try {
            notificationRepository.save(notification);
            logger.debug("::saveNotification success onbehalf of:: {}", notification.getUserId());
        } catch (Exception e) {
            logger.error("::error at saveNotification::", e);
            throw e;
        }
    }

    public boolean deleteNotification(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        logger.debug("::deleteFavorite starts:: ::loggedinUser:: {} ::param:: {}", currentPrincipalName, id);
        try {
            User loggedinUser = this.getUserByUserName(currentPrincipalName);
            if(loggedinUser.getType() != 1) {
                logger.error("::deleteFavorite:: ::User does not have enough rights for this operation!::");
                return false;
            }
            notificationRepository.deleteById(id);
            logger.debug("::deleteFavorite success for:: {}", id);
            return true;
        } catch (Exception e) {
            logger.error("::error at deleteFavorite::", e);
            throw e;
        }
    }

    public int readNotification(int notifId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User loggedinUser = this.getUserByUserName(currentPrincipalName);
        int userId = loggedinUser.getId();
        logger.debug("::readNotification starts:: ::loggedinUser:: {} ::notifId:: {} ", currentPrincipalName, notifId);
        try {
            int result = notificationRepository.readNotification(notifId, userId);
            if(result == 0) {
                logger.error("::user do not have enough rights to read notification(s) on behalf of other user::");
            }
            logger.debug("::readNotification end for notificationId:: {}", notifId);
            return result;
        } catch (Exception e) {
            logger.error("::error at readNotification::", e);
            throw e;
        }
    }

    public int unreadNotification(int notifId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User loggedinUser = this.getUserByUserName(currentPrincipalName);
        int userId = loggedinUser.getId();
        logger.debug("::unreadNotification starts:: ::loggedinUser:: {} ::notifId:: {} ", currentPrincipalName, notifId);
        try {
            int result = notificationRepository.unreadNotification(notifId, userId);
            if(result == 0) {
                logger.error("::user do not have enough rights to unread notification(s) on behalf of other user::");
            }
            logger.debug("::unreadNotification end for notificationId:: {}", notifId);
            return result;
        } catch (Exception e) {
            logger.error("::error at unreadNotification::", e);
            throw e;
        }
    }

    public List<Notification> listAllNotifsByUserId(Integer userId) {
        if (userId == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User loggedinUser = this.getUserByUserName(authentication.getName());
            userId = loggedinUser.getId();
        }

        List<Notification> notifList = notificationRepository.findByUserId(userId);
        for(Notification notif : notifList) {
            if(notif.getOriginSystem() == 0) {
                notif.setTriggerUserObj(this.getNotifTriggerUserObj(notif.getTriggerUser()));
            }
        }
        return notifList;
    }

    public List<Notification> listUnreadNotifsByUserId(Integer userId) {
        if (userId == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User loggedinUser = this.getUserByUserName(authentication.getName());
            userId = loggedinUser.getId();
        }
        List<Notification> notifList = notificationRepository.findUnreadsByUser(userId);
        for(Notification notif : notifList) {
            if(notif.getOriginSystem() == 0) {
                notif.setTriggerUserObj(this.getNotifTriggerUserObj(notif.getTriggerUser()));
            }
        }
        return notifList;
    }

    public List<Notification> findByUserAndType(Integer userId, String type) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        logger.debug("::findByUserAndType starts:: ::loggedinUser:: {} ::type:: {}", currentPrincipalName, type);
        List<Notification> notifList;
        try {
            //type validation
            logger.debug("::validating notifType:: {}", type);
            NotificationType validType = NotificationType.valueOf(type);
            logger.debug("::validation successfull for notifType:: {}", type);
            notifList = notificationRepository.findByUserAndType(userId, type);
            for(Notification notif : notifList) {
                if(notif.getOriginSystem() == 0) {
                    notif.setTriggerUserObj(this.getNotifTriggerUserObj(notif.getTriggerUser()));
                }
            }
            logger.debug("::totalNotifCount:: {}", notifList.size());
        } catch(Exception e) {
            logger.error("::error at findByUserAndType::", e);
            throw e;
        }


        return notifList;
    }

    public User getNotifTriggerUserObj(Integer userId) {
        User triggerUserObj = userRepository.findById(userId).get();
        return triggerUserObj;
    }

    public User getUserByUserName(String userName) {
        User user = userRepository.findByUname(userName);
        return user;
    }
}
