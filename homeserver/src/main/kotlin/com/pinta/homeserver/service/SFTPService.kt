package com.pinta.homeserver.service

import org.apache.sshd.client.SshClient
import org.apache.sshd.client.session.ClientSession
import org.apache.sshd.sftp.client.SftpClient
import org.apache.sshd.sftp.client.SftpClientFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

@Service
class SFTPService(
    private val sshClient: SshClient,
    @param:Value("\${pinta.home.ssh.host}") private val sshHost: String,
    @param:Value("\${pinta.home.ssh.port}") private val sshPort: Int,
    @param:Value("\${pinta.home.ssh.username}") private val sshUsername: String,
    @param:Value("\${pinta.home.ssh.password}") private val sshPassword: String,
) {

    companion object {
        const val SFTP_CONN_TIMEOUT = 5000L
        const val SFTP_AUTH_TIMEOUT = 5000L
    }

    fun retrieveFile(path: String, fileName: String): File {
        createSFTPClient().use { client ->
            val targetFile = File(fileName)

            Files.copy(
                client.read(path),
                targetFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING
            )

            return targetFile
        }
    }

    private fun defaultConnect(): ClientSession {
        if (!this.sshClient.isStarted) {
            sshClient.start()
        }

        val sess = sshClient
            .connect(sshUsername, sshHost, sshPort)
            .verify(SFTP_CONN_TIMEOUT)
            .session;

        sess.addPasswordIdentity(sshPassword);

        sess.auth().verify(SFTP_AUTH_TIMEOUT);

        return sess;
    }

    private fun createSFTPClient(session: ClientSession): SftpClient {
        return SftpClientFactory.instance().createSftpClient(session)
    }

    private fun createSFTPClient(): SftpClient {
        return this.createSFTPClient(defaultConnect())
    }
}
