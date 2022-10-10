package com.bertopcu.KitchenWorld.fe;

import com.bertopcu.KitchenWorld.model.Favorite;
import com.bertopcu.KitchenWorld.model.Material;
import com.bertopcu.KitchenWorld.model.Notification;
import com.bertopcu.KitchenWorld.model.Recipe;
import com.bertopcu.KitchenWorld.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/")
    public void add(@RequestBody Notification notification) {
        notificationService.saveNotification(notification);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/read/{id}")
    public ResponseEntity<Boolean> read(@PathVariable Integer id) {
        int affectedRows = notificationService.readNotification(id);
        if(affectedRows < 1) {
            return new ResponseEntity<Boolean>(false, HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/unread/{id}")
    public ResponseEntity<Boolean> unread(@PathVariable Integer id) {
        int affectedRows = notificationService.unreadNotification(id);
        if(affectedRows < 1) {
            return new ResponseEntity<Boolean>(false, HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/all")
    public List<Notification> list() {
        return notificationService.listAllNotifsByUserId(null);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/unreads")
    public List<Notification> listUnreads() {
        return notificationService.listUnreadNotifsByUserId(null);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteNotification(@PathVariable Integer id) {
        try {
            boolean result= this.notificationService.deleteNotification(id);
            if(!result) {
                return new ResponseEntity<Boolean>(result, HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<Boolean>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.UNAUTHORIZED);
        }
    }
}
