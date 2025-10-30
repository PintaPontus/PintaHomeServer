package com.pinta.homeserver.controller

import com.pinta.homeserver.service.SFTPService
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/files")
class FileController(
    private val sftpService: SFTPService
) {
    @GetMapping("/retrieve", produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
    fun getFileByPath(
        @RequestParam path: String,
        @RequestParam fileName: String,
    ): Resource {
        val finalFile = sftpService.retrieveFile(path, fileName)
        finalFile.deleteOnExit()
        return FileSystemResource(finalFile)
    }
}