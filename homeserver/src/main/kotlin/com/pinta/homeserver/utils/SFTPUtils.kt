package com.pinta.homeserver.utils

import org.apache.sshd.client.SshClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SFTPUtils {

    @Bean
    fun getSSHClient(): SshClient {
        return SshClient.setUpDefaultClient()
    }

}