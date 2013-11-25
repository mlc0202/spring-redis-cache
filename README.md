spring-redis-cache
==================

Spring cache manager implementation with redis as native cache.

 Spring Cache component supplies some annotations to help developer to liberate us from caching data with writing
 getting,and setting  between cache and db codes, spring imports org.springframework.cache.CacheManager interface
 and everyone could implement it with different backend cache implementation. Spring its self supplies EhCache
 implementations. Spring Data Redis(http://projects.spring.io/spring-data-redis/) supplies implementation with redis
 and it could support different redis client such as jedis, jredis etc and many other features, but I think it's too
 heavy and we don't need  most features of it , so I wrote this module with only 3 classes.



