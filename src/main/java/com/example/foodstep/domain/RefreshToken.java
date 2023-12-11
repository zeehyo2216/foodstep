//package com.example.foodstep.domain;
//
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.redis.core.RedisHash;
//import org.springframework.data.redis.core.TimeToLive;
//
//@Getter
//@NoArgsConstructor
//@RedisHash(value = "refresh_token")
//public class RefreshToken{
//    @Id
//    private String id;
//    private User user;
//    @TimeToLive
//    private long expirationTime;
//
//    @Builder
//    public RefreshToken(String id, User user, long expirationTime) {
//        this.id = id;
//        this.user = user;
//        this.expirationTime = expirationTime;
//    }
//}