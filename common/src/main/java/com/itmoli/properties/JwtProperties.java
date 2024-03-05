package com.itmoli.properties;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "tumo.jwt")
public class JwtProperties {

    private String adminSecretKey;
    private long adminTtl;
    private String adminTokenName;
}
