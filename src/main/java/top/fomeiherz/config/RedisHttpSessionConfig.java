package top.fomeiherz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableRedisHttpSession
public class RedisHttpSessionConfig {

    /** Get redis config from application.properties **/
    @Value("${redis.host}")
    private String redisHost;

    /**
     * A do nothing implementation of ConfigureRedisAction.
     *
     * @return
     */
    @Bean
    public static ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }

    /**
     * Thread-safe factory of Redis connections.
     *
     * @return
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        if (redisHost.contains(",")) {
            RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
            Set<RedisNode> nodes = new HashSet<RedisNode>();
            for (String hostPort : redisHost.split(",")) {
                nodes.add(new RedisNode(hostPort.split(":")[0], Integer.parseInt(hostPort.split(":")[1])));
            }
            redisClusterConfiguration.setClusterNodes(nodes);
            return new JedisConnectionFactory(redisClusterConfiguration);
        } else {
            JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
            jedisConnectionFactory.setHostName(redisHost.split(":")[0]);
            jedisConnectionFactory.setPort(Integer.parseInt(redisHost.split(":")[1]));
            return jedisConnectionFactory;
        }
    }

    /**
     * Start RedisHttpSessionConfiguration
     *
     * @return
     */
    @Bean
    public RedisHttpSessionConfiguration redisHttpSessionConfiguration() {
        return new RedisHttpSessionConfiguration();
    }
}