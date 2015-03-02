package com.github.jntakpe.mfm.config;

import com.github.jntakpe.mfm.config.properties.CacheProperties;
import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.instance.HazelcastInstanceFactory;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;

/**
 * Configuration du cache
 *
 * @author jntakpe
 */
@Configuration
@EnableCaching
@AutoConfigureAfter(DataSourceConfig.class)
public class CacheConfig {

    private static final Logger LOG = LoggerFactory.getLogger(CacheConfig.class);

    private static HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();

    @Autowired
    private CacheProperties cacheProperties;

    public static HazelcastInstance getHazelcastInstance() {
        return hazelcastInstance;
    }

    @Bean
    public CacheManager cacheManager() {
        return new HazelcastCacheManager(hazelcastInstance);
    }

    @PostConstruct
    public void hazelcastInstance() {
        LOG.debug("Configuration de l'instance Hazelcast");
        Config config = new Config();
        config.setInstanceName("mfm-cache");
        NetworkConfig networkConfig = config.getNetworkConfig();
        networkConfig.setPort(cacheProperties.getPort());
        networkConfig.setPortAutoIncrement(true);
        JoinConfig joinConfig = networkConfig.getJoin();
        joinConfig.getAwsConfig().setEnabled(false);
        joinConfig.getMulticastConfig().setEnabled(false);
        joinConfig.getTcpIpConfig().setEnabled(false);
        Map<String, MapConfig> mapConfigs = config.getMapConfigs();
        mapConfigs.put("default", defaultConfig());
        mapConfigs.put("com.github.jntakpe.mfm.domain.*", domainConfig());
        hazelcastInstance = HazelcastInstanceFactory.newHazelcastInstance(config);
    }

    @PreDestroy
    public void close() {
        LOG.info("Arrêt de l'instance d'Hazelcast");
        Hazelcast.shutdownAll();
    }

    private MapConfig defaultConfig() {
        MapConfig mapConfig = new MapConfig();
        mapConfig.setBackupCount(cacheProperties.getBackupCount());
        mapConfig.setEvictionPolicy(cacheProperties.getEvictionPolicy());
        mapConfig.setMaxSizeConfig(new MaxSizeConfig(0, MaxSizeConfig.MaxSizePolicy.USED_HEAP_SIZE));
        mapConfig.setEvictionPercentage(cacheProperties.getEvictionPercentage());
        return mapConfig;
    }

    private MapConfig domainConfig() {
        MapConfig mapConfig = new MapConfig();
        mapConfig.setTimeToLiveSeconds(cacheProperties.getTimeToLive());
        return mapConfig;
    }

}